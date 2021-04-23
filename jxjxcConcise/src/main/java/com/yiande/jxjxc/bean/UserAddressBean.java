package com.yiande.jxjxc.bean;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2021/4/20 17:58
 */
public class UserAddressBean {
    /**
     * UserAddress_Type : 0
     * UserAddress_City : string
     * UserAddress_ComName : string
     * UserAddress_Tel : string
     * UserAddress_Address : string
     */
//    UserAddress_Type (integer, optional): 类型 1供应商地址 2物流地址 ,
//    UserAddress_City (string, optional): 物流目的地 ,
//    UserAddress_ComName (string, optional): 物流公司名称 ,
//    UserAddress_Tel (string, optional): 电话 ,
//    UserAddress_Address (string, optional): 地址
    private int UserAddress_Type;
    private String UserAddress_City;
    private String UserAddress_ComName;
    private String UserAddress_Tel;
    private String UserAddress_Address;
    private String UserAddress_Name ;

    public int getUserAddress_Type() {
        return UserAddress_Type;
    }

    public void setUserAddress_Type(int UserAddress_Type) {
        this.UserAddress_Type = UserAddress_Type;
    }

    public String getUserAddress_City() {
        return UserAddress_City;
    }

    public void setUserAddress_City(String UserAddress_City) {
        this.UserAddress_City = UserAddress_City;
    }

    public String getUserAddress_ComName() {
        return UserAddress_ComName;
    }

    public void setUserAddress_ComName(String UserAddress_ComName) {
        this.UserAddress_ComName = UserAddress_ComName;
    }

    public String getUserAddress_Tel() {
        return UserAddress_Tel;
    }

    public void setUserAddress_Tel(String UserAddress_Tel) {
        this.UserAddress_Tel = UserAddress_Tel;
    }

    public String getUserAddress_Address() {
        return UserAddress_Address;
    }

    public void setUserAddress_Address(String UserAddress_Address) {
        this.UserAddress_Address = UserAddress_Address;
    }

    public void setUserAddress_Name(String userAddress_Name) {
        UserAddress_Name = userAddress_Name;
    }

    public String getUserAddress_Name() {
        return UserAddress_Name;
    }
}
