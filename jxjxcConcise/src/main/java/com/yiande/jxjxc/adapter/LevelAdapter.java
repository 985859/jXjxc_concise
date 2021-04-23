package com.yiande.jxjxc.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.yiande.jxjxc.myInterface.LevelData;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.databinding.ItmLevelBinding;


import org.jetbrains.annotations.NotNull;


/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/3 16:20
 */
public class LevelAdapter<T extends LevelData> extends BaseQuickAdapter<T, BaseDataBindingHolder> {

    public LevelAdapter() {
        super(R.layout.itm_level);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder bindingHolder, T t) {
        ItmLevelBinding levelBinding = (ItmLevelBinding) bindingHolder.getDataBinding();
        levelBinding.itmLevelText.setText(t.getName());
    }


}
