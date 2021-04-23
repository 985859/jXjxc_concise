package com.yiande.jxjxc.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.bean.AfterServiceProductBean;
import com.yiande.jxjxc.databinding.ItmAfterServiceProductBinding;

import org.jetbrains.annotations.NotNull;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/9/18 20:21
 */
public class AfterServiceProductAdapter extends BaseQuickAdapter<AfterServiceProductBean, BaseDataBindingHolder> {
    public AfterServiceProductAdapter() {
        super(R.layout.itm_after_service_product);
    }



    @Override
    protected void convert(@NotNull BaseDataBindingHolder holder, AfterServiceProductBean productBean) {
        ItmAfterServiceProductBinding binding = (ItmAfterServiceProductBinding) holder.getDataBinding();

        binding.setData(productBean);


    }


}
