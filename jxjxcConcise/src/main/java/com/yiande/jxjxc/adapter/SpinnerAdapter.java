package com.yiande.jxjxc.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.mylibrary.api.interfaces.SpinnerData;
import com.yiande.jxjxc.R;

import java.util.List;

/**
 * Created by myuser on 2018/3/30.
 */

public class SpinnerAdapter<T extends SpinnerData> extends BaseQuickAdapter<T, BaseDataBindingHolder> {
    int selectIndex = 0;

    public void setSelectIndex(int selectIndex) {
        this.selectIndex = selectIndex;
        notifyDataSetChanged();
    }

    public SpinnerAdapter(List<T> mData) {
        super(R.layout.itm_spinner, mData);

    }



    @Override
    protected void convert(BaseDataBindingHolder holder, T bean) {
        holder.setText(R.id.itm_spinner, bean.getText());
        if (selectIndex == holder.getBindingAdapterPosition()) {
            holder.setTextColor(R.id.itm_spinner, getContext().getResources().getColor(R.color.colorPrimary));
            holder.setGone(R.id.itm_spinnerIMG, false);
        } else {
            holder.setTextColor(R.id.itm_spinner, getContext().getResources().getColor(R.color.textColor));
            holder.setGone(R.id.itm_spinnerIMG, true);
        }
    }
}
