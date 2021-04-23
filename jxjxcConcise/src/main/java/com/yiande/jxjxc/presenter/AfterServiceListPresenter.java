package com.yiande.jxjxc.presenter;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.mylibrary.api.managelayout.TopLinearLayoutManager;
import com.mylibrary.api.utils.DateUtils;
import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.utils.ToastUtil;
import com.retrofithttp.api.RetrofitHttp;
import com.retrofithttp.api.util.RxThreadUtil;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.activity.AZListActivity;
import com.yiande.jxjxc.activity.AfterServiceDetailActivity;
import com.yiande.jxjxc.activity.SelectActivity;
import com.yiande.jxjxc.adapter.AfterServiceAdapter;
import com.yiande.jxjxc.adapter.SpinnerAdapter;
import com.yiande.jxjxc.base.BasePresenter;
import com.yiande.jxjxc.base.HttpObserver;
import com.yiande.jxjxc.bean.AfterServiceBean;
import com.yiande.jxjxc.bean.AfterServiceRowsBean;
import com.yiande.jxjxc.bean.JsonBean;
import com.yiande.jxjxc.bean.KeyValueBean;
import com.yiande.jxjxc.databinding.ActivityAfterServiceListBinding;
import com.yiande.jxjxc.myInterface.EventConsume;
import com.yiande.jxjxc.myInterface.HttpApi;
import com.yiande.jxjxc.myInterface.TypeEnum;
import com.yiande.jxjxc.utils.Util;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/9/19 10:04
 */
public class AfterServiceListPresenter extends BasePresenter<ActivityAfterServiceListBinding> {
    private int page = 1;
    private int uid = 0;//客户ID
    private int aid = 0;//经手人ID
    private String keywords = "";
    private AfterServiceAdapter serviceAdapter;
    private SpinnerAdapter<KeyValueBean> adminAdapter;
    private SpinnerAdapter<KeyValueBean> userAdapter;
    private String beginTIme;
    private String endTime;
    private TimePickerView pvTime;
    private Calendar selectedDate;
    private Calendar startCalendar;
    private Calendar endCalendar;
    private String dateFormat = "yyyy-MM-dd";
    private Date startData;
    private Date endData;
    private int timeType;

    public AfterServiceListPresenter(RxAppCompatActivity mContext, ActivityAfterServiceListBinding binding) {
        super(mContext, binding);
        Intent intent = getIntent();
        if (intent != null) {
            keywords = intent.getStringExtra("keywords");
            if (StringUtil.isNotEmpty(keywords)) {
                mBinding.afterServiceListTop.setTitle(keywords);
                mBinding.afterServiceListTop.setRightText("");
            }
        }

        mBinding.setOnClick(onClik);
        selectedDate = Calendar.getInstance();//系统当前时间
        startCalendar = Calendar.getInstance();
        startCalendar.set(DateUtils.getYear() - 10, 0, 1, 8, 0, 0);
        endCalendar = Calendar.getInstance();
        String endTime = DateUtils.getTime(dateFormat);
        endData = DateUtils.formarStringToDate(endTime, dateFormat);
        mBinding.afterServiceListRefresh.autoRefresh();
    }


    @Override
    protected void initData() {
        super.initData();
        serviceAdapter = new AfterServiceAdapter();
        mBinding.afterServiceListRec.setAdapter(serviceAdapter);
        mBinding.afterServiceListRec.setLayoutManager(new TopLinearLayoutManager(mContext));

        adminAdapter = new SpinnerAdapter(null);
        mBinding.afterServiceListHandlerName.setAdapter(adminAdapter);
        adminAdapter.setEmptyView(Util.getEmptyView(mContext));
        userAdapter = new SpinnerAdapter(null);
        mBinding.afterServiceListState.setAdapter(userAdapter);
        userAdapter.setEmptyView(Util.getEmptyView(mContext));
    }

