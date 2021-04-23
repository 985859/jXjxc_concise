package com.yiande.jxjxc.bean;

import java.util.List;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/15 9:29
 */
public class DebtBean {


    /**
     * Type : 1
     * DebtAll : 1000.0
     * RepayAll : 0
     * DebtNow : 1000.0
     * DebtRows : [{"ID":22,"Money":"1000","AdminName":"Android","Date":"2020-12-16 09:47","No":"","Memo":"欠款 - 主动记录客户欠款￥1000元","State":0}]
     */

    private int Type;
    private String DebtAll;
    private String RepayAll;
    private String DebtNow;
    private List<DebtListBean> DebtRows;

    public int getType() {
        return Type;
    }

    public void setType(int Type) {
        this.Type = Type;
    }

    public String getDebtAll() {
        return DebtAll;
    }

    public void setDebtAll(String DebtAll) {
        this.DebtAll = DebtAll;
    }

    public String getRepayAll() {
        return RepayAll;
    }

    public void setRepayAll(String RepayAll) {
        this.RepayAll = RepayAll;
    }

    public String getDebtNow() {
        return DebtNow;
    }

    public void setDebtNow(String DebtNow) {
        this.DebtNow = DebtNow;
    }

    public List<DebtListBean> getDebtRows() {
        return DebtRows;
    }

    public void setDebtRows(List<DebtListBean> DebtRows) {
        this.DebtRows = DebtRows;
    }


}
