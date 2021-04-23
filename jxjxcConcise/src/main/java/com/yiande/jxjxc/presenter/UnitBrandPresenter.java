package com.yiande.jxjxc.presenter;

import android.app.Dialog;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.mylibrary.api.dialog.MyDialog;
import com.mylibrary.api.managelayout.DividerItemDecoration;
import com.mylibrary.api.managelayout.TopLinearLayoutManager;
import com.retrofithttp.api.RetrofitHttp;
import com.retrofithttp.api.util.RxThreadUtil;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.adapter.UnitBrandAdapter;
import com.yiande.jxjxc.base.BasePresenter;
import com.yiande.jxjxc.base.HttpObserver;
import com.yiande.jxjxc.bean.JsonBean;
import com.yiande.jxjxc.bean.OClassBean;
import com.yiande.jxjxc.databinding.ActivityUnitBrandBinding;
import com.yiande.jxjxc.myInterface.EventConsume;
import com.yiande.jxjxc.myInterface.HttpApi;
import com.yiande.jxjxc.popwindow.AddBrandUnitPop;
import com.yiande.jxjxc.popwindow.ItemPop;
import com.yiande.jxjxc.utils.DialogUtils;
import com.yiande.jxjxc.utils.Util;

import java.util.List;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/9/17 10:52
 */
public class UnitBrandPresenter extends BasePresenter<ActivityUnitBrandBinding> {

    private int flag;
    private UnitBrandAdapter unitBrandAdapter;
    private AddBrandUnitPop addTextPop;
    private ItemPop itemPop;
    private int selectPosition = -1;

    public UnitBrandPresenter(RxAppCompatActivity mContext, ActivityUnitBrandBinding binding) {
        super(mContext, binding);
        Intent intent = getIntent();
        if (intent != null) {
            flag = intent.getIntExtra("type", 2);
            switch (flag) {
                case 2:
                    mBinding.unitBrandTop.setTitle(mContext.getString(R.string.brand));
                    mBinding.unitBrandTop.setRightText(mContext.getString(R.string.brand_add));
                    break;
                case 3:
                    mBinding.unitBrandTop.setTitle(mContext.getString(R.string.unit));
                    mBinding.unitBrandTop.setRightText(mContext.getString(R.string.unit_add));
                    break;
                default:
                    break;
            }
        }


        mBinding.unitBrandRefresh.setEnableLoadMore(false);
        mBinding.unitBrandRefresh.autoRefresh();
    }

    @Override
    protected void initData() {
        super.initData();
        unitBrandAdapter = new UnitBrandAdapter();
        mBinding.unitBrandRec.setLayoutManager(new TopLinearLayoutManager(mContext));
        mBinding.unitBrandRec.setAdapter(unitBrandAdapter);
        mBinding.unitBrandRec.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL,
                0, mContext.getResources().getColor(R.color.background), false));
    }

    @Override
    protected void setListener() {
        super.setListener();
        mBinding.unitBrandRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getData();
            }
        });
        mBinding.unitBrandTop.setRightViewOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addClass(0, "", true);
            }
        });
        unitBrandAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                selectPosition = position;
                getItemPop().setTitle(unitBrandAdapter.getItem(position).getName());
                getItemPop().showPopupWindow(mBinding.unitBrandTop, Gravity.BOTTOM);
            }
        });


    }


    boolean isFirst = true;

    private void getData() {
        RetrofitHttp.getRequest(HttpApi.class)
                .getOClassList(flag)
                .compose(RxThreadUtil.observableToMain())
                .compose(mContext.bindToLifecycle())
                .subscribe(new HttpObserver<JsonBean<List<OClassBean>>>(mContext) {
                    @Override
                    public void onSuccess(JsonBean<List<OClassBean>> bean) {
                        if (bean.code == 1) {
                            unitBrandAdapter.setList(bean.data);
                            if (isFirst) {
                                isFirst = false;
                                unitBrandAdapter.setEmptyView(Util.getEmptyView(mContext));
                            }
                        }
                    }

                    @Override
                    public void onFinsh() {
                        super.onFinsh();
                        mBinding.unitBrandRefresh.finishRefresh();
                    }
                });
    }


    private void delearClass(OClassBean classBean) {
        DialogUtils.showDialog(mContext, "是否删除\n" + classBean.getOClass_Name(), new MyDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (confirm) {
                    RetrofitHttp.getRequest(HttpApi.class)
                            .oClassDel(classBean.getOClass_ID(), flag)
                            .compose(RxThreadUtil.observableToMain())
                            .compose(mContext.bindToLifecycle())
                            .subscribe(new HttpObserver<JsonBean<Object>>(mContext) {
                                @Override
                                public void onSuccess(JsonBean<Object> bean) {
                                    if (bean.code == 1) {
                                        unitBrandAdapter.remove(classBean);
                                    }
                                }
                            });
                }
            }
        });
    }


    private EventConsume<JsonBean<Object>> eventConsume = (bean) -> {

        if (bean.code == 1) {
            getData();
        }

    };

    private void addClass(int id, String edit, boolean isAdd) {
        addTextPop = getAddClassPop();
        addTextPop.setAdd(isAdd);
        addTextPop.setEdit(edit);
        addTextPop.setID(id);
        addTextPop.setFlag(flag);
        switch (flag) {
            case 2:
                if (isAdd) {
                    addTextPop.setTitle("添加品牌名称");
                    addTextPop.setHint("请输入添加的品牌名称");
                } else {
                    addTextPop.setTitle("修改品牌名称");
                    addTextPop.setHint("请输入修改的品牌名称");
                }

                break;
            case 3:
                if (isAdd) {
                    addTextPop.setTitle("添加单位名称");
                    addTextPop.setHint("请输入添加的单位名称");
                } else {
                    addTextPop.setTitle("修改单位名称");
                    addTextPop.setHint("请输入修改的单位名称");
                }

                break;
            default:
                break;
        }
        addTextPop.setOnConfrimLsenter(eventConsume);
        addTextPop.showPopupWindow(mBinding.unitBrandTop, Gravity.BOTTOM);
    }


    private AddBrandUnitPop getAddClassPop() {
        if (addTextPop == null) {
            addTextPop = new AddBrandUnitPop(mContext);
            addTextPop.setTitle("添加分类");
            addTextPop.setHint("请输入商品分类名称");
            addTextPop.setMaxLength(10);
        }
        return addTextPop;
    }

    EventConsume<Integer> itemClickListener = new EventConsume<Integer>() {
        @Override
        public void accept(Integer integer) {
            switch (integer) {
                case 0:
                    addClass(unitBrandAdapter.getItem(selectPosition).getOClass_ID(), unitBrandAdapter.getItem(selectPosition).getOClass_Name(), false);
                    break;
                case 1:
                    delearClass(unitBrandAdapter.getItem(selectPosition));
                    break;
                default:
                    break;
            }
        }
    };

    private ItemPop getItemPop() {
        if (itemPop == null) {
            itemPop = new ItemPop(mContext);
            itemPop.setOnItemClickListener(itemClickListener);
        }
        return itemPop;
    }
}
