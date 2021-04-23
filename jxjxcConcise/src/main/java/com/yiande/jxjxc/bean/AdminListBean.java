package com.yiande.jxjxc.bean;

import com.mylibrary.api.interfaces.SpinnerData;
import com.yiande.jxjxc.myInterface.IPickerData;

import java.io.Serializable;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/9/14 16:59
 */
public class AdminListBean implements SpinnerData, Serializable, IPickerData {


    /**
     * Admin_ID : 22
     * Admin_Name : 123
     * Admin_Mob : 456
     * Admin_Role :
     * Admin_Memo : null
     * Admin_IsOK : 0
     */

    private int Admin_ID;
    private String Admin_Name;
    private String Admin_Mob;
    private String Admin_Role;
    private String Admin_Memo;
    private String Admin_IsWeb;
    private String Admin_IsApp;
    private int Admin_Type;
    private int Admin_IsOK;

    public int getAdmin_ID() {
        return Admin_ID;
    }

    public void setAdmin_ID(int Admin_ID) {
        this.Admin_ID = Admin_ID;
    }

    public String getAdmin_Name() {
        return Admin_Name;
    }

    public void setAdmin_Name(String Admin_Name) {
        this.Admin_Name = Admin_Name;
    }

    public String getAdmin_Mob() {
        return Admin_Mob;
    }

    public void setAdmin_Mob(String Admin_Mob) {
        this.Admin_Mob = Admin_Mob;
    }

    public String getAdmin_Role() {
        return Admin_Role;
    }

    public void setAdmin_Role(String Admin_Role) {
        this.Admin_Role = Admin_Role;
    }

    public String getAdmin_Memo() {
        return Admin_Memo;
    }

    public void setAdmin_Memo(String Admin_Memo) {
        this.Admin_Memo = Admin_Memo;
    }

    public int getAdmin_IsOK() {
        return Admin_IsOK;
    }

    public void setAdmin_IsOK(int Admin_IsOK) {
        this.Admin_IsOK = Admin_IsOK;
    }


    public String getAdmin_IsWeb() {
        return Admin_IsWeb;
    }

    public void setAdmin_IsWeb(String admin_IsWeb) {
        Admin_IsWeb = admin_IsWeb;
    }

    public String getAdmin_IsApp() {
        return Admin_IsApp;
    }

    public void setAdmin_IsApp(String admin_IsApp) {
        Admin_IsApp = admin_IsApp;
    }

    public int getAdmin_Type() {
        return Admin_Type;
    }

    public void setAdmin_Type(int admin_Type) {
        Admin_Type = admin_Type;
    }


    @Override
    public String getText() {
        return getAdmin_Name();
    }

    @Override
    public String getPickerViewText() {
        return getAdmin_Name();
    }

    @Override
    public int getId() {
        return getAdmin_ID();
    }
}
