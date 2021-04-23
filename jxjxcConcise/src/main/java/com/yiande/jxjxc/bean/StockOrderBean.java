package com.yiande.jxjxc.bean;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/9/19 10:18
 */
public class StockOrderBean {

    /**
     * Order_ID : 1
     * Order_No : 20091910017
     * Order_Amount : 10
     * Order_PayableAmount : 10
     * Order_Date : 2020-09-19T00:00:00
     * Order_AddDate : 2020-09-19T16:59:08
     * Order_Worker_ID_Name : null
     */

    private int Order_ID;
    private String Order_No;
    private String Order_Date;
    private String Order_AddDate;
    private String Product_IsBuyPrice;
    private String Order_Memo;
    private int Order_Type;
    /**
     * Order_State 状态1正常 0红冲
     * Order_Hong_Name 红冲人
     * Order_HongDate 红冲时间
     * Order_Hong_Memo 红冲原因
     */

    private int Order_State;
    private String Order_Hong_Name;
    private String Order_HongDate;
    private String Order_Hong_Memo;
    private String Order_AllAmount;// 实付款
    private String Order_FreightAmount;// 运费
    private String Order_PayableAmount;// 应付款
    private String Order_Amount;//合计
    private String Order_Debt;//欠款
    /**
     * Order_IsEdit : 0
     * Order_Balance :
     * Order_ComName : null
     * Order_Admin_Name : null
     * Order_Worker_Name : 张先生
     * Order_Hong_Name : null
     * Order_Hong_Memo : null
     */

    private int Order_IsEdit;
    private boolean IsInvoice;
    private String Order_Balance;
    private String Order_ComName;
    private String Order_Admin_Name;
    private String Order_Worker_Name;


    public int getOrder_ID() {
        return Order_ID;
    }

    public void setOrder_ID(int Order_ID) {
        this.Order_ID = Order_ID;
    }

    public String getOrder_No() {
        return Order_No;
    }

    public void setOrder_No(String Order_No) {
        this.Order_No = Order_No;
    }

    public String getOrder_Amount() {
        return Order_Amount;
    }

    public void setOrder_Amount(String Order_Amount) {
        this.Order_Amount = Order_Amount;
    }

    public String getOrder_PayableAmount() {
        return Order_PayableAmount;
    }

    public void setOrder_PayableAmount(String Order_PayableAmount) {
        this.Order_PayableAmount = Order_PayableAmount;
    }

    public String getOrder_Date() {
        return Order_Date;
    }

    public void setOrder_Date(String Order_Date) {
        this.Order_Date = Order_Date;
    }

    public String getOrder_AddDate() {
        return Order_AddDate;
    }

    public void setOrder_AddDate(String Order_AddDate) {
        this.Order_AddDate = Order_AddDate;
    }


    public int getOrder_Type() {
        return Order_Type;
    }

    public void setOrder_Type(int Order_Type) {
        this.Order_Type = Order_Type;
    }


    public String getOrder_FreightAmount() {
        return Order_FreightAmount;
    }

    public void setOrder_FreightAmount(String order_FreightAmount) {
        Order_FreightAmount = order_FreightAmount;
    }

    public String getProduct_IsBuyPrice() {
        return Product_IsBuyPrice;
    }

    public void setProduct_IsBuyPrice(String product_IsBuyPrice) {
        Product_IsBuyPrice = product_IsBuyPrice;
    }

    public int getOrder_State() {
        return Order_State;
    }

    public void setOrder_State(int Order_State) {
        this.Order_State = Order_State;
    }

    public String getOrder_Hong_Name() {
        return Order_Hong_Name;
    }

    public void setOrder_Hong_Name(String Order_Hong_Name) {
        this.Order_Hong_Name = Order_Hong_Name;
    }

    public String getOrder_HongDate() {
        return Order_HongDate;
    }

    public void setOrder_HongDate(String Order_HongDate) {
        this.Order_HongDate = Order_HongDate;
    }

    public String getOrder_Hong_Memo() {
        return Order_Hong_Memo;
    }

    public void setOrder_Hong_Memo(String Order_Hong_Memo) {
        this.Order_Hong_Memo = Order_Hong_Memo;
    }

    public String getOrder_Memo() {
        return Order_Memo;
    }

    public void setOrder_Memo(String order_Memo) {
        Order_Memo = order_Memo;
    }

    public String getOrder_AllAmount() {
        return Order_AllAmount;
    }

    public void setOrder_AllAmount(String order_AllAmount) {
        Order_AllAmount = order_AllAmount;
    }

    public String getOrder_Debt() {
        return Order_Debt;
    }

    public void setOrder_Debt(String order_Debt) {
        Order_Debt = order_Debt;
    }

    public int getOrder_IsEdit() {
        return Order_IsEdit;
    }

    public void setOrder_IsEdit(int Order_IsEdit) {
        this.Order_IsEdit = Order_IsEdit;
    }

    public String getOrder_Balance() {
        return Order_Balance;
    }

    public void setOrder_Balance(String Order_Balance) {
        this.Order_Balance = Order_Balance;
    }

    public String getOrder_ComName() {
        return Order_ComName;
    }

    public void setOrder_ComName(String Order_ComName) {
        this.Order_ComName = Order_ComName;
    }

    public String getOrder_Admin_Name() {
        return Order_Admin_Name;
    }

    public void setOrder_Admin_Name(String Order_Admin_Name) {
        this.Order_Admin_Name = Order_Admin_Name;
    }

    public String getOrder_Worker_Name() {
        return Order_Worker_Name;
    }

    public void setOrder_Worker_Name(String Order_Worker_Name) {
        this.Order_Worker_Name = Order_Worker_Name;
    }

    public boolean isInvoice() {
        return IsInvoice;
    }

    public void setInvoice(boolean invoice) {
        IsInvoice = invoice;
    }
}
