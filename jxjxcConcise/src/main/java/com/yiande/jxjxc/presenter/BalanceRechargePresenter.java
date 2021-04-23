package com.yiande.jxjxc.presenter;

import android.content.Intent;
import android.view.View;

import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.utils.ToastUtil;
import com.retrofithttp.api.RetrofitHttp;
import com.retrofithttp.api.util.RxThreadUtil;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.base.BasePresenter;
import com.yiande.jxjxc.base.HttpObserver;
import com.yiande.jxjxc.bean.JsonBean;
import com.yiande.jxjxc.databinding.ActivityBalanceRechargeBinding;
import com.yiande.jxjxc.myInterface.HttpApi;
import com.yiande.jxjxc.myInterface.TypeEnum;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/15 14:33
 */
public class BalanceRechargePresenter extends BasePresenter<ActivityBalanceRechargeBinding> {
    private int type;//0  充值 1 扣款
    private int userID;
    private String conName;
    private String balance;


    public BalanceRechargePresenter(RxAppCompatActivity mContext, ActivityBalanceRechargeBinding binding) {
        super(mContext, binding);
        Intent intent = getIntent();
        if (intent != null) {
            type = intent.getIntExtra("type", 0);
            userID = intent.getIntExtra("userID", 0);
            conName = intent.getStringExtra("conName");
            balance = intent.getStringExtra("balance");
        }
        mBinding.setType(type);
        mBinding.setBalance(balance);
        mBinding.setComName(conName);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mBinding.balanceRechargeBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postData();
            }
        });
    }

    private void postData() {
        if (StringUtil.isEmpty(mBinding.getMoney())) {
              ToastUtil.showShort( mBinding.balanceRechargeMoney.getHint());
            return;
        }
        mBinding.balanceRechargeBT.setEnabled(false);
        HttpObserver<JsonBean<Object>> observer = new HttpObserver<JsonBean<Object>>(mContext) {
            @Override
            public void onSuccess(JsonBean<Object> bean) {
                if (bean.code == 1) {
                    mContext.setResult(TypeEnum.REFRESH);
                }
            }

            @Override
            public void msgOnClick(boolean click, int type) {
                super.msgOnClick(click, type);
                mContext.finish();
            }

            @Override
            public void onFinsh() {
                super.onFinsh();
                mBinding.balanceRechargeBT.setEnabled(true);
            }
        };

        if (type == 0) {

            RetrofitHttp.getRequest(HttpApi.class)
                    .userBalanceAdd(userID, mBinding.getMoney(), mBinding.getMemo())
                    .compose(RxThreadUtil.observableToMain())
                    .compose(mContext.bindToLifecycle())
                    .subscribe(observer);
        } else {
            RetrofitHttp.getRequest(HttpApi.class)
                    .userBalanceDeduct(userID, mBinding.getMoney(), mBinding.getMemo())
                    .compose(RxThreadUtil.observableToMain())
                    .compose(mContext.bindToLifecycle())
                    .subscribe(observer);
        }

    }


}
