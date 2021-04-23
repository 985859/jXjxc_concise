package com.yiande.jxjxc.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/9/20 11:38
 */
public class OrderProAddBean implements Serializable, MultiItemEntity {


    /**
     * OrderDetail_IsEdit : 0
     * OrderDetail_EditMemo : 0
     * OrderDetail_EditDate : 2020-12-12 11:34
     * OrderDetail_Product_Title : JZM400A牵引
     * OrderDetail_Product_Model : JZM400A牵引
     * OrderDetail_Quantity : 5
     * OrderDetail_Edit_Quantity : 5
     * OrderDetail_Kg : 10
     * OrderDetail_Edit_Kg : 10
     * OrderDetail_KgAmount : 50
     * OrderDetail_Edit_KgAmount : 50
     * OrderDetail_Price : 45
     * OrderDetail_Edit_Price : 45
     * OrderDetail_Amount : 225
     * OrderDetail_Edit_Amount : 225
     * Product_No : 10039
     */

    private int OrderDetail_IsEdit;


    private String OrderDetail_EditMemo;
    private String OrderDetail_EditDate;
    private String OrderDetail_Product_Title;
    private String OrderDetail_Product_Model;
    private int OrderDetail_Quantity;
    private int OrderDetail_Edit_Quantity;
    private String OrderDetail_Kg;
    private String OrderDetail_Edit_Kg;
    private String OrderDetail_KgAmount;
    private String OrderDetail_Edit_KgAmount;
    private String OrderDetail_Price;
    private String OrderDetail_Edit_Price;
    private String OrderDetail_Amount;
    private String OrderDetail_Edit_Amount;
    private String Product_No;

    /**
     *
     */
    private int OrderDetail_Product_ID;
    private String Product_Price;
    private String Product_SellPrice;
    private String Product_Quantity;
    private String Product_IsBuyPrice;
    private String Product_BuyPrice;
    private String Product_NextPrice;
    private int Product_InventoryNum;


    public int getOrderDetail_IsEdit() {
        return OrderDetail_IsEdit;
    }

    public void setOrderDetail_IsEdit(int OrderDetail_IsEdit) {
        this.OrderDetail_IsEdit = OrderDetail_IsEdit;
    }

    public String getOrderDetail_EditMemo() {
        return OrderDetail_EditMemo;
    }

    public void setOrderDetail_EditMemo(String OrderDetail_EditMemo) {
        this.OrderDetail_EditMemo = OrderDetail_EditMemo;
    }

    public String getOrderDetail_EditDate() {
        return OrderDetail_EditDate;
    }

    public void setOrderDetail_EditDate(String OrderDetail_EditDate) {
        this.OrderDetail_EditDate = OrderDetail_EditDate;
    }

    public String getOrderDetail_Product_Title() {
        return OrderDetail_Product_Title;
    }

    public void setOrderDetail_Product_Title(String OrderDetail_Product_Title) {
        this.OrderDetail_Product_Title = OrderDetail_Product_Title;
    }

    public String getOrderDetail_Product_Model() {
        return OrderDetail_Product_Model;
    }

    public void setOrderDetail_Product_Model(String OrderDetail_Product_Model) {
        this.OrderDetail_Product_Model = OrderDetail_Product_Model;
    }

    public int getOrderDetail_Quantity() {
        return OrderDetail_Quantity;
    }

    public void setOrderDetail_Quantity(int OrderDetail_Quantity) {
        this.OrderDetail_Quantity = OrderDetail_Quantity;
    }

    public int getOrderDetail_Edit_Quantity() {
        return OrderDetail_Edit_Quantity;
    }

    public void setOrderDetail_Edit_Quantity(int OrderDetail_Edit_Quantity) {
        this.OrderDetail_Edit_Quantity = OrderDetail_Edit_Quantity;
    }

    public String getOrderDetail_Kg() {
        return OrderDetail_Kg;
    }

