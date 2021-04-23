package com.yiande.jxjxc.adapter;

import android.text.SpannableStringBuilder;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.mylibrary.api.utils.SpannableUtil;
import com.mylibrary.api.utils.SystemUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.bean.AdminListBean;
import com.yiande.jxjxc.databinding.ItmAdminBinding;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/9/14 17:06
 */
public class AdminAdapter extends BaseQuickAdapter<AdminListBean, BaseDataBindingHolder> {
    public AdminAdapter(@Nullable List<AdminListBean> data) {
        super(R.layout.itm_admin, data);
    }



    @Override
    protected void convert(@NotNull BaseDataBindingHolder holder, AdminListBean adminBean) {
        ItmAdminBinding binding = (ItmAdminBinding) holder.getDataBinding();
        binding.setData(adminBean);

        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(adminBean.getAdmin_Name());
        if (adminBean.getAdmin_Type() == 1) {
            builder = SpannableUtil.setEndTip("管理员",builder,
                    SystemUtil.sp2px(getContext(), 10), SystemUtil.sp2px(getContext(), 12), SystemUtil.sp2px(getContext(), 6),
                    getContext().getResources().getColor(R.color.blue), getContext().getResources().getColor(R.color.white));
        }
        if (adminBean.getAdmin_IsOK() == 0) {
            builder = SpannableUtil.setEndTip(getContext().getString(R.string.disable), builder,
                    SystemUtil.sp2px(getContext(), 10), SystemUtil.sp2px(getContext(), 12), SystemUtil.sp2px(getContext(), 6),
                    getContext().getResources().getColor(R.color.yellow), getContext().getResources().getColor(R.color.white));

        }
        binding.itmAdminName.setRightText(builder);
    }
}
