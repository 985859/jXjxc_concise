package com.retrofithttp.api.util;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.FlowableTransformer;
import io.reactivex.rxjava3.core.MaybeTransformer;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/7/13 9:16
 */
public class RxThreadUtil {
    /**
     * Flowable 切换到主线程
     */
    public static <T> FlowableTransformer<T, T> flowableToMain() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                ;
    }

    /**
     * Observable 切换到主线程  在 DESTROY 结束绑定
     */
    public static <T> ObservableTransformer<T, T> observableToMain() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * Maybe 切换到主线程
     */
    public static <T> MaybeTransformer<T, T> maybeToMain() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
