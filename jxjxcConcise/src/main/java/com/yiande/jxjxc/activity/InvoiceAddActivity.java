package com.yiande.jxjxc.activity;



import android.content.Context;

import androidx.collection.ArrayMap;

import com.mylibrary.api.utils.SkipUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BaseActivity;
import com.yiande.jxjxc.databinding.ActivityInvoiceAddBinding;
import com.yiande.jxjxc.presenter.InvoiceAddPresenter;

public class InvoiceAddActivity extends BaseActivity<InvoiceAddPresenter, ActivityInvoiceAddBinding> {

    public static void start(Context context, int OID) {
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("OID",OID );
        SkipUtil.skipActivity(context,InvoiceAddActivity.class , map, 0);
    }


    @Override
    protected int setLayoutId() {
        return R.layout.activity_invoice_add;
    }

    @Override
    protected InvoiceAddPresenter getPresenter() {
        return new InvoiceAddPresenter(mContext, mBinding);
    }

    @Override
    protected void initImmersionBar() {
        mImmersionBar.titleBar(mBinding.invoiceAddTop).keyboardEnable(true).init();
    }
}