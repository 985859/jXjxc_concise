package com.yiande.jxjxc.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.bean.UserBean;
import com.yiande.jxjxc.databinding.ItmContantsUserBinding;

import org.jetbrains.annotations.NotNull;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2021/4/22 17:23
 */
public class ContactsItemAdapter extends BaseQuickAdapter<UserBean, BaseDataBindingHolder> {
    public ContactsItemAdapter() {
        super(R.layout.itm_contants_user);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder baseDataBindingHolder, UserBean userBean) {
        ItmContantsUserBinding binding = (ItmContantsUserBinding) baseDataBindingHolder.getDataBinding();
        binding.setData(userBean);

    }
}
