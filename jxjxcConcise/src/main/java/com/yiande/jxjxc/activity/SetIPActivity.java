package com.yiande.jxjxc.activity;

import com.gyf.immersionbar.OnKeyboardListener;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BaseActivity;
import com.yiande.jxjxc.databinding.ActivitySetIpBinding;
import com.yiande.jxjxc.presenter.SetIPPresenter;

public class SetIPActivity extends BaseActivity<SetIPPresenter, ActivitySetIpBinding> {


    @Override
    protected int setLayoutId() {
        return R.layout.activity_set_ip;
    }

    @Override
    protected SetIPPresenter getPresenter() {
        return new SetIPPresenter(mContext, mBinding);
    }

    @Override
    protected void initImmersionBar() {

        mImmersionBar.titleBar(mBinding.setIPTop).keyboardEnable(true).setOnKeyboardListener(new OnKeyboardListener() {
            @Override
            public void onKeyboardChange(boolean isPopup, int keyboardHeight) {

            }
        }).init();
    }


}