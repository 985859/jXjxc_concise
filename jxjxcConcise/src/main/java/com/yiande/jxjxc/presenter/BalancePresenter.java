package com.yiande.jxjxc.presenter;

import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.retrofithttp.api.RetrofitHttp;
import com.retrofithttp.api.util.RxThreadUtil;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.activity.BalanceListActivity;
import com.yiande.jxjxc.activity.BalanceRechargeActivity;
import com.yiande.jxjxc.adapter.BalanceAdapter;
import com.yiande.jxjxc.base.BasePresenter;
import com.yiande.jxjxc.base.HttpObserver;
import com.yiande.jxjxc.bean.BalanceBean;
import com.yiande.jxjxc.bean.JsonBean;
import com.yiande.jxjxc.databinding.ActivityBalanceBinding;
import com.yiande.jxjxc.myInterface.EventConsume;
import com.yiande.jxjxc.myInterface.HttpApi;
import com.yiande.jxjxc.myInterface.TypeEnum;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/15 15:24
 */

public class BalancePresenter extends BasePresenter<ActivityBalanceBinding> {
    private BalanceBean balanceBean;
    private BalanceAdapter balanceAdapter;

    private int page = 1;
    private int userID;
    private String userName;

    public BalancePresenter(RxAppCompatActivity mContext, ActivityBalanceBinding binding) {
        super(mContext, binding);
        Intent intent = getIntent();
        if (intent != null) {
            userID = intent.getIntExtra("userID", 0);
            userName = intent.getStringExtra("userName");
        }
        String title = String.format(mContext.getString(R.string.client_balance_title), userName);
        mBinding.balanceTop.setTitle(title);
        mBinding.setOnClick(onClik);
        getData();
    }

    private EventConsume<Integer> onClik = (type) -> {

        switch (type) {
            case 0://客户充值
                if (balanceBean != null)
                    BalanceRechargeActivity.start(mContext, 0, balanceBean.getUID(), balanceBean.getComName(), balanceBean.getBalance());
                break;
            case 1://客户扣款
                if (balanceBean != null)
                    BalanceRechargeActivity.start(mContext, 1, balanceBean.getUID(), balanceBean.getComName(), balanceBean.getBalance());
                break;
            case 2://查看明细
                BalanceListActivity.start(mContext, userID, userName);
                break;
            default:
                break;
        }
    };

    @Override
    protected void initData() {
        super.initData();
        balanceAdapter = new BalanceAdapter();
        mBinding.balanceRec.setAdapter(balanceAdapter);
        mBinding.balanceRec.setLayoutManager(new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
    }

    private void getData() {
        RetrofitHttp.getRequest(HttpApi.class)
                .getUserBalanceList(page, userID, null, null, null)
                .compose(RxThreadUtil.observableToMain())
                .compose(mContext.bindToLifecycle())
                .subscribe(new HttpObserver<JsonBean<BalanceBean>>(mContext) {
                    @Override
                    public void onSuccess(JsonBean<BalanceBean> bean) {
                        if (bean.code == 1) {
                            balanceBean = bean.data;
                            mBinding.setData(bean.data);
                            balanceAdapter.setList(bean.data.getBalanceRows());
                        }

                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {

            case TypeEnum
                    .REFRESH:
                getData();
            mContext.setResult(TypeEnum
                    .REFRESH);
                break;
            default:
                break;
        }
    }
}

