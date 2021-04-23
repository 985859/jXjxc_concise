package com.yiande.jxjxc.presenter;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.mylibrary.api.utils.SkipUtil;
import com.mylibrary.api.utils.SystemUtil;
import com.retrofithttp.api.RetrofitHttp;
import com.retrofithttp.api.util.RxThreadUtil;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.App;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.activity.ContactsActivity;
import com.yiande.jxjxc.activity.SetActivity;
import com.yiande.jxjxc.activity.UserListActivity;
import com.yiande.jxjxc.adapter.RoleAdapter;
import com.yiande.jxjxc.base.BasePresenter;
import com.yiande.jxjxc.base.HttpObserver;
import com.yiande.jxjxc.bean.HomeBean;
import com.yiande.jxjxc.bean.JsonBean;
import com.yiande.jxjxc.databinding.ActivityHomeBinding;
import com.yiande.jxjxc.myInterface.EventConsume;
import com.yiande.jxjxc.myInterface.HttpApi;
import com.yiande.jxjxc.myInterface.TypeEnum;
import com.yiande.jxjxc.utils.Util;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/11/25 9:26
 */
public class HomePresenter extends BasePresenter<ActivityHomeBinding> {

    RoleAdapter roleAdapter;


    public HomePresenter(RxAppCompatActivity mContext, ActivityHomeBinding binding) {
        super(mContext, binding);
        mBinding.setOnClick(onClik);
        mBinding.homeRefresh.autoRefresh();
    }

    @Override
    protected void initData() {
        super.initData();
        Util.getAppFucntion(mContext, null);
        roleAdapter = new RoleAdapter();
        mBinding.homeRoleRec.setLayoutManager(new GridLayoutManager(mContext, 2));
        mBinding.homeRoleRec.setAdapter(roleAdapter);
        mBinding.homeRefresh.setEnableLoadMore(false);

    }

    private EventConsume<Integer> onClik = (type) -> {
        switch (type) {
            case 0://客服
                SystemUtil.skipTel(mContext, mContext.getString(R.string.com_tel));
                break;
            case 1://设置
               SkipUtil.skipActivity(mContext, SetActivity.class);

                break;
            default:
                break;
        }
    };


    @Override
    protected void setListener() {
        super.setListener();
        mBinding.homeRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getData();
                Util.getAppFucntion(mContext, null);
            }
        });

        roleAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Util.skipRoleBean(mContext, roleAdapter.getItem(position).getRole_ID());
            }
        });
    }


    public void getData() {
        RetrofitHttp.getRequest(HttpApi.class)
                .getAdminIndex()
                .compose(RxThreadUtil.observableToMain())
                .compose(mContext.bindToLifecycle())
                .subscribe(new HttpObserver<JsonBean<HomeBean>>(mContext) {
                    @Override
                    public void onSuccess(JsonBean<HomeBean> bean) {
                        if (bean.code == 1) {
                            mBinding.setData(bean.data);
                            App.comName = bean.data.getComName();
                            roleAdapter.setList(bean.data.getRole());
                        }

                    }

                    @Override
                    public void onFinsh() {
                        super.onFinsh();
                        mBinding.homeRefresh.finishRefresh();
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case TypeEnum.REFRESH:
                getData();
                break;
            default:
                break;
        }
    }
}
