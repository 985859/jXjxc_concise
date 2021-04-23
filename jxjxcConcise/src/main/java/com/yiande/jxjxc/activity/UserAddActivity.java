package com.yiande.jxjxc.activity;

import android.content.Context;
import android.view.View;

import androidx.collection.ArrayMap;

import com.gyf.immersionbar.OnKeyboardListener;
import com.mylibrary.api.utils.SkipUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BaseActivity;
import com.yiande.jxjxc.databinding.ActivityUserAddBinding;
import com.yiande.jxjxc.presenter.UserAddPresenter;

public class UserAddActivity extends BaseActivity<UserAddPresenter, ActivityUserAddBinding> {



    public static void start(Context context, int userID,int userType, boolean isAmend) {
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("userID", userID);
        map.put("isAmend", isAmend);
        map.put("userType", userType);
        SkipUtil.skipActivity(context, UserAddActivity.class, map, 0);
    }


    @Override
    protected int setLayoutId() {
        return R.layout.activity_user_add;
    }

    @Override
    protected UserAddPresenter getPresenter() {
        return new UserAddPresenter(mContext, mBinding);
    }

    @Override
    protected void initImmersionBar() {
        mImmersionBar.titleBar(mBinding.userAddTop).keyboardEnable(true).setOnKeyboardListener(new OnKeyboardListener() {
            @Override
            public void onKeyboardChange(boolean isPopup, int keyboardHeight) {
                if (isPopup){
                    mBinding.userAddBT.setVisibility(View.GONE);
                }else {
                    mBinding.userAddBT.setVisibility(View.VISIBLE);
                }
            }
        }).init();
    }
}