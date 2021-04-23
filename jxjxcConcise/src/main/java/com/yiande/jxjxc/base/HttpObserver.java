package com.yiande.jxjxc.base;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;

import com.mylibrary.api.dialog.MyDialog;
import com.mylibrary.api.utils.LogUtil;
import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.utils.ToastUtil;
import com.retrofithttp.api.base.BaseObserver;
import com.yiande.jxjxc.bean.JsonBean;
import com.yiande.jxjxc.utils.DialogUtils;


/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/7/17 16:03
 */
public abstract class HttpObserver<T> extends BaseObserver<T> {
    MyDialog myDialog;

    public boolean showToast = true;

    public HttpObserver(Context context) {
        super(context);
    }

    public HttpObserver(Context context, String msg) {
        super(context, msg);
    }

    public HttpObserver(Context context, boolean showLoading) {
        super(context, showLoading);
    }

    public HttpObserver(Context context, boolean showLoading, boolean isCancelables) {
        super(context, showLoading, isCancelables);
    }

    public HttpObserver(Context context, boolean showLoading, boolean isCancelables, String msg) {
        super(context, showLoading, isCancelables, msg);
    }


    public abstract void onSuccess(T bean);


    @Override
    public void onNext(T t) {
        try {
            if (t instanceof JsonBean) {
                JsonBean bean = (JsonBean) t;
                if (mShowLoading)
                    getMsgDialog(bean);
            }
        } catch (Exception e) {
            onError(e);
        }
        super.onNext(t);


    }

    public void msgOnClick(boolean click, int isMsg) {

    }

    private void getMsgDialog(JsonBean jsonBean) {
        //             "ismsg":"", //提示框样式
        //0：不提示
        //1：一般性提示,如:非空校验提示
        //2：弹窗提示
        //21: 弹窗提示后关闭页面
        //3: token失效
        if (jsonBean != null) {
            switch (jsonBean.ismsg) {
                case 1:
                    //提示框位置
                    //0：居中(默认)
                    //1：顶部
                    //2：底部
                    switch (jsonBean.count) {
                        case 0:
                            ToastUtil.showShort(jsonBean.msg, Gravity.CENTER);
                            break;
                        case 1:
                            ToastUtil.showShort(jsonBean.msg, Gravity.TOP);
                            break;
                        case 3:
                            ToastUtil.showShort(jsonBean.msg, Gravity.BOTTOM);
                            break;
                        default:
                            break;
                    }
                    break;
                case 2:
                    msgDialog(jsonBean.msg, jsonBean.ismsg, false);
                    break;
                case 3:
                    getDialog();
                    break;
                case 21:
                    msgDialog(jsonBean.msg, jsonBean.ismsg, true);
                    break;

                default:
                    break;
            }

        }


    }


    private void msgDialog(String msg, int type, final boolean b) {
        if (StringUtil.isEmpty(msg)) {
            return;
        }
        if (myDialog == null) {
            myDialog = new MyDialog(context);
            String positive = "确定";
            String negative = "";
            myDialog.setContent(msg)
                    .setPositiveButton(positive)
                    .setNegativeButton(negative)
                    .setListener(new MyDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            if (b) {
                                msgOnClick(confirm, type);
                            }
                        }
                    });
            myDialog.setCancelables(false);
            myDialog.setCanceledOnTouchOutside(false);
            myDialog.show();
        }
        showDialog();
    }

    private void getDialog() {
        if (myDialog == null) {
            myDialog = DialogUtils.getEnterDueDialog(context);
        }
        showDialog();
    }

    private void showDialog() {
        if (myDialog != null) {
            Activity activity = myDialog.getOwnerActivity();
            if (activity != null && !activity.isFinishing()) {
                myDialog.show();
            }
        }
    }

    @Override
    public void onException(int code) {
        super.onException(code);
        if (!isMainThread() || showToast == false) {
            return;
        }

        switch (code) {
            case 400:
                ToastUtil.showShort("服务器不理解请求的语法");
                break;
            case 401:
                ToastUtil.showShort("（未授权）请求要求身份验证");
                break;
            case 403:
                ToastUtil.showShort("服务器拒绝请求");
                break;
            case 404:
                ToastUtil.showShort("服务器找不到请求的接口");
                break;
            case 405:
                ToastUtil.showShort("服务器请求指定了方法,请检查");
                break;
            case 408:
                ToastUtil.showShort("（请求超时）服务器等候请求时发生超时");
                break;
            case 414:
                ToastUtil.showShort("请求网址过长，服务器无法处理");
                break;
            case 415:
                ToastUtil.showShort("（不支持的媒体类型）请求的格式不受请求页面的支持");
                break;
            case 416:
                ToastUtil.showShort("请求范围不符合要求");
                break;

            case 500:
                ToastUtil.showShort("服务器遇到错误，无法完成请求");
                break;
            case 501:
                ToastUtil.showShort("服务器不具备完成请求的功能");
                break;
            case 502:
                ToastUtil.showShort("服务器错误网关");
                break;
            case 503:
                ToastUtil.showShort("服务器目前无法使用");
                break;
            case 504:
                ToastUtil.showShort("网关超时");
                break;
            case 505:
                ToastUtil.showShort("服务器不支持请求中所用的HTTP协议版本");
                break;
            case 0:
                ToastUtil.showShort("无效的请求地址");
                break;
            case -2:
                ToastUtil.showShort("Json数据解析错误");
                break;
        }


    }

}
