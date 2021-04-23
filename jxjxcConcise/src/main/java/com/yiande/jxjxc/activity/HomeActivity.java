package com.yiande.jxjxc.activity;

import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BaseActivity;
import com.yiande.jxjxc.databinding.ActivityHomeBinding;
import com.yiande.jxjxc.presenter.HomePresenter;

public class HomeActivity extends BaseActivity<HomePresenter, ActivityHomeBinding> {


    @Override
    protected int setLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected HomePresenter getPresenter() {
        return new HomePresenter(mContext, mBinding);
    }

    @Override
    protected void initImmersionBar() {
        mImmersionBar.statusBarView(mBinding.homeTopView).statusBarDarkFont(true).init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getData();
    }
}