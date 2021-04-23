package com.yiande.jxjxc.view;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mylibrary.api.utils.StringUtil;
import com.yiande.jxjxc.App;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.adapter.ViewOutStockAdapter;
import com.yiande.jxjxc.bean.OrderInfoBean;
import com.yiande.jxjxc.databinding.ViewOutStockBinding;
import com.yiande.jxjxc.utils.FontCustom;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/11/12 10:24
 */
public class OutStockView extends NestedScrollView {

    OrderInfoBean bean;
    Context context;
    ViewOutStockBinding binding;
    ViewOutStockAdapter outStockAdapter;
    int type = 0;//0 出库单  1  入库单

    public OutStockView(Context context) {
        this(context, null);
    }

    public OutStockView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OutStockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        View view = inflate(context, R.layout.view_out_stock, null);
        ViewGroup.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(view, params);
        view.setTag("layout/view_out_stock_0");
        if (!isInEditMode()) {
            binding = DataBindingUtil.bind(view);
            outStockAdapter = new ViewOutStockAdapter();
            binding.viewOutStockRec.setAdapter(outStockAdapter);
            binding.viewOutStockRec.setLayoutManager(new LinearLayoutManager(context) {

                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });


            binding.outStockAmount.getLeftView().setTypeface(FontCustom.setFont(context));
            binding.outStockAmount.getRightView().setTypeface(FontCustom.setFont(context));

            binding.outStockPayableAmount.getLeftView().setTypeface(FontCustom.setFont(context));
            binding.outStockPayableAmount.getRightView().setTypeface(FontCustom.setFont(context));

        }


    }

    public void setType(int type) {
        this.type = type;
        binding.setType(type);
        outStockAdapter.setType(type);
        //0 出库单  1  入库单
        if (type == 0) {

            binding.outStockPayableAmount.setLeftText("应           收:");
            binding.outStockRealyAmount.setLeftText("实           收:");
            binding.outStockDebt.setLeftText("欠           款:");
            binding.outStockAllDebt.setLeftText("当前总欠款:");
        } else {

            binding.outStockPayableAmount.setLeftText("应           付:");
            binding.outStockRealyAmount.setLeftText("实           付:");
            binding.outStockDebt.setLeftText("赊           账:");
            binding.outStockAllDebt.setLeftText("当前总赊账:");

        }


    }

    public void setBean(OrderInfoBean bean) {
        this.bean = bean;
        if (bean != null) {
            binding.setComName(App.comName);
            binding.setData(bean);
            outStockAdapter.setList(bean.getOrder_Detail());
            outStockAdapter.setIsKG(bean.getIsPrintKg());
            if (StringUtil.isNotEmpty(bean.getPrintMemo())) {
                binding.outStockText.setText(Html.fromHtml(bean.getPrintMemo()));
            }

        }

    }
}
