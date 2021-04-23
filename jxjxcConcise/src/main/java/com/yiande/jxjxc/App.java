package com.yiande.jxjxc;

import android.app.Application;
import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.ObservableBoolean;
import androidx.multidex.MultiDex;


import com.hjq.toast.ToastUtils;
import com.mylibrary.api.Database.DatabaseUtil;
import com.mylibrary.api.utils.LogUtil;
import com.mylibrary.api.utils.SharedUtil;
import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.utils.SystemUtil;
import com.retrofithttp.api.RetrofitHttp;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshInitializer;
import com.yiande.jxjxc.bean.KeyBean;
import com.yiande.jxjxc.bean.LoginBean;
import com.yiande.jxjxc.myInterface.HttpApi;
import com.yiande.jxjxc.view.FooterView;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/7/6 10:08
 */
public class App extends Application {

    public static LoginBean token;
    public static String TOKEN = "Token";
    public static String ISENTER = "ISENTER";
    public static String CARNUM = "CARNUM";
    public static String BASEURL = "BASEURL";
    public static String IP = "IP";
    public static String IPTYPE = "IPTYPE";
    public static String VerCode;
    public static String UUID;
    public static String adminName;
    public static String adminMob;
    public static String comName;
    public static boolean isDebug;
    public static boolean isBind = true;
    public static boolean isFisrt = true;//是否第一次安装 协议对话框中设置
    public static int carNum;
    public static List<KeyBean> keyBeanList;
    public static Map<String, String> paramsMap;
    public static ObservableBoolean isEnter = new ObservableBoolean();//是否登录

    public static DatabaseUtil databaseUtil;


    static {
        //启用矢量图兼容
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        //设置全局默认配置（优先级最低，会被其他设置覆盖）
        SmartRefreshLayout.setDefaultRefreshInitializer(new DefaultRefreshInitializer() {
            @Override
            public void initialize(@androidx.annotation.NonNull Context context, @androidx.annotation.NonNull RefreshLayout layout) {
                //全局设置（优先级最低）
                layout.setEnableAutoLoadMore(true);
                layout.setEnableOverScrollDrag(false);
                layout.setEnableOverScrollBounce(true);
                layout.setEnableLoadMoreWhenContentNotFull(true);
                layout.setEnableScrollContentWhenRefreshed(true);
            }
        });
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @androidx.annotation.NonNull
            @Override
            public RefreshHeader createRefreshHeader(@androidx.annotation.NonNull Context context, @androidx.annotation.NonNull RefreshLayout layout) {
                //全局设置主题颜色（优先级第二低，可以覆盖 DefaultRefreshInitializer 的配置，与下面的ClassicsHeader绑定）
                layout.setPrimaryColorsId(R.color.background, R.color.textColor);
                return new ClassicsHeader(context);
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new FooterView(context).setDrawableSize(20);
            }
        });
    }


    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化MultiDex
        MultiDex.install(this);
        isDebug = SystemUtil.isApkInDebug(this);
        SharedUtil.getInstance(this);
        VerCode = SystemUtil.getVerCodeName(this);
        UUID = SystemUtil.getUUID(this);
        databaseUtil = DatabaseUtil.getInterface(this);
        initRetrofitHttp();
        //启用矢量图兼容
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        ToastUtils.init(this);
    }

    public static void initRetrofitHttp() {
        isEnter.set(SharedUtil.getBP(ISENTER, false));
        token = SharedUtil.getObjToBean(TOKEN, new LoginBean().getClass());
        paramsMap = new HashMap<>();
        String baseUrl = SharedUtil.getSP(BASEURL);
        if (StringUtil.isEmpty(baseUrl)) {
            baseUrl = HttpApi.baseUrl;
        } else {
            baseUrl += "/api/";
        }
        if (token != null) {
            paramsMap.put("access_token", token.getToken());
            adminMob = token.getAdmin_Mob();
            adminName = token.getAdmin_Name();
            if (isDebug)
                LogUtil.e("access_token= " + token.getToken());
        }
        paramsMap.put("phoneinfo", SystemUtil.getDeviceBrand() + "    " + SystemUtil.getSystemModel());
        paramsMap.put("phoneudid", UUID);
        paramsMap.put("v", VerCode);
        RetrofitHttp.getInstance()
                .setDEBUG(isDebug)
                .addHeaderParamsMap(paramsMap)
                .setBaseUrl(baseUrl)
                .init();
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }
}
