package com.yiande.jxjxc.bean;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/9/7 8:22
 */
public class LoginBean {
    /**
     * Mob : 13783471301
     * Name : UNICORN
     * Token : .eyJleHAiOjE2MjU1Mzg1MTcuMCwidWlkIjoiMSJ9.IgR_RpDJQAffVm39M-jSkoNU-6_Exo7qmoHEquN-gYA
     * Exp : 0
     */

    private String Admin_Mob;
    private String Admin_Name;
    private String token;


    public String getAdmin_Mob() {
        return Admin_Mob;
    }

    public void setAdmin_Mob(String admin_Mob) {
        Admin_Mob = admin_Mob;
    }

    public String getAdmin_Name() {
        return Admin_Name;
    }

    public void setAdmin_Name(String admin_Name) {
        Admin_Name = admin_Name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
