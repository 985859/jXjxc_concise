package com.retrofithttp.api.base;

import android.content.Context;
import android.os.Looper;
import android.util.Log;

import com.google.gson.JsonSyntaxException;
import com.retrofithttp.api.LoadingDialog;

import java.net.UnknownHostException;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * @Description: 显示加载对话框
 * @Author: hukui
 * @Date: 2020/7/9 9:52
 */
public abstract class BaseObserver<T> implements Observer<T> {
    protected Context context;
    LoadingDialog dialog;
    /**
     * dialog 显示文字
     */
    private String msg; //对话框要显示的文字
    public boolean mShowLoading = true;//是否显示 loadingDialog 默认显示
    public boolean isCancelables = true;//是否显示 是否点击返回取消显示

    private BaseObserver() {}

    public BaseObserver(Context context) {
        this(context, true, true, "");
    }

    public BaseObserver(Context context, String msg) {
        this(context, true, true, msg);
    }

    public BaseObserver(Context context, boolean showLoading) {
        this(context, showLoading, true, "");
    }

    public BaseObserver(Context context, boolean showLoading, boolean isCancelables) {
        this(context, showLoading, isCancelables, "");
    }

    public BaseObserver(Context context, boolean showLoading, boolean isCancelables, String msg) {
        this.context = context;
        this.mShowLoading = showLoading;
        this.isCancelables = isCancelables;
        this.msg = msg;
    }


    @Override
    public void onNext(T bean) {
        try {
            hideDialog();
            onSuccess(bean);
        } catch (Exception e) {
            onError(e);
        }
    }


    public abstract void onSuccess(T bean);


    public void onFinsh() {
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        if (dialog == null && mShowLoading) {
            dialog = new LoadingDialog(context);
            dialog.setMessage(msg);
            dialog.setCancelable(isCancelables);
            dialog.setCanceledOnTouchOutside(isCancelables);
        }
        showDialog();
    }


    @Override
    public void onError(@NonNull Throwable e) {
        onFinsh();
        hideDialog();
        Log.e("BaseObserver onError", e.toString());
        //有异常需要处理
        if (e instanceof retrofit2.HttpException) {
            //HTTP错误
            onException(((retrofit2.HttpException) e).code());
        } else if (e instanceof UnknownHostException) {
            onException(0);
        } else if (e instanceof JsonSyntaxException) {
            onException(-2);
        } else {
            onException(-1);
        }
    }

    @Override
    public void onComplete() {
        onFinsh();
        hideDialog();
    }


    private void showDialog() {
        if (dialog != null && mShowLoading) {
            dialog.show();
        }
    }

    private void hideDialog() {
        if (dialog != null && dialog.isShowing() && mShowLoading) {
            dialog.dismiss();
        }
    }

    //是否在主线程中
    public boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    public void onException(int code) {

    }

}
