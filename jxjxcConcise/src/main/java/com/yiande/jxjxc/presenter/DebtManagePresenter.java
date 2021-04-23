package com.yiande.jxjxc.presenter;

import android.content.Intent;

import androidx.annotation.Nullable;

import com.mylibrary.api.managelayout.TopLinearLayoutManager;
import com.retrofithttp.api.RetrofitHttp;
import com.retrofithttp.api.util.RxThreadUtil;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.activity.DebtManageListActivity;
import com.yiande.jxjxc.adapter.DebtManageAdapter;
import com.yiande.jxjxc.base.BasePresenter;
import com.yiande.jxjxc.base.HttpObserver;
import com.yiande.jxjxc.bean.DebtManageBean;
import com.yiande.jxjxc.bean.JsonBean;
import com.yiande.jxjxc.databinding.ActivityDebtManageBinding;
import com.yiande.jxjxc.myInterface.EventConsume;
import com.yiande.jxjxc.myInterface.HttpApi;
import com.yiande.jxjxc.myInterface.TypeEnum;
import com.yiande.jxjxc.utils.Util;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/16 15:26
 */
public class DebtManagePresenter extends BasePresenter<ActivityDebtManageBinding> {
    private int type;//1 欠款 2 赊账
    private DebtManageAdapter debtManageAdapter;

    public DebtManagePresenter(RxAppCompatActivity mContext, ActivityDebtManageBinding binding) {
        super(mContext, binding);
        Intent intent = getIntent();
        if (intent != null) {
            type = intent.getIntExtra("type", 0);
        }
        mBinding.setType(type);
        debtManageAdapter.setType(type);
        mBinding.setOnClick(onClik);
        getData();

    }

    private EventConsume<Integer> onClik = (state) -> {
        switch (state) {
            case 0:
                DebtManageListActivity.start(mContext, type);
                break;
            default:
                break;
        }
    };



    @Override
    protected void initData() {
        super.initData();
        debtManageAdapter = new DebtManageAdapter();
        debtManageAdapter.setEmptyView(Util.getEmptyView(mContext));
        debtManageAdapter.setUseEmpty(false);
        mBinding.debtManageRec.setAdapter(debtManageAdapter);
        mBinding.debtManageRec.setLayoutManager(new TopLinearLayoutManager(mContext));
    }

    private void getData() {
        RetrofitHttp.getRequest(HttpApi.class)
                .getDebtList(1, type, 2)
                .compose(RxThreadUtil.observableToMain())
                .compose(mContext.bindToLifecycle())
                .subscribe(new HttpObserver<JsonBean<DebtManageBean>>(mContext) {
                    @Override
                    public void onSuccess(JsonBean<DebtManageBean> bean) {
                        debtManageAdapter.setUseEmpty(true);
                        if (bean.data != null) {
                            mBinding.setData(bean.data);
                            debtManageAdapter.setList(bean.data.getDebtRows());
                        }
                    }
                });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case TypeEnum.REFRESH:
                getData();
                break;
            default:
                break;
        }
    }
}
