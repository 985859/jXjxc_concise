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
        mBinding = DataBindingUtil.inflate(inflater, getContentViewID(), null, true);//??????????????????true?????????????????????????????????View??????????????????
        contentView = mBinding.getRoot();
        setContentView(contentView);
        setWidth(width);
        setHeight(height);
        // ???????????????PopupWindow????????????????????????????????????????????????????????????????????????????????????Back????????????dismiss??????
        setBackgroundDrawable(new ColorDrawable(0x00000000));
        // ?????????true?????????PopupWindow???????????? ???????????????????????????
        setTouchable(true);
        // true?????????????????????????????? PopupWindow
        // ???????????????true???setOutsideTouchable???setTouchable??????????????????????????????????????????????????????????????????????????????
        // false???PopupWindow??????????????????
        setFocusable(true);
        // setOutsideTouchable????????????????????????setTouchable(true)???setFocusable(false)
        setOutsideTouchable(true);
        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;   // ??????????????????????????????
            }
        });
        //??????????????????
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
     * ?????? ?????????????????????
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
     * ??????popupWindow????????????????????????????????????????????????
     * ???????????????????????????
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

