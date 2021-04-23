package com.yiande.jxjxc.presenter;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mylibrary.api.dialog.MyDialog;
import com.mylibrary.api.utils.ImageUtils;
import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.utils.SystemUtil;
import com.retrofithttp.api.RetrofitHttp;
import com.retrofithttp.api.util.RxThreadUtil;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;

import com.yiande.jxjxc.App;
import com.yiande.jxjxc.activity.HongActivity;
import com.yiande.jxjxc.activity.ImgDetaicActivity;
import com.yiande.jxjxc.adapter.OrderProAdapter;
import com.yiande.jxjxc.base.BasePresenter;
import com.yiande.jxjxc.base.HttpObserver;
import com.yiande.jxjxc.bean.JsonBean;
import com.yiande.jxjxc.bean.OrderInfoBean;
import com.yiande.jxjxc.myInterface.EventConsume;
import com.yiande.jxjxc.myInterface.HttpApi;
import com.yiande.jxjxc.myInterface.TypeEnum;
import com.yiande.jxjxc.popwindow.AmendMemoPop;
import com.yiande.jxjxc.utils.DialogUtils;
import com.yiande.jxjxc.utils.Util;
import com.yiande.jxjxc.databinding.ActivityProductInStockDetailBinding;


/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/9/20 21:01
 */
public class ProductInStockDetailPresenter extends BasePresenter<ActivityProductInStockDetailBinding> {
    private int ID;
    private OrderInfoBean buyBean;
    private OrderProAdapter proAdapter;
    private AmendMemoPop amendMemoPop;

    public ProductInStockDetailPresenter(RxAppCompatActivity mContext, ActivityProductInStockDetailBinding binding) {
        super(mContext, binding);
        Intent intent = getIntent();
        if (intent != null) {
            ID = intent.getIntExtra("ID", 0);
            getData();
        }
        mBinding.inStockDetailLayout.setType(1);
        initAmendMemoPop();
        initDebt();
    }

    private void initDebt() {
        mBinding.setShowOnCredit(Util.getKeyBean(Util.DEBT) == null ? false : true);
    }

    @Override
    protected void initData() {
        super.initData();
        proAdapter = new OrderProAdapter();
        proAdapter.setType(1);
        proAdapter.setShow(false);
        mBinding.inStockDetailProductRec.setAdapter(proAdapter);
        mBinding.inStockDetailProductRec.setLayoutManager(new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mBinding.stockInDetailOriginalPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buyBean != null) {
                    ImgDetaicActivity.start(mContext, buyBean.getOrder_OriginalPic());
                }

            }
        });

        mBinding.stockInHandelerName.setText(App.adminName);

    }

    @Override
    protected void setListener() {
        super.setListener();
        mBinding.inStockDetailTop.setRightViewOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //检测是否有写的权限
                    int permission = ActivityCompat.checkSelfPermission(mContext, "android.permission.WRITE_EXTERNAL_STORAGE");
                    if (permission != PackageManager.PERMISSION_GRANTED) {
                        DialogUtils.showDialog(mContext, "保存到相册,需要您的存储权限，请前往设置》 权限》 存储 打开", new MyDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                if (confirm) {
                                    SystemUtil.skipSet(mContext);
                                }
                            }
                        });
                    } else {
                        mBinding.inStockDetailLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                int width = mBinding.inStockDetailLayout.getWidth();
                                int height = mBinding.inStockDetailLayout.getHeight();
                                Bitmap bitmap = ImageUtils.viewToBitmap(mBinding.inStockDetailLayout, width, height);
                                ImageUtils.saveBitmapToCamera(mContext, bitmap);

                            }


                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mBinding.stockInDetailHong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buyBean != null)
                    HongActivity.start(mContext, 1, buyBean);
            }
        });
        mBinding.stockInDetailMemo.setEditBTListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buyBean != null) {
                    amendMemoPop.setEdit(buyBean.getOrder_Memo());
                }
                amendMemoPop.showPopupWindow(mBinding.inStockDetailTop, Gravity.BOTTOM);

            }
        });
    }

    private void getData() {
        RetrofitHttp.getRequest(HttpApi.class)
                .getBuyOrderInfoV2(ID)
                .compose(RxThreadUtil.observableToMain())
                .compose(mContext.bindToLifecycle())
                .subscribe(new HttpObserver<JsonBean<OrderInfoBean>>(mContext) {
                    @Override
                    public void onSuccess(JsonBean<OrderInfoBean> bean) {
                        if (bean.code == 1) {
                            buyBean = bean.data;
                            mBinding.setData(bean.data);
                            proAdapter.setList(bean.data.getOrder_Detail());
                            mBinding.inStockDetailLayout.setBean(bean.data);
                        }

                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {

            case TypeEnum.REFRESH:
                mContext.setResult(TypeEnum.REFRESH);
                getData();
                break;
            default:
                break;
        }
    }


    private EventConsume<String> onselectListener = (memo) -> {
        if (StringUtil.isEmpty(memo)) {
            memo = "";
        }
        String finalMemo = memo;
        RetrofitHttp.getRequest(HttpApi.class)
                .modifyMemo(ID, memo)
                .compose(RxThreadUtil.observableToMain())
                .compose(mContext.bindToLifecycle())
                .subscribe(new HttpObserver<JsonBean<Object>>(mContext) {
                    @Override
                    public void onSuccess(JsonBean<Object> bean) {
                        if (bean.code == 1) {
                            buyBean.setOrder_Memo(finalMemo);
                            mBinding.stockInDetailMemo.setText(finalMemo);
                            amendMemoPop.dismiss();
                            mContext.setResult(TypeEnum.REFRESH);
                        }

                    }
                });
    };

    private void initAmendMemoPop() {
        if (amendMemoPop == null) {
            amendMemoPop = new AmendMemoPop(mContext);
            amendMemoPop.setOnSeclcetListener(onselectListener);
        }

    }
}
