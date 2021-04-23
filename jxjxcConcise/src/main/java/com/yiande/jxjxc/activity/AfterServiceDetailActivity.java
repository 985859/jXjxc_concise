package com.yiande.jxjxc.activity;

import android.content.Context;

import androidx.collection.ArrayMap;
import androidx.recyclerview.widget.RecyclerView;

import com.mylibrary.api.utils.SkipUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BaseActivity;
import com.yiande.jxjxc.databinding.ActivityAfterServiceDetailBinding;
import com.yiande.jxjxc.presenter.AfterServiceDetailPresenter;

public class AfterServiceDetailActivity extends BaseActivity<AfterServiceDetailPresenter, ActivityAfterServiceDetailBinding> {

    public static void start(Context context, int id) {
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("id", id);
        SkipUtil.skipActivity(context, AfterServiceDetailActivity.class, map, 0);
    }


    @Override
    protected int setLayoutId() {
        return R.layout.activity_after_service_detail;
    }

    @Override
    protected AfterServiceDetailPresenter getPresenter() {
        return new AfterServiceDetailPresenter(mContext, mBinding);
    }

    @Override
    protected void initImmersionBar() {
        mImmersionBar.titleBar(mBinding.afterServiceDetailTop).init();
    }
}