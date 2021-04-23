package com.yiande.jxjxc.activity;

import android.content.Context;

import androidx.collection.ArrayMap;

import com.mylibrary.api.utils.SkipUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BaseActivity;
import com.yiande.jxjxc.databinding.ActivityAdminListBinding;
import com.yiande.jxjxc.presenter.AdminListPresenter;

public class AdminListActivity extends BaseActivity<AdminListPresenter, ActivityAdminListBinding> {


    public static void start(Context context, String keywords) {
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("keywords", keywords);
        SkipUtil.skipActivity(context, AdminListActivity.class,map,0);
    }


    @Override
    protected int setLayoutId() {
        return R.layout.activity_admin_list;
    }

    @Override
    protected AdminListPresenter getPresenter() {
        return new AdminListPresenter(mContext, mBinding);
    }

    @Override
    protected void initImmersionBar() {
        mImmersionBar.titleBar(mBinding.adminListTop).init();
    }



}