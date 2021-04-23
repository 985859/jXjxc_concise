package com.yiande.jxjxc.activity;

import android.content.Context;

import androidx.collection.ArrayMap;

import com.mylibrary.api.utils.SkipUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BaseActivity;
import com.yiande.jxjxc.databinding.ActivityDebtDetailBinding;
import com.yiande.jxjxc.presenter.DebtDetailPresenter;

public class DebtDetailActivity extends BaseActivity<DebtDetailPresenter, ActivityDebtDetailBinding> {



    /**
     * @param id 欠款/赊账记录ID
     * @return
     * @author hukui
     * @time 2020/12/18
     * @Description
     */
    public static void start(Context context, int type, int id) {
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("type", type);
        map.put("id", id);
        SkipUtil.skipActivity(context, DebtDetailActivity.class, map, 0);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_debt_detail;
    }

    @Override
    protected DebtDetailPresenter getPresenter() {
        return new DebtDetailPresenter(mContext, mBinding);
    }

    @Override
    protected void initImmersionBar() {

        mImmersionBar.titleBar(mBinding.debtDetailTop).keyboardEnable(true).init();
    }
}