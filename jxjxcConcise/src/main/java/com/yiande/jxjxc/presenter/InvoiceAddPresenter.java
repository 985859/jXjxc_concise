package com.yiande.jxjxc.presenter;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.utils.ToastUtil;
import com.retrofithttp.api.RetrofitHttp;
import com.retrofithttp.api.util.RxThreadUtil;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.activity.ImgDetaicActivity;
import com.yiande.jxjxc.adapter.PicImageAdapter;
import com.yiande.jxjxc.base.BasePresenter;
import com.yiande.jxjxc.base.HttpObserver;
import com.yiande.jxjxc.bean.JsonBean;
import com.yiande.jxjxc.databinding.ActivityInvoiceAddBinding;
import com.yiande.jxjxc.myInterface.HttpApi;
import com.yiande.jxjxc.myInterface.TypeEnum;
import com.yiande.jxjxc.utils.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/29 11:10
 */
public class InvoiceAddPresenter extends BasePresenter<ActivityInvoiceAddBinding> {
    private int oID;
    private PicImageAdapter imageAdapter;

    public InvoiceAddPresenter(RxAppCompatActivity mContext, ActivityInvoiceAddBinding binding) {
        super(mContext, binding);
        Intent intent = getIntent();
        if (intent != null) {
            oID = intent.getIntExtra("OID", 0);
        }
    }

    @Override
    protected void initData() {
        super.initData();
        imageAdapter = new PicImageAdapter(null);
        mBinding.invoiceAddPic.PicRec.setAdapter(imageAdapter);
        mBinding.invoiceAddPic.PicRec.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    protected void setListener() {
        super.setListener();
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
        mBinding.invoiceAddPic.PicAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.openCamera(mContext, 1, true, false);
            }
        });
        mBinding.invoiceAddBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upPic();
            }
        });
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

            default:
                break;
        }

    }

    private String pic = "";

    private void upPic() {
        if (StringUtil.isEmpty(mBinding.getMoney())) {
           ToastUtil.showShort( mContext.getString(R.string.invoice_amount_hint));
            return;
        }
        pic = "";
        if (Util.isNotListEmpty(imageAdapter.getData())) {
            List<File> fileList = new ArrayList<>();
            for (LocalMedia media : imageAdapter.getData()) {
                String path = Util.getPath(media);
                File file = Util.compressFile(mContext, path);
                fileList.add(file);
            }
            if (fileList.size() > 0) {
                mBinding.invoiceAddBT.setEnabled(false);
                RetrofitHttp.getRequest(HttpApi.class)
                        .uploadImage(Util.filesToMultipartBody(fileList), 2)
                        .compose(RxThreadUtil.observableToMain())
                        .compose(mContext.bindToLifecycle())
                        .subscribe(new HttpObserver<JsonBean<String>>(mContext) {
                            @Override
                            public void onSuccess(JsonBean<String> bean) {
                                if (bean.code == 1) {
                                    if (StringUtil.isNotEmpty(bean.data)) {
                                        pic += bean.data;
                                        postData();
                                    }
                                }

                            }

                            @Override
                            public void onFinsh() {
                                super.onFinsh();
                                mBinding.invoiceAddBT.setEnabled(true);
                            }
                        });

            } else {
                postData();
            }

        } else {
            postData();
        }

    }


    private void postData() {
        mBinding.invoiceAddBT.setEnabled(false);
        RetrofitHttp.getRequest(HttpApi.class)
                .invoiceAdd(oID, pic, mBinding.getMoney(), mBinding.getMemo())
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
                        mContext.finish();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        super.onError(e);
                    }

                    @Override
                    public void onFinsh() {
                        super.onFinsh();
                        mBinding.invoiceAddBT.setEnabled(true);
                    }
                });

    }
}
