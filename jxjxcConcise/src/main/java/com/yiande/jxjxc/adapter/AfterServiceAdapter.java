package com.yiande.jxjxc.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.bean.AfterServiceRowsBean;
import com.yiande.jxjxc.databinding.ItmAfterServiceBinding;

import org.jetbrains.annotations.NotNull;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/22 11:09
 */
public class AfterServiceAdapter extends BaseQuickAdapter<AfterServiceRowsBean, BaseDataBindingHolder> {

    public AfterServiceAdapter() {
        super(R.layout.itm_after_service);
    }

    protected void convert(@NotNull BaseDataBindingHolder bindingHolder, AfterServiceRowsBean afterServiceListBean) {

        ItmAfterServiceBinding binding = (ItmAfterServiceBinding) bindingHolder.getDataBinding();
        binding.setData(afterServiceListBean);
    }
}
