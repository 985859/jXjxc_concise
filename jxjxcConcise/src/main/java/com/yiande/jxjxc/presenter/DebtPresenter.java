package com.yiande.jxjxc.presenter;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.retrofithttp.api.RetrofitHttp;
import com.retrofithttp.api.util.RxThreadUtil;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.activity.DebtAddActivity;
import com.yiande.jxjxc.activity.DebtDetailActivity;
import com.yiande.jxjxc.activity.DebtListActivity;
import com.yiande.jxjxc.activity.DebtRepaymentActivity;
import com.yiande.jxjxc.adapter.DebtAdapter;
import com.yiande.jxjxc.base.BasePresenter;
import com.yiande.jxjxc.base.HttpObserver;
import com.yiande.jxjxc.bean.DebtBean;
import com.yiande.jxjxc.bean.JsonBean;
import com.yiande.jxjxc.databinding.ActivityDebtBinding;
import com.yiande.jxjxc.myInterface.EventConsume;
import com.yiande.jxjxc.myInterface.HttpApi;
import com.yiande.jxjxc.myInterface.TypeEnum;
import com.yiande.jxjxc.utils.Util;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/14 18:00
 */
public class DebtPresenter extends BasePresenter<ActivityDebtBinding> {
    private int type;//1欠款 2赊账
    private int ID;
    private String name;
    private DebtAdapter debtAdapter;

    public DebtPresenter(RxAppCompatActivity mContext, ActivityDebtBinding binding) {
        super(mContext, binding);
        Intent intent = getIntent();
        if (intent != null) {
            type = intent.getIntExtra("type", 1);
            ID = intent.getIntExtra("id", 0);
            name = intent.getStringExtra("name");
        }
        mBinding.setType(type);
        mBinding.setOnClick(onClik);
        debtAdapter.setType(type);

        //0 赊账 1欠款
        String title = "";
        if (name != null) {
            title += name;
        }
        switch (type) {
            case 1:
                title += "欠款";
                break;
            case 2:
                title += "赊账";
                break;
            default:
                break;
        }
        mBinding.debtTop.setTitle(title);
        getData();
    }

    private EventConsume<Integer> onClik = (state) -> {

        switch (state) {
            case 0://新增欠款/赊账
                DebtAddActivity.start(mContext, type, ID, name);
                break;
            case 1://新增还款
                DebtRepaymentActivity.start(mContext, type, ID, name);
                break;
            case 2://查看明细
                DebtListActivity.start(mContext, type, ID, name);
                break;

            default:
                break;
        }
    };

    @Override
    protected void initData() {
        super.initData();
        debtAdapter = new DebtAdapter();
        debtAdapter.setEmptyView(Util.getEmptyView(mContext));

        mBinding.debtRec.setAdapter(debtAdapter);
        mBinding.debtRec.setLayoutManager(new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
    }

    @Override
    protected void setListener() {
        super.setListener();
        debtAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                DebtDetailActivity.start(mContext, type, debtAdapter.getItem(position).getID());

            }
        });
    }

    private void getData() {
        RetrofitHttp.getRequest(HttpApi.class)
                .getUserDebtList(1, ID, 2, null, null)
                .compose(RxThreadUtil.observableToMain())
                .compose(mContext.bindToLifecycle())
                .subscribe(new HttpObserver<JsonBean<DebtBean>>(mContext) {
                    @Override
                    public void onSuccess(JsonBean<DebtBean> bean) {
                        debtAdapter.setUseEmpty(true);
                        if (bean.data != null) {
                            mBinding.setData(bean.data);
                            debtAdapter.setList(bean.data.getDebtRows());
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
                mContext.setResult(TypeEnum.REFRESH);
                break;
            default:
                break;
        }
    }

}
