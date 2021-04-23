package com.yiande.jxjxc.activity;

import android.content.Context;

import androidx.collection.ArrayMap;

import com.gyf.immersionbar.OnKeyboardListener;
import com.mylibrary.api.utils.SkipUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BaseActivity;
import com.yiande.jxjxc.databinding.ActivitySelectBinding;

import com.yiande.jxjxc.presenter.SelectPresenter;


/**
 * Created by myuser on 2018/4/18.
 */

public class SelectActivity extends BaseActivity<SelectPresenter, ActivitySelectBinding> {

    public static void start(Context context, int type, String hint) {
        start(context, type, 0, hint);
    }

    public static void start(Context context, int type, int state, String hint) {
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("type", type);
        map.put("state", state);
        map.put("hint", hint);
        SkipUtil.skipActivity(context, SelectActivity.class, map);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_select;
    }

    @Override
    protected SelectPresenter getPresenter() {
        return new SelectPresenter(mContext, mBinding);
    }

    @Override
    protected void initImmersionBar() {

        mImmersionBar.titleBar(mBinding.selectTop).keyboardEnable(true).setOnKeyboardListener(new OnKeyboardListener() {
            @Override
            public void onKeyboardChange(boolean isPopup, int keyboardHeight) {

            }
        }).init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.setselectHistory();
    }
}
