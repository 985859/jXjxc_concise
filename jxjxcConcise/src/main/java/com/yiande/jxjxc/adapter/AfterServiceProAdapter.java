package com.yiande.jxjxc.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.bean.AfterServiceProductBean;
import com.yiande.jxjxc.databinding.ItmAfterServiceProBinding;

import org.jetbrains.annotations.NotNull;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/23 14:53
 */
public class AfterServiceProAdapter extends BaseQuickAdapter<AfterServiceProductBean, BaseDataBindingHolder> {
    private boolean isShow = true;

    public AfterServiceProAdapter() {
        super(R.layout.itm_after_service_pro);
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder bindingHolder, AfterServiceProductBean bean) {
        ItmAfterServiceProBinding binding = (ItmAfterServiceProBinding) bindingHolder.getDataBinding();
        binding.setData(bean);
        binding.setShowMore(isShow);
        binding.setIndex(String.valueOf(bindingHolder.getBindingAdapterPosition() + 1));
    }
}
