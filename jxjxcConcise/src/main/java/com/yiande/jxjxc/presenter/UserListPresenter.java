package com.yiande.jxjxc.presenter;

import android.app.Dialog;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.mylibrary.api.dialog.MyDialog;
import com.mylibrary.api.managelayout.TopLinearLayoutManager;
import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.utils.SystemUtil;
import com.mylibrary.api.widget.MySpinnerView;
import com.retrofithttp.api.RetrofitHttp;
import com.retrofithttp.api.util.RxThreadUtil;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.activity.SelectActivity;
import com.yiande.jxjxc.activity.UserAddActivity;
import com.yiande.jxjxc.adapter.SpinnerAdapter;
import com.yiande.jxjxc.adapter.UserAdapter;
import com.yiande.jxjxc.base.BasePopupWindow;
import com.yiande.jxjxc.base.BasePresenter;
import com.yiande.jxjxc.base.HttpObserver;
import com.yiande.jxjxc.bean.JsonBean;
import com.yiande.jxjxc.bean.OClassBean;
import com.yiande.jxjxc.bean.UserBean;
import com.yiande.jxjxc.databinding.ActivityUserListBinding;
import com.yiande.jxjxc.myInterface.EventConsume;
import com.yiande.jxjxc.myInterface.HttpApi;
import com.yiande.jxjxc.myInterface.TypeEnum;
import com.yiande.jxjxc.popwindow.ItemPop;
import com.yiande.jxjxc.popwindow.TopMenuPop;
import com.yiande.jxjxc.utils.DialogUtils;
import com.yiande.jxjxc.utils.Util;

import java.util.List;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/9/15 20:10
 */
public class UserListPresenter extends BasePresenter<ActivityUserListBinding> {
    private String keywords;
    private int page = 1;
    public int user_Type = 1;//1客户 2供应商
    private int user_Class1;
    private int user_IsOK = -1;// 默认-1,0禁用,1启
    private int user_Default = 0;// 默认-1,0禁用,1启
    private UserAdapter userAdapter;
    private int classFlag = 5;  //客户分类(1级) 6供应商分类(1级)
    private SpinnerAdapter<OClassBean> oClassAdapter;
    private ItemPop itemPop;
    private TopMenuPop topMenuPop;
    private int selectPosition = -1;
    private EventConsume<Integer> onClik = (type) -> {
        switch (type) {
            case 0://默认
                user_IsOK = -1;
                user_Default = 0;
                break;
            case 1://启用
                if (user_IsOK == 1) {
                    user_IsOK = -1;
                    user_Default = 0;
                } else {
                    user_IsOK = 1;
                    user_Default = -1;
                }
                break;
            case 2://禁用
                if (user_IsOK == 0) {
                    user_IsOK = -1;
                    user_Default = 0;
                } else {
                    user_IsOK = 0;
                    user_Default = -1;
                }
                break;
            default:
                break;
        }
        initView();
        onRefreshData();
    };

    public UserListPresenter(RxAppCompatActivity mContext, ActivityUserListBinding binding) {
        super(mContext, binding);
        Intent intent = getIntent();
        if (intent != null) {
            keywords = intent.getStringExtra("keywords");
            user_Type = intent.getIntExtra("type", 1);
            mBinding.setUserType(user_Type);
            userAdapter.setUser_Type(user_Type);
            if (StringUtil.isNotEmpty(keywords)) {
                mBinding.userListTop.setTitle(keywords);
                mBinding.userListTop.setRightText("");
            }
            if (user_Type == 1) {
                classFlag = 5;
            } else {
                classFlag = 6;
            }
        }
        topMenuPop.setType(user_Type);
        initView();
        getClassData();
        mBinding.userListRefresh.autoRefresh();
        mBinding.setOnClik(onClik);
    }

    @Override
    protected void initData() {
        super.initData();
        userAdapter = new UserAdapter();
        mBinding.userListRec.setAdapter(userAdapter);
        mBinding.userListRec.setLayoutManager(new TopLinearLayoutManager(mContext));

        oClassAdapter = new SpinnerAdapter<OClassBean>(null);
        mBinding.userListClass.setAdapter(oClassAdapter);
        topMenuPop = new TopMenuPop(mContext);
    }

