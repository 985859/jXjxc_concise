package com.yiande.jxjxc.bean;

import com.yiande.jxjxc.azlist.AZItemEntity;

import java.util.List;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2021/4/22 15:52
 */
public class ContactsBean extends AZItemEntity<List<UserBean>> {
    /**
     * Key : G
     * User : [{"ID":2,"ComName":"古河建机"}]
     */

    private String Key;
    private List<UserBean> UserList;

    public String getKey() {
        return Key;
    }
    public void setKey(String Key) {
        this.Key = Key;
        setSortLetters(getKey());
    }

    public void setUserList(List<UserBean> userList) {
        UserList = userList;
        setValue(userList);
    }

    public List<UserBean> getUserList() {
        return UserList;
    }
}
