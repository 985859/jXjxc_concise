package com.yiande.jxjxc.popwindow;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.activity.ContactsActivity;
import com.yiande.jxjxc.activity.UserAddActivity;
import com.yiande.jxjxc.base.BasePopupWindow;
import com.yiande.jxjxc.databinding.PopTopMenuBinding;
import com.yiande.jxjxc.myInterface.EventConsume;

import java.util.ArrayList;


/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2021/3/27 8:19
 */

public class TopMenuPop extends BasePopupWindow<PopTopMenuBinding> {
    private int type;


    public void setType(int type) {
        this.type = type;
        if (type == 21) {
            mBinding.popTopMenuText.setVisibility(View.GONE);
        } else {
            mBinding.popTopMenuText.setVisibility(View.VISIBLE);
        }
    }

    public TopMenuPop(RxAppCompatActivity context) {
        super(context);
        mBinding.setOnClick(onClick);
        mBinding.topMeunLayout.setOnClickListener(v -> dismiss());
    }


    @Override
    public int getContentViewID() {
        return R.layout.pop_top_menu;
    }

    private EventConsume<Integer> onClick = (type) -> {
        switch (type) {
            case 0:
                UserAddActivity.start(activity, 0, type, false);
                break;
            case 1:
                checkPermission(activity, new String[]{"android.permission.READ_CONTACTS"}, 0);
                break;
            default:
                break;
        }
        dismiss();
    };

    @Override
    public void init(View view) {

    }

    /**
     * 动态权限
     */
    public void checkPermission(Activity activity, String[] permissions, int request) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {   //Android 6.0开始的动态权限，这里进行版本判断
            ArrayList<String> mPermissionList = new ArrayList<>();
            for (int i = 0; i < permissions.length; i++) {
                if (ContextCompat.checkSelfPermission(activity, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    mPermissionList.add(permissions[i]);
                }
            }
            if (mPermissionList.isEmpty()) {  //非初次进入App且已授权
                ContactsActivity.start(activity, type);
            } else {
                //请求权限方法
                String[] permissionsNew = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
                ActivityCompat.requestPermissions(activity, permissionsNew, request); //这个触发下面onRequestPermissionsResult这个回调
            }
        }
    }

}
