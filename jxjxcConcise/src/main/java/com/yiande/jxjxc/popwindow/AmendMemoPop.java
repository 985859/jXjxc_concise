package com.yiande.jxjxc.popwindow;


import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;

import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.utils.SystemUtil;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BasePopupWindow;
import com.yiande.jxjxc.databinding.PopAmendMemoBinding;
import com.yiande.jxjxc.myInterface.EventConsume;


public class AmendMemoPop extends BasePopupWindow<PopAmendMemoBinding> {
    private EventConsume<String> onSeclcetListener;
    private int maxLength = 300;

    public void setOnSeclcetListener(EventConsume<String> onSeclcetListener) {
        this.onSeclcetListener = onSeclcetListener;
    }

    private EventConsume<Integer> onClick = (type) -> {
        switch (type) {
            case 0:
                dismiss();
                break;
            case 1:
                if (onSeclcetListener != null) {
                    onSeclcetListener.accept(mBinding.popAmendMemoText.getText().toString());
                }
                break;

            default:
                break;
        }
    };


    public AmendMemoPop(RxAppCompatActivity context) {
        super(context);
        mBinding.setOnClick(onClick);
    }

    @Override
    public int getContentViewID() {
        return R.layout.pop_amend_memo;
    }

    @Override
    public void init(View view) {
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mBinding.popAmendMemoText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if (maxLength != -1) {
                    if (length >= maxLength) {
                        length = maxLength;
                    }
                    mBinding.popAmendMemoLength.setText(length + "/" + maxLength);
                }
            }
        });
    }


    @Override
    protected void onDismissListener() {
        super.onDismissListener();
        mBinding.popAmendMemoText.setFocusable(false);
        mBinding.popAmendMemoText.setFocusableInTouchMode(false);
        SystemUtil.toggleSoftInput(mBinding.popAmendMemoText);
    }

    @Override
    public void onShowListener() {
        super.onShowListener();
        showKeyboard();
    }

    public void showKeyboard() {
        mBinding.popAmendMemoText.setFocusable(true);
        mBinding.popAmendMemoText.setFocusableInTouchMode(true);
        SystemUtil.showKeyboard(mBinding.popAmendMemoText);
    }


    public void setEdit(String deit) {
        if (deit == null) {
            deit = "";
        }
        mBinding.popAmendMemoText.setText(deit);
        if (deit != null)
            mBinding.popAmendMemoText.setSelection(deit.length());
    }

    public void setHint(String hint) {
        if (hint == null) {
            hint = "";
        }
        mBinding.popAmendMemoText.setHint(hint);
    }

    public void setTitle(String title) {
        if (StringUtil.isEmpty(title)) {
            title = "";
        }
        mBinding.popAmendMemoTitle.setText(title);

    }
}
