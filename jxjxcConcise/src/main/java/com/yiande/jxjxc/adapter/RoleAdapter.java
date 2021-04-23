package com.yiande.jxjxc.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.bean.RoleBean;
import com.yiande.jxjxc.databinding.ItmRoleBinding;
import com.yiande.jxjxc.utils.Util;

import org.jetbrains.annotations.NotNull;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/9/21 9:23
 */
public class RoleAdapter extends BaseQuickAdapter<RoleBean, BaseDataBindingHolder> {

    private int state;
    private int parentPosition;

    public int getParentPosition() {
        return parentPosition;
    }

    public void setParentPosition(int parentPosition) {
        this.parentPosition = parentPosition;
    }

    public void setState(int state) {
        this.state = state;
    }

    public RoleAdapter() {
        super(R.layout.itm_role);
    }



    @Override
    protected void convert(@NotNull BaseDataBindingHolder baseDataBindingHolder, RoleBean roleBean) {
        ItmRoleBinding roleBinding = (ItmRoleBinding) baseDataBindingHolder.getDataBinding();
        roleBinding.setState(state);
        roleBinding.itmRoleText.setText(roleBean.getRole_Name());
        roleBinding.itmRoleText.setTopDrawable(Util.getRolePicID(roleBean.getRole_ID()));

        switch (state) {
            case 1:
                roleBinding.itmRoleState.setImageDrawable(getContext().getResources().getDrawable(R.drawable.clear));
                break;
            case 2:
                roleBinding.itmRoleState.setImageDrawable(getContext().getResources().getDrawable(R.drawable.circle_plus));
                break;
            default:
                break;
        }
    }
}
