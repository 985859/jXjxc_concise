package com.yiande.jxjxc.base;

import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/7/17 14:14
 */
public class BasePresenter<T extends ViewDataBinding> {
    protected T mBinding;
    protected RxAppCompatActivity mContext;

    public BasePresenter(RxAppCompatActivity mContext, T binding) {
        this.mBinding = binding;
        this.mContext = mContext;
        initData();
        setListener();
    }

    protected void initData() {
    }

    protected void setListener() {
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    }

    protected Intent getIntent() {
        return mContext.getIntent();
    }
    protected void finish() {}

}
