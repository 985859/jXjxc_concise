package com.yiande.jxjxc.activity;

import android.content.Context;

import androidx.collection.ArrayMap;

import com.mylibrary.api.utils.SkipUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BaseActivity;
import com.yiande.jxjxc.databinding.ActivityDebtManageBinding;
import com.yiande.jxjxc.presenter.DebtManagePresenter;

public class DebtManageActivity extends BaseActivity<DebtManagePresenter, ActivityDebtManageBinding> {

    public static void start(Context context, int type) {
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("type", type);
        SkipUtil.skipActivity(context, DebtManageActivity.class, map, 0);
    }


    @Override
    protected int setLayoutId() {
        return R.layout.activity_debt_manage;
    }

    @Override
    protected DebtManagePresenter getPresenter() {
        return new DebtManagePresenter(mContext, mBinding);
    }

    @Override
    protected void initImmersionBar() {
        mImmersionBar.titleBar(mBinding.debtManageTop).init();
    }
}