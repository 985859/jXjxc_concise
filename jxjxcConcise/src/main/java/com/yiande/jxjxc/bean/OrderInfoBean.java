package com.yiande.jxjxc.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.yiande.jxjxc.BR;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/12 13:49
 */
public class OrderInfoBean extends BaseObservable implements Serializable {

    private static final long serialVersionUID = 272423043109075863L;
    /**
     * Order_ID : 23
     * Order_No : 201212113411868
     * Order_AddDate : 2020-12-12 11:34
     * Order_Type : 1
     * Order_ComName : 古河建机
     * Order_Name : null
     * Order_Mob : null
     * Order_Address : null
     * Order_Worker_Name : Android
     * Order_Amount : 7725
     * Order_KgAmount : 186.8
     * Order_FreightAmount : 40
     * Order_PayableAmount : 7720
     * Order_RealyAmount : 7720
     * Order_Balance :
     * User_Balance :
     * Order_Cash : 720
     * Order_Bank : 3000
     * Order_Alipay : 1500
     * Order_Wechat : 2500
     * Order_Debt : 0
     * User_AllDebt : 55000
     * Order_Memo : 不欠帐
     * Order_State : 1
     * Order_Hong_Memo : null
     * Order_Hong_Name : null
     * Order_HongDate :
     * ProductList : [{"OrderDetail_IsEdit":0,"OrderDetail_EditMemo":"0","OrderDetail_EditDate":"2020-12-12 11:34","OrderDetail_Product_Title":"JZM400A牵引","OrderDetail_Product_Model":"JZM400A牵引","OrderDetail_Quantity":5,"OrderDetail_Edit_Quantity":5,"OrderDetail_Kg":"10","OrderDetail_Edit_Kg":"10","OrderDetail_KgAmount":"50","OrderDetail_Edit_KgAmount":"50","OrderDetail_Price":"45","OrderDetail_Edit_Price":"45","OrderDetail_Amount":"225","OrderDetail_Edit_Amount":"225"},{"OrderDetail_IsEdit":0,"OrderDetail_EditMemo":"0","OrderDetail_EditDate":"2020-12-12 11:34","OrderDetail_Product_Title":"JZM500B 料斗总成 配件","OrderDetail_Product_Model":"JZM500B料斗","OrderDetail_Quantity":3,"OrderDetail_Edit_Quantity":3,"OrderDetail_Kg":"45.6","OrderDetail_Edit_Kg":"45.6","OrderDetail_KgAmount":"136.8","OrderDetail_Edit_KgAmount":"136.8","OrderDetail_Price":"2500","OrderDetail_Edit_Price":"2500","OrderDetail_Amount":"7500","OrderDetail_Edit_Amount":"7500"}]
     */

    private int Order_ID;
    private int Order_State;
    private int Order_Type;
    private String Order_No;
    private String Order_AddDate;
    private String Order_ComName;
    private String Order_Name;
    private String Order_Mob;
    private String Order_Address;
    private String Order_Worker_Name;
    private String Order_Amount;
    private String Order_KgAmount;
    private String Order_FreightAmount;
    private String Order_PayableAmount;
    private String Order_RealyAmount;
    private String Order_Balance;
    private String User_Balance;
    private String Order_Cash;
    private String Order_Bank;
    private String Order_Alipay;
    private String Order_Wechat;
    private String Order_Debt;
    private String User_AllDebt;
    private String Order_Memo;
    private String Order_Hong_Memo;
    private String Order_Hong_Name;
    private String Order_HongDate;


    private String Order_OriginalNo;
    private String Order_OriginalPic;
    private String Order_Date;
    private String Order_Type_Name;
    private String Order_Admin_Name;
    private String PrintMemo;
    private String Order_PayableAmount_Daxie;

    private boolean Order_IsHong;
    private int IsKg;
    private int IsPrintKg;
    private String Order_IsNo;
    /**
     * Order_Name : null
     * Order_Mob : null
     * Order_Address : null
     * Order_Hong_Memo : null
     * Order_Hong_Name : null
     * Order_OriginalNo : null
     * Order_IsNo : 1
     * Order_Detail : [{"OrderDetail_IsEdit":0,"OrderDetail_EditMemo":"0","OrderDetail_EditDate":"2020-12-12 14:30","OrderDetail_Product_Title":" 搅拌机搅拌机中","OrderDetail_Product_Model":"搅拌机po","OrderDetail_Quantity":1,"OrderDetail_Edit_Quantity":1,"OrderDetail_Kg":"30","OrderDetail_Edit_Kg":"30","OrderDetail_KgAmount":"30","OrderDetail_Edit_KgAmount":"30","OrderDetail_Price":"100","OrderDetail_Edit_Price":"100","OrderDetail_Amount":"100","OrderDetail_Edit_Amount":"100"}]
     */


