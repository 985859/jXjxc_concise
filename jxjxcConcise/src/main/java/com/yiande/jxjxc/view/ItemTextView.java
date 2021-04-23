package com.yiande.jxjxc.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;

import com.mylibrary.api.utils.StringUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.databinding.LayoutItmTextBinding;
import com.yiande.jxjxc.utils.Util;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/11/30 14:56
 */
public class ItemTextView extends LinearLayout {
    Context context;
    public LayoutItmTextBinding binding;

    public ItemTextView(Context context) {
        this(context, null);
    }

    public ItemTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        View view = inflate(context, R.layout.layout_itm_text, null);
        ViewGroup.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(view, params);
        view.setTag("layout/layout_itm_text_0");
        binding = DataBindingUtil.bind(view);
        init(attrs);
    }

    @BindingAdapter("setText")
    public static void setText(ItemTextView typeView, CharSequence text) {
        typeView.setText(text);
    }

    private int must;
    private String title;
    private String supTitle;
    private String hint;
    private String text;

    private void init(AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ItemTextView);
        title = array.getString(R.styleable.ItemTextView_title);
        supTitle = array.getString(R.styleable.ItemTextView_subTitle);
        hint = array.getString(R.styleable.ItemTextView_android_hint);
        text = array.getString(R.styleable.ItemTextView_android_text);
        must = array.getInt(R.styleable.ItemTextView_must, 0);
        array.recycle();
        setMust(must);
        setTitle(title);
        setSubTitle(supTitle);
        setText(text);
        setHint(hint);
    }


    public void setMust(int must) {
        this.must = must;
        setTitle(title);
    }

    public void setTitle(String title) {
        this.title = title;
        if (must == 1) {
            binding.ItemTextTitle.setText(Util.setMustTilte(title));
        } else {
            binding.ItemTextTitle.setText(title);
        }
    }

    private void setSubTitle(String supTitle) {
        this.supTitle = supTitle;
        if(StringUtil.isEmpty(supTitle)){
        binding.ItemTextSubTitle.setVisibility(GONE);
        }else {
            binding.ItemTextSubTitle.setVisibility(VISIBLE);
            binding.ItemTextSubTitle.setText(supTitle);
        }
    }

    public void setHint(String hint) {
        binding.ItemTextText.setHint(hint);
    }

    public void setText(CharSequence text) {
        if (text == null) {
            text = "";
        }
        binding.ItemTextText.setText(text);
    }

}
