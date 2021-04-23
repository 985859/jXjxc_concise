package com.yiande.jxjxc.presenter;

import android.view.View;

import com.mylibrary.api.utils.KeyUtil;
import com.mylibrary.api.utils.SharedUtil;
import com.mylibrary.api.utils.SkipUtil;
import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.utils.SystemUtil;
import com.mylibrary.api.utils.ToastUtil;
import com.retrofithttp.api.RetrofitHttp;
import com.retrofithttp.api.util.RxThreadUtil;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.activity.HomeActivity;
import com.yiande.jxjxc.activity.SetIPActivity;
import com.yiande.jxjxc.base.BasePresenter;
import com.yiande.jxjxc.base.HttpObserver;
import com.yiande.jxjxc.bean.JsonBean;
import com.yiande.jxjxc.bean.LoginBean;
import com.yiande.jxjxc.databinding.ActivityLoginBinding;
import com.yiande.jxjxc.myInterface.HttpApi;

import io.reactivex.rxjava3.annotations.NonNull;

;


/**
 * @Description: 用户登录操作
 * @Author: hukui
 * @Date: 2020/7/15 11:16
 */
public class LoginPresenter extends BasePresenter<ActivityLoginBinding> {

    public LoginPresenter(RxAppCompatActivity mContext, ActivityLoginBinding binding) {
        super(mContext, binding);
        mBinding.setUserLogin(userLogin);
        mBinding.setShowView(true);
    }

    //账户登录功能
    LoginInterface userLogin = (userName, password) -> {
        if (StringUtil.isEmpty(userName)) {
              ToastUtil.showShort( mContext.getString(R.string.hint_user));
            return;
        }
        if (StringUtil.isEmpty(password)) {
              ToastUtil.showShort( mContext.getString(R.string.hint_password));
            return;
        }
        mBinding.loginBT.setEnabled(false);
        RetrofitHttp.getRequest(HttpApi.class)
                .userLogin(userName, KeyUtil.getMD5(password))
                .compose(mContext.bindToLifecycle())
                .compose(RxThreadUtil.observableToMain())
                .subscribe(new HttpObserver<JsonBean<LoginBean>>(mContext, true, false) {
                    @Override
                    public void onSuccess(JsonBean<LoginBean> bean) {
                        if (bean.code == 1) {
                            SharedUtil.putBP(com.yiande.jxjxc.App.ISENTER, true);
                            SharedUtil.putObjToStr(com.yiande.jxjxc.App.TOKEN, bean.data);
                            com.yiande.jxjxc.App.initRetrofitHttp();
                            SkipUtil.skipActivity(mContext, HomeActivity.class);
                            mContext.finish();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        super.onError(e);


                    }

                    @Override
                    public void onFinsh() {
                        super.onFinsh();
                        mBinding.loginBT.setEnabled(true);
                    }
                });
    };

    @Override
    protected void setListener() {
        super.setListener();
        mBinding.loginBttomItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemUtil.skipTel(mContext, mContext.getString(R.string.com_tel));
            }
        });
        mBinding.loginSetIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkipUtil.skipActivity(mContext, SetIPActivity.class);
            }
        });
    }

    public interface LoginInterface {
        void loginCode(String userName, String code);
    }

}
