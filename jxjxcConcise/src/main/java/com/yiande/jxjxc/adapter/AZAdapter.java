package com.yiande.jxjxc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yiande.jxjxc.R;
import com.yiande.jxjxc.azlist.AZBaseAdapter;
import com.yiande.jxjxc.azlist.AZItemEntity;
import com.yiande.jxjxc.bean.UserKeyBean;

import java.util.List;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/10 14:55
 */
public class AZAdapter extends AZBaseAdapter<UserKeyBean, AZAdapter.ItemHolder> {
    private Context context;


    private int state;

    public AZAdapter(Context context, List<UserKeyBean> dataList) {
        super(dataList);
        this.context = context;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.itm_a_z, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        UserKeyBean entity = mDataList.get(position);
        if (entity != null) {
            AZItmAdapter adapter = new AZItmAdapter();
            adapter.setState(state);
            if (entity.getValue() != null) {
                adapter.setList(entity.getValue());
            }
            holder.itmAZRec.setAdapter(adapter);
            holder.itmAZRec.setLayoutManager(new LinearLayoutManager(context) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
        }
    }
    public void setState(int state) {
        this.state = state;
    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        RecyclerView itmAZRec;

        public ItemHolder(View itemView) {
            super(itemView);

            itmAZRec = itemView.findViewById(R.id.itmAZ_Rec);
        }
    }


}
