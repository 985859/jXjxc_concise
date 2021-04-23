package com.yiande.jxjxc.bean;

import java.util.List;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/16 15:34
 */
public class DebtManageBean {

    /**
     * Type : 1
     * DebtAll : 0
     * RepayAll : 21170.0
     * DebtNow : 108600.0
     * DebtRows : [{"ID":21,"Type":1,"ComName":"昌凸(艹皿艹 )","Mob":"11111111111","DebtNow":"2000","DebtAll":"2000","State":0},{"ID":22,"Type":1,"ComName":"大人物案发当时","Mob":"11111111111","DebtNow":"2100","DebtAll":"2100","State":0},{"ID":15,"Type":1,"ComName":"埃尔法","Mob":"18326510325","DebtNow":"0","DebtAll":"0","State":1},{"ID":14,"Type":1,"ComName":"奥尔夫公司","Mob":"13852213212","DebtNow":"0","DebtAll":"0","State":1},{"ID":13,"Type":1,"ComName":"阿尔法","Mob":"13502220220","DebtNow":"59500","DebtAll":"59670","State":0},{"ID":9,"Type":1,"ComName":"清风机械","Mob":"15612345678","DebtNow":"6000","DebtAll":"6000","State":0},{"ID":12,"Type":1,"ComName":"术仑建机","Mob":"150123456789","DebtNow":"2000","DebtAll":"5000","State":0},{"ID":4,"Type":1,"ComName":"","Mob":"","DebtNow":"2000","DebtAll":"3500","State":0},{"ID":3,"Type":1,"ComName":"","Mob":"","DebtNow":"35000","DebtAll":"51500","State":0}]
     */

    private int Type;
    private String DebtAll;
    private String RepayAll;
    private String DebtNow;
    private List<DebtRowsBean> DebtRows;

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

    public List<DebtRowsBean> getDebtRows() {
        return DebtRows;
    }

    public void setDebtRows(List<DebtRowsBean> DebtRows) {
        this.DebtRows = DebtRows;
    }


}
