package com.yiande.jxjxc.presenter;

import android.app.Dialog;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
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
import com.yiande.jxjxc.activity.InvoiceRecordActivity;
import com.yiande.jxjxc.activity.ProductInStockDetailActivity;
import com.yiande.jxjxc.activity.ProductOutStockDetailActivity;
import com.yiande.jxjxc.activity.SelectActivity;
import com.yiande.jxjxc.adapter.SpinnerAdapter;
import com.yiande.jxjxc.adapter.StockOrderAdapter;
import com.yiande.jxjxc.base.BasePresenter;
import com.yiande.jxjxc.base.HttpObserver;
import com.yiande.jxjxc.bean.AdminListBean;
import com.yiande.jxjxc.bean.JsonBean;
import com.yiande.jxjxc.bean.OrderListBean;
import com.yiande.jxjxc.bean.RoleBean;
import com.yiande.jxjxc.bean.StockOrderBean;
import com.yiande.jxjxc.databinding.ActivityStockOrderListBinding;
import com.yiande.jxjxc.myInterface.EventConsume;
import com.yiande.jxjxc.myInterface.HttpApi;
import com.yiande.jxjxc.myInterface.TypeEnum;
import com.yiande.jxjxc.utils.Util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Description: ???????????????
 * @Author: hukui
 * @Date: 2020/9/19 10:04
 */
public class StockOrderListPresenter extends BasePresenter<ActivityStockOrderListBinding> {
    private StockOrderAdapter orderAdapter;
    private int type;//1 ?????? 2 ??????
    private int orderType = 0;//1 ?????? 2 ??????
    private int page = 1;
    private int aID = 0;
    private int searchType = 0;//0:?????? 1:?????? 2:?????? 3:????????? 4 ?????????


    private String keywords;
    private String beginTIme;
    private String endTime;
    private SpinnerAdapter<AdminListBean> adminAdapter;
    private SpinnerAdapter<RoleBean> typeAdapter;
    private SpinnerAdapter<RoleBean> stateAdapter;
    TimePickerView pvTime;
    Calendar selectedDate;
    Calendar startCalendar;
    Calendar endCalendar;
    String dateFormat = "yyyy-MM-dd";
    private Date startData;
    private Date endData;

    public StockOrderListPresenter(RxAppCompatActivity mContext, ActivityStockOrderListBinding binding) {
        super(mContext, binding);
        Intent intent = getIntent();
        if (intent != null) {
            type = intent.getIntExtra("type", 1);
            keywords = intent.getStringExtra("keywords");
            orderAdapter.setType(type);
            mBinding.setType(type);
            if (StringUtil.isNotEmpty(keywords)) {
                mBinding.stockOrderListTop.setTitle(keywords);
                mBinding.stockOrderListTop.setRightText("");
            } else {
                String hint = type == 1 ? "????????????????????????????????????" : "?????????????????????????????????";
                mBinding.stockOrderListTop.setSelectHint(hint);

            }
        }
        List<RoleBean> roleBeanList = new ArrayList<>();
        //Order_Type 1?????? 2?????? 3?????? 4??????
        if (type == 1) {
            orderType = 1;
            roleBeanList.add(new RoleBean(1, "??????"));
            roleBeanList.add(new RoleBean(2, "??????"));
        } else {
            orderType = 3;
            roleBeanList.add(new RoleBean(3, "??????"));
            roleBeanList.add(new RoleBean(4, "??????"));
        }
        typeAdapter.setList(roleBeanList);


        ////0:?????? 1:?????? 2:?????? 3:?????????/????????? 4:?????????
        List<RoleBean> beanList = new ArrayList<>();
        beanList.add(new RoleBean(0, "??????"));
        beanList.add(new RoleBean(1, "??????"));
        if (type == 2) {
            beanList.add(new RoleBean(5, "?????????"));
        } else {
            beanList.add(new RoleBean(4, "?????????"));
        }
        beanList.add(new RoleBean(2, "?????????"));
        if (Util.getKeyBean(Util.DEBT) == null ? false : true) {
            if (type == 1) {
                beanList.add(new RoleBean(3, "?????????"));
            } else {
                beanList.add(new RoleBean(3, "?????????"));
            }
        }
        if (type == 2)
            beanList.add(new RoleBean(4, "?????????"));

        if (type == 2) {
            beanList.add(new RoleBean(6, "????????????"));
            beanList.add(new RoleBean(7, "?????????"));
        } else {
            beanList.add(new RoleBean(5, "????????????"));
            beanList.add(new RoleBean(6, "?????????"));
        }
        stateAdapter.setList(beanList);

        mBinding.stockOrderListRefresh.autoRefresh();

        mBinding.setOnClick(onClik);
        selectedDate = Calendar.getInstance();//??????????????????
        startCalendar = Calendar.getInstance();
        startCalendar.set(DateUtils.getYear() - 10, 0, 1, 8, 0, 0);
        endCalendar = Calendar.getInstance();
        String endTime = DateUtils.getTime(dateFormat);
        endData = DateUtils.formarStringToDate(endTime, dateFormat);
    }


