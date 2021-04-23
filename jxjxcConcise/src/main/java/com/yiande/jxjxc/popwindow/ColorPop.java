package com.yiande.jxjxc.popwindow;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BasePopupWindow;
import com.yiande.jxjxc.databinding.PopColorBinding;
import com.yiande.jxjxc.myInterface.EventConsume;


public class ColorPop extends BasePopupWindow<PopColorBinding> {
    OnSetColorListener onSetColorListener;

    public ColorPop(RxAppCompatActivity context) {
        super(context);
        mBinding.setOnClicek(onClik);
    }

    public void setOnSetColorListener(OnSetColorListener onSetColorListener) {
        this.onSetColorListener = onSetColorListener;
    }

    @Override
    public int getContentViewID() {
        return R.layout.pop_color;
    }

    @Override
    public void init(View view) {
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

    }

    private EventConsume<Integer> onClik = (type) -> {

        switch (type) {

     /*     红#d81e06
            黄#f4ea2a
            绿#1 afa29
            蓝#1296d b
            深蓝#13227 a
            黑#000000
            深灰#333333
            灰#515151*/
            case 1:
                setColor(Color.parseColor("#d81e06"));
                break;
            case 2:
                setColor(Color.parseColor("#f4ea2a"));
                break;
            case 3:
                setColor(Color.parseColor("#1afa29"));
                break;
            case 4:
                setColor(Color.parseColor("#1296db"));
                break;
            case 5:
                setColor(Color.parseColor("#13227a"));
                break;
            case 6:
                setColor(Color.parseColor("#000000"));
                break;
            case 7:
                setColor(Color.parseColor("#333333"));
                break;
            case 8:
                setColor(Color.parseColor("#515151"));
                break;


            default:
                break;
        }
    };


    private void setColor(int size) {
        if (onSetColorListener != null) {
            onSetColorListener.onSetColor(size);
        }
        if (isShowing()) {
            dismiss();
        }
    }

    public interface OnSetColorListener {
        void onSetColor(int color);
    }

}
