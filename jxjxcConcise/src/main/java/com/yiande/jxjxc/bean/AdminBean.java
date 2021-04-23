package com.yiande.jxjxc.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.yiande.jxjxc.BR;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/9/14 16:59
 */
public class AdminBean extends BaseObservable {


    /**
     * Admin_ID : 24
     * Admin_Name : string
     * Admin_Mob : string
     * Admin_Role : string
     * Admin_Memo : null
     * Admin_IsOK : 0
     * Admin_IsWeb : 0
     * Admin_IsApp : 0
     */

    private int Admin_ID;
    private String Admin_Name;
    private String Admin_Mob;
    private String Admin_Role;
    private String Admin_Memo;
    private String Admin_Password;
    private String Admin_Password2;
    private int Admin_IsOK = 1;
    private int Admin_IsWeb = 0;
    private int Admin_IsApp = 0;

    private int Admin_Type;

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

    public String getAdmin_Password() {
        return Admin_Password;
    }

    public void setAdmin_Password(String admin_Password) {
        Admin_Password = admin_Password;
    }

    public int getAdmin_IsWeb() {
        return Admin_IsWeb;
    }

    public void setAdmin_IsWeb(int Admin_IsWeb) {
        this.Admin_IsWeb = Admin_IsWeb;
    }

    public int getAdmin_IsApp() {
        return Admin_IsApp;
    }

    public void setAdmin_IsApp(int Admin_IsApp) {
        this.Admin_IsApp = Admin_IsApp;
    }


    @Bindable
    public int getAdmin_Type() {
        return Admin_Type;
    }

    public void setAdmin_Type(int admin_Type) {
        Admin_Type = admin_Type;
        notifyPropertyChanged(BR.admin_Type);
    }

    public String getAdmin_Password2() {
        return Admin_Password2;
    }

    public void setAdmin_Password2(String admin_Password2) {
        Admin_Password2 = admin_Password2;
    }
}
