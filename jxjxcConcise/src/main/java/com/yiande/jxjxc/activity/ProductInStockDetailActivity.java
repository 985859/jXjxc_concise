package com.yiande.jxjxc.activity;


import android.content.Context;

import androidx.collection.ArrayMap;

import com.mylibrary.api.utils.SkipUtil;
import com.yiande.jxjxc.base.BaseActivity;
import com.yiande.jxjxc.presenter.ProductInStockDetailPresenter;
import com.yiande.jxjxc.R;

import com.yiande.jxjxc.databinding.ActivityProductInStockDetailBinding;


public class ProductInStockDetailActivity extends BaseActivity<ProductInStockDetailPresenter, ActivityProductInStockDetailBinding> {

    public static void start(Context context, int id) {
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("ID", id);
        SkipUtil.skipActivity(context, ProductInStockDetailActivity.class, map, 0);
    }


    @Override
    protected int setLayoutId() {
        return R.layout.activity_product_in_stock_detail;
    }

    @Override
    protected ProductInStockDetailPresenter getPresenter() {
        return new ProductInStockDetailPresenter(mContext, mBinding);
    }

    @Override
    protected void initImmersionBar() {

        mImmersionBar.titleBar(mBinding.inStockDetailTop).init();
    }
}