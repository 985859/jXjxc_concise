package com.yiande.jxjxc.popwindow;


import android.text.InputFilter;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.utils.SystemUtil;
import com.mylibrary.api.utils.ToastUtil;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BasePopupWindow;
import com.yiande.jxjxc.bean.OClassBean;
import com.yiande.jxjxc.bean.OClassBody;
import com.yiande.jxjxc.databinding.PopAddClassBinding;
import com.yiande.jxjxc.myInterface.EventConsume;
import com.yiande.jxjxc.myInterface.EventSendData;
import com.yiande.jxjxc.utils.Util;

import java.util.ArrayList;
import java.util.List;


public class AddClassifyPop extends BasePopupWindow<PopAddClassBinding> {
    private EventSendData<OClassBody, Boolean> onSucceedListener;
    private String toast = "请输入商品分类名称";
    private int type;//0:保持不变(最末) 1:之前 2:之后
    private int ID;
    private OClassBody oClassBody;
    private boolean isAdd = true;
    private ArrayWheelAdapter<OClassBean> wheelAdapter;
    private List<OClassBean> data;
    private int selectIndex = 0;

    ////1产品分类(4级) 2品牌(1级) 3单位(1级) 4多单位 5客户分类(1级) 6供应商分类(1级) 7财务支出分类(2级) 8财务收入分类(2级)必填
    public void setAdd(boolean add) {
        isAdd = add;
        mBinding.setIsAdd(add);
        if (isAdd) {
            setTitle(activity.getString(R.string.class_add));
            initType(2);
        } else {
            setTitle(activity.getString(R.string.class_amend));
            initType(0);
        }
    }

    private EventConsume<Integer> onClick = (type) -> {
        switch (type) {
            case 0:
            case 1:
            case 2:
                initType(type);
                break;
            case 3:
                dismiss();
                break;
            case 4:
                popAddClassConfirm();
                break;
            default:
                break;
        }
    };


    private void popAddClassConfirm() {
        if (StringUtil.isEmpty(mBinding.popAddClassName.getText().toString().trim())) {
            ToastUtil.showShort(toast);
            return;
        }
        oClassBody.setOClass_Name(mBinding.popAddClassName.getText().toString().trim());
        if (Util.isNotListEmpty(data) && selectIndex < data.size()) {
            oClassBody.setOClass_Order_OClass_ID(data.get(selectIndex).getOClass_ID());
        } else {
            oClassBody.setOClass_Order_OClass_ID(0);
        }
        if (onSucceedListener != null)
            onSucceedListener.transform(oClassBody, isAdd);
    }


    public void setOnAddSucceedListener(EventSendData<OClassBody, Boolean> onSucceedListener) {
        this.onSucceedListener = onSucceedListener;
    }

    public AddClassifyPop(RxAppCompatActivity context) {
        super(context);
        mBinding.setOnClick(onClick);
        oClassBody = new OClassBody();
        oClassBody.setOClass_Flag(1);
        oClassBody.setOClass_IsOK(1);
    }

    public void setOClassFlag(int flag) {
        oClassBody.setOClass_Flag(flag);
    }

    public void setOClassIsOK(int isOK) {
        oClassBody.setOClass_IsOK(isOK);
    }

    @Override
    public int getContentViewID() {
        return R.layout.pop_add_class;
    }

    @Override
    public void init(View view) {
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void setListener() {
        super.setListener();
        mBinding.popAddClassWheelview.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                selectIndex = index;
            }
        });
        mBinding.popAddClassWheelview.setCyclic(false);
    }

    @Override
    protected void onDismissListener() {
        super.onDismissListener();
        mBinding.popAddClassName.setFocusable(false);
        mBinding.popAddClassName.setFocusableInTouchMode(false);
        SystemUtil.toggleSoftInput(mBinding.popAddClassName);
    }


    public void showKeyboard() {
        mBinding.popAddClassName.setFocusable(true);
        mBinding.popAddClassName.setFocusableInTouchMode(true);
        SystemUtil.showKeyboard(mBinding.popAddClassName);
    }


    public void setID(int ID) {
        this.ID = ID;
        oClassBody.setOClass_ID(ID);
    }

    public void setFatherID(int ID) {
        this.ID = ID;
        oClassBody.setOClass_FatherID(ID);
    }

    public void setData(List<OClassBean> data) {
        if (data != null && data.size() > 0) {
            List<OClassBean> beanList = new ArrayList<>();
            beanList.addAll(data);
            if (!isAdd) {
                for (OClassBean bean : beanList) {
                    if (ID == bean.getOClass_ID()) {
                        beanList.remove(bean);
                        break;
                    }
                }
            }
            this.data = beanList;
            selectIndex = 0;
            if (beanList != null && beanList.size() > 0) {
                wheelAdapter = new ArrayWheelAdapter(beanList);
                mBinding.popAddClassWheelview.setAdapter(wheelAdapter);
                mBinding.popAddClassLayout.setVisibility(View.VISIBLE);
                if (isAdd) {
                    selectIndex = beanList.size() - 1;
                }
                mBinding.popAddClassWheelview.setCurrentItem(selectIndex);
            } else {
                mBinding.popAddClassLayout.setVisibility(View.GONE);
                oClassBody.setOClass_Order_OClass_ID(0);
            }

        } else {
            mBinding.popAddClassLayout.setVisibility(View.GONE);
            oClassBody.setOClass_Order_OClass_ID(0);
        }
    }


    public void setTitle(String title) {
        mBinding.popAddClassTitle.setText(title);
    }

    public void setHint(String hint) {
        if (StringUtil.isNotEmpty(hint)) {
            mBinding.popAddClassName.setHint(hint);
            toast = hint;
        }
    }

    public void setMaxLength(int max) {
        InputFilter[] filters = {new InputFilter.LengthFilter(max)};
        mBinding.popAddClassName.setFilters(filters);
    }

    public void setEdit(String deit) {
        if (deit != null) {
            mBinding.popAddClassName.setText(deit);
            mBinding.popAddClassName.setSelection(deit.length());
        }

    }


    private void initType(int state) {
        mBinding.popAddClassType1.setChecked(false);
        mBinding.popAddClassType2.setChecked(false);
        mBinding.popAddClassType3.setChecked(false);
        type = state;
        switch (state) {
            case 0:
                mBinding.popAddClassType2.setChecked(true);
                break;
            case 1:
                mBinding.popAddClassType1.setChecked(true);
                break;
            case 2:
                mBinding.popAddClassType3.setChecked(true);
                break;
            default:
                break;

        }
        oClassBody.setOClass_Order(type);
    }


}
