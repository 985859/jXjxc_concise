package com.yiande.jxjxc.bean;

import java.util.List;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/9/19 21:35
 */
public class OrderBody {


    /**
     * Order_Type : 0
     * Order_PayableAmount : 0
     * Order_User_ID : 0
     * Order_OriginalNo : string
     * Order_OriginalPic : string
     * Order_Memo : string
     * Order_Worker_ID : 0
     * Order_Date : 2020-09-20T07:10:15.726Z
     * Order_Cart_No : string
     * Order_Detail : [{"OrderDetail_Product_ID":0,"OrderDetail_Price":0,"OrderDetail_Quantity":0,"OrderDetail_Amount":0}]
     * Order_Name : string
     * Order_Mob : string
     * Order_Address : string
     */

    private int Order_Type;


    private int Order_User_ID;
    private int Order_Invoice_State;
    private String Order_User_Name;
    private String Order_OriginalNo;
    private String Order_OriginalPic;
    private String Order_Memo;

    private int Order_Worker_ID;
    private String Order_Worker_Name;
    private int Order_Admin_ID;
    private String Order_Admin_Name;
    private String Order_Date;
    private String Order_Name;
    private String Order_Mob;
    private String Order_Address;

    private List<OrderProAddBean> Order_Detail;
    private String Order_AllAmount;// 实付款
    private String Order_FreightAmount;// 运费
    private String Order_PayableAmount;// 应付款
    private String Order_Amount;//合计
    private String Order_Debt;//欠款
    private String Order_Cash;
    private String Order_Bank;
    private String Order_Balance;
    private String Order_Alipay;
    private String Order_Wechat;

    public int getOrder_Type() {
        return Order_Type;
    }

    public void setOrder_Type(int Order_Type) {
        this.Order_Type = Order_Type;
    }

    public String getOrder_PayableAmount() {
        return Order_PayableAmount;
    }

    public void setOrder_PayableAmount(String Order_PayableAmount) {
        this.Order_PayableAmount = Order_PayableAmount;
    }

    public int getOrder_User_ID() {
        return Order_User_ID;
    }

    public void setOrder_User_ID(int Order_User_ID) {
        this.Order_User_ID = Order_User_ID;
    }

    public String getOrder_OriginalNo() {
        return Order_OriginalNo;
    }

    public void setOrder_OriginalNo(String Order_OriginalNo) {
        this.Order_OriginalNo = Order_OriginalNo;
    }

    public String getOrder_OriginalPic() {
        return Order_OriginalPic;
    }

    public void setOrder_OriginalPic(String Order_OriginalPic) {
        this.Order_OriginalPic = Order_OriginalPic;
    }

    public String getOrder_Memo() {
        return Order_Memo;
    }

    public void setOrder_Memo(String Order_Memo) {
        this.Order_Memo = Order_Memo;
    }

    public int getOrder_Worker_ID() {
        return Order_Worker_ID;
    }

    public void setOrder_Worker_ID(int Order_Worker_ID) {
        this.Order_Worker_ID = Order_Worker_ID;
    }

    public String getOrder_Date() {
        return Order_Date;
    }

    public void setOrder_Date(String Order_Date) {
        this.Order_Date = Order_Date;
    }


    public List<OrderProAddBean> getOrder_Detail() {
        return Order_Detail;
    }

    public void setOrder_Detail(List<OrderProAddBean> Order_Detail) {
        this.Order_Detail = Order_Detail;
    }


    public String getOrder_Name() {
        return Order_Name;
    }

    public void setOrder_Name(String Order_Name) {
        this.Order_Name = Order_Name;
    }

    public String getOrder_Mob() {
        return Order_Mob;
    }

    public void setOrder_Mob(String Order_Mob) {
        this.Order_Mob = Order_Mob;
    }

    public String getOrder_Address() {
        return Order_Address;
    }

    public void setOrder_Address(String Order_Address) {
        this.Order_Address = Order_Address;
    }

    public String getOrder_FreightAmount() {
        return Order_FreightAmount;
    }

    public void setOrder_FreightAmount(String order_FreightAmount) {
        Order_FreightAmount = order_FreightAmount;
    }

    public String getOrder_Cash() {
        return Order_Cash;
    }

    public void setOrder_Cash(String order_Cash) {
        Order_Cash = order_Cash;
    }

    public String getOrder_Bank() {
        return Order_Bank;
    }

    public void setOrder_Bank(String order_Bank) {
        Order_Bank = order_Bank;
    }

    public String getOrder_Alipay() {
        return Order_Alipay;
    }

    public void setOrder_Alipay(String order_Alipay) {
        Order_Alipay = order_Alipay;
    }

    public String getOrder_Wechat() {
        return Order_Wechat;
    }

    public void setOrder_Wechat(String order_Wechat) {
        Order_Wechat = order_Wechat;
    }

    public String getOrder_Debt() {
        return Order_Debt;
    }

    public void setOrder_Debt(String order_Debt) {
        Order_Debt = order_Debt;
    }

    public String getOrder_AllAmount() {
        return Order_AllAmount;
    }

    public void setOrder_AllAmount(String order_AllAmount) {
        Order_AllAmount = order_AllAmount;
    }

    public String getOrder_Amount() {
        return Order_Amount;
    }

    public void setOrder_Amount(String order_Amount) {
        Order_Amount = order_Amount;
    }

    public String getOrder_User_Name() {
        return Order_User_Name;
    }

    public void setOrder_User_Name(String order_User_Name) {
        Order_User_Name = order_User_Name;
    }

    public String getOrder_Worker_Name() {
        return Order_Worker_Name;
    }

    public void setOrder_Worker_Name(String order_Worker_Name) {
        Order_Worker_Name = order_Worker_Name;
    }

    public int getOrder_Admin_ID() {
        return Order_Admin_ID;
    }

    public void setOrder_Admin_ID(int order_Admin_ID) {
        Order_Admin_ID = order_Admin_ID;
    }

    public String getOrder_Admin_Name() {
        return Order_Admin_Name;
    }

    public void setOrder_Admin_Name(String order_Admin_Name) {
        Order_Admin_Name = order_Admin_Name;
    }

    public String getOrder_Balance() {
        return Order_Balance;
    }

    public void setOrder_Balance(String order_Balance) {
        Order_Balance = order_Balance;
    }

    public int getOrder_Invoice_State() {
        return Order_Invoice_State;
    }

    public void setOrder_Invoice_State(int order_Invoice_State) {
        Order_Invoice_State = order_Invoice_State;
    }
}
