package com.yiande.jxjxc.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/23 8:59
 */
public class AfterServiceProductBean implements Serializable {
    private static final long serialVersionUID = -3472769852034603272L;
    /**
     * ID : 35
     * Pic : 202012/2e9b525a56694e7385efe5cf16992d6e.jpg
     * Title : JZM500B 料斗总成 配件
     * Model : JZM500B料斗
     * Suppliers : [{"User_ID":2,"User_ComName":"古河建机"},{"User_ID":7,"User_ComName":"豫通建筑机械有限公司"},{"User_ID":8,"User_ComName":"上海天宇建筑机械"}]
     */

    private int ID;
    private String Pic;
    private String PicUrl;
    private String Title;
    private String Model;
    private List<UserBean> Suppliers;
    
    private String Money;
    private String Text;
    private String Quantity;

    private int AID;//供应商ID
    /**
     * Quantity : 1
     * Supplier : 供应商-1
     */

    private String Supplier;


    /**
     * Pic : 202012/2e9b525a56694e7385efe5cf16992d6e.jpg
     * Title : JZM500B 料斗总成 配件
     * Model : JZM500B料斗
     * Suppliers : [{"User_ID":2,"User_ComName":"古河建机"},{"User_ID":7,"User_ComName":"豫通建筑机械有限公司"},{"User_ID":8,"User_ComName":"上海天宇建筑机械"}]
     */
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPic() {
        return Pic;
    }

    public void setPic(String Pic) {
        this.Pic = Pic;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String Model) {
        this.Model = Model;
    }

    public List<UserBean> getSuppliers() {
        return Suppliers;
    }

    public void setSuppliers(List<UserBean> Suppliers) {
        this.Suppliers = Suppliers;
    }

    public String getMoney() {
        return Money;
    }

    public void setMoney(String money) {
        Money = money;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public int getAID() {
        return AID;
    }

    public void setAID(int AID) {
        this.AID = AID;
    }

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }


    public String getSupplier() {
        return Supplier;
    }

    public void setSupplier(String Supplier) {
        this.Supplier = Supplier;
    }
}