    @Override
    protected void setListener() {
        super.setListener();
        mBinding.afterServiceListTop.setMySearchViewListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectActivity.start(mContext, 6, 0, mBinding.afterServiceListTop.getSelectHint());
            }
        });
        mBinding.afterServiceListTop.setRightTextListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AZListActivity.start(mContext, 1, 5);
            }
        });


        mBinding.afterServiceListState.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                userAdapter.setSelectIndex(position);
                uid = userAdapter.getItem(position).getKey();
                onRefreshData();
            }
        });
        mBinding.afterServiceListHandlerName.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

                adminAdapter.setSelectIndex(position);
                aid = adminAdapter.getItem(position).getKey();
                onRefreshData();
            }
        });
        mBinding.afterServiceListRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page += 1;
                getData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                onRefreshData();

            }
        });

        serviceAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

                AfterServiceDetailActivity.start(mContext, serviceAdapter.getItem(position).getID());
            }
        });


    }


    private EventConsume<Integer> onClik = (type) -> {

        switch (type) {

            case 0://显示时间筛选
                if (mBinding.afterServiceListTimeLayout.getVisibility() != View.VISIBLE) {
                    mBinding.afterServiceListTimeLayout.setVisibility(View.VISIBLE);
                }
                break;
            case 1://选择开始时间
                startCalendar();
                break;
            case 2://选择结束时间
                endCalendar();
                break;
            case 3: //隐藏时间筛选
                mBinding.afterServiceListTimeLayout.setVisibility(View.GONE);
                beginTIme = null;
                endTime = null;
                mBinding.afterServiceListBeginTime.setText(mContext.getString(R.string.begin_time));
                mBinding.afterServiceListEndTime.setText(mContext.getString(R.string.end_time));
                onRefreshData();
                break;
            default:
                break;
        }
    };


    private void onRefreshData() {
        page = 1;
        getData();

    }

    private void getData() {
        RetrofitHttp.getRequest(HttpApi.class)
                .getServiceList(page, uid, aid, beginTIme, endTime, keywords)
                .compose(RxThreadUtil.observableToMain())
                .compose(mContext.bindToLifecycle())
                .subscribe(new HttpObserver<JsonBean<AfterServiceBean>>(mContext) {
                    @Override
                    public void onSuccess(JsonBean<AfterServiceBean> bean) {
                        if (bean.code == 1 && page == 1) {
                            if (bean.data != null) {
                                bean.data.getSelectUser().add(0, new KeyValueBean(0, "全部"));
                                userAdapter.setList(bean.data.getSelectUser());
                                bean.data.getSelectStaff().add(0, new KeyValueBean(0, "全部"));
                                adminAdapter.setList(bean.data.getSelectStaff());
                            }
                        }

                        JsonBean<List<AfterServiceRowsBean>> jsonBean = new JsonBean<>();
                        if (bean.data != null) {
                            jsonBean.data = bean.data.getServiceRows();
                        }
                        jsonBean.ismsg = bean.ismsg;
                        jsonBean.code = bean.code;
                        jsonBean.count = bean.count;
                        jsonBean.msg = bean.msg;
                        Util.setRceclerData(jsonBean, page, mBinding.afterServiceListRefresh, mBinding.afterServiceListRec, serviceAdapter);
                    }
                });
    }


    void startCalendar() {
        timeType = 1;
        initTimePicker("开始时间");
        pvTime.show();
    }


    void endCalendar() {
        timeType = 2;
        initTimePicker("结束时间");
        pvTime.show();
    }

    private void initTimePicker(String title) {//Dialog 模式下，在底部弹出
        if (timeType == 1) {
            if (startData != null) {
                selectedDate.clear();
                selectedDate.setTime(startData);
            }
        } else {
            selectedDate.clear();
            selectedDate.setTime(endData);
        }
        if (pvTime == null) {
            pvTime = Util.getTimePickerView(mContext, startCalendar, endCalendar, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    if (date != null) {
                        int i;
                        switch (timeType) {
                            case 1:
                                //大于、等于、小于分别返回1、0、-1
                                i = DateUtils.compareData(date, endData);
                                if (i == 1) {
                                         ToastUtil.showShort( "开始时间不能大于结束时间");
                                } else {
                                    beginTIme = DateUtils.formarDateToString(date, dateFormat);
                                    mBinding.afterServiceListBeginTime.setText(beginTIme);
                                    startData = date;
                                    onRefreshData();
                                }
                                break;
                            case 2:
                                //大于、等于、小于分别返回1、0、-1
                                i = DateUtils.compareData(date, startData);
                                if (i == -1) {
                                         ToastUtil.showShort( "结束时间不能小于开始时间");
                                } else {
                                    endTime = DateUtils.formarDateToString(date, dateFormat);
                                    mBinding.afterServiceListEndTime.setText(endTime);
                                    endData = date;
                                    onRefreshData();
                                }
                                break;
                            case 3:
                                break;
                        }
                    }
                }
            });
        }
        pvTime.setDate(selectedDate);
        pvTime.setTitleText(title);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case TypeEnum.REFRESH:

                onRefreshData();
                break;
            default:
                break;
        }
    }
}
