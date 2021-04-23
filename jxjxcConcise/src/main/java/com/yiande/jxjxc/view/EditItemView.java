package com.yiande.jxjxc.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;
import androidx.databinding.adapters.ListenerUtil;
import androidx.databinding.adapters.TextViewBindingAdapter;

import com.mylibrary.api.utils.StringUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.databinding.LayoutEditBinding;
import com.yiande.jxjxc.utils.Util;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/11/23 9:50
 */
public class EditItemView extends LinearLayout {
    Context context;
    private int maxLength;
    LayoutEditBinding binding;
    private String hint;
    private String title;

    public EditItemView(Context context) {
        this(context, null);
    }

    public EditItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        View view = inflate(context, R.layout.layout_edit, null);
        ViewGroup.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(view, params);
        view.setTag("layout/layout_edit_0");
        if (isInEditMode()) {
            binding = DataBindingUtil.bind(view);
            init(attrs);
        }
    }

    private void init(AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.EditItemView);
        String text = array.getString(R.styleable.EditItemView_android_text);
        hint = array.getString(R.styleable.EditItemView_android_hint);
        int must = array.getInt(R.styleable.EditItemView_must, 0);
        int inputType = array.getInt(R.styleable.EditItemView_android_inputType, EditorInfo.TYPE_NULL);
        String title = array.getString(R.styleable.EditItemView_title);
        maxLength = array.getInteger(R.styleable.EditItemView_android_maxLength, -1);
        array.recycle();
        if (inputType != EditorInfo.TYPE_NULL) {
            binding.editText.setInputType(inputType);
        }
        setTitle(title);
        setHint(hint);
        setMust(must);
        setText(text);
        if (maxLength != -1) {
            setMaxLength(maxLength);
        }
    }

    public void setHint(int hint) {
        binding.editText.setHint(context.getString(hint));
    }

    public void setHint(String hint) {
        this.hint = hint;
        binding.editText.setHint(hint);
    }

    public String getHint() {
        return hint;
    }

    public void setMust(int must) {
        if (must == 1) {
            binding.editTitle.setText(Util.setMustTilte(title));
        } else {
            binding.editTitle.setText(title);
        }
    }


    public void setTitle(String title) {
        this.title = title;
        if (StringUtil.isEmpty(title) || "null".equals(title) || "NULL".equals(title)) {
            title = "";
        }
        binding.editTitle.setText(title);
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
        InputFilter[] filters = {new InputFilter.LengthFilter(maxLength)};
        binding.editText.setFilters(filters);
    }

    public void setTitle(int title) {
        setTitle(context.getString(title));
    }


    public EditText getEditText() {
        return binding.editText;
    }

    public void setText(String text) {
        if (StringUtil.isEmpty(text) || "null".equals(text) || "NULL".equals(text)) {
            text = "";
        }
        binding.editText.setText(text);
    }

    public String getText() {
        return binding.editText.getText().toString();
    }

    @BindingAdapter("setEditText")
    public static void setText(EditItemView editView, String value) {
        if (editView != null) {
            String edTextString = editView.getText() == null ? "" : editView.getText();
            value = value == null ? "" : value;
            if (edTextString.equals(value)) {
                return;
            }
            editView.setText(value);
        }
    }


    @InverseBindingAdapter(attribute = "setEditText", event = "textAttrChanged")
    public static String getValue(EditItemView editView) {
        return editView.getText();
    }

    @BindingAdapter(
            value = {"android:beforeTextChanged",
                    "android:onTextChanged",
                    "android:afterTextChanged",
                    "textAttrChanged"},
            requireAll = false)
    public static void setTextWatcher(EditItemView view,
                                      final TextViewBindingAdapter.BeforeTextChanged before,
                                      final TextViewBindingAdapter.OnTextChanged on,
                                      final TextViewBindingAdapter.AfterTextChanged after,
                                      final InverseBindingListener valueAttrChanged) {
        TextWatcher newWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (before != null) {
                    before.beforeTextChanged(s, start, count, after);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (on != null) {
                    on.onTextChanged(s, start, before, count);
                }
                if (valueAttrChanged != null) {
                    valueAttrChanged.onChange();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (after != null) {
                    after.afterTextChanged(s);
                }
            }
        };

        TextWatcher oldValue = ListenerUtil.trackListener(view, newWatcher, R.id.textWatcher);
        if (oldValue != null) {
            view.getEditText().removeTextChangedListener(oldValue);
        }
        view.getEditText().addTextChangedListener(newWatcher);
    }
}


