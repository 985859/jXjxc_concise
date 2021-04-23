package com.yiande.jxjxc.adapter;

import android.util.ArrayMap;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.mylibrary.api.utils.StringUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.bean.OClassBean;
import com.yiande.jxjxc.databinding.ItmOclassBinding;
import com.yiande.jxjxc.myInterface.EventConsume;
import com.yiande.jxjxc.myInterface.OnSelectListener;

import java.util.List;
import java.util.Map;

public class ClassfityAdapter extends BaseQuickAdapter<OClassBean, BaseDataBindingHolder> {
    private int level = 1;
    private int parentPosition = 0;
    private int maxLevel = 1;

    private OnSelectListener<OClassBean> addListener;
    private EventConsume<Map<Integer, Integer>> parentMoreListener;
    private EventConsume<Map<Integer, Integer>> moreListener = (map) -> {
        if (level != 1) {
            map.put(level - 1, parentPosition);
        }
        if (parentMoreListener != null) {
            parentMoreListener.accept(map);
        }
    };

    private View.OnClickListener getMoreListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                Map<Integer, Integer> map = new ArrayMap<>();
                map.put(level, position);
                if (moreListener != null) {
                    moreListener.accept(map);
                }
            }
        };
    }

    public void setMoreListener(EventConsume<Map<Integer, Integer>> moreListener) {
        this.parentMoreListener = moreListener;
    }

    public void setAddListener(OnSelectListener<OClassBean> addListener) {
        this.addListener = addListener;
    }

    public void setParentPosition(int parentPosition) {
        this.parentPosition = parentPosition;
    }

    @Override
    protected void convert(BaseDataBindingHolder helper, OClassBean item) {
        ItmOclassBinding binding = (ItmOclassBinding) helper.getDataBinding();
        binding.itmOClassTitle.setText(StringUtil.arabToRoman(level) + "、" + item.getOClass_Name());
        binding.itmOClassMroe.setTag(helper.getBindingAdapterPosition());
        binding.itmOClassAdd.setTag(helper.getBindingAdapterPosition());
        binding.itmOClassAdd.setOnClickListener(getAddListener());
        if (level == 1) {
            binding.itmOClassV1.setVisibility(View.GONE);
            binding.itmOClassVTop.setVisibility(View.GONE);
        } else {
            binding.itmOClassV1.setVisibility(View.VISIBLE);
            binding.itmOClassVTop.setVisibility(View.VISIBLE);
        }
        binding.itmOClassMroe.setOnClickListener(getMoreListener());
        RecyclerView recyclerView = binding.itmOClassRec;
        View lineV = binding.itmOClassV2;
        if (maxLevel > level) {
            binding.itmOClassAdd.setText(" ＋ 添加" + StringUtil.arabToChinese(level + 1) + "级分类");
            binding.itmOClassAdd.setVisibility(View.VISIBLE);

            if (item.getOClass_Child() != null && item.getOClass_Child().size() > 0) {
                recyclerView.setVisibility(View.VISIBLE);
                lineV.setVisibility(View.VISIBLE);
                ClassfityAdapter classfityAdapter = new ClassfityAdapter(null);
                classfityAdapter.setLevel(level + 1);
                classfityAdapter.setMaxLevel(maxLevel);
                classfityAdapter.setAddListener(addListener);
                classfityAdapter.setMoreListener(moreListener);
                classfityAdapter.setList(item.getOClass_Child());
                classfityAdapter.setParentPosition(helper.getBindingAdapterPosition());
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(classfityAdapter);
            } else {
                recyclerView.setVisibility(View.GONE);
                lineV.setVisibility(View.GONE);
            }
        } else {
            recyclerView.setVisibility(View.GONE);
            binding.itmOClassAdd.setVisibility(View.GONE);
            lineV.setVisibility(View.GONE);
        }

    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
        notifyDataSetChanged();
    }

    public ClassfityAdapter(@Nullable List<OClassBean> data) {
        super(R.layout.itm_oclass, data);
    }


    private View.OnClickListener getAddListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                if (addListener != null) {
                    addListener.onSelect(getData().get(position), getData().get(position).getOClass_Child(), true);
                }
            }
        };
    }


}
