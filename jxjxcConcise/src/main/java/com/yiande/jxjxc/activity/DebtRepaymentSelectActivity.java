package com.yiande.jxjxc.activity;

import android.content.Context;

import androidx.collection.ArrayMap;

import com.mylibrary.api.utils.SkipUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BaseActivity;
import com.yiande.jxjxc.databinding.ActivityDebtRepaymentSelectBinding;
import com.yiande.jxjxc.presenter.DebtRepaymentSelectPresenter;

public class DebtRepaymentSelectActivity extends BaseActivity<DebtRepaymentSelectPresenter, ActivityDebtRepaymentSelectBinding> {


public static void start(Context context, int type,int id) {
    ArrayMap<String, Object> map = new ArrayMap<>();
    map.put("type",type );
    map.put("id",id );
    SkipUtil.skipActivity(context, DebtRepaymentSelectActivity.class, map, 0);
}

    @Override
    protected int setLayoutId() {
        return R.layout.activity_debt_repayment_select;
    }

    @Override
    protected DebtRepaymentSelectPresenter getPresenter() {
        return new DebtRepaymentSelectPresenter(mContext, mBinding);
    }

    @Override
    protected void initImmersionBar() {
        mImmersionBar.titleBar(mBinding.repaymentSelectTop).init();
    }
}