package com.yiande.jxjxc.base;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.mylibrary.api.utils.SystemUtil;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;


public abstract class BasePopupWindow<B extends ViewDataBinding> extends PopupWindow {
    public RxAppCompatActivity activity;
    public View contentView;
    public float alpha = 0.4f;
    protected B mBinding;
    private OnShowCallBack onShowCallBack;

    public BasePopupWindow(RxAppCompatActivity context) {
        super(context);
        init(context, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public BasePopupWindow(RxAppCompatActivity context, int width, int height) {
        super(width, height);
        init(context, width, height);
    }

    public BasePopupWindow(Context context, AttributeSet attrs, RxAppCompatActivity activity) {
        super(context, attrs);
        this.activity = activity;
    }

    public BasePopupWindow(Context context, AttributeSet attrs, int defStyleAttr, RxAppCompatActivity activity) {
        super(context, attrs, defStyleAttr);
        this.activity = activity;
    }

    public BasePopupWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, RxAppCompatActivity activity) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.activity = activity;
    }

    public void setOnShowCallBack(OnShowCallBack onShowCallBack) {
        this.onShowCallBack = onShowCallBack;
    }

    private void init(RxAppCompatActivity context, int width, int height) {
        activity = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mBinding = DataBindingUtil.inflate(inflater, getContentViewID(), null, true);//第四个参数为true很重要，让我们的自定义View可以正常显示
        contentView = mBinding.getRoot();
        setContentView(contentView);
        setWidth(width);
        setHeight(height);
        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        setBackgroundDrawable(new ColorDrawable(0x00000000));
        // 设置为true之后，PopupWindow内容区域 才可以响应点击事件
        setTouchable(true);
        // true时，点击返回键先消失 PopupWindow
        // 但是设置为true时setOutsideTouchable，setTouchable方法就失效了（点击外部不消失，内容区域也不响应事件）
        // false时PopupWindow不处理返回键
        setFocusable(true);
        // setOutsideTouchable设置生效的前提是setTouchable(true)和setFocusable(false)
        setOutsideTouchable(true);
        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;   // 这里面拦截不到返回键
            }
        });
        //监听消失事件
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                onDismissListener();
                showWindowAlpha(1);
            }
        });
        init(contentView);
        setListener();
    }

    protected void onDismissListener() {
    if(onShowCallBack!=null){
    onShowCallBack.onShowCallBack(false);
    }
    }


    public abstract int getContentViewID();

    public abstract void init(View view);

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }


    public void setListener() {
    }

    /**
     * 窗口 弹出前时的处理
     **/
    public void onShowListener() {
        if(onShowCallBack!=null){
            onShowCallBack.onShowCallBack(true);
        }
    }

    public void showPopupWindow(View parent) {
        showPopupWindow(parent, Gravity.TOP, 0, 0);
    }

    public void showPopupWindow(View parent, int gravity, int y) {
        if (gravity == Gravity.BOTTOM) {
            showPopupWindow(parent, gravity, 0, y);
        } else {
            showPopupWindow(parent, gravity, 0, +y);
        }
    }

    public void showPopupWindow(View parent, int gravity) {
        showPopupWindow(parent, gravity, 0, 0);
    }

    /**
     * 显示popupWindow的方式设置，当然可以有别的方式。
     * 一会会列出其他方法
     *
     * @param parent
     */
    public void showPopupWindow(View parent, int gravity, int x, int y) {
        showAtLocation(parent, gravity, x, y);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        if (!this.isShowing()) {
            onShowListener();
            showWindowAlpha(alpha);
        } else {
            this.dismiss();
        }
        super.showAtLocation(parent, gravity, x, y);
    }


    public void showWindowAlpha(float alpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = alpha;
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        activity.getWindow().setAttributes(lp);

    }

    @Override
    public void showAsDropDown(View anchor) {
        showAsDropDown(anchor, 0, 0, Gravity.TOP);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        if (!this.isShowing()) {
            onShowListener();
            if (Build.VERSION.SDK_INT >= 24) {
                Rect rect = new Rect();
                anchor.getGlobalVisibleRect(rect);
                int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
                if ("Xiaomi".equals(Build.MANUFACTURER)) {
                    h += SystemUtil.getNavigationBarHeight(activity) + SystemUtil.getStatusBarHeight(activity);
                }
                setHeight(h);
            }
        } else {
            this.dismiss();
        }
        super.showAsDropDown(anchor, xoff, yoff, gravity);
    }

    public interface OnShowCallBack {
        void onShowCallBack(boolean show);
    }


}

