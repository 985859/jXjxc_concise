package com.yiande.jxjxc.presenter;

import android.app.Dialog;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.google.gson.reflect.TypeToken;
import com.mylibrary.api.dialog.MyDialog;
import com.mylibrary.api.utils.json.JsonUtil;
import com.retrofithttp.api.RetrofitHttp;
import com.retrofithttp.api.util.RxThreadUtil;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.adapter.AfterServiceProductAdapter;
import com.yiande.jxjxc.base.BasePresenter;
import com.yiande.jxjxc.base.HttpObserver;
import com.yiande.jxjxc.bean.AfterServiceProductBean;
import com.yiande.jxjxc.bean.JsonBean;
import com.yiande.jxjxc.databinding.ActivityAfterServiceProductBinding;
import com.yiande.jxjxc.myInterface.EventConsume;
import com.yiande.jxjxc.myInterface.HttpApi;
import com.yiande.jxjxc.myInterface.TypeEnum;
import com.yiande.jxjxc.popwindow.AfterServiceProductSelectPop;
import com.yiande.jxjxc.utils.DialogUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/23 9:43
 */
public class AfterServiceProductPresenter extends BasePresenter<ActivityAfterServiceProductBinding> {

    AfterServiceProductAdapter productAdapter;
    private int userID;
    AfterServiceProductSelectPop ProductSelectPop;

    public AfterServiceProductPresenter(RxAppCompatActivity mContext, ActivityAfterServiceProductBinding binding) {
        super(mContext, binding);
        Intent intent = getIntent();
        if (intent != null) {
            userID = intent.getIntExtra("userID", 0);
        }

        getData();
    }

    @Override
    protected void initData() {
        super.initData();
        productAdapter = new AfterServiceProductAdapter();
        mBinding.afterServiceProductRec.setAdapter(productAdapter);
        mBinding.afterServiceProductRec.setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override
    protected void setListener() {
        super.setListener();
        productAdapter.addChildClickViewIds(R.id.itmAfterServiceProduct_BT);
        productAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {

                switch (view.getId()) {
                    case R.id.itmAfterServiceProduct_BT:
                        getProductSelectPop().setData(productAdapter.getItem(position));
                        getProductSelectPop().showPopupWindow(mBinding.afterServiceProductTop, Gravity.BOTTOM);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void getData() {
        RetrofitHttp.getRequest(HttpApi.class)
                .getUserServiceProduct(userID)
                .compose(RxThreadUtil.observableToMain())
                .compose(mContext.bindToLifecycle())
                .subscribe(new HttpObserver<JsonBean<List<AfterServiceProductBean>>>(mContext) {
                    @Override
                    public void onSuccess(JsonBean<List<AfterServiceProductBean>> bean) {
                        if (bean.code == 1) {
                            productAdapter.setList(bean.data);
                        }

                    }
                });

    }


    @Override
    protected void finish() {
        super.finish();
        Intent intent = new Intent();
        intent.putExtra("listData", (Serializable) proAddBeanList);
        mContext.setResult(TypeEnum.REFRESH, intent);
    }

    private List<AfterServiceProductBean> proAddBeanList = new ArrayList<>();
    private EventConsume<AfterServiceProductBean> proAddBeanEventConsume = (bean) -> {
        proAddBeanList.add(JsonUtil.fromJson(bean, new TypeToken<AfterServiceProductBean>() {
        }.getType()));
        DialogUtils.showDialog(mContext, "是否继续添加产品", "继续添加", "返回", new MyDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (!confirm) {
                    mContext.finish();
                }
            }
        }, false);

    };


    private AfterServiceProductSelectPop getProductSelectPop() {
        if (ProductSelectPop == null) {
            ProductSelectPop = new AfterServiceProductSelectPop(mContext);
            ProductSelectPop.setTopView(mBinding.afterServiceProductTop);
            ProductSelectPop.setProAddBeanEventConsume(proAddBeanEventConsume);
        }
        return ProductSelectPop;
    }
}
