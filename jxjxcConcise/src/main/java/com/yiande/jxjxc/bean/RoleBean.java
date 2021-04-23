package com.yiande.jxjxc.bean;

import com.mylibrary.api.interfaces.SpinnerData;

import java.io.Serializable;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/9/14 20:12
 */
public class RoleBean implements SpinnerData, Serializable {


    /**
     * Role_ID : 2
     * Role_Name : 入库管理
     */
    private int Role_ID;
    private String Role_Name;

    private static final long serialVersionUID = -7322795781458824866L;

    public RoleBean() {
    }

    public RoleBean(int ID, String content) {

        this.Role_ID = ID;
        this.Role_Name = content;
    }


    @Override
    public String getText() {
        return getRole_Name();
    }

    @Override
    public int getId() {
        return getRole_ID();
    }

    public int getRole_ID() {
        return Role_ID;
    }

    public void setRole_ID(int Role_ID) {
        this.Role_ID = Role_ID;

    }

    public String getRole_Name() {
        return Role_Name;
    }

    public void setRole_Name(String Role_Name) {
        this.Role_Name = Role_Name;
    }
}
