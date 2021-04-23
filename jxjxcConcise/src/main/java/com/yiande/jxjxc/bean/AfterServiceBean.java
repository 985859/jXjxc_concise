package com.yiande.jxjxc.bean;

import java.util.List;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/23 17:23
 */
public class AfterServiceBean {

    private List<KeyValueBean> SelectUser;
    private List<KeyValueBean> SelectStaff;
    private List<AfterServiceRowsBean> ServiceRows;

    public List<KeyValueBean> getSelectUser() {
        return SelectUser;
    }

    public void setSelectUser(List<KeyValueBean> SelectUser) {
        this.SelectUser = SelectUser;
    }

    public List<KeyValueBean> getSelectStaff() {
        return SelectStaff;
    }

    public void setSelectStaff(List<KeyValueBean> SelectStaff) {
        this.SelectStaff = SelectStaff;
    }

    public List<AfterServiceRowsBean> getServiceRows() {
        return ServiceRows;
    }

    public void setServiceRows(List<AfterServiceRowsBean> ServiceRows) {
        this.ServiceRows = ServiceRows;
    }




}
