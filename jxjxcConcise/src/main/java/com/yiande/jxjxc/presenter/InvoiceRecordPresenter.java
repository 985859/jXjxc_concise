package com.yiande.jxjxc.presenter;

import android.app.Dialog;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.mylibrary.api.dialog.MyDialog;
import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.utils.ToastUtil;
import com.retrofithttp.api.RetrofitHttp;
import com.retrofithttp.api.util.RxThreadUtil;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.activity.ImgDetaicActivity;
import com.yiande.jxjxc.activity.InvoiceAddActivity;
import com.yiande.jxjxc.adapter.InvoiceAdapter;
import com.yiande.jxjxc.base.BasePresenter;
import com.yiande.jxjxc.base.HttpObserver;
import com.yiande.jxjxc.bean.InvoiceRecordBean;
import com.yiande.jxjxc.bean.JsonBean;
import com.yiande.jxjxc.databinding.ActivityInvoiceRecordBinding;
import com.yiande.jxjxc.myInterface.EventConsume;
import com.yiande.jxjxc.myInterface.HttpApi;
import com.yiande.jxjxc.myInterface.TypeEnum;
import com.yiande.jxjxc.popwindow.AmendMemoPop;
import com.yiande.jxjxc.utils.DialogUtils;
import com.yiande.jxjxc.utils.Util;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/29 11:34
 */
public class InvoiceRecordPresenter extends BasePresenter<ActivityInvoiceRecordBinding> {
    private int oID;
    private int selecetID;
    private int type;//1 入库 2 出库
    private InvoiceAdapter invoiceAdapter;
    private AmendMemoPop amendMemoPop;
    private InvoiceRecordBean recordBean;
    private EventConsume<Integer> onClik = (type) -> {

        switch (type) {

            case 0://新增出票
                InvoiceAddActivity.start(mContext, oID);
                break;
            case 1://停止出票
                invoiceStop();
                break;
            default:
                break;
        }
    };

    public InvoiceRecordPresenter(RxAppCompatActivity mContext, ActivityInvoiceRecordBinding binding) {
        super(mContext, binding);
        Intent intent = getIntent();
        if (intent != null) {
            oID = intent.getIntExtra("OID", 0);
            type = intent.getIntExtra("type", 1);
            mBinding.setType(type);
        }
        invoiceAdapter.setUseEmpty(false);
        mBinding.setOnClick(onClik);
        getData();
    }

    @Override
    protected void initData() {
        super.initData();
        invoiceAdapter = new InvoiceAdapter();
        invoiceAdapter.setEmptyView(Util.getEmptyView(mContext));
        mBinding.invoiceRecordRec.setAdapter(invoiceAdapter);
        mBinding.invoiceRecordRec.setLayoutManager(new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
    }

    @Override
    protected void setListener() {
        super.setListener();

        invoiceAdapter.addChildClickViewIds(R.id.itmInvoice_More,R.id.itmInvoice_Pic);
        invoiceAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                switch (view.getId()) {
                    case R.id.itmInvoice_More:
                        selecetID = invoiceAdapter.getItem(position).getID();
                        getAmendMemoPop().setEdit("");
                        getAmendMemoPop().showPopupWindow(mBinding.invoiceRecordTop, Gravity.BOTTOM);
                        break;
                    case R.id.itmInvoice_Pic:
                        String pic= invoiceAdapter.getItem(position).getPic();
                        ImgDetaicActivity.start(mContext,pic);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void getData() {
        RetrofitHttp.getRequest(HttpApi.class)
                .getInvoiceList(oID)
                .compose(RxThreadUtil.observableToMain())
                .compose(mContext.bindToLifecycle())
                .subscribe(new HttpObserver<JsonBean<InvoiceRecordBean>>(mContext) {
                    @Override
                    public void onSuccess(JsonBean<InvoiceRecordBean> bean) {
                        if (bean.code == 1) {
                            recordBean = bean.data;
                            mBinding.setData(recordBean);
                            invoiceAdapter.setList(bean.data.getInvoiceRows());
                        }
                        invoiceAdapter.setUseEmpty(true);

                    }
                });
    }


    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/12/29
     * @Description 停止出票
     */
    private void invoiceStop() {
        if (recordBean == null) {
            return;
        }
        DialogUtils.showDialog(mContext, "是否停止出票", new MyDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (confirm) {
                    RetrofitHttp.getRequest(HttpApi.class)
                            .invoiceStop(oID)
                            .compose(RxThreadUtil.observableToMain())
                            .compose(mContext.bindToLifecycle())
                            .subscribe(new HttpObserver<JsonBean<Object>>(mContext) {
                                @Override
                                public void onSuccess(JsonBean<Object> bean) {
                                    if (bean.code == 1) {
                                        recordBean.setInvoiceState(0);
                                        mBinding.setData(recordBean);
                                    }

                                }
                            });
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

    private EventConsume<String> onselectListener = (memo) -> {

        if (StringUtil.isEmpty(memo)) {
                 ToastUtil.showShort( "请输入作废原因");
        }

        RetrofitHttp.getRequest(HttpApi.class)
                .invoiceDel(selecetID, memo)
                .compose(RxThreadUtil.observableToMain())
                .compose(mContext.bindToLifecycle())
                .subscribe(new HttpObserver<JsonBean<Object>>(mContext) {
                    @Override
                    public void onSuccess(JsonBean<Object> bean) {
                        if (bean.code == 1) {
                            getData();
                        }
                    }

                    @Override
                    public void onFinsh() {
                        super.onFinsh();
                        if (getAmendMemoPop().isShowing()) {
                            getAmendMemoPop().dismiss();
                        }
                    }
                });
    };

    private AmendMemoPop getAmendMemoPop() {
        if (amendMemoPop == null) {
            amendMemoPop = new AmendMemoPop(mContext);
            amendMemoPop.setTitle("作废原因");
            amendMemoPop.setHint("请输入作废原因");
            amendMemoPop.setOnSeclcetListener(onselectListener);
        }
        return amendMemoPop;
    }
}
