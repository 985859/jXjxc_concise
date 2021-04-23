package com.yiande.jxjxc.presenter;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.mylibrary.api.utils.LogUtil;
import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.utils.ToastUtil;
import com.retrofithttp.api.RetrofitHttp;
import com.retrofithttp.api.util.RxThreadUtil;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.adapter.TypeAdapter;
import com.yiande.jxjxc.base.BasePresenter;
import com.yiande.jxjxc.base.HttpObserver;
import com.yiande.jxjxc.bean.AdminBean;
import com.yiande.jxjxc.bean.JsonBean;
import com.yiande.jxjxc.bean.RoleBean;
import com.yiande.jxjxc.databinding.ActivityAdminAddBinding;
import com.yiande.jxjxc.myInterface.EventConsume;
import com.yiande.jxjxc.myInterface.HttpApi;
import com.yiande.jxjxc.myInterface.TypeEnum;

import java.util.List;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/9/14 19:35
 */
public class AdminAddPresenter extends BasePresenter<ActivityAdminAddBinding> {
    private TypeAdapter roleAdapter;
    private boolean isAmend;
    private int amendID;
    private AdminBean adminBean;

    public AdminAddPresenter(RxAppCompatActivity mContext, ActivityAdminAddBinding binding) {
        super(mContext, binding);
        Intent intent = getIntent();
        adminBean = new AdminBean();

        if (intent != null) {
            isAmend = intent.getBooleanExtra("isAmend", false);
            if (isAmend) {
                mBinding.adminAddTop.setTitle(mContext.getString(R.string.admin_amend));
                mBinding.adminAddPassword.setHint("请输入密码，若不修改为空");
                amendID = intent.getIntExtra("adminID", -1);
                getAdmin();
            } else {
                adminBean.setAdmin_IsOK(1);
                adminBean.setAdmin_Type(2);
                setAdmin(adminBean);
            }
        }

    }

    @Override
    protected void initData() {
        super.initData();
        roleAdapter = new TypeAdapter(null);
        mBinding.adminAddRec.setAdapter(roleAdapter);
        mBinding.adminAddRec.setLayoutManager(new GridLayoutManager(mContext, 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        getRoles();


    }

    @Override
    protected void setListener() {
        super.setListener();

        roleAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                roleAdapter.setSelectIndex(position);
                LogUtil.e("selectID   " + roleAdapter.getselectID());
                adminBean.setAdmin_Role(roleAdapter.getselectID());
            }
        });
        mBinding.adminAddBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAdmin();
            }
        });
        mBinding.adminAddState.setOnselectListener(new EventConsume<Integer>() {
            @Override
            public void accept(Integer integer) {
                switch (integer) {
                    case 0:
                        adminBean.setAdmin_IsOK(1);
                        break;
                    case 1:
                        adminBean.setAdmin_IsOK(0);
                        break;
                    default:
                        break;
                }


            }
        });

        mBinding.adminAddType.setOnselectListener(new EventConsume<Integer>() {
            @Override
            public void accept(Integer integer) {
                switch (integer) {
                    case 0:
                        adminBean.setAdmin_Type(2);
                        break;
                    case 1:
                        adminBean.setAdmin_Type(1);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void getRoles() {
        RetrofitHttp.getRequest(HttpApi.class)
                .getRoles()
                .compose(RxThreadUtil.observableToMain())
                .compose(mContext.bindToLifecycle())
                .subscribe(new HttpObserver<JsonBean<List<RoleBean>>>(mContext) {
                    @Override
                    public void onSuccess(JsonBean<List<RoleBean>> bean) {
                        if (bean.code == 1) {
                            roleAdapter.setList(bean.data);
                        }
                    }
                });
    }

    private void addAdmin() {
        if (adminBean != null) {
            if (StringUtil.isEmpty(adminBean.getAdmin_Name())) {
                ToastUtil.showShort(mContext.getString(R.string.admin_name_hint));
                return;
            }
            if (StringUtil.isEmpty(adminBean.getAdmin_Mob())) {
                ToastUtil.showShort(mContext.getString(R.string.admin_tel_hint));
                return;
            }
            if (!isAmend && adminBean.getAdmin_Type() == 1) {
                if (StringUtil.isEmpty(adminBean.getAdmin_Password())) {
                    ToastUtil.showShort(mContext.getString(R.string.admin_password_hint));
                    return;
                }

                if (StringUtil.isEmpty(adminBean.getAdmin_Password2())) {
                    ToastUtil.showShort(mContext.getString(R.string.admin_password2_hint));
                    return;
                }

                if (!adminBean.getAdmin_Password().equals(adminBean.getAdmin_Password2())) {
                    ToastUtil.showShort("密码和确认密码不相同");
                    return;
                }
            }
            mBinding.adminAddBT.setEnabled(false);
            HttpObserver observer = new HttpObserver<JsonBean<Object>>(mContext) {
                @Override
                public void onSuccess(JsonBean<Object> bean) {
                    if (bean.code == 1) {
                        mContext.setResult(TypeEnum.REFRESH);
                    }
                }

                @Override
                public void msgOnClick(boolean click, int type) {
                    super.msgOnClick(click, type);
                    mContext.finish();
                }

                @Override
                public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                    super.onError(e);
                }

                @Override
                public void onFinsh() {
                    super.onFinsh();
                    mBinding.adminAddBT.setEnabled(true);
                }
            };


            if (StringUtil.isNotEmpty(adminBean.getAdmin_Password())) {
                adminBean.setAdmin_Password(StringUtil.getMD5(adminBean.getAdmin_Password()));
            } else {
                adminBean.setAdmin_Password("");
            }

            if (isAmend) {
                RetrofitHttp.getRequest(HttpApi.class)
                        .adminModify(adminBean)
                        .compose(RxThreadUtil.observableToMain())
                        .compose(mContext.bindToLifecycle())
                        .subscribe(observer);
            } else {
                RetrofitHttp.getRequest(HttpApi.class)
                        .adminAdd(adminBean)
                        .compose(RxThreadUtil.observableToMain())
                        .compose(mContext.bindToLifecycle())
                        .subscribe(observer);
            }

        }

    }


    private void getAdmin() {
        RetrofitHttp.getRequest(HttpApi.class)
                .getAdmin(amendID)
                .compose(RxThreadUtil.observableToMain())
                .compose(mContext.bindToLifecycle())
                .subscribe(new HttpObserver<JsonBean<AdminBean>>(mContext) {
                    @Override
                    public void onSuccess(JsonBean<AdminBean> bean) {
                        if (bean.code == 1) {
                            setAdmin(bean.data);
                        }
                    }

                    @Override
                    public void msgOnClick(boolean click, int type) {
                        super.msgOnClick(click, type);
                        mContext.finish();
                    }


                });
    }

    private void setAdmin(AdminBean adminBean) {
        if (adminBean != null) {
            this.adminBean = adminBean;
            mBinding.setData(adminBean);
            roleAdapter.setselectIds(adminBean.getAdmin_Role());
        }
    }

}
