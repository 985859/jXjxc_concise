package com.yiande.jxjxc.bean;

import java.util.List;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/24 9:29
 */
public class AfterServiceInfoBean {


    /**
     * ID : 1
     * ComName : 阿尔法
     * AdminName : 易安德客服1
     * Begin : 2020/12/22 - 2020/12/23
     * Other : 500
     * Charge : 2000
     * ProductMoney : 1000.0
     * Money : 1500
     * Profit : 500
     * PicList : ["http://192.168.2.110:8098/file/service/202012/0b0058e0c0d04562b306b3aba97d5a62.jpg","http://192.168.2.110:8098/file/service/202012/bf1422a1a2bb422ea01ac0789a9d8079.jpg","http://192.168.2.110:8098/file/service/202012/bc0cc67733bf4ff58787295e8f312073.jpg"]
     * Content : null
     * ServiceProduct : [{"Title":"JZM500B 料斗总成 配件","Model":"JZM500B料斗","Money":"1000","Quantity":1,"Supplier":"","Text":"需要更换启动器"}]
     */

    private int ID;
    private String ComName;
    private String AdminName;
    private String Begin;
    private String Other;
    private String Charge;
    private String ProductMoney;
    private String Money;
    private String Profit;
    private String Content;
    private List<String> PicList;
    private List<AfterServiceProductBean> ServiceProduct;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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

    public String getBegin() {
        return Begin;
    }

    public void setBegin(String Begin) {
        this.Begin = Begin;
    }

    public String getOther() {
        return Other;
    }

    public void setOther(String Other) {
        this.Other = Other;
    }

    public String getCharge() {
        return Charge;
    }

    public void setCharge(String Charge) {
        this.Charge = Charge;
    }

    public String getProductMoney() {
        return ProductMoney;
    }

    public void setProductMoney(String ProductMoney) {
        this.ProductMoney = ProductMoney;
    }

    public String getMoney() {
        return Money;
    }

    public void setMoney(String Money) {
        this.Money = Money;
    }

    public String getProfit() {
        return Profit;
    }

    public void setProfit(String Profit) {
        this.Profit = Profit;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public List<String> getPicList() {
        return PicList;
    }

    public void setPicList(List<String> PicList) {
        this.PicList = PicList;
    }

    public List<AfterServiceProductBean> getServiceProduct() {
        return ServiceProduct;
    }

    public void setServiceProduct(List<AfterServiceProductBean> ServiceProduct) {
        this.ServiceProduct = ServiceProduct;
    }


}
