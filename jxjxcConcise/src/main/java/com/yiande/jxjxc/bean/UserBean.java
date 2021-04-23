package com.yiande.jxjxc.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableBoolean;

import com.yiande.jxjxc.BR;
import com.yiande.jxjxc.myInterface.IPickerData;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/9/15 20:08
 */
public class UserBean  implements IPickerData, Serializable {
    private static final long serialVersionUID = -7197197410303611420L;


    /**
     * User_ID : 7
     * User_ComName : 4554
     * User_Name :
     * User_Mob :
     * User_Address :
     * User_Memo :
     * User_IsOK : 1
     * User_Admin_Name :
     * User_Admin_ID : 58
     * User_Logistics : string
     * User_Class1 : 0
     */

    private int User_ID;
    private String User_ComName;
    private String User_Name;
    private String User_Mob;
    private String User_Tel;//备用电话
    private String User_Memo;
    private int User_IsOK;
    private int User_Type;
    private int User_Class1;
    private String User_Class1Name;
    private String User_AddDate;
    private List<UserAddressBean> User_Address;
    private List<UserAddressBean> User_Logistics;


    private String User_Admin_Name;
    private int User_Admin_ID;
    private int User_Level;
    private String User_Money;
    private String User_Debt;

    private int flag;//通讯录导入标示
    private boolean isSelect;//是否是选中状态

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }


    public boolean isSelect() {
        return isSelect;
    }


    public void setSelect(boolean isSelect) {
       this. isSelect = isSelect;

    }

    @Override
    public int getId() {
        return getUser_ID();
    }

    @Override
    public String getPickerViewText() {
        return getUser_ComName();
    }


    public List<UserAddressBean> getUser_Address() {
        return User_Address;
    }

    public void setUser_Address(List<UserAddressBean> User_Address) {
        this.User_Address = User_Address;
    }

    public int getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(int user_ID) {
        User_ID = user_ID;
    }

    public String getUser_ComName() {
        return User_ComName;
    }

    public void setUser_ComName(String user_ComName) {
        User_ComName = user_ComName;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
    }

    public String getUser_Mob() {
        return User_Mob;
    }

    public void setUser_Mob(String user_Mob) {
        User_Mob = user_Mob;
    }

    public String getUser_Tel() {
        return User_Tel;
    }

    public void setUser_Tel(String user_Tel) {
        User_Tel = user_Tel;
    }

    public String getUser_Memo() {
        return User_Memo;
    }

    public void setUser_Memo(String user_Memo) {
        User_Memo = user_Memo;
    }

    public int getUser_Level() {
        return User_Level;
    }

    public void setUser_Level(int user_Level) {
        User_Level = user_Level;
    }

    public int getUser_IsOK() {
        return User_IsOK;
    }

    public void setUser_IsOK(int user_IsOK) {
        User_IsOK = user_IsOK;
    }

    public String getUser_Admin_Name() {
        return User_Admin_Name;
    }

    public void setUser_Admin_Name(String user_Admin_Name) {
        User_Admin_Name = user_Admin_Name;
    }

    public int getUser_Admin_ID() {
        return User_Admin_ID;
    }

    public void setUser_Admin_ID(int user_Admin_ID) {
        User_Admin_ID = user_Admin_ID;
    }

    public int getUser_Type() {
        return User_Type;
    }

    public void setUser_Type(int user_Type) {
        User_Type = user_Type;
    }

    public String getUser_Money() {
        return User_Money;
    }

    public void setUser_Money(String user_Money) {
        User_Money = user_Money;
    }

    public String getUser_Debt() {
        return User_Debt;
    }

    public void setUser_Debt(String user_Debt) {
        User_Debt = user_Debt;
    }

    public int getUser_Class1() {
        return User_Class1;
    }

    public void setUser_Class1(int user_Class1) {
        User_Class1 = user_Class1;
    }

    public List<UserAddressBean> getUser_Logistics() {
        return User_Logistics;
    }

    public void setUser_Logistics(List<UserAddressBean> user_Logistics) {
        User_Logistics = user_Logistics;
    }

    public String getUser_Class1Name() {
        return User_Class1Name;
    }

    public void setUser_Class1Name(String user_Class1Name) {
        User_Class1Name = user_Class1Name;
    }

    public String getUser_AddDate() {
        return User_AddDate;
    }

    public void setUser_AddDate(String user_AddDate) {
        User_AddDate = user_AddDate;
    }
}
