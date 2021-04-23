package com.yiande.jxjxc.activity;

import android.content.Context;

import androidx.collection.ArrayMap;

import com.mylibrary.api.utils.SkipUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BaseActivity;
import com.yiande.jxjxc.bean.ProductBean;
import com.yiande.jxjxc.databinding.ActivityProductBinding;
import com.yiande.jxjxc.presenter.ProductPresenter;

public class ProductActivity extends BaseActivity<ProductPresenter, ActivityProductBinding> {


    public static void start(Context context, ProductBean productBean,
                             int proID, boolean isAdd) {
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("productBean", productBean);
        map.put("proID", proID);
        map.put("isAdd", isAdd);
        SkipUtil.skipActivity(context, ProductActivity.class, map, 0);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_product;
    }

    @Override
    protected ProductPresenter getPresenter() {
        return new ProductPresenter(mContext, mBinding);
    }

    @Override
    protected void initImmersionBar() {
        mImmersionBar.titleBar(mBinding.productTop).keyboardEnable(true).init();

    }


}