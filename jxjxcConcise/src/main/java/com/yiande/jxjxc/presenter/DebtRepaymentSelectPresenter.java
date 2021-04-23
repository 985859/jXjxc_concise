package com.yiande.jxjxc.presenter;

import android.app.Dialog;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.mylibrary.api.managelayout.TopLinearLayoutManager;
import com.mylibrary.api.utils.DateUtils;
import com.mylibrary.api.utils.ToastUtil;
import com.retrofithttp.api.RetrofitHttp;
import com.retrofithttp.api.util.RxThreadUtil;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.adapter.DebtAdapter;
import com.yiande.jxjxc.base.BasePresenter;
import com.yiande.jxjxc.base.HttpObserver;
import com.yiande.jxjxc.bean.DebtBean;
import com.yiande.jxjxc.bean.DebtListBean;
import com.yiande.jxjxc.bean.JsonBean;
import com.yiande.jxjxc.databinding.ActivityDebtRepaymentSelectBinding;
import com.yiande.jxjxc.myInterface.EventConsume;
import com.yiande.jxjxc.myInterface.HttpApi;
import com.yiande.jxjxc.myInterface.TypeEnum;
import com.yiande.jxjxc.utils.Util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/18 10:11
 */
public class DebtRepaymentSelectPresenter extends BasePresenter<ActivityDebtRepaymentSelectBinding> {

    private int type;//1欠款 2 赊账
    private int ID;


    private int state = 0;
    private int page = 1;


    private DebtAdapter debtAdapter;
    TimePickerView pvTime;
    Calendar selectedDate;
    Calendar startCalendar;
    Calendar endCalendar;
    String dateFormat = "yyyy-MM-dd";
    private Date startData;
    private Date endData;
    private String beginTIme;
    private String endTime;

    public DebtRepaymentSelectPresenter(RxAppCompatActivity mContext, ActivityDebtRepaymentSelectBinding binding) {
        super(mContext, binding);
        Intent intent = getIntent();
        if (intent != null) {
            type = intent.getIntExtra("type", 1);
            ID = intent.getIntExtra("id", 0);

        }

        selectedDate = Calendar.getInstance();//系统当前时间
        startCalendar = Calendar.getInstance();
        startCalendar.set(DateUtils.getYear() - 10, 0, 1, 8, 0, 0);
        endCalendar = Calendar.getInstance();
        String endTime = DateUtils.getTime(dateFormat);
        endData = DateUtils.formarStringToDate(endTime, dateFormat);
        mBinding.setOnClick(onClik);
        mBinding.setType(type);
        debtAdapter.setType(type);

        mBinding.repaymentSelectRefresh.autoRefresh();

    }

    @Override
    protected void initData() {
        super.initData();
        debtAdapter = new DebtAdapter();
        debtAdapter.setShowCheck(true);
        mBinding.repaymentSelectRec.setAdapter(debtAdapter);
        mBinding.repaymentSelectRec.setLayoutManager(new TopLinearLayoutManager(mContext));
    }

    @Override
    protected void setListener() {
        super.setListener();

        mBinding.repaymentSelectRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
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

        debtAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                debtAdapter.setSelectIndex(position);

            }
        });


        mBinding.repaymentSelectBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (debtAdapter.getSelcedIDSize() == 0) {
                        ToastUtil.showShort( "请选择点击选择要还款的选项");
                    return;
                }
                Intent intent = new Intent();
                List<DebtListBean> beanList = new ArrayList<>();
                for (int i = 0; i < debtAdapter.getSelcedIds().size(); i++) {
                    DebtListBean bean = debtAdapter.getItem(debtAdapter.getSelcedIds().get(i));
                    if (bean != null) {
                        beanList.add(bean);
                    }
                }
                intent.putExtra("data", (Serializable) beanList);
                mContext.setResult(TypeEnum.REFRESH, intent);
                mContext.finish();
            }
        });
    }

    private EventConsume<Integer> onClik = (type) -> {
        switch (type) {
            case 0://显示时间筛选
                if (mBinding.repaymentSelectTimeLayout.getVisibility() != View.VISIBLE) {
                    mBinding.repaymentSelectTimeLayout.setVisibility(View.VISIBLE);
                }
                break;
            case 1://选择开始时间
                startCalendar();
                break;
            case 2://选择结束时间
                endCalendar();
                break;
            case 3: //隐藏时间筛选
                beginTIme = null;
                endTime = null;
                mBinding.repaymentSelectBeginTime.setText(mContext.getString(R.string.begin_time));
                mBinding.repaymentSelectEndTime.setText(mContext.getString(R.string.end_time));
                onRefreshData();
                break;
            default:
                break;
        }
    };

    private void startCalendar() {
        initTimePicker("开始时间", 1);
        pvTime.show();
    }


    private void endCalendar() {
        initTimePicker("结束时间", 2);
        pvTime.show();
    }

    private void initTimePicker(String title, final int type) {//Dialog 模式下，在底部弹出
        if (type == 1) {
            if (startData != null) {
                selectedDate.clear();
                selectedDate.setTime(startData);
            }
        } else {
            selectedDate.clear();
            selectedDate.setTime(endData);
        }
        pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (date != null) {
                    int i;
                    switch (type) {
                        case 1:
                            //大于、等于、小于分别返回1、0、-1
                            i = DateUtils.compareData(date, endData);
                            if (i == 1) {
                                    ToastUtil.showShort( "开始时间不能大于结束时间");
                            } else {
                                beginTIme = DateUtils.formarDateToString(date, dateFormat);
                                mBinding.repaymentSelectBeginTime.setText(beginTIme);
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
                                mBinding.repaymentSelectEndTime.setText(endTime);
                                endData = date;
                                onRefreshData();
                            }
                            break;
                        case 3:
                            break;
                    }
                }
            }


        }).setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true)
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText(title)//标题
                .setSubCalSize(13)//确定和取消文字大小
                .setTitleSize(14)//标题文字大小
                .setDate(selectedDate)
                .setRangDate(startCalendar, endCalendar)//起始终止年月日设定
                .setTitleColor(mContext.getResources().getColor(R.color.white))//标题文字颜色
                .setSubmitColor(mContext.getResources().getColor(R.color.white))//确定按钮文字颜色
                .setCancelColor(mContext.getResources().getColor(R.color.white))//取消按钮文字颜色
                .setTitleBgColor(mContext.getResources().getColor(R.color.blue))//标题背景颜色 Night mode
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);
            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }
    }

    private void onRefreshData() {
        page = 1;
        getData();

    }


    private void getData() {
        RetrofitHttp.getRequest(HttpApi.class)
                .getUserDebtList(page, ID, state, beginTIme, endTime)
                .compose(RxThreadUtil.observableToMain())
                .compose(mContext.bindToLifecycle())
                .subscribe(new HttpObserver<JsonBean<DebtBean>>(mContext) {
                    @Override
                    public void onSuccess(JsonBean<DebtBean> bean) {
                        JsonBean<List<DebtListBean>> jsonBean = new JsonBean<>();
                        jsonBean.setCode(bean.code);
                        jsonBean.setIsmsg(bean.ismsg);
                        jsonBean.setMsg(bean.msg);
                        jsonBean.setCount(bean.count);
                        if (bean.data != null)
                            jsonBean.data = bean.data.getDebtRows();
                        Util.setRceclerData(jsonBean, page, mBinding.repaymentSelectRefresh, mBinding.repaymentSelectRec, debtAdapter);
                    }
                });
    }
}
