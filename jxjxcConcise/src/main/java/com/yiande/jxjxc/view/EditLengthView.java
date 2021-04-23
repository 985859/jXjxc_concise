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

import com.yiande.jxjxc.R;
import com.yiande.jxjxc.databinding.ViewEditLengthBinding;
import com.yiande.jxjxc.utils.Util;


/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/8/14 15:53
 */
public class EditLengthView extends LinearLayout {
    private int maxLength;
    Context context;

    ViewEditLengthBinding binding;
    private int must;
    private String title;
    private String text;
    private String hint;

    public EditLengthView(Context context) {
        this(context, null);
    }

    public EditLengthView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditLengthView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        View view = inflate(context, R.layout.view_edit_length, null);
        ViewGroup.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(view, params);
        view.setTag("layout/view_edit_length_0");
        binding = DataBindingUtil.bind(view);
        init(attrs);


    }


    private void init(AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.EditLengthView);
        int inputType = array.getInt(R.styleable.EditLengthView_android_inputType, EditorInfo.TYPE_NULL);
        text = array.getString(R.styleable.EditLengthView_android_text);
        hint = array.getString(R.styleable.EditLengthView_android_hint);
        maxLength = array.getInteger(R.styleable.EditLengthView_android_maxLength, -1);
        must = array.getInt(R.styleable.EditLengthView_must, 0);
        title = array.getString(R.styleable.EditLengthView_title);
        boolean edit = array.getBoolean(R.styleable.EditLengthView_edit, true);
        boolean showEdit = array.getBoolean(R.styleable.EditLengthView_showEdit, false);
        array.recycle();
        if (inputType != EditorInfo.TYPE_NULL) {
            binding.editLengthText.setInputType(inputType);
        }

        setMust(must);
        setTitle(title);
        setText(text);
        setHint(hint);
        setEdit(edit);
        setShowEdit(showEdit);
        if (maxLength != -1) {
            setMaxLength(maxLength);
            binding.editLengthNumber.setText(0 + "/" + maxLength);
        }
        if (!isInEditMode()) {

            binding.editLengthText.addTextChangedListener(new TextWatcher() {
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
                        binding.editLengthNumber.setText(length + "/" + maxLength);
                    }
                }
            });
        }

    }


    public void setMust(int must) {
        this.must = must;
        setTitle(title);
    }


    public void setTitle(String title) {
        this.title = title;
        if (must == 1) {
            binding.editLengthTitle.setText(Util.setMustTilte(title));
        } else {
            binding.editLengthTitle.setText(title);
        }
    }


    public void setTitle(int title) {
        setTitle(context.getString(title));
    }

    public void setText(String text) {
        binding.editLengthText.setText(text);
    }

    public void setEdit(boolean edit) {
        binding.editLengthText.setEnabled(edit);
    }

    public void setShowEdit(boolean edit) {
        if (edit) {
            binding.editLengthBT.setVisibility(VISIBLE);
        } else {
            binding.editLengthBT.setVisibility(GONE);
        }
    }

    public String getText() {
        return binding.editLengthText.getText().toString();
    }

    public EditText getEditText() {
        return binding.editLengthText;
    }

    public void setEditBTListener(OnClickListener listener) {
        if (listener != null) {
            binding.editLengthBT.setOnClickListener(listener);
        }
    }

    public void setHint(int hint) {
        setHint(context.getString(hint));

    }

    public void setHint(String hint) {
        binding.editLengthText.setHint(hint);
    }

    public CharSequence getHint() {
        return binding.editLengthText.getHint();
    }


    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
        InputFilter[] filters = {new InputFilter.LengthFilter(maxLength)};
        binding.editLengthText.setFilters(filters);
    }


    @BindingAdapter("setEditText")
    public static void setText(EditLengthView editLengthView, String value) {
        if (editLengthView != null) {
            String edTextString = editLengthView.getText() == null ? "" : editLengthView.getText();
            value = value == null ? "" : value;
            if (edTextString.equals(value)) {
                return;
            }
            editLengthView.setText(value);
        }
    }


    @InverseBindingAdapter(attribute = "setEditText", event = "textAttrChanged")
    public static String getValue(EditLengthView editLengthView) {
        return editLengthView.getText();
    }

    @BindingAdapter(
            value = {"android:beforeTextChanged",
                    "android:onTextChanged",
                    "android:afterTextChanged",
                    "textAttrChanged"},
            requireAll = false)
    public static void setTextWatcher(EditLengthView view,
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
