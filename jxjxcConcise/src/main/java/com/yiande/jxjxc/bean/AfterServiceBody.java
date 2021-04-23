package com.yiande.jxjxc.bean;

import java.util.List;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/23 8:57
 */
public class AfterServiceBody {


    /**
     * UID : 0
     * SID : 0
     * Begin : string
     * End : string
     * Other : 0
     * Charge : 0
     * Pic : string
     * Content : string
     * Product : [{"ID":0,"Money":0,"Quantity":0,"AID":0,"Text":"string"}]
     */

    private int UID;
    private int SID;
    private String Begin;
    private String End;
    private String Other;
    private String Charge;
    private String Pic;
    private String Content;
    private List<AfterServiceProductBean> Product;

    public int getUID() {
        return UID;
    }

    public void setUID(int UID) {
        this.UID = UID;
    }

    public int getSID() {
        return SID;
    }

    public void setSID(int SID) {
        this.SID = SID;
    }

    public String getBegin() {
        return Begin;
    }

    public void setBegin(String Begin) {
        this.Begin = Begin;
    }

    public String getEnd() {
        return End;
    }

    public void setEnd(String End) {
        this.End = End;
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

    public String getPic() {
        return Pic;
    }

    public void setPic(String Pic) {
        this.Pic = Pic;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public List<AfterServiceProductBean> getProduct() {
        return Product;
    }

    public void setProduct(List<AfterServiceProductBean> Product) {
        this.Product = Product;
    }


}
