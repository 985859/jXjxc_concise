package com.yiande.jxjxc.presenter;

import android.app.Dialog;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.mylibrary.api.dialog.MyDialog;
import com.mylibrary.api.managelayout.TopLinearLayoutManager;
import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.utils.ToastUtil;
import com.retrofithttp.api.RetrofitHttp;
import com.retrofithttp.api.util.RxThreadUtil;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.activity.DebtRepaymentSelectActivity;
import com.yiande.jxjxc.adapter.DebtDataAdapter;
import com.yiande.jxjxc.base.BasePresenter;
import com.yiande.jxjxc.base.HttpObserver;
import com.yiande.jxjxc.bean.DebtListBean;
import com.yiande.jxjxc.bean.JsonBean;
import com.yiande.jxjxc.databinding.ActivityDebtRepaymentBinding;
import com.yiande.jxjxc.myInterface.EventConsume;
import com.yiande.jxjxc.myInterface.HttpApi;
import com.yiande.jxjxc.myInterface.TypeEnum;
import com.yiande.jxjxc.popwindow.ItemPop;
import com.yiande.jxjxc.utils.DialogUtils;
import com.yiande.jxjxc.utils.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/17 17:16
 */
public class DebtRepaymentPresent extends BasePresenter<ActivityDebtRepaymentBinding> {

    private int type;//1欠款 2赊账
    private int ID;
    private String selectID;
    private int selectPosition;
    private String name;
    private ItemPop itemPop;
    private DebtDataAdapter dataAdapter;
    double debt = 0;
    private EventConsume<Integer> onClik = (state) -> {

        switch (state) {
            case 0://选择还款单
                DebtRepaymentSelectActivity.start(mContext, type, ID);
                break;
            case 1://提交还款单

                postData();
                break;
            default:
                break;
        }
    };

    public DebtRepaymentPresent(RxAppCompatActivity mContext, ActivityDebtRepaymentBinding binding) {
        super(mContext, binding);
        Intent intent = getIntent();
        if (intent != null) {
            type = intent.getIntExtra("type", 1);
            ID = intent.getIntExtra("id", 0);
            name = intent.getStringExtra("name");
        }
        mBinding.setType(type);
        mBinding.setOnClick(onClik);
        mBinding.setName(name);
        dataAdapter.setType(type);

    }

    @Override
    protected void initData() {
        super.initData();
        dataAdapter = new DebtDataAdapter();
        mBinding.debtRepaymentRec.setAdapter(dataAdapter);
        mBinding.debtRepaymentRec.setLayoutManager(new TopLinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
    }

    @Override
    protected void setListener() {
        super.setListener();
        dataAdapter.addChildClickViewIds(R.id.itmDebtData_More);
        dataAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                selectPosition = position;
                switch (view.getId()) {
                    case R.id.itmDebtData_More:
                        getItemPop().setTitle("序号" + (position + 1));
                        getItemPop().showPopupWindow(mBinding.debtRepaymentTop, Gravity.BOTTOM);
                        break;

                    default:
                        break;
                }
            }
        });
    }


    private void postData() {
        if (StringUtil.isEmpty(mBinding.getRepayMoney())) {
                ToastUtil.showShort(mContext.getString(R.string.repayment_hint));
            return;
        }

        if (Util.isListEmpty(dataAdapter.getData())) {
                ToastUtil.showShort("请选择需要的还款单");
            return;
        }
        selectID = "";
        for (int i = 0; i < dataAdapter.getData().size(); i++) {
            selectID += dataAdapter.getItem(i).getID();
            if (i != dataAdapter.getData().size() - 1) {
                selectID += ",";
            }
        }
        double d2 = StringUtil.toDouble(mBinding.getRepayMoney());
        if (d2 > debt) {
            DialogUtils.showDialog(mContext, "还款金额大于" + mBinding.debtRepaymentMoney.getTitle() + "，不能提交还款");
        } else {
            DialogUtils.showDialog(mContext, "是否确定还款", new MyDialog.OnCloseListener() {
                @Override
                public void onClick(Dialog dialog, boolean confirm) {
                    if (confirm) {
                        RetrofitHttp.getRequest(HttpApi.class)
                                .repayMoreAdd(selectID, mBinding.getRepayMoney(), mBinding.getMemo())
                                .compose(RxThreadUtil.observableToMain())
                                .compose(mContext.bindToLifecycle())
                                .subscribe(new HttpObserver<JsonBean<Object>>(mContext) {
                                    @Override
                                    public void onSuccess(JsonBean<Object> bean) {
                                        if (bean.code == 1) {
                                            mBinding.setMemo("");
                                            mBinding.setRepayMoney("");
                                            mContext.setResult(TypeEnum.REFRESH);
                                        }

                                    }

                                    @Override
                                    public void msgOnClick(boolean click, int type) {
                                        super.msgOnClick(click, type);
                                        mContext.finish();
                                    }
                                });
                    }
                }
            });

        }

    }

    EventConsume<Integer> itemClickListener = new EventConsume<Integer>() {
        @Override
        public void accept(Integer integer) {
            switch (integer) {
                case 0:
                    dataAdapter.remove(dataAdapter.getItem(selectPosition));
                    computeDebt();
                    break;

                default:
                    break;
            }
        }
    };

    private ItemPop getItemPop() {
        if (itemPop == null) {
            itemPop = new ItemPop(mContext);
            List<String> list = new ArrayList<>();
            list.add("删除");
            itemPop.setItem(list);
            itemPop.setOnItemClickListener(itemClickListener);
        }
        return itemPop;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case TypeEnum
                    .REFRESH:
                if (data != null) {
                    List<DebtListBean> beanList = (List<DebtListBean>) data.getSerializableExtra("data");
                    if (Util.isNotListEmpty(beanList)) {
                        if (Util.isListEmpty(dataAdapter.getData())) {
                            dataAdapter.setList(beanList);
                        } else {
                            List<DebtListBean> list = new ArrayList<>();
                            for (DebtListBean bean : beanList) {
                                for (DebtListBean bean1 : dataAdapter.getData()) {
                                    if (bean.getID() == bean1.getID()) {
                                        list.add(bean);
                                        break;
                                    }
                                }
                            }
                            if (list.size() > 0) {
                                beanList.removeAll(list);
                            }
                            if (beanList.size() > 0) {
                                dataAdapter.addData(beanList);
                            }
                        }
                        computeDebt();
                    }


                }
                break;
            default:
                break;
        }
    }


    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/12/18
     * @Description 计算所选欠款金额
     */
    private void computeDebt() {
        debt = 0;
        if (Util.isNotListEmpty(dataAdapter.getData())) {
            for (DebtListBean bean : dataAdapter.getData()) {
                debt += StringUtil.toDouble(bean.getDebtNow());
            }
            mBinding.setShowRec(true);
        } else {
            mBinding.setShowRec(false);
        }
        mBinding.debtRepaymentMoney.setText(String.valueOf(debt));
    }
}
