package com.yiande.jxjxc.adapter;

import android.view.View;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.activity.DebtActivity;
import com.yiande.jxjxc.bean.DebtRowsBean;
import com.yiande.jxjxc.databinding.ItmDebtManageBinding;

import org.jetbrains.annotations.NotNull;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/16 17:30
 */
public class DebtManageAdapter extends BaseQuickAdapter<DebtRowsBean, BaseDataBindingHolder> {
    private int type;//1 欠款 2 赊账
    public void setType(int type) {
        this.type = type;
    }

    public DebtManageAdapter() {
        super(R.layout.itm_debt_manage);
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                DebtActivity.start(getContext(), type, getItem(position).getID(), getItem(position).getComName());
            }
        });
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder bindingHolder, DebtRowsBean debtRowsBean) {
        ItmDebtManageBinding binding = (ItmDebtManageBinding) bindingHolder.getDataBinding();
        binding.setData(debtRowsBean);
        binding.setType(type);
    }
}
