package com.yiande.jxjxc.popwindow;

import android.view.View;
import android.widget.LinearLayout;

import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.utils.SystemUtil;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BasePopupWindow;
import com.yiande.jxjxc.bean.UserAddressBean;
import com.yiande.jxjxc.databinding.PopLogisticsAddressBinding;
import com.yiande.jxjxc.myInterface.EventConsume;
import com.yiande.jxjxc.myInterface.EventSendData3;
import com.yiande.jxjxc.utils.DialogUtils;


public class LogisticsAddressPop extends BasePopupWindow<PopLogisticsAddressBinding> {
    private UserAddressBean addressBean;
    private int position;
    private int userType;//1客户 2 供应商


    public void setAddressBean(UserAddressBean addressBean, int position) {
        this.addressBean = addressBean;
        if(addressBean!= null){
        addressBean.setUserAddress_Type(2);
        }

        this.position = position;
        mBinding.setData(addressBean);
    }

    public void setUserType(int userType) {
        this.userType = userType;
        if (userType == 1) {
            setHint("请输入客户物流地址");
        } else {
            setHint("请输入供应商物流地址");
        }
    }

    private EventSendData3<Integer, UserAddressBean, Boolean> eventSendData;

    public void setEventSendData(EventSendData3<Integer, UserAddressBean, Boolean> eventSendData) {
        this.eventSendData = eventSendData;
    }

    private EventConsume<Integer> onClick = (type) -> {
        switch (type) {
            case 0:
                dismiss();
                break;
            case 1:
                popLogisticsConfirm();
                break;

            default:
                break;
        }
    };

    public LogisticsAddressPop(RxAppCompatActivity context) {
        super(context);
        mBinding.setOnClick(onClick);
        mBinding.setType(-1);

    }

    @Override
    public int getContentViewID() {
        return R.layout.pop_logistics_address;
    }

    @Override
    public void init(View view) {
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private void popLogisticsConfirm() {
        if (StringUtil.isEmpty(mBinding.popLogisticsText.getText().toString().trim())) {
            DialogUtils.showDialog(activity, "空地址信息，将会删除本条地址，是否确认提交", (dialog, confirm) -> {
                if (confirm) {
                    if (eventSendData != null) {
                        eventSendData.transform(position, addressBean, true);
                    }
                }
            });
            return;
        }
        addressBean.setUserAddress_Address(mBinding.popLogisticsText.getText().toString().trim());
        if (eventSendData != null) {
            eventSendData.transform(position, addressBean, false);
        }
    }

    @Override
    public void setListener() {
        super.setListener();

    }

    @Override
    protected void onDismissListener() {
        super.onDismissListener();
        mBinding.popLogisticsText.setFocusable(false);
        mBinding.popLogisticsText.setFocusableInTouchMode(false);
        mBinding.popLogisticsComName.setFocusableInTouchMode(false);
        mBinding.popLogisticsComName.setFocusableInTouchMode(false);
        mBinding.popLogisticsTel.setFocusableInTouchMode(false);
        mBinding.popLogisticsTel.setFocusableInTouchMode(false);
        mBinding.popLogisticsCity.setFocusableInTouchMode(false);
        mBinding.popLogisticsCity.setFocusableInTouchMode(false);
        SystemUtil.toggleSoftInput(mBinding.popLogisticsText);
    }

    @Override
    public void onShowListener() {
        super.onShowListener();
        mBinding.popLogisticsText.setFocusable(true);
        mBinding.popLogisticsText.setFocusableInTouchMode(true);
        mBinding.popLogisticsComName.setFocusableInTouchMode(true);
        mBinding.popLogisticsComName.setFocusableInTouchMode(true);
        mBinding.popLogisticsTel.setFocusableInTouchMode(true);
        mBinding.popLogisticsTel.setFocusableInTouchMode(true);
        mBinding.popLogisticsCity.setFocusableInTouchMode(true);
        mBinding.popLogisticsCity.setFocusableInTouchMode(true);
        SystemUtil.showKeyboard(mBinding.popLogisticsCity);
    }


    public void setTitle(String title) {
        if (StringUtil.isNotEmpty(title))
            mBinding.popLogisticsTitle.setText(title);
    }

    public void setHint(String hint) {
        if (StringUtil.isNotEmpty(hint)) {
            mBinding.popLogisticsText.setHint(hint);
        }
    }


}
