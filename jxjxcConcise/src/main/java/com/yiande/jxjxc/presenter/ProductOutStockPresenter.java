package com.yiande.jxjxc.presenter;

import android.app.Dialog;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.mylibrary.api.dialog.MyDialog;
import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.utils.ToastUtil;
import com.retrofithttp.api.RetrofitHttp;
import com.retrofithttp.api.util.RxThreadUtil;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.activity.ProductListActivity;
import com.yiande.jxjxc.adapter.OrderProAdapter;
import com.yiande.jxjxc.base.BasePresenter;
import com.yiande.jxjxc.base.HttpObserver;
import com.yiande.jxjxc.bean.AdminListBean;
import com.yiande.jxjxc.bean.JsonBean;
import com.yiande.jxjxc.bean.KeyBean;
import com.yiande.jxjxc.bean.OrderBody;
import com.yiande.jxjxc.bean.OrderProAddBean;
import com.yiande.jxjxc.bean.SellUserInfoBean;
import com.yiande.jxjxc.databinding.ActivityProductOutStockBinding;
import com.yiande.jxjxc.myInterface.EventConsume;
import com.yiande.jxjxc.myInterface.EventSendData;
import com.yiande.jxjxc.myInterface.HttpApi;
import com.yiande.jxjxc.myInterface.TypeEnum;
import com.yiande.jxjxc.popwindow.ItemPop;
import com.yiande.jxjxc.popwindow.ProductSelectPop;
import com.yiande.jxjxc.popwindow.WheelSelectPop;
import com.yiande.jxjxc.utils.DialogUtils;
import com.yiande.jxjxc.utils.Util;

import java.util.List;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/9/21 10:59
 */
public class ProductOutStockPresenter extends BasePresenter<ActivityProductOutStockBinding> {

    private OrderProAdapter proAdapter;

    private WheelSelectPop<AdminListBean> sellAMselectPop;
    private OrderBody orderBody;
    private ProductSelectPop ProductSelectPop;

    private ItemPop itemPop;

    boolean isRemain;//余额是否能被扣除
    private int supID;
    private int level;
    private EventConsume<Integer> onClick = (type) -> {
        switch (type) {
            case 0://选择出库产品
                ProductListActivity.start(mContext, 2, supID, level);
                break;

            case 1://提交出库单
                postData();
                break;

            case 3://选择销售顾问
                getSellAMselectPop().showPopupWindow(mBinding.stockOutTop, Gravity.BOTTOM);
                break;
            default:
                break;
        }
    };

    public ProductOutStockPresenter(RxAppCompatActivity mContext, ActivityProductOutStockBinding binding) {
        super(mContext, binding);
        mBinding.setOnClick(onClick);
        orderBody = new OrderBody();
        orderBody.setOrder_Type(3);
        if (Util.isShowEntry(Util.DRAWABILL)) {
            KeyBean keyBean = Util.getKeyBean(Util.DRAWABILL);
            if (keyBean.getValue() != null && keyBean.getValue().getDefValue() == 1) {
                mBinding.setState(0);
                orderBody.setOrder_Invoice_State(1);
            } else {
                mBinding.setState(1);
                orderBody.setOrder_Invoice_State(0);
            }
        } else {
            mBinding.setState(1);
            orderBody.setOrder_Invoice_State(0);

        }
        mBinding.setData(orderBody);
        Intent intent = getIntent();
        supID = intent.getIntExtra("supID", 0);
        level = intent.getIntExtra("level", 0);
        orderBody.setOrder_User_ID(supID);
        initDebt();
        getBuyOrSellUserInfo();
        getAdmin();
        mBinding.stockOutHandelerName.setText(com.yiande.jxjxc.App.adminName);
    }


    private void initDebt() {
        mBinding.setShowDebt(Util.isShowEntry(Util.DEBT));
        mBinding.setShowYuE(Util.isShowEntry(Util.YUE));
        mBinding.setShoWkg(Util.isShowEntry(Util.KG));
        if (!Util.isShowEntry(Util.YUE)) {
            mBinding.stockOutDeductRemain.setText("");
        }
    }

