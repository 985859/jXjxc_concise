package com.yiande.jxjxc.presenter;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mylibrary.api.managelayout.DividerItemDecoration;
import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.utils.ToastUtil;
import com.retrofithttp.api.RetrofitHttp;
import com.retrofithttp.api.util.RxThreadUtil;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.adapter.UserAddressAdapter;
import com.yiande.jxjxc.base.BasePresenter;
import com.yiande.jxjxc.base.HttpObserver;
import com.yiande.jxjxc.bean.JsonBean;
import com.yiande.jxjxc.bean.OClassBean;
import com.yiande.jxjxc.bean.OClassBody;
import com.yiande.jxjxc.bean.UserAddressBean;
import com.yiande.jxjxc.bean.UserBean;
import com.yiande.jxjxc.bean.UserInfoBean;
import com.yiande.jxjxc.databinding.ActivityUserAddBinding;
import com.yiande.jxjxc.myInterface.EventConsume;
import com.yiande.jxjxc.myInterface.EventSendData;
import com.yiande.jxjxc.myInterface.HttpApi;
import com.yiande.jxjxc.myInterface.TypeEnum;
import com.yiande.jxjxc.popwindow.AddClassifyPop;
import com.yiande.jxjxc.popwindow.WheelSelectPop;
import com.yiande.jxjxc.utils.Util;

import java.util.List;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/9/16 9:32
 */
public class UserAddPresenter extends BasePresenter<ActivityUserAddBinding> {
    private int userType;//1客户 2 供应商
    private int userID;
    private boolean isAmend;
    private int classFlag = 5;  //客户分类(1级) 6供应商分类(1级)

    private WheelSelectPop<OClassBean> classSelectPop;
    private AddClassifyPop addClassifyPop;

    private UserBean userBean;
    private UserAddressAdapter addressAdapter;
    private UserAddressAdapter logisticsAdapter;

    public UserAddPresenter(RxAppCompatActivity mContext, ActivityUserAddBinding binding) {
        super(mContext, binding);
        Intent intent = getIntent();
        userBean = new UserBean();
        userBean.setUser_IsOK(1);//默认启用
        if (intent != null) {
            userType = intent.getIntExtra("userType", 1);
            userID = intent.getIntExtra("userID", 0);
            isAmend = intent.getBooleanExtra("isAmend", false);
            switch (userType) {
                case 1:
                    classFlag = 5;
                    if (isAmend) {
                        mBinding.userAddTop.setTitle(mContext.getString(R.string.amend_user));
                    } else {
                        mBinding.userAddTop.setTitle(mContext.getString(R.string.user_add));
                    }
                    break;
                case 2:
                    classFlag = 6;
                    if (isAmend) {
                        mBinding.userAddTop.setTitle(mContext.getString(R.string.amend_user2));
                    } else {
                        mBinding.userAddTop.setTitle(mContext.getString(R.string.user_add2));
                    }
                    break;
            }
            getClassData();
            if (isAmend) {
                getUserInfo();
            } else {
                userBean.setUser_Type(userType);
                setUserBean(userBean);
            }
        }
    }

    @Override
    protected void initData() {
        super.initData();
        addressAdapter = new UserAddressAdapter();
        addressAdapter.setType(0);
        mBinding.userAddAddressRec.setLayoutManager(new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mBinding.userAddAddressRec.setAdapter(addressAdapter);
        mBinding.userAddAddressRec.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL,
                0, ContextCompat.getColor(mContext, R.color.background)));
        addressAdapter.addData(new UserAddressBean());

