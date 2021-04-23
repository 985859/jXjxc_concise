package com.yiande.jxjxc.bean;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/16 17:23
 */
public class DebtRowsBean {
    /**
     * ID : 21
     * Type : 1
     * ComName : 昌凸(艹皿艹 )
     * Mob : 11111111111
     * DebtNow : 2000
     * DebtAll : 2000
     * State : 0
     */

    private int ID;
    private int Type;
    private String ComName;
    private String Mob;
    private String DebtNow;
    private String DebtAll;
    private int State;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getType() {
        return Type;
    }

    public void setType(int Type) {
        this.Type = Type;
    }

    public String getComName() {
        return ComName;
    }

    public void setComName(String ComName) {
        this.ComName = ComName;
    }

    public String getMob() {
        return Mob;
    }

    public void setMob(String Mob) {
        this.Mob = Mob;
    }

    public String getDebtNow() {
        return DebtNow;
    }

    public void setDebtNow(String DebtNow) {
        this.DebtNow = DebtNow;
    }

    public String getDebtAll() {
        return DebtAll;
    }

    public void setDebtAll(String DebtAll) {
        this.DebtAll = DebtAll;
    }

    public int getState() {
        return State;
    }

    public void setState(int State) {
        this.State = State;
    }
}
