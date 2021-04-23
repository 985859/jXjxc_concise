package com.yiande.jxjxc.activity;

import android.content.Context;

import androidx.collection.ArrayMap;

import com.mylibrary.api.utils.SkipUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BaseActivity;
import com.yiande.jxjxc.databinding.ActivityBalanceRechargeBinding;
import com.yiande.jxjxc.presenter.BalanceRechargePresenter;

public class BalanceRechargeActivity extends BaseActivity<BalanceRechargePresenter, ActivityBalanceRechargeBinding> {


    /**
     * @param type 0 充值 1扣款
     * @return
     * @author hukui
     * @time 2020/12/15
     * @Description
     */
    public static void start(Context context, int type,int userID,String conName,String balance ) {
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("type", type);
        map.put("userID", userID);
        map.put("conName", conName);
        map.put("balance", balance);
        SkipUtil.skipActivity(context, BalanceRechargeActivity.class, map, 0);
    }


    @Override
    protected int setLayoutId() {
        return R.layout.activity_balance_recharge;
    }

    @Override
    protected BalanceRechargePresenter getPresenter() {
        return new BalanceRechargePresenter(mContext, mBinding);
    }

    @Override
    protected void initImmersionBar() {

        mImmersionBar.titleBar(mBinding.balanceRechargeTop).keyboardEnable(true).init();
    }
}