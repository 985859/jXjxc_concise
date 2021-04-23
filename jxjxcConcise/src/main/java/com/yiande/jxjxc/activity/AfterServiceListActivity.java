package com.yiande.jxjxc.activity;

import android.content.Context;

import androidx.collection.ArrayMap;

import com.mylibrary.api.utils.SkipUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BaseActivity;
import com.yiande.jxjxc.databinding.ActivityAfterServiceListBinding;
import com.yiande.jxjxc.presenter.AfterServiceListPresenter;

public class AfterServiceListActivity extends BaseActivity<AfterServiceListPresenter, ActivityAfterServiceListBinding> {
    public static void start(Context context, String text) {
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("keywords", text);
        SkipUtil.skipActivity(context, AfterServiceListActivity.class, map, 0);
    }


    @Override
    protected int setLayoutId() {
        return R.layout.activity_after_service_list;
    }

    @Override
    protected AfterServiceListPresenter getPresenter() {
        return new AfterServiceListPresenter(mContext, mBinding);
    }

    @Override
    protected void initImmersionBar() {

        mImmersionBar.titleBar(mBinding.afterServiceListTop).init();
    }
}