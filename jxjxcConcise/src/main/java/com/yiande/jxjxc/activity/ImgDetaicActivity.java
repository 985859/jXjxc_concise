package com.yiande.jxjxc.activity;

import android.content.Context;

import androidx.collection.ArrayMap;

import com.mylibrary.api.utils.SkipUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BaseActivity;
import com.yiande.jxjxc.databinding.ActivityImgDetailBinding;
import com.yiande.jxjxc.presenter.ImgDetailPresenter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by admin on 2017/1/11.
 * 图片展示页面
 */

public class ImgDetaicActivity extends BaseActivity<ImgDetailPresenter, ActivityImgDetailBinding> {
    public static void start(Context context, String pic) {
        List<String> stringList = new ArrayList<>();
        stringList.add(pic);
        start(context, 0, stringList);
    }

    public static void start(Context context, int code, List<String> stringList) {
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("code", code);
        map.put("list", stringList);
        SkipUtil.skipActivity(context, ImgDetaicActivity.class, map, 0);
    }


    @Override
    protected int setLayoutId() {
        return R.layout.activity_img_detail;
    }

    @Override
    protected ImgDetailPresenter getPresenter() {
        return new ImgDetailPresenter(mContext, mBinding);
    }

    @Override
    protected void initImmersionBar() {
        mImmersionBar.statusBarView(mBinding.imgDetailCon).init();
    }
}