    private void initView() {
        mBinding.setUserDefault(user_Default);
        mBinding.setUserIsOK(user_IsOK);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mBinding.userListTop.setMySearchViewListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectActivity.start(mContext, 2, user_Type, mBinding.userListTop.getSelectHint());
            }
        });
        mBinding.userListTop.setRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (topMenuPop != null) {
                    topMenuPop.showAsDropDown(mBinding.userListTop);
                }
            }
        });
        topMenuPop.setOnShowCallBack(new BasePopupWindow.OnShowCallBack() {
            @Override
            public void onShowCallBack(boolean show) {
                if (show) {
                    mBinding.userListTop.setBackgroundResource(R.color.blue);

                } else {
                    mBinding.userListTop.setBackgroundResource(R.drawable.top_background);
                }
            }
        });
        mBinding.userListRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page += 1;
                getData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getData();
            }
        });

        mBinding.userListClass.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                oClassAdapter.setSelectIndex(position);
                user_Class1 = oClassAdapter.getItem(position).getId();
                onRefreshData();
            }
        });
        mBinding.userListClass.setCallBack(new MySpinnerView.OnShowCallBack() {
            @Override
            public void callBack(boolean show) {
                if (show) {
                    mBinding.userListTopLayout.setRadius(0, 0, 0, 0);
                } else {
                    mBinding.userListTopLayout.setRadius(0, 0, 0, mContext.getResources().getDimension(R.dimen.radius2));
                }
            }
        });

        userAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                UserAddActivity.start(mContext, userAdapter.getItem(position).getUser_ID(), user_Type, true);
            }
        });
        userAdapter.addChildClickViewIds(R.id.itmUser_Call, R.id.itmUser_More);
        userAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                selectPosition = position;
                switch (view.getId()) {
                    case R.id.itmUser_Call:
                        SystemUtil.skipTel(mContext, userAdapter.getItem(position).getUser_Mob());
                        break;
                    case R.id.itmUser_More:
                        getItemPop().setTitle(userAdapter.getItem(position).getUser_ComName());
                        getItemPop().showPopupWindow(mBinding.userListTop, Gravity.BOTTOM);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void getData() {
        RetrofitHttp.getRequest(HttpApi.class)
                .getUserList(page, user_Type, user_Class1, user_IsOK, keywords)
                .compose(RxThreadUtil.observableToMain())
                .compose(mContext.bindToLifecycle())
                .subscribe(new HttpObserver<JsonBean<List<UserBean>>>(mContext) {
                    @Override
                    public void onSuccess(JsonBean<List<UserBean>> bean) {
                        Util.setRceclerData(bean, page, mBinding.userListRefresh, mBinding.userListRec, userAdapter);
                    }

                    @Override
                    public void msgOnClick(boolean click, int isMsg) {
                        super.msgOnClick(click, isMsg);
                        switch (isMsg) {
                            case 21:
                                mContext.finish();
                                break;
                            default:
                                break;
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        super.onError(e);
                    }
                });
    }

    private void onRefreshData() {
        page = 1;
        getData();
    }

    private void getClassData() {
        RetrofitHttp.getRequest(HttpApi.class)
                .getOClassList(classFlag)
                .compose(RxThreadUtil.observableToMain())
                .compose(mContext.bindToLifecycle())
                .subscribe(new HttpObserver<JsonBean<List<OClassBean>>>(mContext, false) {
                    @Override
                    public void onSuccess(JsonBean<List<OClassBean>> bean) {
                        if (bean.code == 1) {
                            if (bean.data != null) {
                                OClassBean classBean = new OClassBean();
                                classBean.setOClass_ID(-1);
                                classBean.setOClass_Name("全部");
                                bean.data.add(0, classBean);
                            }
                            oClassAdapter.setList(bean.data);
                        }
                    }
                });
    }

    private void userDel(int posintion, int id) {
        RetrofitHttp.getRequest(HttpApi.class)
                .userDel(user_Type, id)
                .compose(RxThreadUtil.observableToMain())
                .compose(mContext.bindToLifecycle())
                .subscribe(new HttpObserver<JsonBean<Object>>(mContext) {
                    @Override
                    public void onSuccess(JsonBean<Object> bean) {
                        if (bean.code == 1) {
                            userAdapter.removeAt(posintion);
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        super.onError(e);
                    }
                });
    }

    EventConsume<Integer> itemClickListener = new EventConsume<Integer>() {
        @Override
        public void accept(Integer integer) {
            switch (integer) {
                case 0:
                    UserAddActivity.start(mContext, userAdapter.getItem(selectPosition).getUser_ID(), user_Type, true);
                    break;
                case 1:
                    DialogUtils.showDialog(mContext, "是否删除  " + userAdapter.getItem(selectPosition).getUser_ComName(), new MyDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            if (confirm) {
                                userDel(selectPosition, userAdapter.getItem(selectPosition).getUser_ID());
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case TypeEnum.REFRESH:
            case TypeEnum.INIT:
                getData();
                break;
            default:
                break;
        }
    }

}
