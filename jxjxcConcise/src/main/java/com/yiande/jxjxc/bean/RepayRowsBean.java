package com.yiande.jxjxc.bean;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/18 15:21
 */
public class RepayRowsBean {
    /**
     * RepayMoney : 500
     * Type : 0
     * AdminName : Android
     * Memo : 正常还款 - 本次还款￥500
     * AddDate : 2020-12-18 15:18
     */

    private String RepayMoney;
    private int Type;//0 正常还款 1 最后一笔 2反审核 3 红冲
    private String AdminName;
    private String Memo;
    private String AddDate;

    public String getRepayMoney() {
        return RepayMoney;
    }

    public void setRepayMoney(String RepayMoney) {
        this.RepayMoney = RepayMoney;
    }

    public int getType() {
        return Type;
    }

    public void setType(int Type) {
        this.Type = Type;
    }

    public String getAdminName() {
        return AdminName;
    }

    public void setAdminName(String AdminName) {
        this.AdminName = AdminName;
    }

    public String getMemo() {
        return Memo;
    }

    public void setMemo(String Memo) {
        this.Memo = Memo;
    }

    public String getAddDate() {
        return AddDate;
    }

    public void setAddDate(String AddDate) {
        this.AddDate = AddDate;
    }
}
