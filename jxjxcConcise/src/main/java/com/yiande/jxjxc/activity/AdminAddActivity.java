package com.yiande.jxjxc.activity;

import android.content.Context;
import android.view.View;

import androidx.collection.ArrayMap;

import com.gyf.immersionbar.OnKeyboardListener;
import com.mylibrary.api.utils.SkipUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BaseActivity;
import com.yiande.jxjxc.databinding.ActivityAdminAddBinding;
import com.yiande.jxjxc.presenter.AdminAddPresenter;

public class AdminAddActivity extends BaseActivity<AdminAddPresenter, ActivityAdminAddBinding> {


    public static void start(Context context, int adminID,  boolean isAmend) {
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("adminID", adminID);
        map.put("isAmend", isAmend);
        SkipUtil.skipActivity(context, AdminAddActivity.class, map, 0);
    }


    @Override
    protected int setLayoutId() {
        return R.layout.activity_admin_add;
    }


    @Override
    protected AdminAddPresenter getPresenter() {
        return new AdminAddPresenter(mContext, mBinding);
    }


    @Override
    protected void initImmersionBar() {
        mImmersionBar.titleBar(mBinding.adminAddTop).keyboardEnable(true).setOnKeyboardListener(new OnKeyboardListener() {
            @Override
            public void onKeyboardChange(boolean isPopup, int keyboardHeight) {
                if (isPopup) {
                    mBinding.adminAddBT.setVisibility(View.GONE);
                } else {
                    mBinding.adminAddBT.setVisibility(View.VISIBLE);
                }
            }
        }).init();
    }


}