package com.yiande.jxjxc.activity;

import android.content.Context;
import android.view.View;

import androidx.collection.ArrayMap;

import com.gyf.immersionbar.OnKeyboardListener;
import com.mylibrary.api.utils.SkipUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BaseActivity;
import com.yiande.jxjxc.databinding.ActivityProductOutStockBinding;
import com.yiande.jxjxc.presenter.ProductOutStockPresenter;

public class ProductOutStockActivity extends BaseActivity<ProductOutStockPresenter, ActivityProductOutStockBinding> {

    public static void start(Context context, int supID, String name,int level){
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("supID",supID );
        map.put("name",name );
        map.put("level",level );
        SkipUtil.skipActivity(context,ProductOutStockActivity.class,map,0);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_product_out_stock;
    }

    @Override
    protected ProductOutStockPresenter getPresenter() {
        return new ProductOutStockPresenter(mContext,mBinding);
    }

    @Override
    protected void initImmersionBar() {
        mImmersionBar.titleBar(mBinding.stockOutTop).keyboardEnable(true).setOnKeyboardListener(new OnKeyboardListener() {
            @Override
            public void onKeyboardChange(boolean isPopup, int keyboardHeight) {
                if (isPopup) {
                    mBinding.stockOutBT.setVisibility(View.GONE);
                } else {
                    mBinding.stockOutBT.setVisibility(View.VISIBLE);
                }
            }
        }).init();
    }
}