package com.yiande.jxjxc.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.mylibrary.api.interfaces.SpinnerData;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.databinding.ItmSetIpBinding;

import java.util.List;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/25 15:04
 */
public class IPSetlcetAdapter<T extends SpinnerData> extends BaseQuickAdapter<T, BaseDataBindingHolder> {
    int selectIndx = 0;

    public void setSelectIndex(int selectIndx) {
        int i = this.selectIndx;
        this.selectIndx = selectIndx;
        notifyItemChanged(i);
        notifyItemChanged(this.selectIndx);
    }

    public IPSetlcetAdapter(List<T> mData) {
        super(R.layout.itm_set_ip, mData);
    }

    @Override
    protected void convert(BaseDataBindingHolder holder, T bean) {
        ItmSetIpBinding binding = (ItmSetIpBinding) holder.getDataBinding();
        binding.itmSetIPText.setText(bean.getText());
        if (selectIndx == holder.getBindingAdapterPosition()) {
            binding.itmSetIPText.setTextColor(getContext().getResources().getColor(R.color.blue));
        } else {
            binding.itmSetIPText.setTextColor(getContext().getResources().getColor(R.color.textColor));
        }
    }
}