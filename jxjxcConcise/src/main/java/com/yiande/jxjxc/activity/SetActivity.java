package com.yiande.jxjxc.activity;


import android.app.Dialog;

import com.mylibrary.api.dialog.MyDialog;
import com.mylibrary.api.utils.SharedUtil;
import com.mylibrary.api.utils.SkipUtil;
import com.mylibrary.api.utils.StringUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BaseActivity;
import com.yiande.jxjxc.base.BasePresenter;
import com.yiande.jxjxc.databinding.ActivitySetBinding;
import com.yiande.jxjxc.myInterface.EventConsume;
import com.yiande.jxjxc.utils.DialogUtils;
import com.yiande.jxjxc.utils.Util;

public class SetActivity extends BaseActivity<BasePresenter<ActivitySetBinding>, ActivitySetBinding> {


    @Override
    protected int setLayoutId() {
        return R.layout.activity_set;
    }

    @Override
    protected BasePresenter<ActivitySetBinding> getPresenter() {
        return new BasePresenter<>(mContext, mBinding);
    }

    @Override
    protected void initData() {
        super.initData();
        mBinding.setOnClick(onClik);
        String hint = SharedUtil.getSP(com.yiande.jxjxc.App.BASEURL);
        if (StringUtil.isNotEmpty(hint)) {
            mBinding.setIP.setHint("当前IP的地址为 " + hint);
        }
    }

    private EventConsume<Integer> onClik = (type) -> {

        switch (type) {
            case 0:
                SkipUtil.skipActivity(mContext, SetIPActivity.class);
                break;
            case 1:
                SkipUtil.skipActivity(mContext, AmendPasswordActivity.class);
                break;
            case 2:
                DialogUtils.showDialog(mContext, "是否退出登录", new MyDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if (confirm) {
                            Util.esc((BaseActivity) mContext);
                        }
                    }
                });
                break;

            default:
                break;
        }
    };

    @Override
    protected void initImmersionBar() {

        mImmersionBar.titleBar(mBinding.setTop).init();
    }
}