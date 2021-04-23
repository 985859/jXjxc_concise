package com.yiande.jxjxc.presenter;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mylibrary.api.managelayout.TopLinearLayoutManager;
import com.retrofithttp.api.RetrofitHttp;
import com.retrofithttp.api.util.RxThreadUtil;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.activity.AZListActivity;
import com.yiande.jxjxc.adapter.DebtManageAdapter;
import com.yiande.jxjxc.base.BasePresenter;
import com.yiande.jxjxc.base.HttpObserver;
import com.yiande.jxjxc.bean.DebtManageBean;
import com.yiande.jxjxc.bean.DebtRowsBean;
import com.yiande.jxjxc.bean.JsonBean;
import com.yiande.jxjxc.databinding.ActivityDebtManageListBinding;
import com.yiande.jxjxc.myInterface.EventConsume;
import com.yiande.jxjxc.myInterface.HttpApi;
import com.yiande.jxjxc.myInterface.TypeEnum;
import com.yiande.jxjxc.utils.Util;

import java.util.List;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/17 10:36
 */
public class DebtManageListPresenter extends BasePresenter<ActivityDebtManageListBinding> {
    private int page = 1;
    private int state = 2;
    private int type = 1;    //1 欠款 2赊账
    private DebtManageAdapter debtManageAdapter;

    public DebtManageListPresenter(RxAppCompatActivity mContext, ActivityDebtManageListBinding binding) {
        super(mContext, binding);
        Intent intent = getIntent();
        if (intent != null) {

            type = intent.getIntExtra("type", 1);
        }
        mBinding.setState(state);
        mBinding.setType(type);
        mBinding.setOnClick(onClik);
        debtManageAdapter.setType(type);
        mBinding.debtManageListRefresh.autoRefresh();
    }

    @Override
    protected void initData() {
        super.initData();
        debtManageAdapter = new DebtManageAdapter();
        debtManageAdapter.setEmptyView(Util.getEmptyView(mContext));
        debtManageAdapter.setUseEmpty(false);
        mBinding.debtManageListRec.setAdapter(debtManageAdapter);
        mBinding.debtManageListRec.setLayoutManager(new TopLinearLayoutManager(mContext));
    }

    @Override
    protected void setListener() {
        super.setListener();
        mBinding.debtManageListTop.setRightViewOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (type) {

                    case 1:
                        AZListActivity.start(mContext, 1, 3);
                        break;
                    case 2:
                        AZListActivity.start(mContext, 2, 4);
                        break;
                    default:
                        break;
                }
            }
        });
        mBinding.debtManageListRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page += 1;
                getData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                onRefreshData();

            }
        });
    }

    private EventConsume<Integer> onClik = (type) -> {
        state = type;
        mBinding.setState(state);
        onRefreshData();
    };

    private void onRefreshData() {
        page = 1;
        getData();
    }

    private void getData() {
        RetrofitHttp.getRequest(HttpApi.class)
                .getDebtList(page, type, state)
                .compose(RxThreadUtil.observableToMain())
                .compose(mContext.bindToLifecycle())
                .subscribe(new HttpObserver<JsonBean<DebtManageBean>>(mContext) {
                    @Override
                    public void onSuccess(JsonBean<DebtManageBean> bean) {

                        JsonBean<List<DebtRowsBean>> jsonBean = new JsonBean<>();
                        jsonBean.setCode(bean.code);
                        jsonBean.setIsmsg(bean.ismsg);
                        jsonBean.setMsg(bean.msg);
                        jsonBean.setCount(bean.count);
                        if (bean.data != null)
                            jsonBean.data = bean.data.getDebtRows();
                        Util.setRceclerData(jsonBean, page, mBinding.debtManageListRefresh, mBinding.debtManageListRec, debtManageAdapter);
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == TypeEnum.REFRESH) {
            onRefreshData();
            mContext.setResult(TypeEnum.REFRESH);
        }
    }
}
