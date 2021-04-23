package com.yiande.jxjxc.bean;

import java.util.List;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2021/4/22 8:33
 */
public class UserInfoBean {


    /**
     * UserInfo : {"User_ID":74,"User_Type":1,"User_ComName":"客户001","User_Name":"联系人001","User_Mob":"13500001111","User_Address":null,"User_Admin_ID":0,"User_Memo":"这是客户0001的备注","User_IsOK":1,"User_AddDate":"2021-04-21T17:58:56","User_Class1":143,"User_Class1Name":"客户类别1","User_Tel":"13500000000"}
     * UserAddress : [{"UserAddress_ID":40,"UserAddress_Address":"这是客户0001公司地址","UserAddress_Type":1,"UserAddress_Name":"公司地址"},{"UserAddress_ID":41,"UserAddress_Address":"这是客户0001送货地址","UserAddress_Type":1,"UserAddress_Name":"送货地址"}]
     * Logistics : [{"UserAddress_ID":42,"UserAddress_Address":"这是物流公司1的地址","UserAddress_Type":2,"UserAddress_City":"目的地1","UserAddress_ComName":"物流公司1","UserAddress_Tel":"135666611111"},{"UserAddress_ID":43,"UserAddress_Address":"这是物流公司2的地址","UserAddress_Type":2,"UserAddress_City":"目的地2 ","UserAddress_ComName":"物流公司2","UserAddress_Tel":"13500002222"}]
     */

    private UserBean UserInfo;
    private List<UserAddressBean> UserAddress;
    private List<UserAddressBean> Logistics;

    public UserBean getUserInfo() {
        return UserInfo;
    }

    public void setUserInfo(UserBean UserInfo) {
        this.UserInfo = UserInfo;
    }

    public List<UserAddressBean> getUserAddress() {
        return UserAddress;
    }

    public void setUserAddress(List<UserAddressBean> UserAddress) {
        this.UserAddress = UserAddress;
    }

    public List<UserAddressBean> getLogistics() {
        return Logistics;
    }

    public void setLogistics(List<UserAddressBean> Logistics) {
        this.Logistics = Logistics;
    }


}
