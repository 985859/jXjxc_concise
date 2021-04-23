package com.yiande.jxjxc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.mylibrary.api.managelayout.DividerItemDecoration;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.azlist.AZBaseAdapter;
import com.yiande.jxjxc.bean.ContactsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/10 14:55
 */
public class ContactsAdapter extends AZBaseAdapter<ContactsBean, ContactsAdapter.ItemHolder> {
    private Context context;
    DividerItemDecoration dividerItemDecoration;
    private List<Integer> flagList = new ArrayList<>();

    public void setFlagList(List<Integer> flagList, boolean isAll) {
        this.flagList = flagList;
    }

    public ContactsAdapter(Context context, List<ContactsBean> dataList) {
        super(dataList);
        this.context = context;
        dividerItemDecoration = new DividerItemDecoration(context, LinearLayoutManager.VERTICAL, 0, ContextCompat.getColor(context, R.color.background));
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.itm_a_z, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {

        ContactsBean entity = mDataList.get(position);
        if (entity != null) {
            ContactsItemAdapter itemAdapter = new ContactsItemAdapter();
            if (entity.getValue() != null) {
                itemAdapter.setList(entity.getValue());
            }
            itemAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                    itemAdapter.getItem(position).setSelect(!itemAdapter.getItem(position).isSelect());
                    itemAdapter.notifyItemChanged(position);
                }
            });
            holder.itmAZRec.setAdapter(itemAdapter);
            holder.itmAZRec.removeItemDecoration(dividerItemDecoration);
            holder.itmAZRec.addItemDecoration(dividerItemDecoration);
            holder.itmAZRec.setLayoutManager(new LinearLayoutManager(context) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
        }
    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        RecyclerView itmAZRec;

        public ItemHolder(View itemView) {
            super(itemView);
            itmAZRec = itemView.findViewById(R.id.itmAZ_Rec);
        }
    }


}
