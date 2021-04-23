package com.yiande.jxjxc.popwindow;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.mylibrary.api.managelayout.DividerItemDecoration;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BasePopupWindow;
import com.yiande.jxjxc.databinding.ItmText2Binding;
import com.yiande.jxjxc.databinding.PopItemBinding;
import com.yiande.jxjxc.myInterface.EventConsume;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/11/26 11:10
 */
public class ItemPop extends BasePopupWindow<PopItemBinding> {

    BaseQuickAdapter<String, BaseDataBindingHolder> quickAdapter;
    private List<String> itemList = new ArrayList<>();
    private EventConsume<Integer> itemClickListener;

    public void setOnItemClickListener(EventConsume<Integer> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ItemPop(RxAppCompatActivity context) {
        super(context);
        mBinding.setOnClick(onClik);
        quickAdapter.setList(itemList);
        addItem("修改");
        addItem("删除");
    }

    @Override
    public int getContentViewID() {
        return R.layout.pop_item;
    }

    @Override
    public void init(View view) {
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mBinding.popItemRec.setLayoutManager(new LinearLayoutManager(activity));
        quickAdapter = new BaseQuickAdapter<String, BaseDataBindingHolder>(R.layout.itm_text2) {
            @Override
            protected void onItemViewHolderCreated(@NotNull BaseDataBindingHolder viewHolder, int viewType) {
                super.onItemViewHolderCreated(viewHolder, viewType);
                DataBindingUtil.bind(viewHolder.itemView);
            }

            @Override
            protected void convert(@NotNull BaseDataBindingHolder bindingHolder, String s) {
                ItmText2Binding binding = (ItmText2Binding) bindingHolder.getDataBinding();
                binding.itmText2.setText(s);
            }
        };
        mBinding.popItemRec.setAdapter(quickAdapter);

        mBinding.popItemRec.addItemDecoration(new DividerItemDecoration(activity, LinearLayoutManager.VERTICAL,
                0, activity.getResources().getColor(R.color.background), false));
        quickAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                dismiss();
                if (itemClickListener != null) {
                    itemClickListener.accept(position);
                }
            }
        });
    }

    public void setTitle(String title) {
        mBinding.setTitle(title);
    }


    public void addItem(String item) {
        quickAdapter.addData(item);

    }

    public void setItem(List<String> items) {
        quickAdapter.setList(items);

    }

    private EventConsume<Integer> onClik = (type) -> {
        switch (type) {

            case 0:
                dismiss();
                break;
            default:
                break;
        }
    };
}
