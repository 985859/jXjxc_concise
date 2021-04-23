package com.yiande.jxjxc.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;

import com.mylibrary.api.dialog.MyDialog;
import com.mylibrary.api.utils.SkipUtil;
import com.mylibrary.api.utils.SystemUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BaseActivity;
import com.yiande.jxjxc.databinding.ActivityUserListBinding;
import com.yiande.jxjxc.presenter.UserListPresenter;
import com.yiande.jxjxc.utils.DialogUtils;


public class UserListActivity extends BaseActivity<UserListPresenter, ActivityUserListBinding> {
    // Type = 1;//1客户 2供应商
    public static void start(Context context, int type, String keywords) {
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("keywords", keywords);
        map.put("type", type);
        SkipUtil.skipActivity(context, UserListActivity.class, map, 0);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_user_list;
    }

    @Override
    protected UserListPresenter getPresenter() {
        return new UserListPresenter(mContext, mBinding);
    }

    @Override
    protected void initImmersionBar() {
        mImmersionBar.titleBar(mBinding.userListTop).init();
    }

    /**
     * requestPermissions的回调
     * 一个或多个权限请求结果回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasAllGranted = true;
        //判断是否拒绝  拒绝后要怎么处理 以及取消再次提示的处理
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                hasAllGranted = false;
                break;
            }
        }
        if (hasAllGranted) { //同意权限做的处理,开启服务提交通讯录
            ContactsActivity.start(mContext, mPresenter.user_Type);
        } else {    //拒绝授权做的处理，弹出弹框提示用户授权
            DialogUtils.showDialog(mContext, "导入通讯录，需要读取通讯库，请前往设置> 权限> 通讯录  打开", "前往", "取消", new MyDialog.OnCloseListener() {
                @Override
                public void onClick(Dialog dialog, boolean confirm) {
                    if (confirm) {
                        SystemUtil.skipSet(mContext);
                    }
                }
            }, false);
        }
    }
}