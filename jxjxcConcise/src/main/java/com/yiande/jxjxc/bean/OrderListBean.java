package com.yiande.jxjxc.bean;

import java.util.List;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/11/14 16:50
 */
public class OrderListBean {

    /**
     * Amount : 1,480,457.00
     * PayableAmount : 1,535,700.00
     * RealyAmount : 1,395,430.00
     * ProfitAmount :
     * FreightAmount : 21,441.00
     * DebtAmount : 140,270.00
     * OrderList : [{"Order_ID":2,"Order_No":"201209155616604","Order_Type":1,"Order_Amount":"500000","Order_PayableAmount":"500000","Order_AllAmount":"500000","Order_Debt":"0","Order_FreightAmount":"20000","Order_IsEdit":0,"Order_Balance":"","Order_AddDate":"2020-12-09 15:56","Order_ComName":null,"Order_Admin_Name":null,"Order_Worker_Name":"张先生","Order_Memo":"","Order_State":1,"Order_Hong_Name":null,"Order_Hong_Memo":null,"Order_HongDate":""},{"Order_ID":4,"Order_No":"201209161436654","Order_Type":1,"Order_Amount":"250000","Order_PayableAmount":"250000","Order_AllAmount":"200000","Order_Debt":"50000","Order_FreightAmount":"0","Order_IsEdit":0,"Order_Balance":"","Order_AddDate":"2020-12-09 16:14","Order_ComName":null,"Order_Admin_Name":null,"Order_Worker_Name":"张先生","Order_Memo":"","Order_State":1,"Order_Hong_Name":null,"Order_Hong_Memo":null,"Order_HongDate":""},{"Order_ID":13,"Order_No":"20121016562072","Order_Type":1,"Order_Amount":"162500","Order_PayableAmount":"162500","Order_AllAmount":"152500","Order_Debt":"10000","Order_FreightAmount":"500","Order_IsEdit":0,"Order_Balance":"","Order_AddDate":"2020-12-10 16:56","Order_ComName":null,"Order_Admin_Name":null,"Order_Worker_Name":"易安德客服1","Order_Memo":"","Order_State":1,"Order_Hong_Name":null,"Order_Hong_Memo":null,"Order_HongDate":""},{"Order_ID":14,"Order_No":"20121016562072","Order_Type":1,"Order_Amount":"162500","Order_PayableAmount":"162500","Order_AllAmount":"152500","Order_Debt":"10000","Order_FreightAmount":"500","Order_IsEdit":0,"Order_Balance":"","Order_AddDate":"2020-12-10 16:56","Order_ComName":null,"Order_Admin_Name":null,"Order_Worker_Name":"易安德客服1","Order_Memo":"","Order_State":1,"Order_Hong_Name":null,"Order_Hong_Memo":null,"Order_HongDate":""},{"Order_ID":16,"Order_No":"20121108442785","Order_Type":1,"Order_Amount":"142475","Order_PayableAmount":"142475","Order_AllAmount":"122475","Order_Debt":"20000","Order_FreightAmount":"0","Order_IsEdit":0,"Order_Balance":"","Order_AddDate":"2020-12-11 08:44","Order_ComName":null,"Order_Admin_Name":null,"Order_Worker_Name":"易安德客服1","Order_Memo":"","Order_State":1,"Order_Hong_Name":null,"Order_Hong_Memo":null,"Order_HongDate":""},{"Order_ID":17,"Order_No":"201211091024174","Order_Type":1,"Order_Amount":"200000","Order_PayableAmount":"200000","Order_AllAmount":"150000","Order_Debt":"50000","Order_FreightAmount":"0","Order_IsEdit":0,"Order_Balance":"","Order_AddDate":"2020-12-11 09:10","Order_ComName":null,"Order_Admin_Name":null,"Order_Worker_Name":"易安德客服1","Order_Memo":"","Order_State":1,"Order_Hong_Name":null,"Order_Hong_Memo":null,"Order_HongDate":""},{"Order_ID":19,"Order_No":"201212090820411","Order_Type":1,"Order_Amount":"45","Order_PayableAmount":"45","Order_AllAmount":"45","Order_Debt":"0","Order_FreightAmount":"1","Order_IsEdit":0,"Order_Balance":"","Order_AddDate":"2020-12-12 09:08","Order_ComName":"","Order_Admin_Name":"Android","Order_Worker_Name":"Android","Order_Memo":"这是入库备注","Order_State":1,"Order_Hong_Name":null,"Order_Hong_Memo":null,"Order_HongDate":""},{"Order_ID":20,"Order_No":"201212091317924","Order_Type":1,"Order_Amount":"5000","Order_PayableAmount":"5000","Order_AllAmount":"5000","Order_Debt":"0","Order_FreightAmount":"100","Order_IsEdit":0,"Order_Balance":"","Order_AddDate":"2020-12-12 09:13","Order_ComName":"","Order_Admin_Name":"Android","Order_Worker_Name":"Android","Order_Memo":"","Order_State":1,"Order_Hong_Name":null,"Order_Hong_Memo":null,"Order_HongDate":""},{"Order_ID":23,"Order_No":"201212113411868","Order_Type":1,"Order_Amount":"7725","Order_PayableAmount":"7720","Order_AllAmount":"7720","Order_Debt":"0","Order_FreightAmount":"40","Order_IsEdit":0,"Order_Balance":"","Order_AddDate":"2020-12-12 11:34","Order_ComName":"古河建机","Order_Admin_Name":"Android","Order_Worker_ 2020-12-12 16:04:11.393 3187-3229/com.yiande.jxjxc I/okhttp.OkHttpClient: o":"不欠帐","Order_State":1,"Order_Hong_Name":null,"Order_Hong_Memo":null,"Order_HongDate":""},{"Order_ID":24,"Order_No":"201212143056302","Order_Type":1,"Order_Amount":"100","Order_PayableAmount":"300","Order_AllAmount":"30","Order_Debt":"270","Order_FreightAmount":"0","Order_IsEdit":0,"Order_Balance":"","Order_AddDate":"2020-12-12 14:30","Order_ComName":"古河建机","Order_Admin_Name":"IOS","Order_Worker_Name":"IOS","Order_Memo":"","Order_State":1,"Order_Hong_Name":null,"Order_Hong_Memo":null,"Order_HongDate":""}]
     */

