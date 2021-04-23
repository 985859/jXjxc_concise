package com.yiande.jxjxc.adapter;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.bean.OClassBean;
import com.yiande.jxjxc.databinding.ItmUnitBrandBinding;

import org.jetbrains.annotations.NotNull;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/9/17 10:44
 */
public class UnitBrandAdapter extends BaseQuickAdapter<OClassBean, BaseDataBindingHolder> {


    public UnitBrandAdapter() {
        super(R.layout.itm_unit_brand);
    }

    /**
     * 当 ViewHolder 创建完毕以后，会执行此回掉
     * 可以在这里做任何你想做的事情
     */
    @Override
    protected void onItemViewHolderCreated(@NotNull BaseDataBindingHolder viewHolder, int viewType) {
        super.onItemViewHolderCreated(viewHolder, viewType);
        // 绑定 view
        DataBindingUtil.bind(viewHolder.itemView);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder baseDataBindingHolder, OClassBean classBean) {
        ItmUnitBrandBinding binding = (ItmUnitBrandBinding) baseDataBindingHolder.getDataBinding();
        binding.setData(classBean);
    }


}
