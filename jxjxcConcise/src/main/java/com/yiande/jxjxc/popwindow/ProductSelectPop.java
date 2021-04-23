package com.yiande.jxjxc.popwindow;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;

import com.mylibrary.api.utils.PointLengthFilter;
import com.mylibrary.api.utils.SpannableUtil;
import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.utils.SystemUtil;
import com.mylibrary.api.utils.ToastUtil;
import com.retrofithttp.api.RetrofitHttp;
import com.retrofithttp.api.util.RxThreadUtil;

import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BasePopupWindow;
import com.yiande.jxjxc.base.HttpObserver;
import com.yiande.jxjxc.bean.JsonBean;
import com.yiande.jxjxc.bean.OrderProAddBean;
import com.yiande.jxjxc.bean.ProductBean;

import com.yiande.jxjxc.databinding.PopProductSelectBinding;
import com.yiande.jxjxc.myInterface.EventConsume;
import com.yiande.jxjxc.myInterface.HttpApi;
import com.yiande.jxjxc.utils.Util;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/9/20 9:04
 */
public class ProductSelectPop extends BasePopupWindow<PopProductSelectBinding> {
    private int type = 1;//1 入库产品  2 出库产品
    private int level = 0;//客户级别
    private int supID = 0;//客户ID

    private OrderProAddBean proAddBean;
    private PointLengthFilter lengthFilter;
    private EventConsume<OrderProAddBean> proAddBeanEventConsume;

    public void setProAddBeanEventConsume(EventConsume<OrderProAddBean> proAddBeanEventConsume) {
        this.proAddBeanEventConsume = proAddBeanEventConsume;
    }

    public ProductSelectPop(RxAppCompatActivity context, int type) {
        super(context);
        setType(type);
        proAddBean = new OrderProAddBean();
    }

    private void setType(int type) {
        this.type = type;
        mBinding.setType(type);
        if (type == 1) {
            setLevel(0);
        }
    }

    public void setSupID(int supID) {
        this.supID = supID;
    }

    public void setData(ProductBean data) {
        mBinding.popProselectNumber.setText("");
        mBinding.popProselectPrice.setText("");
        OrderProAddBean bean = new OrderProAddBean();
        if (data != null) {
            bean.setProduct_ID(data.getProduct_ID());
            bean.setOrderDetail_Product_Title(data.getProduct_Title());
            bean.setOrderDetail_Product_Model(data.getProduct_Model());
            bean.setProduct_IsBuyPrice(data.getProduct_IsBuyPrice());
            bean.setProduct_BuyPrice(data.getProduct_BuyPrice());
            bean.setProduct_SellPrice(Util.getLevelPrice(level, data));
            bean.setProduct_InventoryNum(StringUtil.toInt(data.getProduct_InventoryNum()));
            bean.setOrderDetail_Kg(data.getProduct_Kg());
            setProAddBean(bean);
            if (type == 2) {
                getBuyOrSellUserInfo(data.getProduct_ID());
            }

        }

    }

    public void setProAddBean(OrderProAddBean proAddBean) {
        if (proAddBean != null) {
            this.proAddBean = proAddBean;
            mBinding.popProselectProTitle.setRightText(proAddBean.getOrderDetail_Product_Title());
            mBinding.popProselectProModel.setRightText(proAddBean.getOrderDetail_Product_Model());
            mBinding.popProselectBuyPrice.setRightText(Util.setPrice(proAddBean.getProduct_BuyPrice()));
            mBinding.popProselectSellPrice.setRightText(Util.setPrice(proAddBean.getProduct_SellPrice()));
            mBinding.popProselectInventeryNum.setRightText(String.valueOf(proAddBean.getProduct_InventoryNum()));
            setNextPrice(proAddBean.getProduct_NextPrice());
            if (StringUtil.isNotEmpty(proAddBean.getOrderDetail_Price())) {
                mBinding.popProselectPrice.setText(proAddBean.getOrderDetail_Price());
                mBinding.popProselectPrice.setSelection(proAddBean.getOrderDetail_Price().length());
            }

            if (proAddBean.getOrderDetail_Quantity() > 0) {
                mBinding.popProselectNumber.setText(String.valueOf(proAddBean.getOrderDetail_Quantity()));
            } else {
                mBinding.popProselectNumber.setText("");
            }
            mBinding.popProselectAmount.setText(String.valueOf(proAddBean.getOrderDetail_Amount()));
        }
    }


