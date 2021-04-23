package com.yiande.jxjxc.adapter;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.mylibrary.api.utils.LogUtil;
import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.utils.ToastUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.bean.RoleBean;
import com.yiande.jxjxc.databinding.ItmTextBinding;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/8/4 18:57
 */
public class TypeAdapter extends BaseQuickAdapter<RoleBean, BaseDataBindingHolder> {

    private int maxIndex = 9999999;
    private boolean isCancle = false;//只有在maxIndex=1  单选有效 默认单选不可取消

    private List<Integer> selectIds = new ArrayList<>();

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
                    ToastUtil.showShort( "最多选中" + maxIndex + "项");
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


    public String getselectID() {
        String id = "";
        if (getSelcedIDSize() > 0) {
            for (int i : getSelcedIds()) {
                if (i < getSelcedIds().size()) {
                    id += getData().get(i).getRole_ID() + ",";
                }
            }
        }
        if (StringUtil.isNotEmpty(id) && id.endsWith(","))
            id.substring(0, id.length() - 1);
        return id;
    }


    public void setselectIds(String ids) {
        if (StringUtil.isNotEmpty(ids)) {
            selectIds.clear();
            String[] idList = ids.split(",");
            if (idList != null && idList.length > 0 && getData().size() > 0) {
                for (String index : idList) {
                    int i = StringUtil.toInt(index);
                    for (int k = 0; k < getData().size(); k++) {
                        if (i == getData().get(k).getRole_ID()) {
                            selectIds.add(k);
                            break;
                        }
                    }
                }
                LogUtil.e("");
            }
            notifyDataSetChanged();
        }

    }

    public void setselectID(int id) {
        selectIds.clear();
        for (int k = 0; k < getData().size(); k++) {
            if (id == getData().get(k).getRole_ID()) {
                setSelectIndex(k);
            }
        }
    }


    public void setMaxIndex(int maxIndex) {
        this.maxIndex = maxIndex;
    }

    public TypeAdapter(@Nullable List<RoleBean> data) {
        super(R.layout.itm_text, data);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder holder, @NotNull RoleBean bean) {
        ItmTextBinding binding = (ItmTextBinding) holder.getDataBinding();
        binding.setData(bean);
        binding.itmText.setText(bean.getRole_Name());
        int index = -1;
        if (selectIds.size() > 0 && selectIds != null) {
            if (maxIndex > 1) {
                for (int i : selectIds) {
                    if (i == holder.getBindingAdapterPosition()) {
                        index = holder.getBindingAdapterPosition();
                        break;
                    }
                }
            } else if (maxIndex == 1) {
                index = selectIds.get(0);
            }
        }

        binding.itmText.setText(bean.getRole_Name());
        if (index == holder.getBindingAdapterPosition()) {
            binding.itmText.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.blue));
            binding.itmText.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        } else {
            binding.itmText.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray));
            binding.itmText.setTextColor(ContextCompat.getColor(getContext(), R.color.textColor));
        }
    }
}
