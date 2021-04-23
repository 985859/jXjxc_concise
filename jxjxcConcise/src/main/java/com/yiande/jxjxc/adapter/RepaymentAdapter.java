package com.yiande.jxjxc.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.bean.RepayRowsBean;
import com.yiande.jxjxc.databinding.ItmRepaymentBinding;

import org.jetbrains.annotations.NotNull;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/17 16:03
 */
public class RepaymentAdapter extends BaseQuickAdapter<RepayRowsBean, BaseDataBindingHolder> {
    public RepaymentAdapter() {
        super(R.layout.itm_repayment);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder bindingHolder, RepayRowsBean bean) {

        ItmRepaymentBinding binding = (ItmRepaymentBinding) bindingHolder.getDataBinding();
        binding.setData(bean);
        //0 正常还款 1 最后一笔 2反审核 3 红冲

        switch (bean.getType()) {
            case 0:
                binding.itmDebtDetailType.setText("还款");
                binding.itmDebtDetailType.setBackgroundColor(getContext().getResources().getColor(R.color.blue));
                break;
            case 1:
                binding.itmDebtDetailType.setText("结清");
                binding.itmDebtDetailType.setBackgroundColor(getContext().getResources().getColor(R.color.green));

                break;
            case 2:
                binding.itmDebtDetailType.setText("反审核");
                binding.itmDebtDetailType.setBackgroundColor(getContext().getResources().getColor(R.color.yellow));
                break;
            case 3:
                binding.itmDebtDetailType.setText("红冲");
                binding.itmDebtDetailType.setBackgroundColor(getContext().getResources().getColor(R.color.red));
                break;


            default:
                break;
        }

    }
}