    public void setLevel(int level) {
        this.level = level;
        int id = Util.getLevelIocn(level);
        if (level == 0) {
            mBinding.popProselectSellPrice.setLeftText("销售价");
        } else {
            mBinding.popProselectSellPrice.setLeftText("售价");
        }
        mBinding.popProselectSellPrice.setLeftIcon(id);
    }


    @Override
    public int getContentViewID() {
        return R.layout.pop_product_select;
    }

    @Override
    public void init(View view) {
        lengthFilter = new PointLengthFilter();
        mBinding.popProselectPrice.setFilters(new InputFilter[]{new PointLengthFilter()});
        mBinding.popProselectNumber.setFilters(new InputFilter[]{lengthFilter});
    }


    @Override
    public void setListener() {
        super.setListener();
        mBinding.popProselectPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                double price = StringUtil.toDouble(s.toString());
                int num = StringUtil.toInt(mBinding.popProselectNumber.getText().toString().trim());
                countPrice(num, price);
                proAddBean.setOrderDetail_Price(String.valueOf(price));
            }
        });
        mBinding.popProselectNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                double price = StringUtil.toDouble(mBinding.popProselectPrice.getText().toString().trim());
                int num = StringUtil.toInt(s.toString());
                countPrice(num, price);
                if (proAddBean != null) {
                    proAddBean.setOrderDetail_Quantity(num);
                    double kg = StringUtil.toDouble(proAddBean.getOrderDetail_Kg());
                    proAddBean.setOrderDetail_KgAmount(StringUtil.doubleToString(num * kg, "0.###"));
                }

            }
        });

        mBinding.popProselectClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mBinding.popProselectBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (StringUtil.isEmpty(proAddBean.getOrderDetail_Price())) {
                    ToastUtil.showShort(R.string.hint_pro_price);
                    return;
                }


                if (proAddBean.getOrderDetail_Quantity() < 1) {
                    ToastUtil.showShort(R.string.hint_pro_number);
                    return;
                }

                if (proAddBeanEventConsume != null) {
                    proAddBeanEventConsume.accept(proAddBean);
                }
                dismiss();
            }
        });
    }


    private void countPrice(int num, double price) {
        double amount = price * num;
        mBinding.popProselectAmount.setText(SpannableUtil.setPrice(amount));
        if (proAddBean != null) {
            proAddBean.setOrderDetail_Amount(String.valueOf(amount));
        }

    }

    @Override
    protected void onDismissListener() {
        super.onDismissListener();
        mBinding.popProselectPrice.setFocusable(false);
        mBinding.popProselectPrice.setFocusableInTouchMode(false);

        mBinding.popProselectNumber.setFocusable(false);
        mBinding.popProselectNumber.setFocusableInTouchMode(false);

        SystemUtil.toggleSoftInput(mBinding.popProselectNumber);
    }

    @Override
    public void onShowListener() {
        super.onShowListener();
        showKeyboard();
    }

    public void showKeyboard() {
        mBinding.popProselectPrice.setFocusable(true);
        mBinding.popProselectPrice.setFocusableInTouchMode(true);

        mBinding.popProselectNumber.setFocusable(true);
        mBinding.popProselectNumber.setFocusableInTouchMode(true);
        SystemUtil.showKeyboard(mBinding.popProselectPrice);
    }

    private void getBuyOrSellUserInfo(int id) {

        RetrofitHttp.getRequest(HttpApi.class)
                .getBuyOrSellUserInfo(id, supID)
                .compose(RxThreadUtil.observableToMain())
                .compose(activity.bindToLifecycle())
                .subscribe(new HttpObserver<JsonBean<String>>(activity) {
                    @Override
                    public void onSuccess(JsonBean<String> bean) {
                        if (bean.code == 1) {
                            if (proAddBean != null) {
                                proAddBean.setProduct_NextPrice(bean.data);
                                setNextPrice(bean.data);
                            }
                        }

                    }
                });
    }

    private void setNextPrice(String price) {
        if (StringUtil.isNotEmpty(price)) {
            mBinding.popProselectNextPrice.setRightText(Util.setPrice(price));
        } else {
            mBinding.popProselectNextPrice.setRightText("暂无");
        }

    }
}
