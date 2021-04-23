package com.yiande.jxjxc.activity;

import android.content.Context;

import androidx.collection.ArrayMap;

import com.mylibrary.api.utils.SkipUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BaseActivity;
import com.yiande.jxjxc.databinding.ActivityProductListBinding;
import com.yiande.jxjxc.presenter.ProducutListPresenter;

public class ProductListActivity extends BaseActivity<ProducutListPresenter, ActivityProductListBinding> {

    public static void start(Context context, String keywords) {
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("keywords", keywords);
        SkipUtil.skipActivity(context, ProductListActivity.class, map, 0);
    }

    public static void start(Context context, int type,int userID,int level) {
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("type", type);
        map.put("userID", userID);
        map.put("level", level);
        SkipUtil.skipActivity(context, ProductListActivity.class, map, 0);
    }



    @Override
    protected int setLayoutId() {
        return R.layout.activity_product_list;
    }

    @Override
    protected ProducutListPresenter getPresenter() {
        return new ProducutListPresenter(mContext, mBinding);
    }

    @Override
    protected void initImmersionBar() {

        mImmersionBar.titleBar(mBinding.productListTop).init();
    }
}