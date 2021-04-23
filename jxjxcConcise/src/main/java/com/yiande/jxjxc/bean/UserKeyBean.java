package com.yiande.jxjxc.bean;

import com.yiande.jxjxc.azlist.AZItemEntity;

import java.util.List;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/11 8:40
 */
public class UserKeyBean extends AZItemEntity<List<SupplierBean>> {


    /**
     * Key : G
     * User : [{"ID":2,"ComName":"古河建机"}]
     */

    private String Key;
    private List<SupplierBean> User;

    public String getKey() {
        return Key;
    }

    public void setKey(String Key) {
        this.Key = Key;
        setSortLetters(getKey());
    }

    public List<SupplierBean> getUser() {
        return User;
    }

    public void setUser(List<SupplierBean> User) {
        this.User = User;
        setValue(User);
    }


}
