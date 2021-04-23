package com.yiande.jxjxc.activity;

import android.content.Context;

import androidx.collection.ArrayMap;

import com.mylibrary.api.utils.SkipUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BaseActivity;
import com.yiande.jxjxc.databinding.ActivityUnitBrandBinding;
import com.yiande.jxjxc.presenter.UnitBrandPresenter;

public class UnitBrandActivity extends BaseActivity<UnitBrandPresenter, ActivityUnitBrandBinding> {

    public static void start(Context context, int type) {
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("type", type);
        SkipUtil.skipActivity(context, UnitBrandActivity.class, map, 0);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_unit_brand;
    }


    @Override
    protected UnitBrandPresenter getPresenter() {
        return new UnitBrandPresenter(mContext, mBinding);
    }


    @Override
    protected void initImmersionBar() {
        mImmersionBar.titleBar(mBinding.unitBrandTop).init();
    }
}