    private void initDebt() {
        orderAdapter.setShowDebt(Util.isShowEntry(Util.DEBT));
        orderAdapter.setShowYuE(Util.isShowEntry(Util.YUE));
    }

    @Override
    protected void initData() {
        super.initData();
        orderAdapter = new StockOrderAdapter();
        mBinding.stockOrderListRec.setAdapter(orderAdapter);
        mBinding.stockOrderListRec.setLayoutManager(new TopLinearLayoutManager(mContext));

        adminAdapter = new SpinnerAdapter(null);
        mBinding.stockOrderListHandlerName.setAdapter(adminAdapter);
        getAdminData();
        typeAdapter = new SpinnerAdapter(null);
        mBinding.stockOrderListType.setAdapter(typeAdapter);


        stateAdapter = new SpinnerAdapter<>(null);
        mBinding.stockOrderListState.setAdapter(stateAdapter);
    }

    private EventConsume<Integer> onClik = (type) -> {

        switch (type) {

            case 0://??????????????????
                if (mBinding.stockOrderListTimeLayout.getVisibility() != View.VISIBLE) {
                    mBinding.stockOrderListTimeLayout.setVisibility(View.VISIBLE);
                }
                break;
            case 1://??????????????????
                startCalendar();
                break;
            case 2://??????????????????
                endCalendar();
                break;
            case 3: //??????????????????
                mBinding.stockOrderListTimeLayout.setVisibility(View.GONE);
                beginTIme = null;
                endTime = null;
                mBinding.stockOrderListBeginTime.setText(mContext.getString(R.string.begin_time));
                mBinding.stockOrderListEndTime.setText(mContext.getString(R.string.end_time));
                onRefreshData();
                break;
            default:
                break;
        }
    };

