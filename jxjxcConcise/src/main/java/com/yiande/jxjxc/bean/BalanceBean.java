package com.yiande.jxjxc.bean;

import java.util.List;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/21 8:49
 */
public class BalanceBean {

    /**
     * UID : 16
     * ComName : 贝拉阿道夫
     * Name : 阿道夫
     * Balance : 50000
     * BalanceRows : [{"ID":4,"Money":"50000","AdminName":"张先生","AddDate":"2020-12-14 14:48","No":null,"Memo":"系统充值 50000元","Type":1}]
     */

    private int UID;
    private String ComName;
    private String Name;
    private String Balance;
    private List<BalanceRowsBean> BalanceRows;

    public int getUID() {
        return UID;
    }

    public void setUID(int UID) {
        this.UID = UID;
    }

    public String getComName() {
        return ComName;
    }

    public void setComName(String ComName) {
        this.ComName = ComName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String Balance) {
        this.Balance = Balance;
    }

    public List<BalanceRowsBean> getBalanceRows() {
        return BalanceRows;
    }

    public void setBalanceRows(List<BalanceRowsBean> BalanceRows) {
        this.BalanceRows = BalanceRows;
    }


}
