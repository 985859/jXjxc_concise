package com.yiande.jxjxc.bean;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/15 17:44
 */
public class BalanceRowsBean {
    /**
     * ID : 4
     * Money : 50000
     * AdminName : 张先生
     * AddDate : 2020-12-14 14:48
     * No : null
     * Memo : 系统充值 50000元
     * Type : 1
     */

    private int ID;
    private String Money;
    private String AdminName;
    private String AddDate;
    private String No;
    private String Memo;
    private int Type;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getMoney() {
        return Money;
    }

    public void setMoney(String Money) {
        this.Money = Money;
    }

    public String getAdminName() {
        return AdminName;
    }

    public void setAdminName(String AdminName) {
        this.AdminName = AdminName;
    }

    public String getAddDate() {
        return AddDate;
    }

    public void setAddDate(String AddDate) {
        this.AddDate = AddDate;
    }

    public String getNo() {
        return No;
    }

    public void setNo(String No) {
        this.No = No;
    }

    public String getMemo() {
        return Memo;
    }

    public void setMemo(String Memo) {
        this.Memo = Memo;
    }

    public int getType() {
        return Type;
    }

    public void setType(int Type) {
        this.Type = Type;
    }
}
