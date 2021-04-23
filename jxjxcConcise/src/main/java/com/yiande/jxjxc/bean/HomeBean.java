package com.yiande.jxjxc.bean;

import java.util.List;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/9/21 8:03
 */
public class HomeBean {


    /**
     * IsKg : 0
     * IsPrintKg : 0
     * ComName : 易安德电子商务有限公司
     * PayableAmount3 : ¥ 0.00
     * PayableAmount3_Month : ¥ 0.00
     * Role : [{"Role_ID":1,"Role_Name":"销售分析"},{"Role_ID":2,"Role_Name":"入库管理"},{"Role_ID":3,"Role_Name":"出库管理"},{"Role_ID":4,"Role_Name":"产品管理"}]
     */

    private int IsKg;
    private int IsPrintKg;
    private String ComName;
    private String PayableAmount3;
    private String PayableAmount3_Month;
    private List<RoleBean> Role;

    public int getIsKg() {
        return IsKg;
    }

    public void setIsKg(int IsKg) {
        this.IsKg = IsKg;
    }

    public int getIsPrintKg() {
        return IsPrintKg;
    }

    public void setIsPrintKg(int IsPrintKg) {
        this.IsPrintKg = IsPrintKg;
    }

    public String getComName() {
        return ComName;
    }

    public void setComName(String ComName) {
        this.ComName = ComName;
    }

    public String getPayableAmount3() {
        return PayableAmount3;
    }

    public void setPayableAmount3(String PayableAmount3) {
        this.PayableAmount3 = PayableAmount3;
    }

    public String getPayableAmount3_Month() {
        return PayableAmount3_Month;
    }

    public void setPayableAmount3_Month(String PayableAmount3_Month) {
        this.PayableAmount3_Month = PayableAmount3_Month;
    }

    public List<RoleBean> getRole() {
        return Role;
    }

    public void setRole(List<RoleBean> Role) {
        this.Role = Role;
    }

}
