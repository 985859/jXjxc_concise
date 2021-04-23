package com.yiande.jxjxc.adapter;

import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.mylibrary.api.utils.StringUtil;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.bean.UserAddressBean;
import com.yiande.jxjxc.databinding.ItmUserAddressBinding;
import com.yiande.jxjxc.myInterface.EventSendData3;
import com.yiande.jxjxc.popwindow.AddAddressPop;
import com.yiande.jxjxc.popwindow.LogisticsAddressPop;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2021/4/20 18:02
 */
public class UserAddressAdapter extends BaseQuickAdapter<UserAddressBean, BaseDataBindingHolder> {
    private int userType;//1 客户 2 供应商
    private int type;//0 客户、供应商所在地址 2 物流地址
    private AddAddressPop addAddressPop;
    private LogisticsAddressPop logisticsAddressPop;

    public void setType(int type) {
        this.type = type;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public UserAddressAdapter() {
        super(R.layout.itm_user_address);
        addChildClickViewIds(R.id.itmUserAddress_Add, R.id.itmUserAddress_Text);
        setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                switch (view.getId()) {
                    case R.id.itmUserAddress_Add:
                        if (type == 0) {
                            getAddAddressPop().setTitle(getContext().getString(R.string.client_address_add));
                            getAddAddressPop().setAddressBean(new UserAddressBean(), -1);
                            getAddAddressPop().showPopupWindow(view, Gravity.BOTTOM);
                        } else {
                            getLogisticsAddressPop().setTitle(getContext().getString(R.string.supplier_address_add));
                            getLogisticsAddressPop().setAddressBean(new UserAddressBean(), -1);
                            getLogisticsAddressPop().showPopupWindow(view, Gravity.BOTTOM);
                        }
                        break;
                    case R.id.itmUserAddress_Text:
                        if (type == 0) {
                            getAddAddressPop().setTitle(getTitle(position));
                            getAddAddressPop().setAddressBean(getItem(position), position);
                            getAddAddressPop().showPopupWindow(view, Gravity.BOTTOM);
                        } else {
                            getLogisticsAddressPop().setTitle(getTitle(position));
                            getLogisticsAddressPop().setAddressBean(getItem(position), position);
                            getLogisticsAddressPop().showPopupWindow(view, Gravity.BOTTOM);
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder baseDataBindingHolder, UserAddressBean bean) {
        ItmUserAddressBinding binding = (ItmUserAddressBinding) baseDataBindingHolder.getDataBinding();
        if (baseDataBindingHolder.getBindingAdapterPosition() == 0) {
            binding.setShowAdd(true);
        } else {
            binding.setShowAdd(false);
        }

        String title = getTitle(baseDataBindingHolder.getBindingAdapterPosition());
        String hint = "";
        String addText = "";
        if (userType == 1) {
            if (type == 0) {
                hint = getContext().getString(R.string.client_address_hint);
                addText = getContext().getString(R.string.client_address_add);
            } else {
                hint = getContext().getString(R.string.client_address_hint2);
                addText = getContext().getString(R.string.client_address_add2);
            }
        } else {
            if (type == 0) {
                hint = getContext().getString(R.string.supplier_address_hint);
                addText = getContext().getString(R.string.supplier_address_add);
            } else {
                hint = getContext().getString(R.string.supplier_address_hint2);
                addText = getContext().getString(R.string.supplier_address_add2);
            }
        }
        binding.itmUserAddressTitle.setText(title);
        binding.itmUserAddressAdd.setText(String.format(getContext().getString(R.string.plus), addText));
        binding.itmUserAddressText.setHint(hint);
        String text = "";
        String s = " -- ";
        if (type == 0) {
            text = bean.getUserAddress_Address();
            if (StringUtil.isNotEmpty(bean.getUserAddress_Name())) {
                text = bean.getUserAddress_Name() + s + text;
            }
        } else {
            List<String> stringList = new ArrayList<>();
            stringList.add(bean.getUserAddress_City());
            stringList.add(bean.getUserAddress_ComName());
            stringList.add(bean.getUserAddress_Tel());
            stringList.add(bean.getUserAddress_Address());
            for (int i = 0; i < stringList.size(); i++) {
                if (StringUtil.isNotEmpty(stringList.get(i))) {
                    text += stringList.get(i) + s;
                }
            }
            if (text.endsWith(s)) {
                text = text.substring(0, text.length() - s.length());
            }

        }
        if (StringUtil.isNotEmpty(text)) {
            binding.itmUserAddressText.setText(text);
        }

    }

    private String getTitle(int position) {
        String title;
        if (userType == 1) {
            if (type == 0) {
                title = getContext().getString(R.string.client_address);
            } else {
                title = getContext().getString(R.string.logistics_address);
            }
        } else {
            if (type == 0) {
                title = getContext().getString(R.string.supplier_address);

            } else {
                title = getContext().getString(R.string.logistics_address);
            }

        }
        if (getItemCount() > 1) {
            title = title + (position + 1);
        }
        return title;
    }

    private EventSendData3<Integer, UserAddressBean, Boolean> addAddressSendData = (position, bean, isDealer) -> {
        if(position==-1){
        addData(bean);
        }else {
            if (isDealer) {
                if (position == 0) {
                    setData(position, bean);
                } else {
                    removeAt(position);
                }
            } else {
                setData(position, bean);
            }

        }
        getAddAddressPop().dismiss();
    };

    public AddAddressPop getAddAddressPop() {
        if (addAddressPop == null) {
            addAddressPop = new AddAddressPop((RxAppCompatActivity) getContext());
            addAddressPop.setEventSendData(addAddressSendData);
            addAddressPop.setUserType(userType);
        }
        return addAddressPop;
    }


    private EventSendData3<Integer, UserAddressBean, Boolean> logisticsSendData = (position, bean, isDealer) -> {
        if (position == -1) {
            addData(bean);
        } else {
            if (isDealer) {
                if (position == 0) {
                    setData(position, bean);
                } else {
                    removeAt(position);
                }
            } else {
                setData(position, bean);
            }
        }
        getLogisticsAddressPop().dismiss();
    };

    public LogisticsAddressPop getLogisticsAddressPop() {
        if (logisticsAddressPop == null) {
            logisticsAddressPop = new LogisticsAddressPop((RxAppCompatActivity) getContext());
            logisticsAddressPop.setEventSendData(logisticsSendData);
            logisticsAddressPop.setUserType(userType);
        }
        return logisticsAddressPop;
    }
}
