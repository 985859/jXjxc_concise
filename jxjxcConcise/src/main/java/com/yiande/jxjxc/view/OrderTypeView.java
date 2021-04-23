package com.yiande.jxjxc.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.yiande.jxjxc.R;
import com.yiande.jxjxc.databinding.LayoutOrderTypeBinding;
import com.yiande.jxjxc.myInterface.EventConsume;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/7 17:07
 */
public class OrderTypeView extends LinearLayout {
    Context context;
    private int type;
    public LayoutOrderTypeBinding binding;

    private EventConsume<Integer> onselectListener;

    public OrderTypeView(Context context) {
        this(context, null);
    }

    public OrderTypeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OrderTypeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        View view = inflate(context, R.layout.layout_order_type, null);
        ViewGroup.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(view, params);
        view.setTag("layout/layout_order_type_0");
        binding = DataBindingUtil.bind(view);
        init(attrs);


    }

    int color1;
    int color2;

    private void init(AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.OrderTypeView);
        String title = array.getString(R.styleable.OrderTypeView_title);
        String text1 = array.getString(R.styleable.OrderTypeView_type_text1);
        String text2 = array.getString(R.styleable.OrderTypeView_type_text2);

        color1 = array.getResourceId(R.styleable.OrderTypeView_type_color1, R.color.gray3);
        color2 = array.getResourceId(R.styleable.OrderTypeView_type_color2, R.color.gray3);

        type = array.getInt(R.styleable.OrderTypeView_type, 0);
        array.recycle();
        setTitle(title);
        setText1(text1);
        setText2(text2);
        setColor1(color1);
        setColor2(color2);
        setType(type);

        binding.stockOrderType1.setOnClickListener(v -> onClik.accept(0));
        binding.stockOrderType2.setOnClickListener(v -> onClik.accept(1));
    }

    private EventConsume<Integer> onClik = (type) -> {
        setType(type);
        if (onselectListener != null) {
            onselectListener.accept(type);
        }
    };

    public void setOnselectListener(EventConsume<Integer> onselectListener) {
        this.onselectListener = onselectListener;
    }

    public void setTitle(String title) {
        binding.stockOrderTitle.setText(title);
    }

    public void setText1(String text1) {
        binding.stockOrderType1.setText(text1);
    }

    public void setText2(String text2) {
        binding.stockOrderType2.setText(text2);
    }

    public void setColor1(int color1) {
        this.color1 = color1;
    }

    public void setColor2(int color2) {
        this.color2 = color2;
    }

    public void setType(int type) {
        if (type == 0) {
            binding.stockOrderType1.setBackgroundColor(ContextCompat.getColor(context, color1));
            binding.stockOrderType2.setBackgroundColor(ContextCompat.getColor(context, color2));

            binding.stockOrderType1.setTextColor(ContextCompat.getColor(context, R.color.white));
            binding.stockOrderType2.setTextColor(ContextCompat.getColor(context, R.color.textColor));
        } else {
            binding.stockOrderType1.setBackgroundColor(ContextCompat.getColor(context, color2));
            binding.stockOrderType2.setBackgroundColor(ContextCompat.getColor(context, color1));

            binding.stockOrderType1.setTextColor(ContextCompat.getColor(context, R.color.textColor));
            binding.stockOrderType2.setTextColor(ContextCompat.getColor(context, R.color.white));
        }
    }


}
