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

import com.mylibrary.api.utils.LogUtil;
import com.mylibrary.api.utils.SharedUtil;
import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.utils.SystemUtil;
import com.retrofithttp.api.LoadingDialog;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.base.BasePresenter;
import com.yiande.jxjxc.databinding.ActivityStatisticsBinding;
import com.yiande.jxjxc.myInterface.TypeEnum;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/9/21 21:07
 */
public class StatisticsPresenter extends BasePresenter<ActivityStatisticsBinding> {
    String title;
    LoadingDialog dialog;
    private int type = 1;


    public StatisticsPresenter(RxAppCompatActivity mContext, ActivityStatisticsBinding binding) {
        super(mContext, binding);
        Intent intent = getIntent();
        if (intent != null) {
            try {
                type = intent.getIntExtra("type", 0);
                switch (type) {
                    case 101:
                        title = "销售分析";
                        break;
                    case 102:
                        title = "利润分析";
                        break;
                    case 103:
                        title = "余额分析";
                        break;
                    case 104:
                        title = "欠款分析";
                        break;
                    case 203:
                        title = "入库统计";
                        break;
                    case 303:
                        title = "出库统计";
                        break;
                    default:
                        break;
                }
                mBinding.statisticsTop.setTitle(title);
                getDtata();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void getDtata() {
        String url = SharedUtil.getSP(com.yiande.jxjxc.App.BASEURL) + "/";
        switch (type) {
            case 101:
                url += "App_XiaoShou.aspx";
                break;
            case 102:
                url += "App_LiRun.aspx";
                break;
            case 103:
                url += "App_Balance.aspx";
                break;
            case 104:
                title = "欠款分析";
                url += "App_QianKuan.aspx";
                break;
            case 203:
                title = "入库统计";
                break;
            case 303:
                title = "出库统计";
                break;
            default:
                break;
        }

        showDialog();
        mBinding.statisticsWeb.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (StringUtil.isEmpty(title)) {
                    mBinding.statisticsTop.setTitle(view.getTitle());
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

        ArrayMap<String, String> map = new ArrayMap<>();
        if (com.yiande.jxjxc.App.token != null)
            map.put("access_token", com.yiande.jxjxc.App.token.getToken());
        map.put("os", "1");
        map.put("v", SystemUtil.getVerCodeName(mContext));
        LogUtil.e("url=" + url);
        mBinding.statisticsWeb.loadUrl(url, map);


    }


    @Override
    protected void initData() {
        WebSettings mWebSetting = mBinding.statisticsWeb.getSettings();
        mWebSetting.setJavaScriptEnabled(true);
        //======================保证页面的完整显示在手机屏幕上=======================
        mWebSetting.setUseWideViewPort(true);
        mWebSetting.setLoadWithOverviewMode(true);
        //设置支持缩放
        mWebSetting.setSupportZoom(false);
        mWebSetting.setDomStorageEnabled(true);
        // 设置出现缩放工具
        mWebSetting.setBuiltInZoomControls(false);
        mWebSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        // 设置与Js交互的权限
        mWebSetting.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        mWebSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebSetting.setTextZoom(100);
        //JS调用android 方法设置 WebAppInterface 里面写逻辑处理

        //      View级别  硬件加速 setlayertype
        //      注：您不能在view级别开启硬件加速
        mBinding.statisticsWeb.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        /**
         * MIXED_CONTENT_ALWAYS_ALLOW：允许从任何来源加载内容，即使起源是不安全的；
         * MIXED_CONTENT_NEVER_ALLOW：不允许Https加载Http的内容，即不允许从安全的起源去加载一个不安全的资源；
         * MIXED_CONTENT_COMPATIBILITY_MODE：当涉及到混合式内容时，WebView 会尝试去兼容最新Web浏览器的风格。
         **/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBinding.statisticsWeb.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mBinding.statisticsWeb.setVerticalScrollBarEnabled(false);
        mBinding.statisticsWeb.setVerticalScrollbarOverlay(false);
        mBinding.statisticsWeb.setHorizontalScrollBarEnabled(true);
        mBinding.statisticsWeb.setHorizontalScrollbarOverlay(false);
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
        if (dialog != null ) {
            dialog.show();
        }
    }

    private void hideDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }


}