    private String Amount;
    private String PayableAmount;
    private String RealyAmount;
    private String ProfitAmount;
    private String FreightAmount;
    private String DebtAmount;
    private List<StockOrderBean> OrderList;

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getPayableAmount() {
        return PayableAmount;
    }

    public void setPayableAmount(String payableAmount) {
        PayableAmount = payableAmount;
    }

    public String getRealyAmount() {
        return RealyAmount;
    }

    public void setRealyAmount(String realyAmount) {
        RealyAmount = realyAmount;
    }

    public String getProfitAmount() {
        return ProfitAmount;
    }

    public void setProfitAmount(String profitAmount) {
        ProfitAmount = profitAmount;
    }

    public String getFreightAmount() {
        return FreightAmount;
    }

    public void setFreightAmount(String freightAmount) {
        FreightAmount = freightAmount;
    }

    public String getDebtAmount() {
        return DebtAmount;
    }

    public void setDebtAmount(String debtAmount) {
        DebtAmount = debtAmount;
    }

    public List<StockOrderBean> getOrderList() {
        return OrderList;
    }

    public void setOrderList(List<StockOrderBean> orderList) {
        OrderList = orderList;
    }


    //    public static class OrderListBean {
//        /**
//         * Order_ID : 2
//         * Order_No : 201209155616604
//         * Order_Type : 1
//         * Order_Amount : 500000
//         * Order_PayableAmount : 500000
//         * Order_AllAmount : 500000
//         * Order_Debt : 0
//         * Order_FreightAmount : 20000
//         * Order_IsEdit : 0
//         * Order_Balance :
//         * Order_AddDate : 2020-12-09 15:56
//         * Order_ComName : null
//         * Order_Admin_Name : null
//         * Order_Worker_Name : 张先生
//         * Order_Memo :
//         * Order_State : 1
//         * Order_Hong_Name : null
//         * Order_Hong_Memo : null
//         * Order_HongDate :
//         * Order_Worker_ 2020-12-12 16:04:11.393 3187-3229/com.yiande.jxjxc I/okhttp.OkHttpClient: o : 不欠帐
//         */
//
//        private int Order_ID;
//        private String Order_No;
//        private int Order_Type;
//        private String Order_Amount;
//        private String Order_PayableAmount;
//        private String Order_AllAmount;
//        private String Order_Debt;
//        private String Order_FreightAmount;
//        private int Order_IsEdit;
//        private String Order_Balance;
//        private String Order_AddDate;
//        private Object Order_ComName;
//        private Object Order_Admin_Name;
//        private String Order_Worker_Name;
//        private String Order_Memo;
//        private int Order_State;
//        private Object Order_Hong_Name;
//        private Object Order_Hong_Memo;
//        private String Order_HongDate;
//        @SerializedName("Order_Worker_ 2020-12-12 16:04:11.393 3187-3229/com.yiande.jxjxc I/okhttp.OkHttpClient: o")
//        private String _$Order_Worker_2020121216041139331873229ComYiandejxjxcIOkhttpOkHttpClientO232; // FIXME check this code
//
//        public int getOrder_ID() {
//            return Order_ID;
//        }
//
//        public void setOrder_ID(int Order_ID) {
//            this.Order_ID = Order_ID;
//        }
//
//        public String getOrder_No() {
//            return Order_No;
//        }
//
//        public void setOrder_No(String Order_No) {
//            this.Order_No = Order_No;
//        }
//
//        public int getOrder_Type() {
//            return Order_Type;
//        }
//
//        public void setOrder_Type(int Order_Type) {
//            this.Order_Type = Order_Type;
//        }
//
//        public String getOrder_Amount() {
//            return Order_Amount;
//        }
//
//        public void setOrder_Amount(String Order_Amount) {
//            this.Order_Amount = Order_Amount;
//        }
//
//        public String getOrder_PayableAmount() {
//            return Order_PayableAmount;
//        }
//
//        public void setOrder_PayableAmount(String Order_PayableAmount) {
//            this.Order_PayableAmount = Order_PayableAmount;
//        }
//
//        public String getOrder_AllAmount() {
//            return Order_AllAmount;
//        }
//
//        public void setOrder_AllAmount(String Order_AllAmount) {
//            this.Order_AllAmount = Order_AllAmount;
//        }
//
//        public String getOrder_Debt() {
//            return Order_Debt;
//        }
//
//        public void setOrder_Debt(String Order_Debt) {
//            this.Order_Debt = Order_Debt;
//        }
//
//        public String getOrder_FreightAmount() {
//            return Order_FreightAmount;
//        }
//
//        public void setOrder_FreightAmount(String Order_FreightAmount) {
//            this.Order_FreightAmount = Order_FreightAmount;
//        }
//
//        public int getOrder_IsEdit() {
//            return Order_IsEdit;
//        }
//
//        public void setOrder_IsEdit(int Order_IsEdit) {
//            this.Order_IsEdit = Order_IsEdit;
//        }
//
//        public String getOrder_Balance() {
//            return Order_Balance;
//        }
//
//        public void setOrder_Balance(String Order_Balance) {
//            this.Order_Balance = Order_Balance;
//        }
//
//        public String getOrder_AddDate() {
//            return Order_AddDate;
//        }
//
//        public void setOrder_AddDate(String Order_AddDate) {
//            this.Order_AddDate = Order_AddDate;
//        }
//
//        public Object getOrder_ComName() {
//            return Order_ComName;
//        }
//
//        public void setOrder_ComName(Object Order_ComName) {
//            this.Order_ComName = Order_ComName;
//        }
//
//        public Object getOrder_Admin_Name() {
//            return Order_Admin_Name;
//        }
//
//        public void setOrder_Admin_Name(Object Order_Admin_Name) {
//            this.Order_Admin_Name = Order_Admin_Name;
//        }
//
//        public String getOrder_Worker_Name() {
//            return Order_Worker_Name;
//        }
//
//        public void setOrder_Worker_Name(String Order_Worker_Name) {
//            this.Order_Worker_Name = Order_Worker_Name;
//        }
//
//        public String getOrder_Memo() {
//            return Order_Memo;
//        }
//
//        public void setOrder_Memo(String Order_Memo) {
//            this.Order_Memo = Order_Memo;
//        }
//
//        public int getOrder_State() {
//            return Order_State;
//        }
//
//        public void setOrder_State(int Order_State) {
//            this.Order_State = Order_State;
//        }
//
//        public Object getOrder_Hong_Name() {
//            return Order_Hong_Name;
//        }
//
//        public void setOrder_Hong_Name(Object Order_Hong_Name) {
//            this.Order_Hong_Name = Order_Hong_Name;
//        }
//
//        public Object getOrder_Hong_Memo() {
//            return Order_Hong_Memo;
//        }
//
//        public void setOrder_Hong_Memo(Object Order_Hong_Memo) {
//            this.Order_Hong_Memo = Order_Hong_Memo;
//        }
//
//        public String getOrder_HongDate() {
//            return Order_HongDate;
//        }
//
//        public void setOrder_HongDate(String Order_HongDate) {
//            this.Order_HongDate = Order_HongDate;
//        }
//
//        public String get_$Order_Worker_2020121216041139331873229ComYiandejxjxcIOkhttpOkHttpClientO232() {
//            return _$Order_Worker_2020121216041139331873229ComYiandejxjxcIOkhttpOkHttpClientO232;
//        }
//
//        public void set_$Order_Worker_2020121216041139331873229ComYiandejxjxcIOkhttpOkHttpClientO232(String _$Order_Worker_2020121216041139331873229ComYiandejxjxcIOkhttpOkHttpClientO232) {
//            this._$Order_Worker_2020121216041139331873229ComYiandejxjxcIOkhttpOkHttpClientO232 = _$Order_Worker_2020121216041139331873229ComYiandejxjxcIOkhttpOkHttpClientO232;
//        }
//    }
}