    @Override
    protected void setListener() {
        super.setListener();
        mBinding.stockOrderListTop.setMySearchViewListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectActivity.start(mContext, 4, type, mBinding.stockOrderListTop.getSelectHint());
            }
        });
        mBinding.stockOrderListTop.setRightTextListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (type) {
                    case 1:
                        AZListActivity.start(mContext, 2, 2);
                        break;
                    case 2:
                        AZListActivity.start(mContext, 1, 1);
                        break;
                    default:
                        break;
                }
            }
        });
        mBinding.stockOrderListType.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                typeAdapter.setSelectIndex(position);
                orderType = typeAdapter.getItem(position).getId();
                onRefreshData();
            }
        });


        mBinding.stockOrderListState.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                stateAdapter.setSelectIndex(position);
                searchType = stateAdapter.getItem(position).getId();
                onRefreshData();
            }
        });
        mBinding.stockOrderListHandlerName.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                adminAdapter.setSelectIndex(position);
                aID = adminAdapter.getItem(position).getId();
                onRefreshData();
            }
        });
        mBinding.stockOrderListRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
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
        orderAdapter.addChildClickViewIds(R.id.itmStockOrder_BT);
        orderAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {

                switch (view.getId()) {
                    case R.id.itmStockOrder_BT:
                        InvoiceRecordActivity.start(mContext, orderAdapter.getItem(position).getOrder_ID(), type);
                        break;
                    default:
                        break;
                }
            }
        });
        orderAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                switch (type) {
                    case 1:
                        ProductInStockDetailActivity.start(mContext, orderAdapter.getItem(position).getOrder_ID());
                        break;
                    case 2:
                        ProductOutStockDetailActivity.start(mContext, orderAdapter.getItem(position).getOrder_ID());
                        break;
                    default:
                        break;
                }
            }
        });


    }

    private void onRefreshData() {
        page = 1;
        initDebt();
        getData();

    }

    private void getData() {

        HttpObserver observer = new HttpObserver<JsonBean<OrderListBean>>(mContext) {
            @Override
            public void onSuccess(JsonBean<OrderListBean> bean) {
                if (page == 1 && bean.code == 1) {
                    mBinding.setAmountBean(bean.data);
                }
                JsonBean<List<StockOrderBean>> jsonBean = new JsonBean<>();
                jsonBean.setCode(bean.code);
                jsonBean.setIsmsg(bean.ismsg);
                jsonBean.setMsg(bean.msg);
                jsonBean.setCount(bean.count);
                if (bean.data != null)
                    jsonBean.data = bean.data.getOrderList();
                Util.setRceclerData(jsonBean, page, mBinding.stockOrderListRefresh, mBinding.stockOrderListRec, orderAdapter);
            }

            @Override
            public void msgOnClick(boolean click, int isMsg) {
                super.msgOnClick(click, type);

                switch (isMsg) {

                    case 21:
                        mContext.finish();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFinsh() {
                super.onFinsh();
                if (page == 1) {
                    mBinding.stockOrderListRefresh.finishRefresh();

                } else {
                    mBinding.stockOrderListRefresh.finishLoadMore();
                }
            }
        };
        if (type == 1) {//???????????????
            RetrofitHttp.getRequest(HttpApi.class)
                    .getOrderBuyList(page, orderType, aID, beginTIme, endTime, keywords, searchType)
                    .compose(RxThreadUtil.observableToMain())
                    .compose(mContext.bindToLifecycle())
                    .subscribe(observer);
        } else {//???????????????
            RetrofitHttp.getRequest(HttpApi.class)
                    .getOrderSellList(page, orderType, aID, beginTIme, endTime, keywords, searchType)
                    .compose(RxThreadUtil.observableToMain())
                    .compose(mContext.bindToLifecycle())
                    .subscribe(observer);
        }
    }


    private void getAdminData() {
        RetrofitHttp.getRequest(HttpApi.class)
                .getAdmins()
                .compose(RxThreadUtil.observableToMain())
                .compose(mContext.bindToLifecycle())
                .subscribe(new HttpObserver<JsonBean<List<AdminListBean>>>(mContext, false) {
                    @Override
                    public void onSuccess(JsonBean<List<AdminListBean>> bean) {

                        if (bean.code == 1 && Util.isNotListEmpty(bean.data)) {
                            AdminListBean listBean = new AdminListBean();
                            listBean.setAdmin_ID(0);
                            listBean.setAdmin_Name("??????");
                            bean.data.add(0, listBean);
                        }
                        adminAdapter.setList(bean.data);
                    }


                });
    }

    void startCalendar() {
        initTimePicker("????????????", 1);
        pvTime.show();
    }


    void endCalendar() {
        initTimePicker("????????????", 2);
        pvTime.show();
    }


    private void initTimePicker(String title, final int type) {//Dialog ???????????????????????????
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
                            //????????????????????????????????????1???0???-1
                            i = DateUtils.compareData(date, endData);
                            if (i == 1) {
                                ToastUtil.showShort( "????????????????????????????????????");
                            } else {
                                beginTIme = DateUtils.formarDateToString(date, dateFormat);
                                mBinding.stockOrderListBeginTime.setText(beginTIme);
                                startData = date;
                                onRefreshData();
                            }
                            break;
                        case 2:
                            //????????????????????????????????????1???0???-1
                            i = DateUtils.compareData(date, startData);
                            if (i == -1) {
                                ToastUtil.showShort( "????????????????????????????????????");
                            } else {
                                endTime = DateUtils.formarDateToString(date, dateFormat);
                                mBinding.stockOrderListEndTime.setText(endTime);
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
                .setSubmitText("??????")//??????????????????
                .setCancelText("??????")//??????????????????
                .setTitleText(title)//??????
                .setSubCalSize(13)//???????????????????????????
                .setTitleSize(14)//??????????????????
                .setDate(selectedDate)
                .setRangDate(startCalendar, endCalendar)//???????????????????????????
                .setTitleColor(mContext.getResources().getColor(R.color.white))//??????????????????
                .setSubmitColor(mContext.getResources().getColor(R.color.white))//????????????????????????
                .setCancelColor(mContext.getResources().getColor(R.color.white))//????????????????????????
                .setTitleBgColor(mContext.getResources().getColor(R.color.blue))//?????????????????? Night mode
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);
            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//??????????????????
                dialogWindow.setGravity(Gravity.BOTTOM);//??????Bottom,????????????
            }
        }
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
