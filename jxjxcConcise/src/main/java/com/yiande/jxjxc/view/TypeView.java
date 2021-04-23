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
import androidx.databinding.BindingMethod;
import androidx.databinding.BindingMethods;
import androidx.databinding.DataBindingUtil;

import com.mylibrary.api.utils.StringUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.databinding.LayoutTypeBinding;
import com.yiande.jxjxc.utils.Util;


@BindingMethods({
        @BindingMethod(type =TypeView.class, attribute = "title",method = "setTitle"),
        @BindingMethod(type =TypeView.class, attribute = "subTitle",method = "setSubTitle"),
        @BindingMethod(type =TypeView.class, attribute = "hint",method = "setHint"),
        @BindingMethod(type =TypeView.class, attribute = "text",method = "setText"),
        @BindingMethod(type =TypeView.class, attribute = "must",method = "setMust"),
        @BindingMethod(type =TypeView.class, attribute = "typeView_addText",method = "setAddText"),
})
/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/11/25 15:25
 */
public class TypeView extends LinearLayout {
    Context context;
    public LayoutTypeBinding binding;

    public TypeView(Context context) {
        this(context, null);
    }

    public TypeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TypeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        View view = inflate(context, R.layout.layout_type, null);
        ViewGroup.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(view, params);
        view.setTag("layout/layout_type_0");
        binding = DataBindingUtil.bind(view);
        init(attrs);
    }

    public void setAddOnClickListener(OnClickListener clickListener) {
        if (clickListener != null)
            binding.TypeAdd.setOnClickListener(clickListener);
    }

    private String title;
    private String supTitle;
    private String hint;
    private String addText;
    private String text;
    private int must;

    private void init(AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TypeView);
        title = array.getString(R.styleable.TypeView_title);
        supTitle = array.getString(R.styleable.TypeView_subTitle);
        hint = array.getString(R.styleable.TypeView_android_hint);
        addText = array.getString(R.styleable.TypeView_typeView_addText);
        text = array.getString(R.styleable.TypeView_android_text);
        int must = array.getInt(R.styleable.TypeView_must, 0);
        array.recycle();
        setMust(must);
        setTitle(title);
        setSubTitle(supTitle);
        setText(text);
        setAddText(addText);
        setHint(hint);
    }

    @BindingAdapter("setText")
    public static void setText(TypeView typeView, String text) {
        typeView.setText(text);
    }


    public void setTitle(String title) {
        if (must == 1) {
            binding.TypeTitle.setText(Util.setMustTilte(title));
        } else {
            binding.TypeTitle.setText(title);
        }
    }

    public void setSubTitle(String title) {
        if (StringUtil.isEmpty(title)) {
            binding.TypeSubTitle.setVisibility(GONE);
        } else {
            binding.TypeSubTitle.setVisibility(VISIBLE);
            binding.TypeSubTitle.setText(supTitle);
        }
    }

    public void setHint(String hint) {
        binding.TypeText.setHint(hint);
    }

    public void setAddText(String addText) {
        if (StringUtil.isEmpty(addText)) {

            binding.TypeAdd.setVisibility(GONE);
        } else {
            binding.TypeAdd.setVisibility(VISIBLE);
            binding.TypeAdd.setText(String.format(context.getString(R.string.plus), addText));
        }

    }

    public void setText(String text) {
        if (text == null) {
            text = "";
        }
        binding.TypeText.setText(text);
    }

    public void setMust(int must) {

        this.must = must;
        setTitle(title);
    }

    public TextView getTextView() {
        return binding.TypeText;
    }

}
