package com.yiande.jxjxc.presenter;

import com.mylibrary.api.utils.KeyUtil;
import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.utils.ToastUtil;
import com.retrofithttp.api.RetrofitHttp;
import com.retrofithttp.api.util.RxThreadUtil;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BaseActivity;
import com.yiande.jxjxc.base.BasePresenter;
import com.yiande.jxjxc.base.HttpObserver;
import com.yiande.jxjxc.bean.JsonBean;
import com.yiande.jxjxc.databinding.ActivityAmendPasswordBinding;
import com.yiande.jxjxc.myInterface.EventSendData;
import com.yiande.jxjxc.myInterface.HttpApi;
import com.yiande.jxjxc.utils.Util;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/9/21 17:50
 */
public class AmendPasswordPresenter extends BasePresenter<ActivityAmendPasswordBinding> {
    private EventSendData<String, String> eventSendData = (p1, p2) -> {
        if (StringUtil.isEmpty(p1)) {
            ToastUtil.showShort( R.string.hint_password);
            return;
        }
        if (StringUtil.isEmpty(p2)) {
            ToastUtil.showShort( R.string.intput_confrim_password);
            return;
        }
        if (!p1.equals(p2)) {
            ToastUtil.showShort( "密码和确认密码不一致");
            return;
        }

        RetrofitHttp.getRequest(HttpApi.class)
                .passwordModify(KeyUtil.getMD5(p1))
                .compose(RxThreadUtil.observableToMain())
                .compose(mContext.bindToLifecycle())
                .subscribe(new HttpObserver<JsonBean<Object>>(mContext) {
                    @Override
                    public void onSuccess(JsonBean<Object> bean) {
                        if (bean.code == 1) {

                        }

                    }

                    @Override
                    public void msgOnClick(boolean click, int type) {
                        super.msgOnClick(click, type);
                        Util.esc((BaseActivity) mContext);
                    }
                });
    };

    public AmendPasswordPresenter(RxAppCompatActivity mContext, ActivityAmendPasswordBinding binding) {
        super(mContext, binding);
        mBinding.setOnClick(eventSendData);

        mBinding.amendPasswordName.setText(com.yiande.jxjxc.App.adminName);
        mBinding.amendPasswordMob.setText(com.yiande.jxjxc.App.adminMob);
    }
}
