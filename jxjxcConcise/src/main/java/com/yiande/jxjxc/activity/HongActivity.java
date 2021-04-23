package com.yiande.jxjxc.activity;

import android.content.Context;

import androidx.collection.ArrayMap;

import com.mylibrary.api.utils.SkipUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BaseActivity;
import com.yiande.jxjxc.bean.OrderInfoBean;
import com.yiande.jxjxc.databinding.ActivityHongBinding;
import com.yiande.jxjxc.presenter.HongPresenter;

public class HongActivity extends BaseActivity<HongPresenter, ActivityHongBinding> {

    public static void start(Context context, int type, OrderInfoBean bean) {
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("type", type);
        map.put("bean", bean);
        SkipUtil.skipActivity(context, HongActivity.class, map, 0);

    }


    @Override
    protected int setLayoutId() {
        return R.layout.activity_hong;
    }

    @Override
    protected HongPresenter getPresenter() {
        return new HongPresenter(mContext, mBinding);
    }

    @Override
    protected void initImmersionBar() {

        mImmersionBar.titleBar(mBinding.hongTop).init();
    }
}