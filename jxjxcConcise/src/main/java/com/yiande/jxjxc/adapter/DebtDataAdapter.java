package com.yiande.jxjxc.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.bean.DebtListBean;
import com.yiande.jxjxc.databinding.ItmDebtDataBinding;

import org.jetbrains.annotations.NotNull;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/18 10:51
 */
public class DebtDataAdapter extends BaseQuickAdapter<DebtListBean, BaseDataBindingHolder> {
    private int type;//1欠款 2 赊账

    public void setType(int type) {
        this.type = type;
    }

    public DebtDataAdapter() {
        super(R.layout.itm_debt_data);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder bindingHolder, DebtListBean bean) {

        ItmDebtDataBinding binding = (ItmDebtDataBinding) bindingHolder.getDataBinding();
        binding.setData(bean);
        binding.setType(type);
        binding.setIndex(String.valueOf(bindingHolder.getBindingAdapterPosition()+1));
    }
}
