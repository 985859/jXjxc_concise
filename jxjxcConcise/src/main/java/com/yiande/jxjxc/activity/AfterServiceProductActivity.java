package com.yiande.jxjxc.activity;

import android.content.Context;

import androidx.collection.ArrayMap;

import com.mylibrary.api.utils.SkipUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BaseActivity;
import com.yiande.jxjxc.databinding.ActivityAfterServiceProductBinding;
import com.yiande.jxjxc.presenter.AfterServiceProductPresenter;

public class AfterServiceProductActivity extends BaseActivity<AfterServiceProductPresenter, ActivityAfterServiceProductBinding> {


    public static void start(Context context, int userID) {
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("userID", userID);
        SkipUtil.skipActivity(context, AfterServiceProductActivity.class, map, 0);
    }


    @Override
    protected int setLayoutId() {
        return R.layout.activity_after_service_product;
    }

    @Override
    protected AfterServiceProductPresenter getPresenter() {
        return new AfterServiceProductPresenter(mContext, mBinding);
    }

    @Override
    protected void initImmersionBar() {

        mImmersionBar.titleBar(mBinding.afterServiceProductTop).init();
    }
}