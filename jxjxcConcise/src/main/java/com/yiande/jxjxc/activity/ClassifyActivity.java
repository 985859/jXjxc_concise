package com.yiande.jxjxc.activity;

import android.content.Context;

import androidx.collection.ArrayMap;

import com.mylibrary.api.utils.SkipUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BaseActivity;
import com.yiande.jxjxc.bean.OClassBean;
import com.yiande.jxjxc.databinding.ActivityClassfityBinding;
import com.yiande.jxjxc.presenter.ClassifyPresenter;

import java.util.List;

public class ClassifyActivity extends BaseActivity<ClassifyPresenter, ActivityClassfityBinding> {
    public static void start(Context context, List<OClassBean> data,int q) {
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("data", data);
        SkipUtil.skipActivity(context, ClassifyActivity.class, map, q);
    }
    @Override
    protected int setLayoutId() {
        return R.layout.activity_classfity;
    }


    @Override
    protected ClassifyPresenter getPresenter() {
        return new ClassifyPresenter(mContext, mBinding);
    }

    @Override
    protected void initImmersionBar() {
        mImmersionBar.titleBar(mBinding.classfityTop).init();
    }
}