    public void setOrderDetail_Kg(String OrderDetail_Kg) {
        this.OrderDetail_Kg = OrderDetail_Kg;
    }

    public String getOrderDetail_Edit_Kg() {
        return OrderDetail_Edit_Kg;
    }

    public void setOrderDetail_Edit_Kg(String OrderDetail_Edit_Kg) {
        this.OrderDetail_Edit_Kg = OrderDetail_Edit_Kg;
    }

    public String getOrderDetail_KgAmount() {
        return OrderDetail_KgAmount;
    }

    public void setOrderDetail_KgAmount(String OrderDetail_KgAmount) {
        this.OrderDetail_KgAmount = OrderDetail_KgAmount;
    }

    public String getOrderDetail_Edit_KgAmount() {
        return OrderDetail_Edit_KgAmount;
    }

    public void setOrderDetail_Edit_KgAmount(String OrderDetail_Edit_KgAmount) {
        this.OrderDetail_Edit_KgAmount = OrderDetail_Edit_KgAmount;
    }

    public String getOrderDetail_Price() {
        return OrderDetail_Price;
    }

    public void setOrderDetail_Price(String OrderDetail_Price) {
        this.OrderDetail_Price = OrderDetail_Price;
    }

    public String getOrderDetail_Edit_Price() {
        return OrderDetail_Edit_Price;
    }

    public void setOrderDetail_Edit_Price(String OrderDetail_Edit_Price) {
        this.OrderDetail_Edit_Price = OrderDetail_Edit_Price;
    }

    public String getOrderDetail_Amount() {
        return OrderDetail_Amount;
    }

    public void setOrderDetail_Amount(String OrderDetail_Amount) {
        this.OrderDetail_Amount = OrderDetail_Amount;
    }

    public String getOrderDetail_Edit_Amount() {
        return OrderDetail_Edit_Amount;
    }

    public void setOrderDetail_Edit_Amount(String OrderDetail_Edit_Amount) {
        this.OrderDetail_Edit_Amount = OrderDetail_Edit_Amount;
    }


    public int getProduct_ID() {
        return OrderDetail_Product_ID;
    }

    public void setProduct_ID(int orderDetail_Product_ID) {
        OrderDetail_Product_ID = orderDetail_Product_ID;
    }

    public String getProduct_No() {
        return Product_No;
    }

    public void setProduct_No(String Product_No) {
        this.Product_No = Product_No;
    }

    public String getProduct_Price() {
        return Product_Price;
    }

    public void setProduct_Price(String product_Price) {
        Product_Price = product_Price;
    }

    public String getProduct_Quantity() {
        return Product_Quantity;
    }

    public void setProduct_Quantity(String product_Quantity) {
        Product_Quantity = product_Quantity;
    }

    public int getProduct_InventoryNum() {
        return Product_InventoryNum;
    }

    public void setProduct_InventoryNum(int product_InventoryNum) {
        Product_InventoryNum = product_InventoryNum;
    }

    public String getProduct_IsBuyPrice() {
        return Product_IsBuyPrice;
    }

    public void setProduct_IsBuyPrice(String product_IsBuyPrice) {
        Product_IsBuyPrice = product_IsBuyPrice;
    }

    public String getProduct_BuyPrice() {
        return Product_BuyPrice;
    }

    public void setProduct_BuyPrice(String product_BuyPrice) {
        Product_BuyPrice = product_BuyPrice;
    }

    public String getProduct_SellPrice() {
        return Product_SellPrice;
    }

    public void setProduct_SellPrice(String product_SellPrice) {
        Product_SellPrice = product_SellPrice;
    }

    public String getProduct_NextPrice() {
        return Product_NextPrice;
    }

    public void setProduct_NextPrice(String product_NextPrice) {
        Product_NextPrice = product_NextPrice;
    }



    @Override
    public int getItemType() {
        return OrderDetail_IsEdit == 0 ? 0 : 1;
    }
}

