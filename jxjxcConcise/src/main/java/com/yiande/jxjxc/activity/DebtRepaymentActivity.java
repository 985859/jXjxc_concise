package com.yiande.jxjxc.activity;

import android.content.Context;

import androidx.collection.ArrayMap;

import com.mylibrary.api.utils.SkipUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BaseActivity;
import com.yiande.jxjxc.databinding.ActivityDebtRepaymentBinding;
import com.yiande.jxjxc.presenter.DebtRepaymentPresent;

public class DebtRepaymentActivity extends BaseActivity<DebtRepaymentPresent, ActivityDebtRepaymentBinding> {
    public static void start(Context context, int type, int id, String name) {
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("type", type);
        map.put("id", id);
        map.put("name", name);
        SkipUtil.skipActivity(context, DebtRepaymentActivity.class, map, 0);
    }



    @Override
    protected int setLayoutId() {
        return R.layout.activity_debt_repayment;
    }

    @Override
    protected DebtRepaymentPresent getPresenter() {
        return new DebtRepaymentPresent(mContext, mBinding);
    }

    @Override
    protected void initImmersionBar() {

        mImmersionBar.titleBar(mBinding.debtRepaymentTop).keyboardEnable(true).init();
    }
}