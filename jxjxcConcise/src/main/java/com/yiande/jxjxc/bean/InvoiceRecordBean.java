package com.yiande.jxjxc.bean;

import java.util.List;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/29 14:24
 */
public class InvoiceRecordBean {


    /**
     * OID : 38
     * No : 201216165154426
     * Type : 1
     * Amount : 1
     * PayableAmount : 1
     * RelayPayableAmount : 0
     * ComName : 古河建机
     * InvoiceState : 1
     * InvoiceRows : [{"ID":1,"Money":"500","Memo":"","IsDel":0,"DelWhy":"","Pic":"http://192.168.2.110:8098/file/invoice/202012/342bcad5d3a2473789b13e09b5e2a416.jpg","AddDate":"2020-12-29 14:23","AdminName":"Android"}]
     */

    private int OID;
    private String No;
    private int Type;//0 停止出票 1 出票中
    private String Amount;
    private String PayableAmount;
    private String RelayPayableAmount;
    private String ComName;
    private int InvoiceState;
    private List<InvoiceRowsBean> InvoiceRows;
    /**
     * InvoiceMoney : 0
     * InvoiceRows : []
     */

    private String InvoiceMoney;


    public int getOID() {
        return OID;
    }

    public void setOID(int OID) {
        this.OID = OID;
    }

    public String getNo() {
        return No;
    }

    public void setNo(String No) {
        this.No = No;
    }

    public int getType() {
        return Type;
    }

    public void setType(int Type) {
        this.Type = Type;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String Amount) {
        this.Amount = Amount;
    }

    public String getPayableAmount() {
        return PayableAmount;
    }

    public void setPayableAmount(String PayableAmount) {
        this.PayableAmount = PayableAmount;
    }

    public String getRelayPayableAmount() {
        return RelayPayableAmount;
    }

    public void setRelayPayableAmount(String RelayPayableAmount) {
        this.RelayPayableAmount = RelayPayableAmount;
    }

    public String getComName() {
        return ComName;
    }

    public void setComName(String ComName) {
        this.ComName = ComName;
    }

    public int getInvoiceState() {
        return InvoiceState;
    }

    public void setInvoiceState(int InvoiceState) {
        this.InvoiceState = InvoiceState;
    }

    public List<InvoiceRowsBean> getInvoiceRows() {
        return InvoiceRows;
    }

    public void setInvoiceRows(List<InvoiceRowsBean> InvoiceRows) {
        this.InvoiceRows = InvoiceRows;
    }


    public String getInvoiceMoney() {
        return InvoiceMoney;
    }

    public void setInvoiceMoney(String InvoiceMoney) {
        this.InvoiceMoney = InvoiceMoney;
    }


}
