package com.yiande.jxjxc.presenter;

import android.app.Dialog;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.mylibrary.api.dialog.MyDialog;
import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.utils.ToastUtil;
import com.retrofithttp.api.RetrofitHttp;
import com.retrofithttp.api.util.RxThreadUtil;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.App;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.activity.ImgDetaicActivity;
import com.yiande.jxjxc.activity.ProductListActivity;
import com.yiande.jxjxc.adapter.OrderProAdapter;
import com.yiande.jxjxc.adapter.PicImageAdapter;
import com.yiande.jxjxc.base.BasePresenter;
import com.yiande.jxjxc.base.HttpObserver;
import com.yiande.jxjxc.bean.AdminListBean;
import com.yiande.jxjxc.bean.JsonBean;
import com.yiande.jxjxc.bean.KeyBean;
import com.yiande.jxjxc.bean.OrderBody;
import com.yiande.jxjxc.bean.OrderProAddBean;
import com.yiande.jxjxc.bean.SellUserInfoBean;
import com.yiande.jxjxc.bean.UserBean;
import com.yiande.jxjxc.databinding.ActivityProductInStockBinding;
import com.yiande.jxjxc.myInterface.EventConsume;
import com.yiande.jxjxc.myInterface.EventSendData;
import com.yiande.jxjxc.myInterface.HttpApi;
import com.yiande.jxjxc.myInterface.TypeEnum;
import com.yiande.jxjxc.popwindow.ItemPop;
import com.yiande.jxjxc.popwindow.ProductSelectPop;
import com.yiande.jxjxc.utils.DialogUtils;
import com.yiande.jxjxc.utils.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/9/19 15:34
 */
public class ProductInStockPresenter extends BasePresenter<ActivityProductInStockBinding> {

    private OrderProAdapter proAdapter;
    private PicImageAdapter imageAdapter;
    private OrderBody orderBody;
    private ProductSelectPop ProductSelectPop;
    private int selectIndex;
    private int supID;
    private ItemPop itemPop;
    private EventConsume<Integer> onClick = (type) -> {
        switch (type) {
            case 0://添加入库产品
                ProductListActivity.start(mContext, 1, supID, 0);
                break;
            case 1://提交数据
                addStockIn();
                break;
            case 2://选择图片
                addPic();
                break;

            default:
                break;
        }
    };

    public ProductInStockPresenter(RxAppCompatActivity mContext, ActivityProductInStockBinding binding) {
        super(mContext, binding);
        mBinding.setOnClick(onClick);
        orderBody = new OrderBody();
        orderBody.setOrder_FreightAmount("");
        orderBody.setOrder_Type(1);
        mBinding.setData(orderBody);
        if (Util.isShowEntry(Util.DRAWABILL)) {
            KeyBean keyBean = Util.getKeyBean(Util.DRAWABILL);
            if (keyBean.getValue()!=null && keyBean.getValue().getDefValue() == 1){
                mBinding.setState(0);
                orderBody.setOrder_Invoice_State(1);
            } else{
                mBinding.setState(1);
                orderBody.setOrder_Invoice_State(0);
            }
        } else {
            mBinding.setState(1);
            orderBody.setOrder_Invoice_State(0);

        }
        Intent intent = getIntent();
        supID = intent.getIntExtra("supID", 0);
        String name = intent.getStringExtra("name");
        if (StringUtil.isEmpty(name)) {
            name = "";
        }
        mBinding.stockInSupplier.setText(name);
        orderBody.setOrder_User_ID(supID);
        initDebt();
        getBuyOrSellUserInfo();
    }


    private void initDebt() {
        mBinding.setShowDebt(Util.isShowEntry(Util.DEBT));
    }

