package com.yiande.jxjxc.bean;

import com.mylibrary.api.interfaces.SpinnerData;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/11/19 14:19
 */

public class IPBean implements SpinnerData {
    int id;
    String value;

    public IPBean(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getText() {
        return value;
    }

    @Override
    public int getId() {
        return id;
    }
}
