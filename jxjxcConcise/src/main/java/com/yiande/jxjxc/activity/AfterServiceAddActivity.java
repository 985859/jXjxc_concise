package com.yiande.jxjxc.activity;


import android.content.Context;

import androidx.collection.ArrayMap;

import com.mylibrary.api.utils.SkipUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BaseActivity;
import com.yiande.jxjxc.databinding.ActivityAfterServiceAddBinding;
import com.yiande.jxjxc.presenter.AfterServiceAddPresenter;

public class AfterServiceAddActivity extends BaseActivity<AfterServiceAddPresenter, ActivityAfterServiceAddBinding> {

    public static void start(Context context, int userID, String userComName) {
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("userID", userID);
        map.put("userComName", userComName);
        SkipUtil.skipActivity(context, AfterServiceAddActivity.class, map, 0);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_after_service_add;
    }

    @Override
    protected AfterServiceAddPresenter getPresenter() {
        return new AfterServiceAddPresenter(mContext, mBinding);
    }

    @Override
    protected void initImmersionBar() {

        mImmersionBar.titleBar(mBinding.afterServiceAddTop).keyboardEnable(true).init();
    }
}