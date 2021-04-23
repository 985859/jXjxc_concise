package com.yiande.jxjxc.activity;

import android.content.Context;

import androidx.collection.ArrayMap;

import com.mylibrary.api.utils.SkipUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BaseActivity;
import com.yiande.jxjxc.databinding.ActivityStatisticsBinding;
import com.yiande.jxjxc.presenter.StatisticsPresenter;

public class StatisticsActivity extends BaseActivity<StatisticsPresenter, ActivityStatisticsBinding> {

    public static void start(Context context, int type, int  index) {
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("type", type);
        map.put("index", index);
        SkipUtil.skipActivity(context, StatisticsActivity.class, map);
    }



    @Override
    protected int setLayoutId() {
        return R.layout.activity_statistics;
    }

    @Override
    protected StatisticsPresenter getPresenter() {
        return new StatisticsPresenter(mContext, mBinding);
    }

    @Override
    protected void initImmersionBar() {

        mImmersionBar.titleBar(mBinding.statisticsTop).init();
    }


}