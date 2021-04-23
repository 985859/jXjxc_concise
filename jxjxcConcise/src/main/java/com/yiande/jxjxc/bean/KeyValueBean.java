package com.yiande.jxjxc.bean;

import com.mylibrary.api.interfaces.SpinnerData;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/23 17:26
 */
public class KeyValueBean implements SpinnerData {

    /**
     * Key : 13
     * Value : 阿尔法
     */

    private int Key;
    private String Value;
    public KeyValueBean() {

    }
    public KeyValueBean(int key, String value) {
        Key = key;
        Value = value;
    }

    public int getKey() {
        return Key;
    }

    public void setKey(int Key) {
        this.Key = Key;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String Value) {
        this.Value = Value;
    }

    @Override
    public String getText() {
        return Value;
    }

    @Override
    public int getId() {
        return Key;
    }
}
