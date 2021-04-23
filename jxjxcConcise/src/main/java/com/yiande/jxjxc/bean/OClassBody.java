package com.yiande.jxjxc.bean;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/9/16 20:47
 */
public class OClassBody {


    /**
     * OClass_Flag : 1 产品分类 2 品牌  3单位
     * OClass_Name : string
     * OClass_FatherID : 0
     * OClass_IsOK : 0
     * OClass_Order_OClass_ID : 0
     * OClass_Order : 0 //0不变 1前面 2后面
     * OClass_ID : 0
     */
    private int OClass_Flag;//1产品分类(4级) 2品牌(1级) 3单位(1级) 4多单位 5客户分类(1级) 6供应商分类(1级) 7财务支出分类(2级) 8财务收入分类(2级)必填
    private String OClass_Name;
    private int OClass_FatherID;
    private int OClass_IsOK;
    private int OClass_Order_OClass_ID;
    private int OClass_Order;
    private int OClass_ID;

    public int getOClass_Flag() {
        return OClass_Flag;
    }

    public void setOClass_Flag(int OClass_Flag) {
        this.OClass_Flag = OClass_Flag;
    }

    public String getOClass_Name() {
        return OClass_Name;
    }

    public void setOClass_Name(String OClass_Name) {
        this.OClass_Name = OClass_Name;
    }

    public int getOClass_FatherID() {
        return OClass_FatherID;
    }

    public void setOClass_FatherID(int OClass_FatherID) {
        this.OClass_FatherID = OClass_FatherID;
    }

    public int getOClass_IsOK() {
        return OClass_IsOK;
    }

    public void setOClass_IsOK(int OClass_IsOK) {
        this.OClass_IsOK = OClass_IsOK;
    }

    public int getOClass_Order_OClass_ID() {
        return OClass_Order_OClass_ID;
    }

    public void setOClass_Order_OClass_ID(int OClass_Order_OClass_ID) {
        this.OClass_Order_OClass_ID = OClass_Order_OClass_ID;
    }

    public int getOClass_Order() {
        return OClass_Order;
    }

    public void setOClass_Order(int OClass_Order) {
        this.OClass_Order = OClass_Order;
    }

    public int getOClass_ID() {
        return OClass_ID;
    }

    public void setOClass_ID(int OClass_ID) {
        this.OClass_ID = OClass_ID;
    }
}
