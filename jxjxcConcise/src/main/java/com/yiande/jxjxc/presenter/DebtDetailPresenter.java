package com.yiande.jxjxc.presenter;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;

import com.mylibrary.api.dialog.MyDialog;
import com.mylibrary.api.managelayout.TopLinearLayoutManager;
import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.utils.ToastUtil;
import com.retrofithttp.api.RetrofitHttp;
import com.retrofithttp.api.util.RxThreadUtil;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.adapter.RepaymentAdapter;
import com.yiande.jxjxc.base.BasePresenter;
import com.yiande.jxjxc.base.HttpObserver;
import com.yiande.jxjxc.bean.DebtDaetailBean;
import com.yiande.jxjxc.bean.JsonBean;
import com.yiande.jxjxc.databinding.ActivityDebtDetailBinding;
import com.yiande.jxjxc.myInterface.HttpApi;
import com.yiande.jxjxc.myInterface.TypeEnum;
import com.yiande.jxjxc.utils.DialogUtils;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/17 14:56
 */
public class DebtDetailPresenter extends BasePresenter<ActivityDebtDetailBinding> {
    private int type;//1欠款 2 赊账
    private int ID;
    private DebtDaetailBean daetailBean;

    private RepaymentAdapter repaymentAdapter;

    public DebtDetailPresenter(RxAppCompatActivity mContext, ActivityDebtDetailBinding binding) {
        super(mContext, binding);
        Intent intent = getIntent();
        if (intent != null) {
            type = intent.getIntExtra("type", 1);
            ID = intent.getIntExtra("id", 0);
        }
        mBinding.setType(type);
        getData();
    }

    @Override
    protected void initData() {
        super.initData();
        repaymentAdapter = new RepaymentAdapter();
        mBinding.debtDetailRec.setAdapter(repaymentAdapter);
        mBinding.debtDetailRec.setLayoutManager(new TopLinearLayoutManager(mContext));

    }

    @Override
    protected void setListener() {
        super.setListener();
        mBinding.debtDetailPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postData();
            }
        });
    }


    private void getData() {
        RetrofitHttp.getRequest(HttpApi.class)
                .getDebtInfo(ID)
                .compose(RxThreadUtil.observableToMain())
                .compose(mContext.bindToLifecycle())
                .subscribe(new HttpObserver<JsonBean<DebtDaetailBean>>(mContext) {
                    @Override
                    public void onSuccess(JsonBean<DebtDaetailBean> bean) {
                        daetailBean = bean.data;
                        if (bean.code == 1) {
                            mBinding.setData(bean.data);
                            repaymentAdapter.setList(bean.data.getRepayRows());
                        }
                    }
                });
    }

    private void postData() {
        if (StringUtil.isEmpty(mBinding.getRepayMoney())) {
              ToastUtil.showShort( mContext.getString(R.string.repayment_hint));
            return;
        }
        double d = 0;
        if (daetailBean != null) {
            d = StringUtil.toDouble(daetailBean.getDebtNowMoney());
        }
        double d2 = StringUtil.toDouble(mBinding.getRepayMoney());
        if (d2 > d) {
            DialogUtils.showDialog(mContext, "还款金额大于" + mBinding.debtDetailMoney.getTitle() + "，不能提交还款");
        } else {
            DialogUtils.showDialog(mContext, "是否确定还款", new MyDialog.OnCloseListener() {
                @Override
                public void onClick(Dialog dialog, boolean confirm) {
                    if(confirm){
                        RetrofitHttp.getRequest(HttpApi.class)
                                .repayAdd(ID, mBinding.getRepayMoney(), mBinding.getMemo())
                                .compose(RxThreadUtil.observableToMain())
                                .compose(mContext.bindToLifecycle())
                                .subscribe(new HttpObserver<JsonBean<Object>>(mContext) {
                                    @Override
                                    public void onSuccess(JsonBean<Object> bean) {
                                        if (bean.code == 1) {
                                            mBinding.setMemo("");
                                            mBinding.setRepayMoney("");
                                            getData();
                                            mContext.setResult(TypeEnum.REFRESH);
                                        }

                                    }

                                    @Override
                                    public void msgOnClick(boolean click, int type) {
                                        super.msgOnClick(click, type);

                                    }
                                });
                    }
                }
            });

        }

    }


}
