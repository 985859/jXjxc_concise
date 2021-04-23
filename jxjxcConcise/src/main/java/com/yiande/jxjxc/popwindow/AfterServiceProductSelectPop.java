package com.yiande.jxjxc.popwindow;

import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;

import com.mylibrary.api.utils.PointLengthFilter;
import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.utils.SystemUtil;

import com.mylibrary.api.utils.ToastUtil;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BasePopupWindow;
import com.yiande.jxjxc.bean.AfterServiceProductBean;
import com.yiande.jxjxc.bean.UserBean;

import com.yiande.jxjxc.databinding.PopAfterServiceProductSelectBinding;
import com.yiande.jxjxc.myInterface.EventConsume;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/9/20 9:04
 */
public class AfterServiceProductSelectPop extends BasePopupWindow<PopAfterServiceProductSelectBinding> {

    private AfterServiceProductBean productBean;
    private WheelSelectPop<UserBean> selectPop;
    private View topView;
    private EventConsume<AfterServiceProductBean> proAddBeanEventConsume;

    public void setProAddBeanEventConsume(EventConsume<AfterServiceProductBean> proAddBeanEventConsume) {
        this.proAddBeanEventConsume = proAddBeanEventConsume;
    }

    public void setTopView(View topView) {
        this.topView = topView;
    }

    public AfterServiceProductSelectPop(RxAppCompatActivity context) {
        super(context);
        selectPop = new WheelSelectPop(context);
        selectPop.setTitle("选择供应商");
        selectPop.setEventConsume((bean, reset) -> {
            if (productBean != null) {
                if (reset) {
                    productBean.setAID(0);
                    productBean.setSupplier("");
                    mBinding.popAfterServiceProSupplier.setLeftText(activity.getString(R.string.supplier_select));
                    mBinding.popAfterServiceProSupplier.setLeftTextColor(activity.getResources().getColor(R.color.gray3));
                } else {
                    productBean.setAID(bean.getUser_ID());
                    productBean.setSupplier(bean.getUser_ComName());
                    mBinding.popAfterServiceProSupplier.setLeftText(bean.getUser_ComName());
                    mBinding.popAfterServiceProSupplier.setLeftTextColor(activity.getResources().getColor(R.color.textColor));
                }
            }
            handler.sendEmptyMessageAtTime(0, 500);

        });
    }


    @Override
    public int getContentViewID() {
        return R.layout.pop_after_service_product_select;
    }

    @Override
    public void init(View view) {

        mBinding.popAfterServiceProMoney.setFilters(new InputFilter[]{new PointLengthFilter()});

    }


    public void setData(AfterServiceProductBean data) {
        if (data == null) {
            return;
        }
        productBean = data;
        mBinding.setData(data);
        selectPop.setData(data.getSuppliers());
        selectPop.setID(data.getAID());
        if (StringUtil.isNotEmpty(data.getSupplier())) {
            mBinding.popAfterServiceProSupplier.setLeftText(data.getSupplier());
        }

    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                showWindowAlpha(alpha);
            }
        }
    };

    @Override
    public void setListener() {
        super.setListener();
        mBinding.popAfterServiceProClose.setOnClickListener((view) -> {
            dismiss();
        });
        mBinding.popAfterServiceProSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (topView != null) {
                    selectPop.showPopupWindow(topView, Gravity.BOTTOM);
                }

            }
        });
        mBinding.popAfterServiceProBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (StringUtil.toInt(productBean.getQuantity()) <= 0) {
                    ToastUtil.showShort( R.string.maintain_quantity_hint);
                    return;
                }
                if (StringUtil.isEmpty(productBean.getText())) {
                    ToastUtil.showShort( R.string.fault_sketch_hint);
                    return;
                }


                if (proAddBeanEventConsume != null) {
                    proAddBeanEventConsume.accept(productBean);
                }
                dismiss();
            }
        });
    }


    @Override
    protected void onDismissListener() {
        super.onDismissListener();
        mBinding.popAfterServiceProQuantity.setFocusable(false);
        mBinding.popAfterServiceProQuantity.setFocusableInTouchMode(false);

        mBinding.popAfterServiceProMoney.setFocusable(false);
        mBinding.popAfterServiceProMoney.setFocusableInTouchMode(false);

        mBinding.popAfterServiceProFaultSketch.setFocusable(false);
        mBinding.popAfterServiceProFaultSketch.setFocusableInTouchMode(false);
        SystemUtil.toggleSoftInput(mBinding.popAfterServiceProQuantity);
    }

    @Override
    public void onShowListener() {
        super.onShowListener();
        showKeyboard();
    }

    public void showKeyboard() {
        mBinding.popAfterServiceProQuantity.setFocusable(true);
        mBinding.popAfterServiceProQuantity.setFocusableInTouchMode(true);

        mBinding.popAfterServiceProMoney.setFocusable(true);
        mBinding.popAfterServiceProMoney.setFocusableInTouchMode(true);

        mBinding.popAfterServiceProFaultSketch.setFocusable(true);
        mBinding.popAfterServiceProFaultSketch.setFocusableInTouchMode(true);
        SystemUtil.toggleSoftInput(mBinding.popAfterServiceProQuantity);
    }


}
