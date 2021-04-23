package com.yiande.jxjxc.utils;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.widget.ItemView;
import com.mylibrary.api.widget.MySpinnerView;
import com.mylibrary.api.widget.TopSearchView;
import com.mylibrary.api.widget.TopView;
import com.mylibrary.api.widget.VariedLinearLayout;
import com.mylibrary.api.widget.VariedTextView;
import com.mylibrary.api.widget.picassoImage.PicassoImageView;
import com.yiande.jxjxc.R;

/**
 * @Description: xml 设置属性
 * @Author: hukui
 * @Date: 2020/7/16 16:08
 */
public class BindUtil {
    @BindingAdapter("setBackground")
    public static void setBackground(View view, Drawable resId) {
        view.setBackground(resId);
    }


    @BindingAdapter("setBackgroundColor")
    public static void setBackgroundColor(View view, int resId) {
        view.setBackgroundColor(resId);
    }

    @BindingAdapter("setTextColor")
    public static void setTextColor(TextView view, ColorStateList color) {
        view.setTextColor(color);
    }

    @BindingAdapter("setTextColor")
    public static void setTextColor(TextView view, int resId) {
        view.setTextColor(resId);
    }

    @BindingAdapter("setPicUrl")
    public static void setTextColor(PicassoImageView view, String url) {
        view.setImageUrl(url, R.drawable.icon_stub);
    }

    @BindingAdapter("setText")
    public static void setLeftText(TextView view, CharSequence text) {
        if (StringUtil.isNotEmpty(text) && !"null".equals(text)) {
            view.setText(text);
        } else {
            view.setText("");
        }

    }

    @BindingAdapter("setItemLeftText")
    public static void setLeftText(ItemView view, CharSequence text) {
        if (StringUtil.isNotEmpty(text) && !"null".equals(text)) {
            view.setLeftText(text);
        } else {
            view.setLeftText("");
        }

    }

    @BindingAdapter("setRightText")
    public static void setRightText(ItemView view, CharSequence text) {
        if (StringUtil.isNotEmpty(text) && !"null".equals(text)) {
            view.setRightText(text);
        } else {
            view.setRightText("");
        }

    }

    @BindingAdapter("setRightText")
    public static void setRightText(TopView view, CharSequence text) {
        if (StringUtil.isNotEmpty(text)) {
            view.setRightText(text);
        } else {
            view.setRightText("");
        }

    }

    @BindingAdapter("setRightText")
    public static void setRightText(TopSearchView view, CharSequence text) {
        if (StringUtil.isNotEmpty(text) && !"null".equals(text)) {
            view.setRightText(text);
        } else {
            view.setRightText("");
        }

    }

    @BindingAdapter("setToptTilte")
    public static void setToptTilte(TopView view, CharSequence tilte) {
        if (StringUtil.isNotEmpty(tilte) && !"null".equals(tilte)) {
            view.setTitle(tilte);
        } else {
            view.setTitle("");
        }

    }


    @BindingAdapter("spinnerText")
    public static void spinnerText(MySpinnerView view, CharSequence tilte) {
        if (StringUtil.isNotEmpty(tilte) && !"null".equals(tilte)) {
            view.setText(tilte);
        } else {
            view.setText("");
        }

    }

    @BindingAdapter("setDrawableRight")
    public static void setDrawableRight(VariedTextView view, Drawable drawable) {
        if (view != null && drawable != null) {
            view.setRightDrawable(drawable);
        }
    }

    @BindingAdapter("setDrawableRight")
    public static void setDawableRight(VariedTextView view, int drawable) {
        if (view != null) {
            view.setRightDrawable(drawable);
        }
    }

    @BindingAdapter({"startColor", "endColor"})
    public static void setVariedLinearLayoutBackground(VariedLinearLayout view, int startColor, int endColor) {

        view.setGradientColor(startColor, endColor);

    }


}
