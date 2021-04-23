package com.yiande.jxjxc.activity;

import com.gyf.immersionbar.OnKeyboardListener;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BaseActivity;
import com.yiande.jxjxc.databinding.ActivityLoginBinding;
import com.yiande.jxjxc.presenter.LoginPresenter;

public class LoginActivity extends BaseActivity<LoginPresenter, ActivityLoginBinding> {


    @Override
    protected int setLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginPresenter getPresenter() {
        return new LoginPresenter(mContext, mBinding);
    }

    @Override
    protected void initImmersionBar() {

        mImmersionBar.keyboardEnable(true).setOnKeyboardListener(new OnKeyboardListener() {
            @Override
            public void onKeyboardChange(boolean isPopup, int keyboardHeight) {
                if(isPopup){
                    mBinding.setShowView(false);
                }else {
                    mBinding.setShowView(true);
                }
            }
        }).init();
    }
}