    @Override
    protected void initData() {
        super.initData();


        proAdapter = new OrderProAdapter();
        proAdapter.setOrderType(1);
        proAdapter.setIsKg(Util.isShowEntry(Util.KG));
        mBinding.stockOutDeductRemain.setSubTitleColor(mContext.getResources().getColor(R.color.red));
        mBinding.stockOutProductRec.setAdapter(proAdapter);
        mBinding.stockOutProductRec.setLayoutManager(new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

    }

    @Override
    protected void setListener() {
        super.setListener();
        mBinding.stockOutInvoiceState.setOnselectListener(new EventConsume<Integer>() {
            @Override
            public void accept(Integer integer) {
                if (integer == 0) {
                    orderBody.setOrder_Invoice_State(1);
                } else {
                    orderBody.setOrder_Invoice_State(0);
                }
            }
        });
        mBinding.stockOutState.setOnselectListener(new EventConsume<Integer>() {
            @Override
            public void accept(Integer type) {
                //Order_Type 1进货 2报溢 3出库 4报损
                switch (type) {
                    case 0:
                        orderBody.setOrder_Type(3);
                        break;
                    case 1:
                        orderBody.setOrder_Type(4);
                        break;
                    default:
                        break;
                }
            }
        });
        proAdapter.addChildClickViewIds(R.id.itmOrderPro_More);
        proAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                selectIndex = position;
                switch (view.getId()) {
                    case R.id.itmOrderPro_More:
                        getItemPop().setTitle(proAdapter.getData().get(position).getOrderDetail_Product_Title());
                        getItemPop().showPopupWindow(mBinding.stockOutTop, Gravity.BOTTOM);
                        break;
                    default:
                        break;
                }
            }
        });

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                allAmount();
            }
        };
        mBinding.stockOutCash.getEditText().addTextChangedListener(watcher);
        mBinding.stockOutBank.getEditText().addTextChangedListener(watcher);
        mBinding.stockOutAlipay.getEditText().addTextChangedListener(watcher);
        mBinding.stockOutWechat.getEditText().addTextChangedListener(watcher);
        mBinding.stockOutDeductRemain.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                double d = StringUtil.toDouble(s.toString());
                double d1 = StringUtil.toDouble(mBinding.stockOutRemain.getText().trim());
                if (d > d1) {
                    isRemain = true;
                    mBinding.stockOutDeductRemain.setSubTitle("(余额不足)");
                } else {
                    isRemain = false;
                    mBinding.stockOutDeductRemain.setSubTitle("");
                }
                allAmount();
            }
        });
        mBinding.stockOutPayableAmount.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                debtAmoubt();
            }
        });
    }


    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/12/10
     * @Description 合计金额
     */
    private void amount() {
        if (Util.isListEmpty(proAdapter.getData())) {
            mBinding.stockOutAmount.setText("0.00");
            mBinding.stockOutKgAmount.setText(Util.setKg("0"));

            return;
        }
        double amount = 0.00;
        double kgAmount = 0.000;
        for (OrderProAddBean bean : proAdapter.getData()) {
            amount += StringUtil.toDouble(bean.getOrderDetail_Amount());
            kgAmount += StringUtil.toDouble(bean.getOrderDetail_KgAmount());
        }
        mBinding.stockOutAmount.setText(String.valueOf(amount));
        mBinding.stockOutKgAmount.setText(String.valueOf(kgAmount));
    }


    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/12/10
     * @Description 实际收款金额
     */
    private void allAmount() {
        double d = getPayalbeAmount();
        mBinding.stockOutAllAmount.setText(StringUtil.doubleToString(d, "0.##"));
        debtAmoubt();
    }


    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/12/10
     * @Description 欠款金额
     */
    private void debtAmoubt() {
        double pay = StringUtil.toDouble(mBinding.stockOutPayableAmount.getText());
        double debt = pay - getPayalbeAmount();
        if (debt < 0) {
            debt = 0;
        }
        mBinding.stockOutDebt.setText(StringUtil.doubleToString(debt, "0.##"));
    }

    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/12/8
     * @Description 获取实际支付金额之和
     */
    private double getPayalbeAmount() {
        double amount = 0.00;
        double cashAmount = StringUtil.toDouble(mBinding.stockOutCash.getText());
        double bankAmount = StringUtil.toDouble(mBinding.stockOutBank.getText());
        double alipayAmount = StringUtil.toDouble(mBinding.stockOutAlipay.getText());
        double wechatAmount = StringUtil.toDouble(mBinding.stockOutWechat.getText());
        double remainAmount = StringUtil.toDouble(mBinding.stockOutDeductRemain.getText());
        amount = cashAmount + bankAmount + alipayAmount + wechatAmount + remainAmount;
        return amount;
    }

    private void getBuyOrSellUserInfo() {
        if (supID == 0) {
            mBinding.setShowDebt(false);
            mBinding.setShowYuE(false);
            mBinding.stockOutDeductRemain.setText("0");
            mBinding.stockOutComName.setText("散客");
            return;
        }
        RetrofitHttp.getRequest(HttpApi.class)
                .getBuyOrSellUserInfo(supID)
                .compose(RxThreadUtil.observableToMain())
                .compose(mContext.bindToLifecycle())
                .subscribe(new HttpObserver<JsonBean<SellUserInfoBean>>(mContext) {
                    @Override
                    public void onSuccess(JsonBean<SellUserInfoBean> bean) {
                        if (bean.code == 1) {
                            mBinding.stockOutRemain.setText(bean.data.getBalance());
                            mBinding.stockOutComName.setText(bean.data.getComName());
                            mBinding.stockOutName.setText(bean.data.getName());
                            mBinding.stockOutMob.setText(bean.data.getMob());
                            mBinding.stockOutAddress.setText(bean.data.getAddress());
                            mBinding.stockOutAllDebt.setText(bean.data.getDebt());
                        }

                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {

            case TypeEnum.REFRESH:
                if (data != null) {
                    List<OrderProAddBean> addBeanList = (List<OrderProAddBean>) data.getSerializableExtra("listData");
                    setProData(addBeanList);
                }

                break;
            default:
                break;
        }

    }

    EventConsume<Integer> itemClickListener = new EventConsume<Integer>() {
        @Override
        public void accept(Integer integer) {
            switch (integer) {
                case 0:
                    getProductSelectPop().setProAddBean(proAdapter.getData().get(selectIndex));
                    getProductSelectPop().showPopupWindow(mBinding.stockOutTop, Gravity.BOTTOM);

                    break;
                case 1:
                    DialogUtils.showDialog(mContext, "是否删除  " + proAdapter.getItem(selectIndex).getOrderDetail_Product_Title(), new MyDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            if (confirm) {
                                proAdapter.removeAt(selectIndex);
                                showProRec();
                                amount();
                            }
                        }
                    });
                    break;
                default:
                    break;
            }
        }
    };

    private ItemPop getItemPop() {
        if (itemPop == null) {
            itemPop = new ItemPop(mContext);
            itemPop.setOnItemClickListener(itemClickListener);
        }
        return itemPop;
    }

    private EventSendData<AdminListBean, Boolean> sellAMListener = (userBean, aBoolean) -> {
        if (userBean != null) {
            orderBody.setOrder_Admin_ID(userBean.getAdmin_ID());
            orderBody.setOrder_Admin_Name(userBean.getAdmin_Name());
        } else {
            orderBody.setOrder_Admin_ID(0);
            orderBody.setOrder_Admin_Name("");
        }

    };

    private WheelSelectPop getSellAMselectPop() {
        if (sellAMselectPop == null) {
            sellAMselectPop = new WheelSelectPop<>(mContext);
            sellAMselectPop.setEventConsume(sellAMListener);
            sellAMselectPop.setTitle("选择销售顾问");
            sellAMselectPop.setShowTextView(mBinding.stockOutSellAM.getTextView());
        }
        return sellAMselectPop;
    }

    private void getAdmin() {
        RetrofitHttp.getRequest(HttpApi.class)
                .getAdmins()
                .compose(RxThreadUtil.observableToMain())
                .compose(mContext.bindToLifecycle())
                .subscribe(new HttpObserver<JsonBean<List<AdminListBean>>>(mContext) {
                    @Override
                    public void onSuccess(JsonBean<List<AdminListBean>> bean) {
                        if (bean.code == 1) {
                            getSellAMselectPop().setData(bean.data);
                        }

                    }
                });
    }


    private void setProData(List<OrderProAddBean> data) {
        if (data == null || data.size() == 0) {
            return;
        }
        if (Util.isNotListEmpty(proAdapter.getData())) {

            for (int i = 0; i < data.size(); i++) {
                OrderProAddBean bean = data.get(i);
                boolean isAdd = true;
                for (int k = 0; k < proAdapter.getData().size(); k++) {
                    OrderProAddBean bean1 = proAdapter.getData().get(k);
                    if (bean.getProduct_ID() == bean1.getProduct_ID()) {
                        proAdapter.setData(k, bean);
                        isAdd = false;
                        break;
                    }
                }
                if (isAdd) {
                    proAdapter.addData(bean);
                }
            }
            amount();
        } else {
            proAdapter.addData(data);
            amount();
        }
        showProRec();
    }


    private void showProRec() {
        if (Util.isNotListEmpty(proAdapter.getData())) {
            mBinding.setShowRec(true);
        } else {
            mBinding.setShowRec(false);
        }

    }


    private int selectIndex = 0;
    private EventConsume<OrderProAddBean> proAddBeanEventConsume = (bean) -> {
        proAdapter.setData(selectIndex, bean);
        amount();
    };

    private ProductSelectPop getProductSelectPop() {
        if (ProductSelectPop == null) {
            ProductSelectPop = new ProductSelectPop(mContext, 1);
            ProductSelectPop.setProAddBeanEventConsume(proAddBeanEventConsume);
        }
        return ProductSelectPop;
    }


    private EventSendData<AdminListBean, Boolean> eventConsume = (bean, isReset) -> {
        if (isReset) {
            orderBody.setOrder_Worker_ID(0);
        } else {
            if (bean != null) {
                orderBody.setOrder_Worker_ID(bean.getAdmin_ID());
            }

        }

    };


    private void postData() {
        if (Util.isListEmpty(proAdapter.getData())) {
            ToastUtil.showShort( "请选择出库的产品");
            return;
        }
        double d = StringUtil.toDouble(mBinding.stockOutPayableAmount.getText().trim());
        double d1 = StringUtil.toDouble(mBinding.stockOutAllAmount.getText().trim());
        if (d1 > d) {
            DialogUtils.showDialog(mContext, "实际收款金额大于应收金额，无法提交");
        } else if (d1 == d) {
            addStockOut();
        } else {
            DialogUtils.showDialog(mContext, "实际收款金额小于应收金额，是否提交", new MyDialog.OnCloseListener() {
                @Override
                public void onClick(Dialog dialog, boolean confirm) {
                    if (confirm) {
                        addStockOut();
                    }
                }
            });
        }

    }

    private void addStockOut() {
        if (orderBody != null) {
            orderBody.setOrder_Detail(proAdapter.getData());
        }
        mBinding.stockOutBT.setEnabled(false);
        HttpObserver observer = new HttpObserver<JsonBean<Object>>(mContext) {
            @Override
            public void onSuccess(JsonBean<Object> bean) {
                if (bean.code == 1) {
                    mContext.setResult(TypeEnum.REFRESH);
                }
            }

            @Override
            public void msgOnClick(boolean click, int type) {
                super.msgOnClick(click, type);
                if (type == 6) {
                    Util.getAppFucntion(mContext, (code) -> {
                        if (code == 1) {
                            initDebt();
                        }
                    });
                } else {
                    mContext.finish();
                }

            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                super.onError(e);
            }

            @Override
            public void onFinsh() {
                super.onFinsh();
                mBinding.stockOutBT.setEnabled(true);
            }
        };
        RetrofitHttp.getRequest(HttpApi.class)
                .srderSellAdd(orderBody)
                .compose(RxThreadUtil.observableToMain())
                .compose(mContext.bindToLifecycle())
                .subscribe(observer);
    }

}
