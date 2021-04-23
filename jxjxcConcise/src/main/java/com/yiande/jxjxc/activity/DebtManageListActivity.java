package com.yiande.jxjxc.activity;

import android.content.Context;

import androidx.collection.ArrayMap;

import com.mylibrary.api.utils.SkipUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BaseActivity;
import com.yiande.jxjxc.databinding.ActivityDebtManageListBinding;
import com.yiande.jxjxc.presenter.DebtManageListPresenter;

public class DebtManageListActivity extends BaseActivity<DebtManageListPresenter, ActivityDebtManageListBinding> {
    public static void start(Context context, int type) {
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("type", type);
        SkipUtil.skipActivity(context, DebtManageListActivity.class, map, 0);
    }


    @Override
    protected int setLayoutId() {
        return R.layout.activity_debt_manage_list;
    }

    @Override
    protected DebtManageListPresenter getPresenter() {
        return new DebtManageListPresenter(mContext,mBinding);
    }

    @Override
    protected void initImmersionBar() {

        mImmersionBar.titleBar(mBinding.debtManageListTop).init();
    }
}