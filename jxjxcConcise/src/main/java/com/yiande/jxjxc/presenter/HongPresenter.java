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
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BasePresenter;
import com.yiande.jxjxc.base.HttpObserver;
import com.yiande.jxjxc.bean.JsonBean;
import com.yiande.jxjxc.bean.OrderInfoBean;
import com.yiande.jxjxc.databinding.ActivityHongBinding;
import com.yiande.jxjxc.myInterface.HttpApi;
import com.yiande.jxjxc.myInterface.TypeEnum;
import com.yiande.jxjxc.utils.DialogUtils;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/11/13 9:59
 */
public class HongPresenter extends BasePresenter<ActivityHongBinding> {

    private int type;//0 出库单 1 入库单
    private OrderInfoBean buyBean;

    public HongPresenter(RxAppCompatActivity mContext, ActivityHongBinding binding) {
        super(mContext, binding);
        Intent intent = getIntent();
        if (intent != null) {
            type = intent.getIntExtra("type", 0);
            buyBean = (OrderInfoBean) intent.getSerializableExtra("bean");
            mBinding.setType(type);
            mBinding.setData(buyBean);
            String str = "";
            int colorType = 0;
            if (buyBean != null) {
                //Order_Type 1进货 2报溢 3出库 4报损
                switch (buyBean.getOrder_Type()) {
                    case 1:
                        str = "进货";
                        mBinding.hongType.setBackgroundColor(mContext.getResources().getColor(R.color.blue));
                        break;
                    case 2:
                        str = "报溢";
                        colorType = 1;
                        mBinding.hongType.setBackgroundColor(mContext.getResources().getColor(R.color.yellow));
                        break;
                    case 3:
                        str = "出库";
                        mBinding.hongType.setBackgroundColor(mContext.getResources().getColor(R.color.green));
                        break;
                    case 4:
                        str = "报损";
                        colorType = 1;
                        mBinding.hongType.setBackgroundColor(mContext.getResources().getColor(R.color.yellow));
                        break;
                    default:
                        break;
                }
            }
            mBinding.hongType.setText(str);
        }
    }

    @Override
    protected void setListener() {
        super.setListener();
        mBinding.hongBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String memo = mBinding.hongMemo.getText();
                if (StringUtil.isEmpty(memo)) {
                    ToastUtil.showShort( mContext.getString(R.string.hint_hong));
                    return;
                }

                double debt = StringUtil.toDouble(buyBean.getOrder_Debt());
                String msg = "是否确认红冲";
                if (debt > 0) {
                    if (type == 0) {
                        msg = "此出库单有欠款记录,红冲后将删除欠款记录! 请谨慎操作!";
                    } else {
                        msg = "此入库单有赊账记录,红冲后将删除赊账记录! 请谨慎操作!";
                    }
                }

                DialogUtils.showDialog(mContext, msg, "确认红冲", "取消", new MyDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if (confirm) {
                            orderHong();
                        }
                    }
                }, false);

            }
        });
    }

    private void orderHong() {
        mBinding.hongBT.setEnabled(false);
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
                mBinding.hongBT.setEnabled(true);
            }
        };


        if (type == 0) {
            RetrofitHttp.getRequest(HttpApi.class)
                    .orderSellHong(buyBean.getOrder_ID(), mBinding.hongMemo.getText())
                    .compose(RxThreadUtil.observableToMain())
                    .compose(mContext.bindToLifecycle())
                    .subscribe(observer);
        } else {
            RetrofitHttp.getRequest(HttpApi.class)
                    .orderBuyHong(buyBean.getOrder_ID(), mBinding.hongMemo.getText())
                    .compose(RxThreadUtil.observableToMain())
                    .compose(mContext.bindToLifecycle())
                    .subscribe(observer);
        }
    }
}
