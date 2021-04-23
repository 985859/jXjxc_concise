package com.yiande.jxjxc.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.bean.StockOrderBean;
import com.yiande.jxjxc.databinding.ItemStoreOrderBinding;

import org.greenrobot.greendao.annotation.NotNull;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/9/19 10:19
 */
public class StockOrderAdapter extends BaseQuickAdapter<StockOrderBean, BaseDataBindingHolder> {

    private int type = 1;//1 入库 2出库
    private boolean showYuE;
    private boolean showDebt;

    public void setShowYuE(boolean showYuE) {
        this.showYuE = showYuE;
    }

    public void setShowDebt(boolean showDebt) {
        this.showDebt = showDebt;
    }

    public StockOrderAdapter() {
        super(R.layout.item_store_order);
    }

    public void setType(int type) {
        this.type = type;
    }


    @Override
    protected void convert(@NotNull BaseDataBindingHolder helper, @NotNull StockOrderBean item) {


        ItemStoreOrderBinding binding = (ItemStoreOrderBinding) helper.getDataBinding();
        binding.setData(item);
        binding.setType(type);
        binding.setShowYuE(showYuE);
        binding.setShowDebt(showDebt);
        if (item.getOrder_State() == 0) {
            binding.itmStoreOrderState.setBackgroundColor(getContext().getResources().getColor(R.color.red));
            binding.itmStoreOrderState.setText("红冲");
        }

        // //Order_Type 1进货 2报溢 3出库 4报损
        switch (item.getOrder_Type()) {
            case 1:
                binding.itmStoreOrderType.setText("进货");
                binding.itmStoreOrderType.setBackgroundColor(getContext().getResources().getColor(R.color.blue));
                break;
            case 2:
                binding.itmStoreOrderType.setText("报溢");
                binding.itmStoreOrderType.setBackgroundColor(getContext().getResources().getColor(R.color.yellow));
                break;
            case 3:
                binding.itmStoreOrderType.setText("出库");
                binding.itmStoreOrderType.setBackgroundColor(getContext().getResources().getColor(R.color.blue));
                break;
            case 4:
                binding.itmStoreOrderType.setText("报损");
                binding.itmStoreOrderType.setBackgroundColor(getContext().getResources().getColor(R.color.yellow));
                break;
            default:
                break;
        }

    }


}