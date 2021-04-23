package com.yiande.jxjxc.activity;

import android.content.Context;

import androidx.collection.ArrayMap;

import com.mylibrary.api.utils.SkipUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BaseActivity;
import com.yiande.jxjxc.databinding.ActivityInvoiceRecordBinding;
import com.yiande.jxjxc.presenter.InvoiceRecordPresenter;

public class InvoiceRecordActivity extends BaseActivity<InvoiceRecordPresenter, ActivityInvoiceRecordBinding> {


    public static void start(Context context, int oid,int type) {
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("OID", oid);
        map.put("type", type);
        SkipUtil.skipActivity(context, InvoiceRecordActivity.class, map, 0);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_invoice_record;
    }

    @Override
    protected InvoiceRecordPresenter getPresenter() {
        return new InvoiceRecordPresenter(mContext, mBinding);
    }

    @Override
    protected void initImmersionBar() {
        mImmersionBar.titleBar(mBinding.invoiceRecordTop).init();
    }
}