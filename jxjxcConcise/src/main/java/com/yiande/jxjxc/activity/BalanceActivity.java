package com.yiande.jxjxc.activity;

import android.content.Context;

import androidx.collection.ArrayMap;

import com.mylibrary.api.utils.SkipUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BaseActivity;
import com.yiande.jxjxc.databinding.ActivityBalanceBinding;
import com.yiande.jxjxc.presenter.BalancePresenter;

public class BalanceActivity extends BaseActivity<BalancePresenter, ActivityBalanceBinding> {


    public static void start(Context context, int userID, String userName) {
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("userID", userID);
        map.put("userName", userName);
        SkipUtil.skipActivity(context, BalanceActivity.class, map, 0);
    }


    @Override
    protected int setLayoutId() {
        return R.layout.activity_balance;
    }

    @Override
    protected BalancePresenter getPresenter() {
        return new BalancePresenter(mContext, mBinding);
    }

    @Override
    protected void initImmersionBar() {
        mImmersionBar.statusBarDarkFont(true).titleBar(mBinding.balanceTop).init();
    }
}