package com.yiande.jxjxc.bean;

import java.io.Serializable;


/**
 * @author hukui
 * @time 2020/7/13
 * @Description 数据的基类
 */

public class JsonBean<T> implements Serializable {
    private static final long serialVersionUID = 5213230387175987834L;
    public int code;
    public String msg;
    public int ismsg;
    public int count = 10;
    public T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getIsmsg() {
        return ismsg;
    }

    public void setIsmsg(int ismsg) {
        this.ismsg = ismsg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}