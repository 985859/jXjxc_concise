package com.yiande.jxjxc.bean;

import java.io.Serializable;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/15 10:02
 */
public class DebtListBean implements Serializable {


    private static final long serialVersionUID = 9187570026710351470L;
    /**
     * ID : 22
     * Money : 1000
     * AdminName : Android
     * Date : 2020-12-16 09:47
     * No :
     * Memo : 欠款 - 主动记录客户欠款￥1000元
     * State : 0:欠款 1:结清
     */

    private int ID;
    private String Money;
    private String DebtNow;
    private String AdminName;
    private String Date;
    private String No;
    private String Memo;
    private int State;

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

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
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

    public int getState() {
        return State;
    }

    public void setState(int State) {
        this.State = State;
    }

    public String getDebtNow() {
        return DebtNow;
    }

    public void setDebtNow(String debtNow) {
        DebtNow = debtNow;
    }
}
