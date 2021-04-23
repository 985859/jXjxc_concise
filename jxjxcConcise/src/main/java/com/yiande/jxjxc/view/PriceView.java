package com.yiande.jxjxc.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;
import androidx.databinding.adapters.ListenerUtil;
import androidx.databinding.adapters.TextViewBindingAdapter;

import com.mylibrary.api.utils.PointLengthFilter;
import com.mylibrary.api.utils.StringUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.databinding.ViewPriceBinding;
import com.yiande.jxjxc.utils.Util;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/11/23 9:50
 */
public class PriceView extends LinearLayout {
    Context context;

    ViewPriceBinding binding;
    String hint;
    String title;
    int must;

    public PriceView(Context context) {
        this(context, null);
    }

    public PriceView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PriceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        View view = inflate(context, R.layout.view_price, null);
        ViewGroup.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(view, params);
        view.setTag("layout/view_price_0");
        binding = DataBindingUtil.bind(view);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PriceView);
        String text = array.getString(R.styleable.PriceView_android_text);
        hint = array.getString(R.styleable.PriceView_android_hint);
        must = array.getInt(R.styleable.PriceView_must, 0);
        title = array.getString(R.styleable.PriceView_title);
        String subTitle = array.getString(R.styleable.PriceView_subTitle);
        Drawable icon = array.getDrawable(R.styleable.PriceView_priceView_Icon);
        int iconSize = (int) array.getDimension(R.styleable.PriceView_priceView_IconSize, 32);
        boolean edit = array.getBoolean(R.styleable.PriceView_priceView_Edit, true);
        boolean showLine = array.getBoolean(R.styleable.PriceView_priceView_ShowLine, false);
        boolean addFilter = array.getBoolean(R.styleable.PriceView_priceView_AddFilter, true);
        array.recycle();
        setEdit(edit);
        setMust(must);
        setTitle(title);
        setSubTitle(subTitle);
        setHint(hint);
        setShowLine(showLine);

        if (icon != null) {
            binding.PricieTitle.setDrawableLeftSize(iconSize, iconSize);
            binding.PricieTitle.setLeftDrawable(icon);
        }
        if (addFilter) {
            PointLengthFilter filter = new PointLengthFilter();
            filter.setPointerLength(2);
            binding.PricieText.setFilters(new InputFilter[]{filter});
        }
        if (StringUtil.isNotEmpty(text)) {
            setText(text);
        }

    }

    public void setHint(int hint) {
        binding.PricieText.setHint(context.getString(hint));

    }

    public void setHint(String hint) {
        binding.PricieText.setHint(hint);
    }

    @BindingAdapter("setHint")
    public static void setHint(PriceView priceView, String hint) {
        priceView.setHint(hint);
    }

    @BindingAdapter("setSubTitle")
    public static void setSubTitle(PriceView priceView, String title) {
        priceView.setSubTitle(title);
    }


    public void setMust(int must) {
        this.must = must;
        setTitle(title);
    }


    public void setTitle(String title) {
        this.title = title;
        if (must == 1) {
            binding.PricieTitle.setText(Util.setMustTilte(title));
        } else {
            binding.PricieTitle.setText(title);
        }
    }


    public void setEdit(boolean edit) {
        binding.PricieText.setEnabled(edit);
        binding.PricieText.showClear(edit);
    }


    public void setSubTitle(String subTitle) {
        binding.PricieSubTitle.setText(subTitle);
    }

    public void setSubTitleColor(int color) {
        binding.PricieSubTitle.setTextColor(color);
    }


    public void setTitle(int title) {
        setTitle(context.getString(title));
    }


    public EditText getEditText() {
        return binding.PricieText;
    }

    public void setText(String text) {
        binding.PricieText.setText(text);
    }

    public String getText() {
        return binding.PricieText.getText().toString();
    }

    public void setShowLine(boolean showLine) {
        if (showLine) {
            binding.PricieV.setVisibility(VISIBLE);
        } else {
            binding.PricieV.setVisibility(GONE);
        }
    }

    @BindingAdapter("setEditText")
    public static void setText(PriceView editView, String value) {
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
    public static String getValue(PriceView editView) {
        return editView.getText();
    }

    @BindingAdapter(
            value = {"android:beforeTextChanged",
                    "android:onTextChanged",
                    "android:afterTextChanged",
                    "textAttrChanged"},
            requireAll = false)
    public static void setTextWatcher(PriceView view,
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

    public String getHint() {
        return binding.PricieText.getHint().toString();
    }

    public String getTitle() {
        return binding.PricieTitle.getText().toString();
    }
}


