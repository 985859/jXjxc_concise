package com.yiande.jxjxc.bean;

import java.util.List;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2021/1/6 16:29
 */
public class ProductListBean {

    private List<ProductBean> data;
    private String amount;

    public List<ProductBean> getData() {
        return data;
    }

    public void setData(List<ProductBean> data) {
        this.data = data;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
