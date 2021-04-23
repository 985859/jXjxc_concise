package com.yiande.jxjxc.popwindow;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;

import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.utils.SystemUtil;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BasePopupWindow;
import com.yiande.jxjxc.bean.UserAddressBean;
import com.yiande.jxjxc.databinding.PopAddAddressBinding;
import com.yiande.jxjxc.myInterface.EventConsume;
import com.yiande.jxjxc.myInterface.EventSendData3;
import com.yiande.jxjxc.utils.DialogUtils;


public class AddAddressPop extends BasePopupWindow<PopAddAddressBinding> {
    private UserAddressBean addressBean;
    private int position;
    private int userType;//1客户 2 供应商


    public void setAddressBean(UserAddressBean addressBean, int position) {
        this.addressBean = addressBean;
        this.position = position;
        if (addressBean != null) {
            addressBean.setUserAddress_Type(1);
        }
        mBinding.setData(addressBean);
    }

    public void setUserType(int userType) {
        this.userType = userType;
        if (userType == 1) {
            setHint("请输入客户所在地址");
        } else {
            setHint("请输入供应商所在地址");
        }
    }

    private EventSendData3<Integer, UserAddressBean, Boolean> eventSendData;

    public void setEventSendData(EventSendData3<Integer, UserAddressBean, Boolean> eventSendData) {
        this.eventSendData = eventSendData;
    }

    private EventConsume<Integer> onClick = (type) -> {
        String s;
        switch (type) {
            case 0:
                dismiss();
                break;
            case 1:
                popAddressConfirm();
                break;
            case 2:
                s = activity.getString(R.string.address_com);
                mBinding.popAddressName.setText(s);
                mBinding.popAddressName.setSelection(s.length());

                mBinding.setType(0);
                break;
            case 3:
                s = activity.getString(R.string.address_send);
                mBinding.popAddressName.setText(s);
                mBinding.popAddressName.setSelection(s.length());
                mBinding.setType(1);
                break;
            default:
                break;
        }
    };

    public AddAddressPop(RxAppCompatActivity context) {
        super(context);
        mBinding.setOnClick(onClick);
        mBinding.setType(-1);

    }

    @Override
    public int getContentViewID() {
        return R.layout.pop_add_address;
    }

    @Override
    public void init(View view) {
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private void popAddressConfirm() {
        if (StringUtil.isEmpty(mBinding.popAddressText.getText().toString().trim())) {
            DialogUtils.showDialog(activity, "空地址信息，将会删除本条地址，是否确认提交", (dialog, confirm) -> {
                if (confirm) {
                    if (eventSendData != null) {
                        eventSendData.transform(position, addressBean, true);
                    }
                }
            });
            return;
        }
        addressBean.setUserAddress_Address(mBinding.popAddressText.getText().toString().trim());
        if (eventSendData != null) {
            eventSendData.transform(position, addressBean, false);
        }
    }

    @Override
    public void setListener() {
        super.setListener();

        mBinding.popAddressName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null) {
                    if (activity.getString(R.string.address_com).equals(s.toString())) {
                        mBinding.setType(0);
                    } else if (activity.getString(R.string.address_send).equals(s.toString())) {
                        mBinding.setType(1);
                    } else {
                        mBinding.setType(-1);
                    }
                } else {
                    mBinding.setType(-1);
                }
            }
        });
    }

    @Override
    protected void onDismissListener() {
        super.onDismissListener();
        mBinding.popAddressText.setFocusable(false);
        mBinding.popAddressText.setFocusableInTouchMode(false);
        mBinding.popAddressName.setFocusableInTouchMode(false);
        mBinding.popAddressName.setFocusableInTouchMode(false);
        SystemUtil.toggleSoftInput(mBinding.popAddressText);
    }

    @Override
    public void onShowListener() {
        super.onShowListener();
        mBinding.popAddressText.setFocusable(true);
        mBinding.popAddressText.setFocusableInTouchMode(true);

        mBinding.popAddressName.setFocusable(true);
        mBinding.popAddressName.setFocusableInTouchMode(true);
        SystemUtil.showKeyboard(mBinding.popAddressName);
    }


    public void setTitle(String title) {
        if (StringUtil.isNotEmpty(title))
            mBinding.popAddressTitle.setText(title);
    }

    public void setHint(String hint) {
        if (StringUtil.isNotEmpty(hint)) {
            mBinding.popAddressText.setHint(hint);
        }
    }


}
