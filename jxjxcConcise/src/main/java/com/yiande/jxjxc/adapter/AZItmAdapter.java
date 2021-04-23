package com.yiande.jxjxc.adapter;

import android.view.View;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.mylibrary.api.utils.ToastUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.activity.AfterServiceAddActivity;
import com.yiande.jxjxc.activity.DebtAddActivity;
import com.yiande.jxjxc.activity.ProductInStockActivity;
import com.yiande.jxjxc.activity.ProductOutStockActivity;
import com.yiande.jxjxc.bean.SupplierBean;
import com.yiande.jxjxc.databinding.ItmAZTextBinding;

import org.jetbrains.annotations.NotNull;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/10 14:55
 */
public class AZItmAdapter extends BaseQuickAdapter<SupplierBean, BaseDataBindingHolder> {

    private int state;

    public void setState(int state) {
        this.state = state;
    }

    public AZItmAdapter() {
        super(R.layout.itm_a_z_text);
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                SupplierBean bean = getItem(position);
                if (bean != null) {
                    switch (state) {
                        case 1://产品出库
                            ProductOutStockActivity.start(getContext(), bean.getID(), bean.getComName(), bean.getUserLevel());
                            break;
                        case 2://产品入库
                            ProductInStockActivity.start(getContext(), bean.getID(), bean.getComName());
                            break;
                        case 3:// 新增欠款
                            if (bean.getID() == 0) {
                                ToastUtil.showShort( "散客不能欠款");
                                return;
                            }
                            DebtAddActivity.start(getContext(), 1, bean.getID(), bean.getComName());
                            break;
                        case 4:// 新增赊账
                            if (bean.getID() == 0) {
                                ToastUtil.showShort( "散户不能赊账");
                                return;
                            }
                            DebtAddActivity.start(getContext(), 2, bean.getID(), bean.getComName());
                            break;
                        case 5:// 新增售后

                            AfterServiceAddActivity.start(getContext(),  bean.getID(), bean.getComName());
                            break;
                        default:
                            break;
                    }
                }

            }
        });
    }


    @Override
    protected void convert(@NotNull BaseDataBindingHolder bindingHolder, SupplierBean bean) {
        ItmAZTextBinding binding = (ItmAZTextBinding) bindingHolder.getDataBinding();
        binding.itmAZText.setText(bean.getComName());

    }
}
