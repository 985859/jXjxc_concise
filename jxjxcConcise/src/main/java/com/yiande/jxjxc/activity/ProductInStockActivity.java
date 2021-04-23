package com.yiande.jxjxc.activity;

import android.content.Context;
import android.view.View;

import androidx.collection.ArrayMap;

import com.gyf.immersionbar.OnKeyboardListener;
import com.mylibrary.api.utils.SkipUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BaseActivity;
import com.yiande.jxjxc.databinding.ActivityProductInStockBinding;
import com.yiande.jxjxc.presenter.ProductInStockPresenter;

public class ProductInStockActivity extends BaseActivity<ProductInStockPresenter, ActivityProductInStockBinding> {
    public static void start(Context context, int supID, String name) {
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("supID", supID);
        map.put("name", name);
        SkipUtil.skipActivity(context, ProductInStockActivity.class, map, 0);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_product_in_stock;
    }

    @Override
    protected ProductInStockPresenter getPresenter() {
        return new ProductInStockPresenter(mContext, mBinding);
    }

    @Override
    protected void initImmersionBar() {

        mImmersionBar.titleBar(mBinding.stockInTop).keyboardEnable(true).setOnKeyboardListener(new OnKeyboardListener() {
            @Override
            public void onKeyboardChange(boolean isPopup, int keyboardHeight) {
                if (isPopup) {
                    mBinding.stockInBT.setVisibility(View.GONE);
                } else {
                    mBinding.stockInBT.setVisibility(View.VISIBLE);
                }
            }
        }).init();
    }
}