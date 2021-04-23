package com.yiande.jxjxc.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.gyf.immersionbar.ImmersionBar;
import com.mylibrary.api.dialog.MyDialog;
import com.mylibrary.api.utils.ActivityManager;
import com.mylibrary.api.utils.SystemUtil;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;


public abstract class BaseActivity<T extends BasePresenter<B>, B extends ViewDataBinding> extends RxAppCompatActivity {
    /**
     * 标示
     */
    protected final String TAG = getClass().getName();
    public ActivityManager activityManager;
    /**
     * Context对象
     */
    protected RxAppCompatActivity mContext;
    private InputMethodManager imm;
    protected ImmersionBar mImmersionBar;
    protected B mBinding;
    protected T mPresenter;


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    protected View getStatusBarView() {
        return null;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        activityManager = ActivityManager.getInstance();
        activityManager.addActivity(this);//添加到堆栈中
        beforSetLayou(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, setLayoutId());
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
        try {
            //初始化沉浸式
            if (isImmersionBarEnabled()) {
                initImmersionBar();
            }
            mPresenter = getPresenter();
            initData();
        } catch (Exception e) {
            Log.e(getPackageName(), getClass().getName() + " " + e.toString());
        }
        boolean b = isNetworkAvailable(this);
        if (b == false) {
            new MyDialog(mContext, com.mylibrary.api.R.style.dialog, "没有网络,请前往设置,打开网络连接", new MyDialog.OnCloseListener() {
                @Override
                public void onClick(Dialog dialog, boolean confirm) {
                    if (confirm) {
                        SystemUtil.skipSet(mContext);
                    }
                }
            }).setNegativeButton("取消")
                    .setPositiveButton("设置");

        }
    }

    protected void beforSetLayou(Bundle savedInstanceState) {
    }

    protected void initData() {
    }

    /**
     * 退出当前activity
     *
     * @see Activity#finish()
     */

    public void finish() {
        if(mPresenter!=null){
            mPresenter.finish();
        }

        super.finish();
        try {
            hideSoftKeyBoard();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.imm = null;

        if (activityManager != null)
            activityManager.finishActivity(this);//从堆栈中移除

    }

    protected abstract int setLayoutId();

    protected abstract T getPresenter();

    protected abstract void initImmersionBar();

    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    public void hideSoftKeyBoard() {
        View localView = getCurrentFocus();
        if (this.imm == null) {
            this.imm = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
        }
        if ((localView != null) && (this.imm != null)) {
            this.imm.hideSoftInputFromWindow(localView.getWindowToken(), 2);
        }
    }

    /***
     * 判断当前网络状态
     ***/

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {

        } else {
            NetworkInfo[] infos = manager.getAllNetworkInfo();
            if (infos != null) {
                for (int i = 0; i < infos.length; i++) {
                    if (infos[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 设置 app 不随着系统字体的调整而变化
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    public boolean checkPermission(Context context, String[] permissions) {
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();
        for (String permission : permissions) {
            int per = packageManager.checkPermission(permission, packageName);
            if (PackageManager.PERMISSION_DENIED == per) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mPresenter != null) {
            mPresenter.onActivityResult(requestCode, resultCode, data);
        }
    }


}