    @Override
    protected void initData() {
        super.initData();
        imageAdapter = new PicImageAdapter(null);
        mBinding.stockInPic.PicRec.setAdapter(imageAdapter);
        mBinding.stockInPic.PicRec.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

        proAdapter = new OrderProAdapter();
        mBinding.stockInProductRec.setAdapter(proAdapter);
        mBinding.stockInProductRec.setLayoutManager(new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mBinding.stockInHandelerName.setText(App.adminName);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mBinding.stockInInvoiceState.setOnselectListener(new EventConsume<Integer>() {
            @Override
            public void accept(Integer integer) {
                if (integer == 0) {
                    orderBody.setOrder_Invoice_State(1);
                } else {
                    orderBody.setOrder_Invoice_State(0);
                }
            }
        });
        mBinding.stockInState.setOnselectListener(new EventConsume<Integer>() {
            @Override
            public void accept(Integer type) {
                //Order_Type 1进货 2报溢 3出库 4报损
                switch (type) {
                    case 0:
                        orderBody.setOrder_Type(1);
                        break;
                    case 1:
                        orderBody.setOrder_Type(2);
                        break;
                    default:
                        break;
                }
            }
        });
        proAdapter.addChildClickViewIds(R.id.itmOrderPro_More);
        proAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                selectIndex = position;
                switch (view.getId()) {
                    case R.id.itmOrderPro_More:
                        getItemPop().setTitle(proAdapter.getData().get(position).getOrderDetail_Product_Title());
                        getItemPop().showPopupWindow(mBinding.stockInTop, Gravity.BOTTOM);
                        break;
                    default:
                        break;
                }
            }
        });
        imageAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                List<String> stringList = new ArrayList<>();
                for (LocalMedia media : imageAdapter.getData()) {
                    if ("url".equals(media.getMimeType())) {
                        stringList.add(media.getPath());
                    } else {
                        String path = Util.getPath(media);

                        stringList.add(path);
                    }
                }
                ImgDetaicActivity.start(mContext, position, stringList);
            }
        });
        mBinding.stockInPic.PicAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.accept(2);
            }
        });

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                allAmount();
            }
        };
        mBinding.stockInCash.getEditText().addTextChangedListener(watcher);
        mBinding.stockInBank.getEditText().addTextChangedListener(watcher);
        mBinding.stockInAlipay.getEditText().addTextChangedListener(watcher);
        mBinding.stockInWechat.getEditText().addTextChangedListener(watcher);
        mBinding.stockInPayableAmount.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setOnCredit();
            }
        });
    }

    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/12/8
     * @Description 计算合计金额
     */
    private void amount() {
        if (Util.isListEmpty(proAdapter.getData())) {
            mBinding.stockInAmount.setText("0.00");
            return;
        }
        double amount = 0.00;
        for (OrderProAddBean bean : proAdapter.getData()) {
            amount += StringUtil.toDouble(bean.getOrderDetail_Amount());
        }
        String str = String.valueOf(amount);
        mBinding.stockInAmount.setText(str);
    }

    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/12/8
     * @Description 计算实际应付金额
     */
    private void allAmount() {
        double d = getAllAmount();
        mBinding.stockInAllAmount.setText(StringUtil.doubleToString(d, "0.##"));
        setOnCredit();
    }

    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/12/8
     * @Description 计算赊账金额
     */
    private void setOnCredit() {
        double pay = StringUtil.toDouble(mBinding.stockInPayableAmount.getText());
        double onCredit = pay - getAllAmount();
        if (onCredit < 0) {
            onCredit = 0;
        }
        mBinding.stockInOnCredit.setText(StringUtil.doubleToString(onCredit, "0.##"));
    }

    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/12/8
     * @Description 获取实际支付金额之和
     */
    private double getAllAmount() {
        double amount = 0.00;
        double cashAmount = StringUtil.toDouble(mBinding.stockInCash.getText());
        double bankAmount = StringUtil.toDouble(mBinding.stockInBank.getText());
        double alipayAmount = StringUtil.toDouble(mBinding.stockInAlipay.getText());
        double wechatAmount = StringUtil.toDouble(mBinding.stockInWechat.getText());
        amount = cashAmount + bankAmount + alipayAmount + wechatAmount;
        return amount;
    }


    private void addPic() {
        Util.openCamera(mContext, 1, true, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case RESULT_OK:
                switch (requestCode) {
                    case PictureConfig.CHOOSE_REQUEST:
                        // 结果回调
                        List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);

                        if (Util.isNotListEmpty(selectList)) {

                            imageAdapter.setList(selectList);
                        } else {
                                ToastUtil.showShort( "获取图片失败");
                        }

                        break;
                    default:
                        break;
                }
                break;
            case TypeEnum.REFRESH:
                if (data != null) {
                    List<OrderProAddBean> addBeanList = (List<OrderProAddBean>) data.getSerializableExtra("listData");
                    setProData(addBeanList);
                }

                break;
            default:
                break;
        }

    }

    private void setProData(List<OrderProAddBean> data) {
        if (data == null || data.size() == 0) {
            return;
        }
        if (Util.isNotListEmpty(proAdapter.getData())) {

            for (int i = 0; i < data.size(); i++) {
                OrderProAddBean bean = data.get(i);
                boolean isAdd = true;
                for (int k = 0; k < proAdapter.getData().size(); k++) {
                    OrderProAddBean bean1 = proAdapter.getData().get(k);
                    if (bean.getProduct_ID() == bean1.getProduct_ID()) {
                        proAdapter.setData(k, bean);
                        isAdd = false;
                        break;
                    }
                }
                if (isAdd) {
                    proAdapter.addData(bean);
                }
            }
            amount();
        } else {
            proAdapter.addData(data);
            amount();
        }
        showProRec();
    }


    private void showProRec() {
        if (Util.isNotListEmpty(proAdapter.getData())) {
            mBinding.setShowRec(true);
        } else {
            mBinding.setShowRec(false);
        }

    }


    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/12/12
     * @Description 获取供应商信息
     */
    private void getBuyOrSellUserInfo() {
        if (supID == 0 || !Util.isShowEntry(Util.DEBT)) {
            mBinding.setShowDebt(false);
            return;
        }

        RetrofitHttp.getRequest(HttpApi.class)
                .getBuyOrSellUserInfo(supID)
                .compose(RxThreadUtil.observableToMain())
                .compose(mContext.bindToLifecycle())
                .subscribe(new HttpObserver<JsonBean<SellUserInfoBean>>(mContext) {
                    @Override
                    public void onSuccess(JsonBean<SellUserInfoBean> bean) {
                        if (bean.code == 1) {
                            mBinding.stockInSupplier.setText(bean.data.getComName());
                            mBinding.stockInAllOnCredit.setText(bean.data.getDebt());
                        }
                    }
                });
    }


    private EventConsume<OrderProAddBean> proAddBeanEventConsume = (bean) -> {
        proAdapter.setData(selectIndex, bean);
        amount();
    };

    private ProductSelectPop getProductSelectPop() {
        if (ProductSelectPop == null) {
            ProductSelectPop = new ProductSelectPop(mContext, 1);
            ProductSelectPop.setProAddBeanEventConsume(proAddBeanEventConsume);
        }
        return ProductSelectPop;
    }


    private EventSendData<AdminListBean, Boolean> eventConsume = (bean, isReset) -> {
        if (isReset) {
            orderBody.setOrder_Worker_ID(0);
        } else {
            if (bean != null) {
                orderBody.setOrder_Worker_ID(bean.getAdmin_ID());
            }

        }

    };


    private EventSendData<UserBean, Boolean> userBeanEventConsume = (bean, isReset) -> {
        if (isReset) {
            orderBody.setOrder_User_ID(0);
        } else {
            if (bean != null) {
                orderBody.setOrder_User_ID(bean.getUser_ID());
            }

        }

    };


    EventConsume<Integer> itemClickListener = new EventConsume<Integer>() {
        @Override
        public void accept(Integer integer) {
            switch (integer) {
                case 0:
                    getProductSelectPop().setProAddBean(proAdapter.getData().get(selectIndex));
                    getProductSelectPop().showPopupWindow(mBinding.stockInTop, Gravity.BOTTOM);
                    break;
                case 1:
                    DialogUtils.showDialog(mContext, "是否删除  " + proAdapter.getItem(selectIndex).getOrderDetail_Product_Title(), new MyDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            if (confirm) {
                                proAdapter.removeAt(selectIndex);
                                showProRec();
                                amount();
                            }
                        }
                    });
                    break;
                default:
                    break;
            }
        }
    };

    private ItemPop getItemPop() {
        if (itemPop == null) {
            itemPop = new ItemPop(mContext);
            itemPop.setOnItemClickListener(itemClickListener);
        }
        return itemPop;
    }

    private void addStockIn() {

        if (Util.isListEmpty(proAdapter.getData())) {
                ToastUtil.showShort( "请选择入库的产品");
            return;
        }
        if (StringUtil.isEmpty(mBinding.stockInPayableAmount.getText().trim())) {
                ToastUtil.showShort( mBinding.stockInPayableAmount.getHint());
            return;
        }

        double d = StringUtil.toDouble(mBinding.stockInPayableAmount.getText().trim());
        double d1 = StringUtil.toDouble(mBinding.stockInAllAmount.getText().trim());
        if (d1 > d) {
            DialogUtils.showDialog(mContext, "实际付款金额大于应付金额，无法提交");
        } else if (d1 == d) {
            upPic();
        } else {
            DialogUtils.showDialog(mContext, "实际付款金额小于应付金额，是否提交", new MyDialog.OnCloseListener() {
                @Override
                public void onClick(Dialog dialog, boolean confirm) {
                    if (confirm) {
                        upPic();
                    }
                }
            });
        }

    }

    private String pic = "";

    private void upPic() {
        pic = "";

        if (orderBody != null) {
            orderBody.setOrder_Detail(proAdapter.getData());
            if (Util.isNotListEmpty(imageAdapter.getData())) {
                List<File> fileList = new ArrayList<>();
                for (LocalMedia media : imageAdapter.getData()) {
                    if ("url".equals(media.getMimeType())) {
                        pic += media.getCutPath() + ',';
                    } else {
                        String path = Util.getPath(media);
                        File file = Util.compressFile(mContext, path);
                        fileList.add(file);
                    }
                }
                orderBody.setOrder_OriginalPic(pic);
                if (fileList.size() > 0) {
                    mBinding.stockInBT.setEnabled(false);
                    RetrofitHttp.getRequest(HttpApi.class)
                            .uploadImage(Util.filesToMultipartBody(fileList), 0)
                            .compose(RxThreadUtil.observableToMain())
                            .compose(mContext.bindToLifecycle())
                            .subscribe(new HttpObserver<JsonBean<String>>(mContext) {
                                @Override
                                public void onSuccess(JsonBean<String> bean) {
                                    if (bean.code == 1) {
                                        if (StringUtil.isNotEmpty(bean.data)) {
                                            pic += bean.data;
                                            orderBody.setOrder_OriginalPic(pic);
                                            postData();
                                        }
                                    }

                                }

                                @Override
                                public void onFinsh() {
                                    super.onFinsh();
                                    mBinding.stockInBT.setEnabled(true);
                                }
                            });

                } else {
                    postData();
                }

            } else {
                postData();
            }

        }

    }


    private void postData() {
        mBinding.stockInBT.setEnabled(false);
        HttpObserver observer = new HttpObserver<JsonBean<Object>>(mContext) {
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
                    Util.getAppFucntion(mContext, (code) -> {
                        if (code == 1) {
                            initDebt();
                        }
                    });
                } else {
                    mContext.finish();
                }

            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                super.onError(e);
            }

            @Override
            public void onFinsh() {
                super.onFinsh();
                mBinding.stockInBT.setEnabled(true);
            }
        };
        RetrofitHttp.getRequest(HttpApi.class)
                .orderBuyAdd(orderBody)
                .compose(RxThreadUtil.observableToMain())
                .compose(mContext.bindToLifecycle())
                .subscribe(observer);

    }


}
