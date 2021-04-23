package com.yiande.jxjxc.popwindow;

import android.text.InputFilter;
import android.view.View;

import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.utils.SystemUtil;
import com.mylibrary.api.utils.ToastUtil;
import com.retrofithttp.api.RetrofitHttp;
import com.retrofithttp.api.util.RxThreadUtil;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BasePopupWindow;
import com.yiande.jxjxc.base.HttpObserver;
import com.yiande.jxjxc.bean.JsonBean;
import com.yiande.jxjxc.bean.OClassBody;
import com.yiande.jxjxc.databinding.PopAddTextBinding;
import com.yiande.jxjxc.myInterface.EventConsume;
import com.yiande.jxjxc.myInterface.HttpApi;


public class AddBrandUnitPop extends BasePopupWindow<PopAddTextBinding> {
    private EventConsume<JsonBean<Object>> onConfrimLsenter;
    private OClassBody oClassBody;
    private boolean isAdd = true;
    public void setAdd(boolean add) {
        isAdd = add;
    }

    private EventConsume<Integer> onClick = (type) -> {
        switch (type) {
            case 0:
                dismiss();
                break;
            case 1:
                popTextComfrim();
                break;
            default:
                break;
        }
    };


    private void popTextComfrim() {
        if (StringUtil.isEmpty(mBinding.popTextName.getText().toString().trim())) {
            ToastUtil.showShort( mBinding.popTextName.getHint());
            return;
        }
        oClassBody.setOClass_Name(mBinding.popTextName.getText().toString().trim());
        postData();
    }


    public AddBrandUnitPop(RxAppCompatActivity context) {
        super(context);
        mBinding.setOnClick(onClick);
        oClassBody = new OClassBody();
        oClassBody.setOClass_IsOK(1);
    }

    public void setFlag(int flag) {
        oClassBody.setOClass_Flag(flag);
    }

    public void setID(int id) {
        oClassBody.setOClass_ID(id);
    }

    @Override
    public int getContentViewID() {
        return R.layout.pop_add_text;
    }

    @Override
    public void init(View view) {
        setHeight((int) (SystemUtil.getScreenHeight(activity)/2.5));

    }

    @Override
    public void setListener() {
        super.setListener();

    }

    @Override
    protected void onDismissListener() {
        super.onDismissListener();
        mBinding.popTextName.setFocusable(false);
        mBinding.popTextName.setFocusableInTouchMode(false);
        SystemUtil.toggleSoftInput(mBinding.popTextName);
    }

    @Override
    public void onShowListener() {
        super.onShowListener();
        mBinding.popTextName.setFocusable(true);
        mBinding.popTextName.setFocusableInTouchMode(true);
        SystemUtil.showKeyboard(mBinding.popTextName);
    }




    public void setTitle(String title) {
        if (StringUtil.isNotEmpty(title))
            mBinding.popTextTitle.setText(title);
    }

    public void setHint(String hint) {
        if (StringUtil.isNotEmpty(hint)) {
            mBinding.popTextName.setHint(hint);
        }
    }

    public void setMaxLength(int max) {
        InputFilter[] filters = {new InputFilter.LengthFilter(max)};
        mBinding.popTextName.setFilters(filters);
    }

    public void setEdit(String deit) {
        if (deit != null) {
            mBinding.popTextName.setText(deit);
            mBinding.popTextName.setSelection(deit.length());
        }

    }

    public void setOnConfrimLsenter(EventConsume<JsonBean<Object>> onConfrimLsenter) {
        this.onConfrimLsenter = onConfrimLsenter;
    }

    public void postData() {
        HttpObserver observer = new HttpObserver<JsonBean<Object>>(activity) {
            @Override
            public void onSuccess(JsonBean<Object> bean) {
                if (bean.code == 1) {
                    dismiss();
                }
                if (onConfrimLsenter != null) {
                    onConfrimLsenter.accept(bean);
                }
            }
        };
        if (isAdd) {
            RetrofitHttp.getRequest(HttpApi.class)
                    .oClassAdd(oClassBody)
                    .compose(RxThreadUtil.observableToMain())
                    .compose(activity.bindToLifecycle())
                    .subscribe(observer);
        } else {
            RetrofitHttp.getRequest(HttpApi.class)
                    .oClassModify(oClassBody)
                    .compose(RxThreadUtil.observableToMain())
                    .compose(activity.bindToLifecycle())
                    .subscribe(observer);
        }
    }

}
