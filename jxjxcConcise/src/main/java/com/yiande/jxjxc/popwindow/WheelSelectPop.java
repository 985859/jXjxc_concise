package com.yiande.jxjxc.popwindow;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.utils.SystemUtil;
import com.retrofithttp.api.RetrofitHttp;
import com.retrofithttp.api.util.RxThreadUtil;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BasePopupWindow;
import com.yiande.jxjxc.base.HttpObserver;
import com.yiande.jxjxc.bean.AdminListBean;
import com.yiande.jxjxc.bean.JsonBean;
import com.yiande.jxjxc.bean.UserBean;

import com.yiande.jxjxc.databinding.PopProjectSelectBinding;
import com.yiande.jxjxc.myInterface.EventConsume;
import com.yiande.jxjxc.myInterface.EventSendData;
import com.yiande.jxjxc.myInterface.HttpApi;
import com.yiande.jxjxc.myInterface.IPickerData;
import com.yiande.jxjxc.utils.Util;

import java.util.List;


public class WheelSelectPop<T extends IPickerData> extends BasePopupWindow<PopProjectSelectBinding> {
    private int ID = -1;
    private int selectIndex = 0;
    private int index = 0;
    private ArrayWheelAdapter<T> wheelAdapter;
    private EventSendData<T, Boolean> eventConsume;
    private List<T> data;
    private TextView showTextView;

    public void setShowTextView(TextView showTextView) {
        this.showTextView = showTextView;
    }

    public void setEventConsume(EventSendData<T, Boolean> eventConsume) {
        this.eventConsume = eventConsume;
    }

    public WheelSelectPop(RxAppCompatActivity context) {
        super(context);
        mBinding.setOnClick(onClik);
    }


    @Override
    public int getContentViewID() {
        return R.layout.pop_project_select;
    }

    @Override
    public void init(View view) {
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mBinding.projectselectWheel.setCyclic(false);
    }

    private EventConsume<Integer> onClik = (type) -> {
        switch (type) {
            case 1://重置
                if (eventConsume != null) eventConsume.transform(null, true);
                showText("");
                break;
            case 2://确定
                if (wheelAdapter != null && index < wheelAdapter.getItemsCount()) {
                    selectIndex = index;
                    T infoBean = (T) wheelAdapter.getItem(index);
                    showText(infoBean.getPickerViewText());
                    if (eventConsume != null) eventConsume.transform(infoBean, false);
                }
                break;
        }
        dismiss();
    };

    @Override
    public void setListener() {
        super.setListener();
        mBinding.projectselectWheel.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                WheelSelectPop.this.index = index;
            }
        });
    }

    private void showText(String text) {
        if (showTextView != null) showTextView.setText(StringUtil.isEmpty(text) ? "" : text);
    }

    public void setTitle(@NonNull String title) {
        mBinding.setTitle(title);
    }


    public void setID(int ID) {
        this.ID = ID;
        setselectID();
    }

    public void setData(List<T> data) {
        if (Util.isNotListEmpty(data)) {
            this.data = data;
            wheelAdapter = new ArrayWheelAdapter(data);
            mBinding.projectselectWheel.setAdapter(wheelAdapter);
            setselectID();
            mBinding.setShowEmpty(false);
        } else {
            mBinding.setShowEmpty(true);
        }
        mBinding.projectselectWheel.postInvalidate();
    }

    private void setCurrentItem(int i) {
        selectIndex = i;
        mBinding.projectselectWheel.setCurrentItem(i);
    }



    @Override
    public void onShowListener() {
        super.onShowListener();
        mBinding.projectselectWheel.setCurrentItem(selectIndex);
        SystemUtil.toggleSoftInput(mBinding.projectselectTitle);
    }

    private void setselectID() {
        if (ID != -1 && Util.isNotListEmpty(data)) {
            for (int i = 0; i < data.size(); i++) {
                T bean = data.get(i);
                if (ID == bean.getId()) {
                    setCurrentItem(i);
                    showText(bean.getPickerViewText());
                    break;
                }
            }
        } else {
            showText("");
            mBinding.projectselectWheel.setCurrentItem(0);
        }
    }





    public void getAdminData(RxAppCompatActivity mContext) {
        RetrofitHttp.getRequest(HttpApi.class)
                .getAdmins()
                .compose(RxThreadUtil.observableToMain())
                .compose(mContext.bindToLifecycle())
                .subscribe(new HttpObserver<JsonBean<List<AdminListBean>>>(mContext, false) {
                    @Override
                    public void onSuccess(JsonBean<List<AdminListBean>> bean) {
                        setData((List<T>) bean.data);
                    }

                });
    }


    public void getUserData(RxAppCompatActivity mContext, String type) {
        RetrofitHttp.getRequest(HttpApi.class)
                .getAllUserList(type)
                .compose(RxThreadUtil.observableToMain())
                .compose(mContext.bindToLifecycle())
                .subscribe(new HttpObserver<JsonBean<List<UserBean>>>(mContext, false) {
                    @Override
                    public void onSuccess(JsonBean<List<UserBean>> bean) {
                        setData((List<T>) bean.data);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        super.onError(e);
                    }
                });
    }


}
