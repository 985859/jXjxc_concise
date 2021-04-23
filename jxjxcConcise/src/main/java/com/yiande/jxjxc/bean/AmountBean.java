package com.yiande.jxjxc.bean;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/11/14 16:40
 */
public class AmountBean  {




    /**
     * Amount : 51431.00
     * PayableAmount : 51431.00
     * FreightAmount : 0.00
     * BuyAmount : 5714.00
     */

    private String Amount;
    private String PayableAmount;
    private String FreightAmount;
    private String BuyAmount;

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

    public String getFreightAmount() {
        return FreightAmount;
    }

    public void setFreightAmount(String FreightAmount) {
        this.FreightAmount = FreightAmount;
    }

    public String getBuyAmount() {
        return BuyAmount;
    }

    public void setBuyAmount(String BuyAmount) {
        this.BuyAmount = BuyAmount;
    }
}
