package com.yiande.jxjxc.activity;

import android.content.Context;

import androidx.collection.ArrayMap;

import com.mylibrary.api.utils.SkipUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BaseActivity;
import com.yiande.jxjxc.databinding.ActivityStockOrderListBinding;
import com.yiande.jxjxc.presenter.StockOrderListPresenter;

public class StockOrderListActivity extends BaseActivity<StockOrderListPresenter, ActivityStockOrderListBinding> {


    public static void start(Context context, int type,String keywords) {
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("type", type);
        map.put("keywords", keywords);
        SkipUtil.skipActivity(context, StockOrderListActivity.class,map );
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_stock_order_list;
    }

    @Override
    protected StockOrderListPresenter getPresenter() {
        return new StockOrderListPresenter(mContext, mBinding);
    }

    @Override
    protected void initImmersionBar() {
        mImmersionBar.titleBar(mBinding.stockOrderListTop).init();
    }
}