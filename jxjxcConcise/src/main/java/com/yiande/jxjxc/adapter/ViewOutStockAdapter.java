package com.yiande.jxjxc.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.bean.OrderProAddBean;
import com.yiande.jxjxc.databinding.ItmViewOutStockBinding;

import org.jetbrains.annotations.NotNull;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/11/12 11:28
 */
public class ViewOutStockAdapter extends BaseQuickAdapter<OrderProAddBean, BaseDataBindingHolder<ItmViewOutStockBinding>> {

    private int isKG;
    private int type;

    public void setType(int type) {
        this.type = type;
    }

    public void setIsKG(int isKG) {
        this.isKG = isKG;
    }

    public ViewOutStockAdapter() {
        super(R.layout.itm_view_out_stock);
    }


    @Override
    protected void convert(@NotNull BaseDataBindingHolder<ItmViewOutStockBinding> holder, OrderProAddBean bean) {
        ItmViewOutStockBinding binding = holder.getDataBinding();
        binding.setIndex(holder.getBindingAdapterPosition() + 1);
        binding.setData(bean);
        binding.setIsPrintKg(isKG);
        binding.setType(type);
    }
}
