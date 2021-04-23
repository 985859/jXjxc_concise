package com.yiande.jxjxc.activity;

import android.content.Context;

import androidx.collection.ArrayMap;

import com.mylibrary.api.utils.SkipUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BaseActivity;
import com.yiande.jxjxc.databinding.ActivityAZListBinding;
import com.yiande.jxjxc.presenter.AZPresenter;

public class AZListActivity extends BaseActivity<AZPresenter, ActivityAZListBinding> {


    public static void start(Context context, int type,int state) {
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("type",type );
        map.put("state",state );
        SkipUtil.skipActivity(context,AZListActivity.class,map,0);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_a_z_list;
    }

    @Override
    protected AZPresenter getPresenter() {
        return new AZPresenter(mContext, mBinding);
    }

    @Override
    protected void initImmersionBar() {
        mImmersionBar.titleBar(mBinding.AZListTop).init();
    }
}