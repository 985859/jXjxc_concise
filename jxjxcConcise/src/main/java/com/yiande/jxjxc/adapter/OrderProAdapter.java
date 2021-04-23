package com.yiande.jxjxc.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.bean.OrderProAddBean;
import com.yiande.jxjxc.databinding.ItmOrderPro1Binding;
import com.yiande.jxjxc.databinding.ItmOrderProBinding;

import org.jetbrains.annotations.NotNull;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/9/20 16:43
 */
public class OrderProAdapter extends BaseMultiItemQuickAdapter<OrderProAddBean, BaseDataBindingHolder> {
    private boolean isShow = true;
    private int type;//1 是否显示单价 小计
    private int orderType;//0 入库单 1 出库单
    private boolean isKg;//0 不显示重量  1 显示重量

    public OrderProAdapter() {
        super();
        addItemType(0, R.layout.itm_order_pro);
        addItemType(1, R.layout.itm_order_pro_1);
    }


    public void setShow(boolean show) {
        isShow = show;
    }

    public void setType(int type) {
        this.type = type;
    }


    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public void setIsKg(boolean isKg) {
        this.isKg = isKg;
    }


    @Override
    protected void convert(@NotNull BaseDataBindingHolder baseDataBindingHolder, OrderProAddBean bean) {

        switch (bean.getItemType()) {

            case 0:
                ItmOrderProBinding proBinding = (ItmOrderProBinding) baseDataBindingHolder.getDataBinding();
                proBinding.setData(bean);
                proBinding.setOrderType(orderType);
                proBinding.setIsKg(isKg);
                proBinding.setIndex(String.valueOf(baseDataBindingHolder.getBindingAdapterPosition() + 1));
                proBinding.setShowMore(isShow);
                break;
            case 1:
                ItmOrderPro1Binding binding = (ItmOrderPro1Binding) baseDataBindingHolder.getDataBinding();
                binding.setData(bean);
                binding.setOrderType(orderType);
                binding.setIndex(String.valueOf(baseDataBindingHolder.getBindingAdapterPosition() + 1));
                binding.setIsKg(isKg);
                binding.setShowMore(isShow);
                break;
            default:
                break;
        }

    }
}