        logisticsAdapter = new UserAddressAdapter();
        logisticsAdapter.setType(1);
        mBinding.userAddLogisticsRec.setLayoutManager(new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mBinding.userAddLogisticsRec.setAdapter(logisticsAdapter);
        mBinding.userAddLogisticsRec.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL,
                0, ContextCompat.getColor(mContext, R.color.background)));
        logisticsAdapter.addData(new UserAddressBean());
    }

    @Override
    protected void setListener() {
        super.setListener();
        mBinding.userAddBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });
        mBinding.userAddState.setOnselectListener(new EventConsume<Integer>() {
            @Override
            public void accept(Integer integer) {
                if (integer == 0) {
                    setState(1);
                } else {
                    setState(0);
                }
            }
        });

        mBinding.userAddClass.setAddOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAddClassifyPop().showPopupWindow(mBinding.userAddTop, Gravity.BOTTOM);
            }
        });
        mBinding.userAddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getClassSelectPop().showPopupWindow(mBinding.userAddTop, Gravity.BOTTOM);
            }
        });

    }


    public void setState(int state) {
        userBean.setUser_IsOK(state);
    }

    private void addUser() {

        if (userBean != null) {
            if (StringUtil.isEmpty(userBean.getUser_ComName())) {
                ToastUtil.showShort(mBinding.userAddCom.getHint());
                return;
            }
            if (StringUtil.isEmpty(userBean.getUser_Mob())) {
                ToastUtil.showShort(mBinding.userAddMob.getHint());
                return;
            }
            if (userBean != null) {
                userBean.setUser_Address(addressAdapter.getData());
                userBean.setUser_Logistics(logisticsAdapter.getData());
            }
            mBinding.userAddBT.setEnabled(false);
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
                    mBinding.userAddBT.setEnabled(true);
                }
            };
            if (isAmend) {
                RetrofitHttp.getRequest(HttpApi.class)
                        .userModify(userBean)
                        .compose(RxThreadUtil.observableToMain())
                        .compose(mContext.bindToLifecycle())
                        .subscribe(observer);
            } else {
                RetrofitHttp.getRequest(HttpApi.class)
                        .userAdd(userBean)
                        .compose(RxThreadUtil.observableToMain())
                        .compose(mContext.bindToLifecycle())
                        .subscribe(observer);
            }
        }
    }

    private void getUserInfo() {
        RetrofitHttp.getRequest(HttpApi.class)
                .getUser(userID, userType)
                .compose(RxThreadUtil.observableToMain())
                .compose(mContext.bindToLifecycle())
                .subscribe(new HttpObserver<JsonBean<UserInfoBean>>(mContext) {
                    @Override
                    public void onSuccess(JsonBean<UserInfoBean> bean) {

                        if (bean.code == 1 && bean.data != null) {
                            UserBean userBean = bean.data.getUserInfo();
                            if (userBean != null) {
                                userBean.setUser_Logistics(bean.data.getLogistics());
                                userBean.setUser_Address(bean.data.getUserAddress());
                            }
                            setUserBean(userBean);
                        }

                    }
                });
    }

    private void setUserBean(UserBean userBean) {
        if (userBean != null) {
            this.userBean = userBean;
            mBinding.setData(userBean);
            mBinding.userAddClass.setText(userBean.getUser_Class1Name());
            addressAdapter.setUserType(userBean.getUser_Type());
            logisticsAdapter.setUserType(userBean.getUser_Type());
            if (classSelectPop != null) {
                classSelectPop.setID(userBean.getUser_Class1());
            }
            if (Util.isNotListEmpty(userBean.getUser_Address())) {
                addressAdapter.setList(userBean.getUser_Address());
            }
            if (Util.isNotListEmpty(userBean.getUser_Logistics())) {
                logisticsAdapter.setList(userBean.getUser_Logistics());
            }
        }
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
                            getClassSelectPop().setData(bean.data);
                            getAddClassifyPop().setData(bean.data);
                        }
                    }
                });
    }

    EventSendData<OClassBean, Boolean> oClassSendData = (oClassBean, aBoolean) -> {
        if (oClassBean == null) {
            userBean.setUser_Class1(-1);
            mBinding.userAddClass.setText("");
        } else {
            userBean.setUser_Class1(oClassBean.getOClass_ID());
            mBinding.userAddClass.setText(oClassBean.getOClass_Name());
        }

    };

    public WheelSelectPop<OClassBean> getClassSelectPop() {
        if (classSelectPop == null) {
            classSelectPop = new WheelSelectPop<>(mContext);
            classSelectPop.setEventConsume(oClassSendData);
            if (userType == 1) {
                classSelectPop.setTitle(mContext.getString(R.string.client_Class));

            } else {
                classSelectPop.setTitle(mContext.getString(R.string.supplier_Class));
            }
        }
        return classSelectPop;
    }

    EventSendData<OClassBody, Boolean> addClassSendData = (oClassBody, aBoolean) -> {
        RetrofitHttp.getRequest(HttpApi.class)
                .oClassAdd(oClassBody)
                .compose(RxThreadUtil.observableToMain())
                .compose(mContext.bindToLifecycle())
                .subscribe(new HttpObserver<JsonBean<Object>>(mContext) {
                    @Override
                    public void onSuccess(JsonBean<Object> bean) {
                        if (bean.code == 1) {
                            getClassData();
                            getAddClassifyPop().dismiss();
                        }
                    }
                });
    };

    public AddClassifyPop getAddClassifyPop() {
        if (addClassifyPop == null) {
            addClassifyPop = new AddClassifyPop(mContext);
            addClassifyPop.setOnAddSucceedListener(addClassSendData);
            addClassifyPop.setAdd(true);
            if (userType == 1) {
                addClassifyPop.setTitle(mContext.getString(R.string.client_Class_Add));
                addClassifyPop.setHint("请输入客户分类");
            } else {
                addClassifyPop.setTitle(mContext.getString(R.string.supplier_Class_Add));
                addClassifyPop.setHint("请输入供应商分类");
            }

        }
        addClassifyPop.setOClassFlag(classFlag);
        return addClassifyPop;
    }


}
