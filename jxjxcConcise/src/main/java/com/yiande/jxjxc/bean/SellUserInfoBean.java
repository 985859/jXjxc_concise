package com.yiande.jxjxc.bean;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/12 8:36
 */
public class SellUserInfoBean {


    /**
     * AdminName : Android
     * Type : 1
     * Name : 爱的色放
     * Mob : 13502220220
     * Address : 000000000000000000000
     * Balance : 0
     * Debt : 0
     */

    private String AdminName;
    private int Type;
    private String Name;
    private String ComName;
    private String Mob;
    private String Address;
    private String Balance;
    private String Debt;

    public String getAdminName() {
        return AdminName;
    }

    public void setAdminName(String AdminName) {
        this.AdminName = AdminName;
    }

    public int getType() {
        return Type;
    }

    public void setType(int Type) {
        this.Type = Type;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getMob() {
        return Mob;
    }

    public void setMob(String Mob) {
        this.Mob = Mob;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String Balance) {
        this.Balance = Balance;
    }

    public String getDebt() {
        return Debt;
    }

    public void setDebt(String Debt) {
        this.Debt = Debt;
    }

    public String getComName() {
        return ComName;
    }

    public void setComName(String comName) {
        ComName = comName;
    }
}
