package com.yiande.jxjxc.adapter;

import android.text.SpannableStringBuilder;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.mylibrary.api.utils.SpannableUtil;
import com.mylibrary.api.utils.SystemUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.bean.UserBean;
import com.yiande.jxjxc.databinding.ItmSupplierBinding;
import com.yiande.jxjxc.databinding.ItmUserBinding;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/11/30 9:25
 */
public class UserAdapter extends BaseQuickAdapter<UserBean, BaseDataBindingHolder> {
    private int user_Type = 1;//1客户 2供应商
    public UserAdapter() {
        super(R.layout.itm_user);
    }
    public void setUser_Type(int user_Type) {
        this.user_Type = user_Type;
    }
    @Override
    protected void convert(@NotNull BaseDataBindingHolder bindingHolder, UserBean userBean) {
        ItmUserBinding userBinding = (ItmUserBinding) bindingHolder.getDataBinding();
        userBinding.setData(userBean);
        userBinding.setContext(getContext());
        if (userBean.getUser_IsOK() == 0) {
            SpannableStringBuilder builder = SpannableUtil.setStartTip(getContext().getString(R.string.disable), userBean.getUser_ComName(),
                    SystemUtil.sp2px(getContext(), 10), SystemUtil.sp2px(getContext(), 12), SystemUtil.sp2px(getContext(), 6),
                    getContext().getResources().getColor(R.color.yellow), getContext().getResources().getColor(R.color.white));
            userBinding.itmUserComName.setRightText(builder);
        } else {
            userBinding.itmUserComName.setRightText(userBean.getUser_ComName());
        }
    }

}
