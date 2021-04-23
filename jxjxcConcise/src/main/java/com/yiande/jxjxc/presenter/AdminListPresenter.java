package com.yiande.jxjxc.presenter;

import android.app.Dialog;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.mylibrary.api.dialog.MyDialog;
import com.mylibrary.api.managelayout.TopLinearLayoutManager;
import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.utils.SystemUtil;
import com.retrofithttp.api.RetrofitHttp;
import com.retrofithttp.api.util.RxThreadUtil;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.activity.AdminAddActivity;
import com.yiande.jxjxc.activity.SelectActivity;
import com.yiande.jxjxc.adapter.AdminAdapter;
import com.yiande.jxjxc.base.BasePresenter;
import com.yiande.jxjxc.base.HttpObserver;
import com.yiande.jxjxc.bean.AdminListBean;
import com.yiande.jxjxc.bean.JsonBean;
import com.yiande.jxjxc.databinding.ActivityAdminListBinding;
import com.yiande.jxjxc.myInterface.EventConsume;
import com.yiande.jxjxc.myInterface.HttpApi;
import com.yiande.jxjxc.myInterface.TypeEnum;
import com.yiande.jxjxc.popwindow.ItemPop;
import com.yiande.jxjxc.utils.DialogUtils;
import com.yiande.jxjxc.utils.Util;

import java.util.List;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/9/14 17:36
 */
public class AdminListPresenter extends BasePresenter<ActivityAdminListBinding> {
    private String keywords;
    private int page = 1;
    private AdminAdapter adminAdapter;
    private ItemPop itemPop;
    private int selectPosition = -1;
    private int adminDefault = 0;
    private int adminIsOk = -1;//1启用0禁用,-1默认(全部)
    private int adminType = 0;//0默认1管理员2员工
    private EventConsume<Integer> onClik = (type) -> {
        switch (type) {
            case 0://默认
                adminIsOk = -1;
                adminType = 0;
                adminDefault = 0;
                break;
            case 1://启用
                if (adminIsOk == 1) {
                    adminIsOk = -1;
                    if (adminType == 0) {
                        adminDefault = 0;
                    }
                } else {
                    adminIsOk = 1;
                    adminDefault = -1;
                }
                break;
            case 2://禁用
                if (adminIsOk == 0) {
                    adminIsOk = -1;
                    if (adminType == 0) {
                        adminDefault = 0;
                    }
                } else {
                    adminIsOk = 0;
                    adminDefault = -1;
                }
                break;
            case 3://管理员
                if (adminType == 1) {
                    adminType = 0;
                    if (adminIsOk == -1) {
                        adminDefault = 0;
                    }
                } else {
                    adminType = 1;
                    adminDefault = -1;
                }
                break;
            case 4://员工
                if (adminType == 2) {
                    adminType = 0;
                    if (adminIsOk == -1) {
                        adminDefault = 0;
                    }
                } else {
                    adminType = 2;
                    adminDefault = -1;
                }
                break;
            default:
                break;
        }
        initView();
        onRefreshData();
    };

    public AdminListPresenter(RxAppCompatActivity mContext, ActivityAdminListBinding binding) {
        super(mContext, binding);
        Intent intent = getIntent();
        if (intent != null) {
            keywords = intent.getStringExtra("keywords");
            if (StringUtil.isNotEmpty(keywords)) {
                mBinding.adminListTop.setTitle(keywords);
            }
        }
        mBinding.setOnClik(onClik);
        initView();
        mBinding.adminListRefresh.autoRefresh();
    }

    private void initView() {
        mBinding.setAdminDefault(adminDefault);
        mBinding.setAdminIsOK(adminIsOk);
        mBinding.setAdminType(adminType);
    }

    @Override
    protected void initData() {
        super.initData();
        adminAdapter = new AdminAdapter(null);
        mBinding.adminListRec.setAdapter(adminAdapter);
        mBinding.adminListRec.setLayoutManager(new TopLinearLayoutManager(mContext));
    }

    @Override
    protected void setListener() {
        super.setListener();
        mBinding.adminListTop.setMySearchViewListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectActivity.start(mContext, 1, mBinding.adminListTop.getSelectHint());
            }
        });
        mBinding.adminListRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page += 1;
                getData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                onRefreshData();
            }
        });

        mBinding.adminListTop.setRightTextListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminAddActivity.start(mContext, -1, false);
            }
        });

        adminAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                AdminAddActivity.start(mContext, adminAdapter.getItem(position).getAdmin_ID(), true);
            }
        });

        adminAdapter.addChildClickViewIds(R.id.itmAdmin_Call, R.id.itmAdmin_More);
        adminAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                selectPosition = position;
                switch (view.getId()) {
                    case R.id.itmAdmin_More:
                        getItemPop().setTitle(adminAdapter.getItem(position).getAdmin_Name());
                        getItemPop().showPopupWindow(mBinding.adminListTop, Gravity.BOTTOM);
                        break;
                    case R.id.itmAdmin_Call:
                        SystemUtil.skipTel(mContext, adminAdapter.getItem(position).getAdmin_Mob());
                        break;
                    default:
                        break;
                }
            }
        });
    }

    EventConsume<Integer> itemClickListener = new EventConsume<Integer>() {
        @Override
        public void accept(Integer integer) {
            switch (integer) {
                case 0:
                    AdminAddActivity.start(mContext, adminAdapter.getItem(selectPosition).getAdmin_ID(), true);
                    break;
                case 1:
                    DialogUtils.showDialog(mContext, "是否删除  " + adminAdapter.getItem(selectPosition).getAdmin_Name(), new MyDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            if (confirm) {
                                adminDel(selectPosition, adminAdapter.getItem(selectPosition).getAdmin_ID());
                            }
                        }
                    });
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

    private void getData() {
        RetrofitHttp.getRequest(HttpApi.class)
                .getAdminList(page, keywords, adminIsOk, adminType)
                .compose(RxThreadUtil.observableToMain())
                .compose(mContext.bindToLifecycle())
                .subscribe(new HttpObserver<JsonBean<List<AdminListBean>>>(mContext) {
                    @Override
                    public void onSuccess(JsonBean<List<AdminListBean>> bean) {
                        Util.setRceclerData(bean, page, mBinding.adminListRefresh, mBinding.adminListRec, adminAdapter);
                    }

                    @Override
                    public void msgOnClick(boolean click, int isMsg) {
                        super.msgOnClick(click, isMsg);
                        mContext.finish();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        super.onError(e);
                    }
                });
    }

    private void adminDel(int posintion, int id) {
        RetrofitHttp.getRequest(HttpApi.class)
                .adminDel(id)
                .compose(RxThreadUtil.observableToMain())
                .compose(mContext.bindToLifecycle())
                .subscribe(new HttpObserver<JsonBean<Object>>(mContext) {
                    @Override
                    public void onSuccess(JsonBean<Object> bean) {
                        if (bean.code == 1) {
                            adminAdapter.removeAt(posintion);
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        super.onError(e);
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case TypeEnum.REFRESH:
                onRefreshData();
                break;
            default:
                break;
        }
    }

    private void onRefreshData() {
        page = 1;
        getData();
    }
}
