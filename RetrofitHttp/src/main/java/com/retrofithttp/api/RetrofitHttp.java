package com.retrofithttp.api;


import com.retrofithttp.api.interceptor.BasicParamsInterceptor;
import com.retrofithttp.api.interceptor.RetryInterceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Description: 初始化请求管理
 * @Author: hukui
 * @Date: 2020/7/10 8:57
 */
public class RetrofitHttp {

    /*基础url*/
    public static String baseUrl = "";
    /*连接超时时间*/
    private long connectTimeout = 20;
    /*读取超时时间*/
    private long readTimeout = 20;
    /*写入超时时间*/
    private long writeTimeout = 20;
    /*是否打印日志  默认不打印 */
    public boolean DEBUG = false;
    /*有网情况下的本地缓存时间默认60秒*/
    private int cookieNetWorkTime = 60;
    /*无网络的情况下本地缓存时间默认30天*/
    private int cookieNoNetWorkTime = 24 * 60 * 60 * 30;
    /* 失败后retry次数*/
    private int retryCount = 2;////假如设置为2次重试的话，则最大可能请求4次（默认1次+2次重试 + 最后一次默认）
    /*失败后retry延迟*/
    private long retryDelay = 3000;
    /*失败后retry叠加延迟*/
    private long retryIncreaseDelay = 3000;

    /*是否需要缓存处理*/
    private boolean cache;
    /*方法-如果需要缓存必须设置这个参数；不需要不用設置*/
    private String method = "";
    /*缓存url-可手动设置*/
    private String cacheUrl;

    Map<String, String> queryParamsMap = new HashMap<>();
    Map<String, String> paramsMap = new HashMap<>();
    Map<String, String> headerParamsMap = new HashMap<>();
    List<String> headerLinesList = new ArrayList<>();

    private static RetrofitHttp mInstance;
    private static Retrofit retrofit;

    private RetrofitHttp() {}

    public static RetrofitHttp getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitHttp.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitHttp();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化必要对象和参数
     */
    public void init() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(connectTimeout, TimeUnit.SECONDS)//设置连接超时时间
                .readTimeout(readTimeout, TimeUnit.SECONDS) //设置读取超时时间
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)  //设置写入超时时间
                .addInterceptor(new RetryInterceptor(retryCount, retryDelay, retryIncreaseDelay))  //添加错误重试次数
                .addInterceptor(httpLoggingInterceptor); //添加日志过滤器
        //添加公共参数拦截器
        addaramsInterceptor(builder);
        // 初始化okhttp
        OkHttpClient client = builder.build();
        //初始化Retrofit
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static <T> T getRequest(Class<T> requestApi) {
        return retrofit.create(requestApi);
    }


    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/7/10
     * @Description 添加公共参数  头部拦截器
     */
    private void addaramsInterceptor(OkHttpClient.Builder builder) {
        if (builder != null && (headerLinesList.size() > 0 || headerParamsMap.size() > 0
                || queryParamsMap.size() > 0 || paramsMap.size() > 0)) {
            BasicParamsInterceptor.Builder paramsBuilder = new BasicParamsInterceptor.Builder();
            if (headerLinesList.size() > 0)
                paramsBuilder.addHeaderLinesList(headerLinesList);
            if (headerParamsMap.size() > 0)
                paramsBuilder.addHeaderParamsMap(headerParamsMap);
            if (queryParamsMap.size() > 0)
                paramsBuilder.addQueryParamsMap(queryParamsMap);
            if (paramsMap.size() > 0)
                paramsBuilder.addParamsMap(paramsMap);
            builder.addInterceptor(paramsBuilder.build());
        }
    }

    public RetrofitHttp setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public RetrofitHttp setCookieNetWorkTime(int cookieNetWorkTime) {
        this.cookieNetWorkTime = cookieNetWorkTime;
        return this;
    }

    public RetrofitHttp setCookieNoNetWorkTime(int cookieNoNetWorkTime) {
        this.cookieNoNetWorkTime = cookieNoNetWorkTime;
        return this;
    }

    public RetrofitHttp setRetryCount(int retryCount) {
        this.retryCount = retryCount;
        return this;
    }

    public RetrofitHttp setRetryDelay(long retryDelay) {
        this.retryDelay = retryDelay;
        return this;
    }

    public RetrofitHttp setRetryIncreaseDelay(long retryIncreaseDelay) {
        this.retryIncreaseDelay = retryIncreaseDelay;
        return this;
    }

    public RetrofitHttp setCache(boolean cache) {
        this.cache = cache;
        return this;
    }

    public RetrofitHttp setMethod(String method) {
        this.method = method;
        return this;
    }

    public RetrofitHttp setCacheUrl(String cacheUrl) {
        this.cacheUrl = cacheUrl;
        return this;
    }

    public RetrofitHttp setConnectTimeout(long connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public RetrofitHttp setReadTimeout(long readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    public RetrofitHttp setWriteTimeout(long writeTimeout) {
        this.writeTimeout = writeTimeout;
        return this;
    }

    public RetrofitHttp setDEBUG(boolean DEBUG) {
        this.DEBUG = DEBUG;
        return this;
    }

    public RetrofitHttp addParam(String key, String value) {
        paramsMap.put(key, value);
        return this;
    }

    public RetrofitHttp addParamsMap(Map<String, String> paramMap) {
        if (paramMap != null && paramMap.size() > 0)
            paramsMap.putAll(paramMap);
        return this;
    }

    public RetrofitHttp addHeaderParam(String key, String value) {
        headerParamsMap.put(key, value);
        return this;
    }

    public RetrofitHttp addHeaderParamsMap(Map<String, String> paramsMap) {
        if (paramsMap != null && paramsMap.size() > 0)
            headerParamsMap.putAll(paramsMap);
        return this;
    }

    public RetrofitHttp addHeaderLine(String headerLine) {
        int index = headerLine.indexOf(":");
        if (index == -1) {
            throw new IllegalArgumentException("Unexpected header: " + headerLine);
        }
        headerLinesList.add(headerLine);
        return this;
    }

    public RetrofitHttp addHeaderLinesList(List<String> linesList) {
        if (linesList != null || linesList.size() > 0) {
            for (String headerLine : linesList) {
                int index = headerLine.indexOf(":");
                if (index == -1) {
                    throw new IllegalArgumentException("Unexpected header: " + headerLine);
                }
                headerLinesList.add(headerLine);
            }
        }

        return this;
    }

    public RetrofitHttp addQueryParam(String key, String value) {
        queryParamsMap.put(key, value);
        return this;
    }

    public RetrofitHttp addQueryParamsMap(Map<String, String> paramsMap) {
        if (paramsMap != null && paramsMap.size() > 0)
            queryParamsMap.putAll(paramsMap);
        return this;
    }


}
