package com.yiande.jxjxc.presenter;

import android.content.Intent;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;
import androidx.recyclerview.widget.GridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.mylibrary.api.Database.DatabaseUtil;
import com.mylibrary.api.managelayout.DividerGridItemDecoration;
import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.utils.SystemUtil;
import com.mylibrary.api.utils.ToastUtil;
import com.mylibrary.api.widget.FlowLayout;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.activity.AdminListActivity;
import com.yiande.jxjxc.activity.AfterServiceListActivity;
import com.yiande.jxjxc.activity.ProductListActivity;
import com.yiande.jxjxc.activity.StockOrderListActivity;
import com.yiande.jxjxc.activity.UserListActivity;
import com.yiande.jxjxc.base.BasePresenter;
import com.yiande.jxjxc.bean.KeywordBean;
import com.yiande.jxjxc.databinding.ActivitySelectBinding;


import java.util.List;
import java.util.Map;

import static com.mylibrary.api.Database.MyDBOpenHelper.SELECT;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/8/10 10:04
 */
public class SelectPresenter extends BasePresenter<ActivitySelectBinding> {
    int type = 0;
    int state = 0;

    public SelectPresenter(RxAppCompatActivity mContext, ActivitySelectBinding binding) {
        super(mContext, binding);
        Intent intent = mContext.getIntent();
        String hint = null;
        if (intent != null) {
            type = intent.getIntExtra("type", 0);
            state = intent.getIntExtra("state", 0);
            hint = intent.getStringExtra("hint");
        }
        mBinding.selectTop.setSelectHint(hint == null ? "" : hint);
    }

    private BaseQuickAdapter<KeywordBean, BaseViewHolder> dataAdapter;

    @Override
    protected void initData() {
        super.initData();
        mBinding.selectTop.getMySearchView().getEdittext().setFocusable(true);
        mBinding.selectTop.getMySearchView().getEdittext().setFocusableInTouchMode(true);
        mBinding.selectRec.setLayoutManager(new GridLayoutManager(mContext, 1));
        mBinding.selectRec.setAdapter(dataAdapter);
        mBinding.selectRec.addItemDecoration(new DividerGridItemDecoration(mContext, 1, R.color.background));

        dataAdapter = new BaseQuickAdapter<KeywordBean, BaseViewHolder>(R.layout.itm_select_text, null) {
            @Override
            protected void convert(BaseViewHolder helper, KeywordBean item) {
                helper.setText(R.id.itmselect_text, item.getTitle());
            }
        };
    }


    @Override
    protected void setListener() {
        super.setListener();
        mBinding.selectTop.setRightTextListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.finish();
            }
        });
        mBinding.selectTop.getMySearchView().getEdittext().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == 3) {
                    skipShopList(mBinding.selectTop.getMySearchView().getEdittext().getText().toString().trim(), type);
                }
                return false;
            }
        });

        mBinding.selectHistory.setSelectListener(new FlowLayout.OnFlowSelectListener<Object>() {
            @Override
            public void flowSelect(View view, int index, Object bean) {
                TextView textView = (TextView) view;
                skipShopList(textView.getText().toString().trim(), type);
            }
        });

        mBinding.selectTop.getMySearchView().setSearchCollapsedListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipShopList(mBinding.selectTop.getMySearchView().getEdittext().getText().toString().trim(), type);
            }
        });
        mBinding.selectDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.selectHistory.removeAllViews();
                mBinding.selectHistoryText.setVisibility(View.GONE);
                mBinding.selectDelete.setVisibility(View.GONE);
                DatabaseUtil.empty(SELECT);
            }
        });

        dataAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                skipShopList(dataAdapter.getData().get(position).getTitle(), type);
            }
        });
    }


    private void skipShopList(String query, int type) {
        if (StringUtil.isNotEmpty(query)) {

            ArrayMap<String, Object> map = new ArrayMap<>();
            DatabaseUtil.delete(SELECT, "title", query);
            map.put("title", query);
            map.put("type", type);
            DatabaseUtil.insertCondition(SELECT, map);
            switch (type) {
                case 1:
                    AdminListActivity.start(mContext, query);
                    break;
                case 2:
                    UserListActivity.start(mContext, state, query);
                    break;
                case 3:
                    ProductListActivity.start(mContext, query);
                    break;
                case 4:
                    StockOrderListActivity.start(mContext, state, query);
                    break;
                case 5:

                    break;
                case 6:
                    AfterServiceListActivity.start(mContext,  query);
                    break;
            }
            mContext.finish();
        } else {
            ToastUtil.showShort( "请输入要搜索的内容");
        }
    }


    public void setselectHistory() {
        mBinding.selectHistory.removeAllViews();
        List<Map<String, Object>> mapList = com.yiande.jxjxc.App.databaseUtil.queryCondition(SELECT, null, null, null);
        if (mapList != null && mapList.size() > 0) {
            mBinding.selectHistoryText.setVisibility(View.VISIBLE);
            mBinding.selectDelete.setVisibility(View.VISIBLE);
            for (int i = mapList.size() - 1; i >= 0; i--) {
                TextView textView = getTextView(mapList.get(i).get("title").toString().trim());
                mBinding.selectHistory.addView(textView);
            }
        } else {
            mBinding.selectHistoryText.setVisibility(View.GONE);
            mBinding.selectDelete.setVisibility(View.GONE);
        }
    }

    private TextView getTextView(String text) {
        TextView textView = new TextView(mContext);
        if (StringUtil.isNotEmpty(text))
            textView.setText(text);
        textView.setTextColor(mContext.getResources().getColor(R.color.textColor));
        textView.setTextSize(12);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundResource(R.drawable.shape_texet);
        textView.setPadding(SystemUtil.dp2px(mContext, 12), SystemUtil.dp2px(mContext, 2), SystemUtil.dp2px(mContext, 12), SystemUtil.dp2px(mContext, 2));
        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        marginLayoutParams.bottomMargin = SystemUtil.dp2px(mContext, 4);
        marginLayoutParams.topMargin = SystemUtil.dp2px(mContext, 4);
        marginLayoutParams.leftMargin = SystemUtil.dp2px(mContext, 4);
        marginLayoutParams.rightMargin = SystemUtil.dp2px(mContext, 4);
        textView.setLayoutParams(marginLayoutParams);
        return textView;
    }


}
