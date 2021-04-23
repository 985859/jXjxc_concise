package com.yiande.jxjxc.adapter;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.mylibrary.api.utils.ToastUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.bean.DebtListBean;
import com.yiande.jxjxc.databinding.ItmDebtBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/15 10:03
 */
public class DebtAdapter extends BaseQuickAdapter<DebtListBean, BaseDataBindingHolder> {
    private int type;//1欠款 2赊账
    private int maxIndex = 9999999;
    private boolean isCancle;//只有在maxIndex=1  单选有效 默认单选不可取消
    private boolean showCheck;
    private List<Integer> selectIds = new ArrayList<>();

    public void setShowCheck(boolean showCheck) {
        this.showCheck = showCheck;
    }

    public void setCancle(boolean cancle) {
        isCancle = cancle;
    }

    public void setSelectIndex(int id) {
        if (maxIndex == 1) {
            int oldID = -1;
            if (selectIds.size() > 0)
                oldID = selectIds.get(0);
            if (oldID == id) {
                if (isCancle) {
                    selectIds.clear();
                    notifyItemChanged(id);
                } else {
                    return;
                }
            } else {
                selectIds.clear();
                selectIds.add(id);
                if (oldID != -1)
                    notifyItemChanged(oldID);
                notifyItemChanged(id);
            }
        } else {
            boolean isAdd = true;
            int index = -1;
            for (int i = 0; i < selectIds.size(); i++) {
                if (selectIds.get(i) == id) {
                    isAdd = false;
                    index = i;
                }
            }
            if (isAdd) {
                if (selectIds.size() >= maxIndex) {
                    ToastUtil.showShort("最多选中" + maxIndex + "项");
                } else {
                    selectIds.add(id);
                    notifyItemChanged(id);
                }
            } else {
                if (index != -1) {
                    selectIds.remove(index);
                    notifyItemChanged(id);
                }
            }
        }

    }

    public void delearID() {
        if (selectIds != null && selectIds.size() > 0) {
            for (int i = selectIds.size() - 1; i >= 0; i--) {
                int index = selectIds.get(i);
                selectIds.remove(i);
                notifyItemChanged(index);
            }
        }

    }


    public int getSelcedIDSize() {
        return selectIds == null ? 0 : selectIds.size();
    }

    public List<Integer> getSelcedIds() {
        return selectIds == null ? new ArrayList<>() : selectIds;
    }

    public void setMaxIndex(int maxIndex) {
        this.maxIndex = maxIndex;
    }


    public void setType(int type) {
        this.type = type;
    }

    public DebtAdapter() {
        super(R.layout.itm_debt);

    }


    @Override
    protected void convert(@NotNull BaseDataBindingHolder bindingHolder, DebtListBean debtListBean) {
        ItmDebtBinding binding = (ItmDebtBinding) bindingHolder.getDataBinding();
        binding.setData(debtListBean);
        binding.setType(type);
        binding.setShowCheck(showCheck);
        int index = -1;
        if (showCheck) {
            if (selectIds.size() > 0 && selectIds != null) {
                if (maxIndex > 1) {
                    for (int i : selectIds) {
                        if (i == bindingHolder.getBindingAdapterPosition()) {
                            index = bindingHolder.getBindingAdapterPosition();
                            break;
                        }
                    }
                } else if (maxIndex == 1) {
                    index = selectIds.get(0);
                }
            }
            if (index == bindingHolder.getBindingAdapterPosition()) {
                binding.itmDebtCheck.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.check_img_b));
            } else {
                binding.itmDebtCheck.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.check_imgno));
            }
        }


    }
}
