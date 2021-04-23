package com.yiande.jxjxc.bean;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/29 14:29
 */
public class InvoiceRowsBean {
    /**
     * ID : 1
     * Money : 500
     * Memo :
     * IsDel : 0
     * DelWhy :
     * Pic : http://192.168.2.110:8098/file/invoice/202012/342bcad5d3a2473789b13e09b5e2a416.jpg
     * AddDate : 2020-12-29 14:23
     * AdminName : Android
     */

    private int ID;
    private String Money;
    private String Memo;
    private int IsDel;
    private String DelWhy;
    private String Pic;
    private String AddDate;
    private String AdminName;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getMoney() {
        return Money;
    }

    public void setMoney(String Money) {
        this.Money = Money;
    }

    public String getMemo() {
        return Memo;
    }

    public void setMemo(String Memo) {
        this.Memo = Memo;
    }

    public int getIsDel() {
        return IsDel;
    }

    public void setIsDel(int IsDel) {
        this.IsDel = IsDel;
    }

    public String getDelWhy() {
        return DelWhy;
    }

    public void setDelWhy(String DelWhy) {
        this.DelWhy = DelWhy;
    }

    public String getPic() {
        return Pic;
    }

    public void setPic(String Pic) {
        this.Pic = Pic;
    }

    public String getAddDate() {
        return AddDate;
    }

    public void setAddDate(String AddDate) {
        this.AddDate = AddDate;
    }

    public String getAdminName() {
        return AdminName;
    }

    public void setAdminName(String AdminName) {
        this.AdminName = AdminName;
    }
}
