package com.yiande.jxjxc.popwindow;

import android.view.View;

import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BasePopupWindow;
import com.yiande.jxjxc.databinding.PopSizeBinding;
import com.yiande.jxjxc.myInterface.EventConsume;


public class SizePop extends BasePopupWindow<PopSizeBinding> {
    OnSetSizeListener onSetSizeListener;
    public SizePop(RxAppCompatActivity context) {
        super(context);
        mBinding.setOnClicek(onClik);
    }

    public void setOnSetSizeListener(OnSetSizeListener onSetSizeListener) {
        this.onSetSizeListener = onSetSizeListener;
    }


    @Override
    public int getContentViewID() {
        return R.layout.pop_size;
    }

    @Override
    public void init(View view) {

    }

    private EventConsume<Integer> onClik = (type) -> {

        switch (type) {

            case 1:
                setSize(14, 3);
                break;
            case 2:
                setSize(16, 4);
                break;
            case 3:
                setSize(18, 5);
                break;

            default:
                break;
        }
    };


    private void setSize(int size, int fonsize) {
        if (onSetSizeListener != null) {
            onSetSizeListener.onSetSize(size, fonsize);
        }
        if (isShowing()) {
            dismiss();
        }
    }

    public interface OnSetSizeListener {
        void onSetSize(int size, int fontSize);
    }

}
