package com.yiande.jxjxc.presenter;

import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;

import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.utils.SystemUtil;
import com.mylibrary.api.utils.web.WebAppInterface;
import com.mylibrary.api.utils.web.WebUtil;
import com.retrofithttp.api.LoadingDialog;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.App;
import com.yiande.jxjxc.base.BasePresenter;
import com.yiande.jxjxc.databinding.ActivityHtml5Binding;
import com.yiande.jxjxc.myInterface.TypeEnum;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/9/22 9:38
 */
public class Html5Presenter extends BasePresenter<ActivityHtml5Binding> {

    String url;

    public Html5Presenter(RxAppCompatActivity mContext, ActivityHtml5Binding binding) {
        super(mContext, binding);
        Intent intent = getIntent();
        if (intent != null) {
            url = intent.getStringExtra("content");
            title = intent.getStringExtra("title");
            isUrl = intent.getBooleanExtra("isUrl", true);
            if (StringUtil.isNotEmpty(title)) {
                mBinding.htmlTop.setTitle(title);
            }
            getDtata();
        }
    }


    private void getDtata() {
        if (!StringUtil.isEmpty(url)) {
            showDialog();
            mBinding.htmlWeb.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    if (StringUtil.isEmpty(title)) {
                        mBinding.htmlTop.setTitle(view.getTitle());
                    }
                    hideDialog();
                    String js = "var script = document.createElement('script');";
                    js += "script.type = 'text/javascript';";
                    js += "var child=document.getElementsByTagName('a')[0];";
                    js += "child.οnclick=function(){userIdClick();};";
                    js += "function h5Box(arg,arg1,arg2){window.android.jsCallAndroidArgs(arg,arg1,arg2);};";
                    view.loadUrl("javascript:" + js);//注入js
                    super.onPageFinished(view, url);
                }

                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    handler.proceed();
                }
            });
        }
        if (isUrl) {
            ArrayMap<String, String> map = new ArrayMap<>();
            if (App.token != null)
                map.put("access_token", App.token.getToken());
            map.put("os", "1");
            map.put("v", SystemUtil.getVerCodeName(mContext));
            mBinding.htmlWeb.loadUrl(url, map);
        } else {
            WebUtil.webHtml(url, mBinding.htmlWeb);
        }
    }

    String title;
    boolean isUrl = true;
    LoadingDialog dialog;

   

    @Override
    protected void initData() {
        WebSettings mWebSetting = mBinding.htmlWeb.getSettings();
        mWebSetting.setJavaScriptEnabled(true);
        //======================保证页面的完整显示在手机屏幕上=======================
        mWebSetting.setUseWideViewPort(true);
        mWebSetting.setLoadWithOverviewMode(true);
        //设置支持缩放
        mWebSetting.setSupportZoom(false);
        // 设置出现缩放工具
        mWebSetting.setBuiltInZoomControls(false);
        mWebSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebSetting.setJavaScriptEnabled(true);
        mWebSetting.setTextZoom(100);
        //      View级别  硬件加速 setlayertype
        //      注：您不能在view级别开启硬件加速
        mBinding.htmlWeb.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        /**
         * MIXED_CONTENT_ALWAYS_ALLOW：允许从任何来源加载内容，即使起源是不安全的；
         * MIXED_CONTENT_NEVER_ALLOW：不允许Https加载Http的内容，即不允许从安全的起源去加载一个不安全的资源；
         * MIXED_CONTENT_COMPATIBILITY_MODE：当涉及到混合式内容时，WebView 会尝试去兼容最新Web浏览器的风格。
         **/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBinding.htmlWeb.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mBinding.htmlWeb.setVerticalScrollBarEnabled(false);
        mBinding.htmlWeb.setVerticalScrollbarOverlay(false);
        mBinding.htmlWeb.setHorizontalScrollBarEnabled(false);
        mBinding.htmlWeb.setHorizontalScrollbarOverlay(false);
        mBinding.htmlWeb.addJavascriptInterface(new WebAppInterface(mContext), "android");


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case TypeEnum.ENTER:
                initData();
                break;
            default:
                break;

        }
    }

    private void showDialog() {
        if (dialog == null) {
            dialog = new LoadingDialog(mContext);
        }
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    private void hideDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
    
}