    private List<OrderProAddBean> Order_Detail;
    /**
     * Order_Invoice_State : 0
     * Order_RealyAmount_Daxie : (大写：零元整)
     * Order_Debt_Daxie : (大写：玖佰元整)
     * User_AllDebt_Daxie : (大写：玖佰元整)
     * Order_IsEdit : 0
     * IsPrintDebt : 1
     * IsPrintDebtAll : 1
     * Order_IsNo : 1
     */

    private int Order_Invoice_State;
    private String Order_RealyAmount_Daxie;
    private String Order_Debt_Daxie;
    private String User_AllDebt_Daxie;
    private int Order_IsEdit;
    private int IsPrintDebt;
    private int IsPrintDebtAll;


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

    public String getOrder_ComName() {
        return Order_ComName;
    }

    public void setOrder_ComName(String Order_ComName) {
        this.Order_ComName = Order_ComName;
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

    public String getOrder_Worker_Name() {
        return Order_Worker_Name;
    }

    public void setOrder_Worker_Name(String Order_Worker_Name) {
        this.Order_Worker_Name = Order_Worker_Name;
    }

    public String getOrder_Amount() {
        return Order_Amount;
    }

    public void setOrder_Amount(String Order_Amount) {
        this.Order_Amount = Order_Amount;
    }

    public String getOrder_KgAmount() {
        return Order_KgAmount;
    }

    public void setOrder_KgAmount(String Order_KgAmount) {
        this.Order_KgAmount = Order_KgAmount;
    }

    public String getOrder_FreightAmount() {
        return Order_FreightAmount;
    }

    public void setOrder_FreightAmount(String Order_FreightAmount) {
        this.Order_FreightAmount = Order_FreightAmount;
    }

    public String getOrder_PayableAmount() {
        return Order_PayableAmount;
    }

    public void setOrder_PayableAmount(String Order_PayableAmount) {
        this.Order_PayableAmount = Order_PayableAmount;
    }

    public String getOrder_RealyAmount() {
        return Order_RealyAmount;
    }

    public void setOrder_RealyAmount(String Order_RealyAmount) {
        this.Order_RealyAmount = Order_RealyAmount;
    }

    public String getOrder_Balance() {
        return Order_Balance;
    }

    public void setOrder_Balance(String Order_Balance) {
        this.Order_Balance = Order_Balance;
    }

    public String getUser_Balance() {
        return User_Balance;
    }

    public void setUser_Balance(String User_Balance) {
        this.User_Balance = User_Balance;
    }

    public String getOrder_Cash() {
        return Order_Cash;
    }

    public void setOrder_Cash(String Order_Cash) {
        this.Order_Cash = Order_Cash;
    }

    public String getOrder_Bank() {
        return Order_Bank;
    }

    public void setOrder_Bank(String Order_Bank) {
        this.Order_Bank = Order_Bank;
    }

    public String getOrder_Alipay() {
        return Order_Alipay;
    }

    public void setOrder_Alipay(String Order_Alipay) {
        this.Order_Alipay = Order_Alipay;
    }

    public String getOrder_Wechat() {
        return Order_Wechat;
    }

    public void setOrder_Wechat(String Order_Wechat) {
        this.Order_Wechat = Order_Wechat;
    }

    public String getOrder_Debt() {
        return Order_Debt;
    }

    public void setOrder_Debt(String Order_Debt) {
        this.Order_Debt = Order_Debt;
    }

    public String getUser_AllDebt() {
        return User_AllDebt;
    }

    public void setUser_AllDebt(String User_AllDebt) {
        this.User_AllDebt = User_AllDebt;
    }

    @Bindable
    public String getOrder_Memo() {
        return Order_Memo;
    }

    public void setOrder_Memo(String Order_Memo) {
        this.Order_Memo = Order_Memo;
        notifyPropertyChanged(BR.order_Memo);
    }


    public int getOrder_State() {
        return Order_State;
    }

    public void setOrder_State(int Order_State) {
        this.Order_State = Order_State;
    }

    public String getOrder_Hong_Memo() {
        return Order_Hong_Memo;
    }

    public void setOrder_Hong_Memo(String Order_Hong_Memo) {
        this.Order_Hong_Memo = Order_Hong_Memo;
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


    public String getOrder_OriginalNo() {
        return Order_OriginalNo;
    }

    public void setOrder_OriginalNo(String order_OriginalNo) {
        Order_OriginalNo = order_OriginalNo;
    }

    public String getOrder_OriginalPic() {
        return Order_OriginalPic;
    }

    public void setOrder_OriginalPic(String order_OriginalPic) {
        Order_OriginalPic = order_OriginalPic;
    }

    public String getOrder_Date() {
        return Order_Date;
    }

    public void setOrder_Date(String order_Date) {
        Order_Date = order_Date;
    }

    public String getOrder_Type_Name() {
        return Order_Type_Name;
    }

    public void setOrder_Type_Name(String order_Type_Name) {
        Order_Type_Name = order_Type_Name;
    }

    public String getPrintMemo() {
        return PrintMemo;
    }

    public void setPrintMemo(String printMemo) {
        PrintMemo = printMemo;
    }

    public String getOrder_PayableAmount_Daxie() {
        return Order_PayableAmount_Daxie;
    }

    public void setOrder_PayableAmount_Daxie(String order_PayableAmount_Daxie) {
        Order_PayableAmount_Daxie = order_PayableAmount_Daxie;
    }

    public boolean isOrder_IsHong() {
        return Order_IsHong;
    }

    public void setOrder_IsHong(boolean order_IsHong) {
        Order_IsHong = order_IsHong;
    }

    public int getIsKg() {
        return IsKg;
    }

    public void setIsKg(int isKg) {
        IsKg = isKg;
    }

    public int getIsPrintKg() {
        return IsPrintKg;
    }

    public void setIsPrintKg(int isPrintKg) {
        IsPrintKg = isPrintKg;
    }

    public String getOrder_IsNo() {
        return Order_IsNo;
    }

    public void setOrder_IsNo(String order_IsNo) {
        Order_IsNo = order_IsNo;
    }


    public List<OrderProAddBean> getOrder_Detail() {
        return Order_Detail;
    }

    public void setOrder_Detail(List<OrderProAddBean> Order_Detail) {
        this.Order_Detail = Order_Detail;
    }

    public String getOrder_Admin_Name() {
        return Order_Admin_Name;
    }

    public void setOrder_Admin_Name(String order_Admin_Name) {
        Order_Admin_Name = order_Admin_Name;
    }

    public int getOrder_Invoice_State() {
        return Order_Invoice_State;
    }

    public void setOrder_Invoice_State(int Order_Invoice_State) {
        this.Order_Invoice_State = Order_Invoice_State;
    }

    public String getOrder_RealyAmount_Daxie() {
        return Order_RealyAmount_Daxie;
    }

    public void setOrder_RealyAmount_Daxie(String Order_RealyAmount_Daxie) {
        this.Order_RealyAmount_Daxie = Order_RealyAmount_Daxie;
    }

    public String getOrder_Debt_Daxie() {
        return Order_Debt_Daxie;
    }

    public void setOrder_Debt_Daxie(String Order_Debt_Daxie) {
        this.Order_Debt_Daxie = Order_Debt_Daxie;
    }

    public String getUser_AllDebt_Daxie() {
        return User_AllDebt_Daxie;
    }

    public void setUser_AllDebt_Daxie(String User_AllDebt_Daxie) {
        this.User_AllDebt_Daxie = User_AllDebt_Daxie;
    }

    public int getOrder_IsEdit() {
        return Order_IsEdit;
    }

    public void setOrder_IsEdit(int Order_IsEdit) {
        this.Order_IsEdit = Order_IsEdit;
    }



    public int getIsPrintDebt() {
        return IsPrintDebt;
    }

    public void setIsPrintDebt(int IsPrintDebt) {
        this.IsPrintDebt = IsPrintDebt;
    }

    public int getIsPrintDebtAll() {
        return IsPrintDebtAll;
    }

    public void setIsPrintDebtAll(int IsPrintDebtAll) {
        this.IsPrintDebtAll = IsPrintDebtAll;
    }


}
