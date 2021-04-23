package com.yiande.jxjxc.activity;

import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BaseActivity;
import com.yiande.jxjxc.databinding.ActivityAmendPasswordBinding;
import com.yiande.jxjxc.presenter.AmendPasswordPresenter;

public class AmendPasswordActivity extends BaseActivity<AmendPasswordPresenter, ActivityAmendPasswordBinding> {



    @Override
    protected int setLayoutId() {
        return R.layout.activity_amend_password;
    }

    @Override
    protected AmendPasswordPresenter getPresenter() {
        return new AmendPasswordPresenter(mContext,mBinding);
    }

    @Override
    protected void initImmersionBar() {

        mImmersionBar.titleBar(mBinding.amendPasswordTop).keyboardEnable(true).init();
    }
}