package com.yiande.jxjxc.activity;

import android.content.Context;

import androidx.collection.ArrayMap;

import com.mylibrary.api.utils.SkipUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BaseActivity;
import com.yiande.jxjxc.databinding.ActivityProductOutStockDetailBinding;
import com.yiande.jxjxc.presenter.ProductOutStockDetailPresenter;

public class ProductOutStockDetailActivity extends BaseActivity<ProductOutStockDetailPresenter, ActivityProductOutStockDetailBinding> {
    public static void start(Context context, int id) {
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("ID", id);
        SkipUtil.skipActivity(context, ProductOutStockDetailActivity.class, map, 0);
    }


    @Override
    protected int setLayoutId() {
        return R.layout.activity_product_out_stock_detail;
    }

    @Override
    protected ProductOutStockDetailPresenter getPresenter() {
        return new ProductOutStockDetailPresenter(mContext, mBinding);
    }

    @Override
    protected void initImmersionBar() {
        mImmersionBar.titleBar(mBinding.stockOutDetailTop).init();
    }
}