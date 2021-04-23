package com.yiande.jxjxc.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.bean.InvoiceRowsBean;
import com.yiande.jxjxc.databinding.ItmInvoiceBinding;

import org.jetbrains.annotations.NotNull;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/29 15:41
 */
public class InvoiceAdapter extends BaseQuickAdapter<InvoiceRowsBean, BaseDataBindingHolder> {
    public InvoiceAdapter() {
        super(R.layout.itm_invoice);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder bindingHolder, InvoiceRowsBean invoiceRowsBean) {
        ItmInvoiceBinding binding = (ItmInvoiceBinding) bindingHolder.getDataBinding();
        binding.setData(invoiceRowsBean);
        binding.setIndex(String.valueOf(bindingHolder.getBindingAdapterPosition() + 1));
    }
}
