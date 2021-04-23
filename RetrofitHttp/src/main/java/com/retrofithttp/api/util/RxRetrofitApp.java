package com.retrofithttp.api.util;

import android.content.Context;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/9/7 9:37
 */
public class RxRetrofitApp {
    private static Context context;

    public static void setApplication(Context context0) {
        context = context0;
    }

    public static Context getApplication() {
        return context;
    }
}
