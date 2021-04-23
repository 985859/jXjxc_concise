package com.yiande.jxjxc.utils;

import android.app.Dialog;
import android.content.Context;

import com.mylibrary.api.dialog.MyDialog;
import com.mylibrary.api.utils.SkipUtil;
import com.mylibrary.api.utils.StringUtil;
import com.yiande.jxjxc.activity.LoginActivity;
import com.yiande.jxjxc.base.BaseActivity;


/**
 * Created by myuser on 2018/3/15.
 */

public class DialogUtils {


    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/7/17
     * @Description 登录对话框
     */

    public static void isEnterDialog(final Context context) {
        MyDialog dialog = new MyDialog(context);
        dialog.setNegativeButton("取消");
        dialog.setPositiveButton("登录");
        dialog.setContent("您还未登录,请先登录");
        dialog.setTitle("温馨提示");
        dialog.setListener(new MyDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (confirm) {
                    SkipUtil.skipActivity(context, LoginActivity.class, 0);
                }
            }
        });
        dialog.show();
    }

    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/7/17
     * @Description 登录token 过期对话框
     */

    public static MyDialog getEnterDueDialog(Context context) {
        MyDialog myDialog = new MyDialog(context);
        myDialog.setContent("该账号的登录凭证已到期或已在其他设备登录，是否重新登录?")
                .setPositiveButton("重新登录")
                .setListener(new MyDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if (confirm) {
                            Util.esc((BaseActivity) context);
                        }

                    }
                });
        myDialog.setCancelable(false);
        myDialog.show();
        return myDialog;
    }

    public static MyDialog showDialog(Context context, String msg) {
        return showDialog(context, -1, "", msg, "确定", "", null, true);
    }

    public static MyDialog showDialog(Context context, String msg, MyDialog.OnCloseListener listener) {
        return showDialog(context, -1, "", msg, "确定", "取消", listener, true);
    }

    public static MyDialog showDialog(Context context, String msg, MyDialog.OnCloseListener listener, boolean cancelable) {
        return showDialog(context, -1, "", msg, "确定", "取消", listener, cancelable);
    }

    public static MyDialog showDialog(Context context, String title, String msg, String positive, String negative, MyDialog.OnCloseListener listener, boolean cancelable) {
        return showDialog(context, -1, title, msg, positive, negative, listener, cancelable);
    }

    public static MyDialog showDialog(Context context, String msg, String positive, String negative, MyDialog.OnCloseListener listener, boolean cancelable) {
        return showDialog(context, -1, "", msg, positive, negative, listener, cancelable);
    }

    public static MyDialog showDialog(Context context, int contIMG, String title, String msg, String positive, String negative, MyDialog.OnCloseListener listener, boolean cancelable) {
        MyDialog dialog = new MyDialog(context);
        String str = "温馨提示";
        if (StringUtil.isNotEmpty(title)) {
            str = title;
        }
        dialog.setTitle(str)
                .setContent(msg)
                .setContentImg(contIMG)
                .setPositiveButton(positive)
                .setNegativeButton(negative)
                .setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(cancelable);
        if (listener != null) {
            dialog.setListener(listener);
        }
        dialog.show();
        return dialog;
    }


}
