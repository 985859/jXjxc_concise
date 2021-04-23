package com.yiande.jxjxc.bean;

import java.util.List;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/18 14:50
 */
public class DebtDaetailBean {


    /**
     * No :
     * AddDate : 2020-12-18 15:03:45
     * ComName : 璨阳啊啊啊
     * AdminName : Android
     * IsDone : 0// 0 未结清 1 已结清
     * DebtMoney : 1000
     * DebtNowMoney : 500
     * RepayT0T1 : 500
     * RepayT2T3 : 0
     * RepayRealy : 500
     * ID : 33
     * Type : 1
     * RepayRows : [{"RepayMoney":"500","Type":0,"AdminName":"Android","Memo":"正常还款 - 本次还款￥500","AddDate":"2020-12-18 15:18"}]
     */

    private String No;
    private String AddDate;
    private String ComName;
    private String AdminName;
    private int IsDone;
    private String DebtMoney;
    private String DebtNowMoney;
    private String RepayT0T1;
    private String RepayT2T3;
    private String RepayRealy;
    private String Memo;
    private int ID;
    private int Type;
    private List<RepayRowsBean> RepayRows;

    public String getNo() {
        return No;
    }

    public void setNo(String No) {
        this.No = No;
    }

    public String getAddDate() {
        return AddDate;
    }

    public void setAddDate(String AddDate) {
        this.AddDate = AddDate;
    }

    public String getComName() {
        return ComName;
    }

    public void setComName(String ComName) {
        this.ComName = ComName;
    }

    public String getAdminName() {
        return AdminName;
    }

    public void setAdminName(String AdminName) {
        this.AdminName = AdminName;
    }

    public int getIsDone() {
        return IsDone;
    }

    public void setIsDone(int IsDone) {
        this.IsDone = IsDone;
    }

    public String getDebtMoney() {
        return DebtMoney;
    }

    public void setDebtMoney(String DebtMoney) {
        this.DebtMoney = DebtMoney;
    }

    public String getDebtNowMoney() {
        return DebtNowMoney;
    }

    public void setDebtNowMoney(String DebtNowMoney) {
        this.DebtNowMoney = DebtNowMoney;
    }

    public String getRepayT0T1() {
        return RepayT0T1;
    }

    public void setRepayT0T1(String RepayT0T1) {
        this.RepayT0T1 = RepayT0T1;
    }

    public String getRepayT2T3() {
        return RepayT2T3;
    }

    public void setRepayT2T3(String RepayT2T3) {
        this.RepayT2T3 = RepayT2T3;
    }

    public String getRepayRealy() {
        return RepayRealy;
    }

    public void setRepayRealy(String RepayRealy) {
        this.RepayRealy = RepayRealy;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getType() {
        return Type;
    }

    public void setType(int Type) {
        this.Type = Type;
    }

    public List<RepayRowsBean> getRepayRows() {
        return RepayRows;
    }

    public void setRepayRows(List<RepayRowsBean> RepayRows) {
        this.RepayRows = RepayRows;
    }

    public String getMemo() {
        return Memo;
    }

    public void setMemo(String memo) {
        Memo = memo;
    }
}
