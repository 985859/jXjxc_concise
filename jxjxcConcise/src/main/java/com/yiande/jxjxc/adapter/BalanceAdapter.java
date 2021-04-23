package com.yiande.jxjxc.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.bean.BalanceRowsBean;
import com.yiande.jxjxc.databinding.ItmBalanceBinding;

import org.jetbrains.annotations.NotNull;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/15 17:44
 */
public class BalanceAdapter extends BaseQuickAdapter<BalanceRowsBean, BaseDataBindingHolder> {
    public BalanceAdapter() {
        super(R.layout.itm_balance);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder bindingHolder, BalanceRowsBean balanceListBean) {

        ItmBalanceBinding binding = (ItmBalanceBinding) bindingHolder.getDataBinding();
        binding.setData(balanceListBean);
    }
}
