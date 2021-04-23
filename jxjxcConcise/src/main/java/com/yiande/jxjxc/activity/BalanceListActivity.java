package com.yiande.jxjxc.activity;

import android.content.Context;

import androidx.collection.ArrayMap;

import com.mylibrary.api.utils.SkipUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BaseActivity;
import com.yiande.jxjxc.databinding.ActivityBalanceListBinding;
import com.yiande.jxjxc.presenter.BalanceListPresenter;

public class BalanceListActivity extends BaseActivity<BalanceListPresenter, ActivityBalanceListBinding> {


    public static void start(Context context, int userID, String userName) {
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("userID", userID);
        map.put("userName", userName);
        SkipUtil.skipActivity(context, BalanceListActivity.class, map, 0);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_balance_list;
    }

    @Override
    protected BalanceListPresenter getPresenter() {
        return new BalanceListPresenter(mContext, mBinding);
    }

    @Override
    protected void initImmersionBar() {
        mImmersionBar.titleBar(mBinding.balanceListTop).init();
    }
}