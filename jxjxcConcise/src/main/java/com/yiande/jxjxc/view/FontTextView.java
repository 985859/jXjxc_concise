package com.yiande.jxjxc.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yiande.jxjxc.utils.FontCustom;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/11/2 10:46
 */
public class FontTextView extends androidx.appcompat.widget.AppCompatTextView {
    public FontTextView(@NonNull Context context) {
        this(context, null);
    }

    public FontTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FontTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 初始化字体
     *
     * @param context
     */
    private void init(Context context) {
        //设置字体样式
        setTypeface(FontCustom.setFont(context));
    }
}
