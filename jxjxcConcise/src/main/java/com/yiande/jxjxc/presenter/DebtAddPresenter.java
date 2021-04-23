package com.yiande.jxjxc.presenter;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;

import com.mylibrary.api.dialog.MyDialog;
import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.utils.ToastUtil;
import com.retrofithttp.api.RetrofitHttp;
import com.retrofithttp.api.util.RxThreadUtil;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.base.BasePresenter;
import com.yiande.jxjxc.base.HttpObserver;
import com.yiande.jxjxc.bean.JsonBean;
import com.yiande.jxjxc.databinding.ActivityDebtAddBinding;
import com.yiande.jxjxc.myInterface.HttpApi;
import com.yiande.jxjxc.myInterface.TypeEnum;
import com.yiande.jxjxc.utils.DialogUtils;
import com.yiande.jxjxc.utils.Util;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/15 16:14
 */
public class DebtAddPresenter extends BasePresenter<ActivityDebtAddBinding> {
    private int type;// 1欠款 2 赊账
    private int ID;
    private String name;


    public DebtAddPresenter(RxAppCompatActivity mContext, ActivityDebtAddBinding binding) {
        super(mContext, binding);
        Intent intent = getIntent();
        if (intent != null) {
            type = intent.getIntExtra("type", 1);
            ID = intent.getIntExtra("id", 0);
            name = intent.getStringExtra("name");
        }
        mBinding.setType(type);
        mBinding.setName(name);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mBinding.debtAddBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDebtAdd();
            }
        });
    }

    private void userDebtAdd() {
        if (StringUtil.isEmpty(mBinding.getMoney())) {
                 ToastUtil.showShort(mBinding.debtAddMoney.getHint());
            return;
        }
        String msg = "是否新增赊账";
        if (type == 1) {
            msg = "是否新增欠款";
        }
        DialogUtils.showDialog(mContext, msg, new MyDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (confirm) {
                    mBinding.debtAddBT.setEnabled(false);
                    RetrofitHttp.getRequest(HttpApi.class)
                            .userDebtAdd(ID, mBinding.getMoney(), mBinding.getMemo())
                            .compose(RxThreadUtil.observableToMain())
                            .compose(mContext.bindToLifecycle())
                            .subscribe(new HttpObserver<JsonBean<Object>>(mContext) {
                                @Override
                                public void onSuccess(JsonBean<Object> bean) {
                                    if (bean.code == 1) {
                                        mContext.setResult(TypeEnum.REFRESH);
                                    }
                                }

                                @Override
                                public void msgOnClick(boolean click, int type) {
                                    super.msgOnClick(click, type);
                                    if (type == 6) {
                                        Util.getAppFucntion(mContext, null);
                                    } else {
                                        mContext.finish();
                                    }
                                }

                                @Override
                                public void onFinsh() {
                                    super.onFinsh();
                                    mBinding.debtAddBT.setEnabled(true);
                                }
                            });
                }
            }
        });


    }
}
