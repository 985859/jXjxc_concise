package com.yiande.jxjxc.presenter;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.luck.picture.lib.entity.LocalMedia;
import com.mylibrary.api.utils.StringUtil;
import com.retrofithttp.api.RetrofitHttp;
import com.retrofithttp.api.util.RxThreadUtil;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.activity.ImgDetaicActivity;
import com.yiande.jxjxc.activity.RichTextActivity;
import com.yiande.jxjxc.adapter.AfterServiceProAdapter;
import com.yiande.jxjxc.adapter.PicImageAdapter;
import com.yiande.jxjxc.base.BasePresenter;
import com.yiande.jxjxc.base.HttpObserver;
import com.yiande.jxjxc.bean.AfterServiceInfoBean;
import com.yiande.jxjxc.bean.JsonBean;
import com.yiande.jxjxc.databinding.ActivityAfterServiceDetailBinding;
import com.yiande.jxjxc.myInterface.HttpApi;
import com.yiande.jxjxc.utils.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/24 9:22
 */
public class AfterServiceDetailPresenter extends BasePresenter<ActivityAfterServiceDetailBinding> {

    private int ID;
    private PicImageAdapter imageAdapter;
    AfterServiceProAdapter proAdapter;
    private String content;

    public AfterServiceDetailPresenter(RxAppCompatActivity mContext, ActivityAfterServiceDetailBinding binding) {
        super(mContext, binding);
        Intent intent = getIntent();
        if (intent != null) {
            ID = intent.getIntExtra("id", 0);
        }

        getData();
    }

    @Override
    protected void initData() {
        super.initData();

        proAdapter = new AfterServiceProAdapter();
        proAdapter.setShow(false);
        mBinding.afterServiceDetailProductRec.setAdapter(proAdapter);
        mBinding.afterServiceDetailProductRec.setLayoutManager(new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        imageAdapter = new PicImageAdapter(null);
        imageAdapter.setShowDlear(false);
        mBinding.afterServiceDetailPic.LayoutRec.setAdapter(imageAdapter);
        mBinding.afterServiceDetailPic.LayoutRec.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
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
        mBinding.afterServiceDetailDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(StringUtil.isNotEmpty(content)){
                    RichTextActivity.start(mContext, "售后详情记录", content, false);
                }
            }
        });
    }

    private void getData() {
        RetrofitHttp.getRequest(HttpApi.class)
                .getServiceInfo(ID)
                .compose(RxThreadUtil.observableToMain())
                .compose(mContext.bindToLifecycle())
                .subscribe(new HttpObserver<JsonBean<AfterServiceInfoBean>>(mContext) {
                    @Override
                    public void onSuccess(JsonBean<AfterServiceInfoBean> bean) {
                        if (bean.code == 1) {
                            mBinding.setData(bean.data);
                            content = bean.data.getContent();
                            proAdapter.setList(bean.data.getServiceProduct());
                            if (Util.isNotListEmpty(bean.data.getPicList())) {
                                List<LocalMedia> selectList = new ArrayList<>();
                                for (String s : bean.data.getPicList()) {
                                    LocalMedia localMedia = new LocalMedia();
                                    localMedia.setPath(s);
                                    localMedia.setMimeType("url");
                                    selectList.add(localMedia);
                                }
                                imageAdapter.setList(selectList);


                            }
                        }

                    }
                });
    }


}
