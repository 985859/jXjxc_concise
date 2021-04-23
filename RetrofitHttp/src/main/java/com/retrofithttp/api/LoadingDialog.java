package com.retrofithttp.api;

/**
 * @Description: 网络加载对话框
 * @Author: hukui
 * @Date: 2020/7/9 11:22
 */

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.retrofithttp.api.base.LoadingView;

/**
 * 自定义加载进度对话框
 * Created by Dylan on 2016-10-28.
 */

public class LoadingDialog extends Dialog {
    private LoadingView loadingView;

    public LoadingDialog(@NonNull Context context) {
        this(context, R.style.loaddialog);
    }

    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.logingdialog);
        loadingView = findViewById(R.id.loadingView);
        setCanceledOnTouchOutside(false);
    }




    /**
     * 为加载进度个对话框设置不同的提示消息
     *
     * @param message 给用户展示的提示信息
     * @return build模式设计，可以链式调用
     */
    public LoadingDialog setMessage(String message) {
        if (!TextUtils.isEmpty(message))
            loadingView.setText(message);
        return this;
    }
}
