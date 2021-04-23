package com.yiande.jxjxc.bean;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/11/25 9:11
 */
public class DataAnalysisBean {
    /**
     * "NewSell": 0,                   //新出库
     * "SellState": 1,                 //出库比较状态 0:持平 1:上升 2:下降
     * "NewUser": 3,                   //新客户
     * "UserState": 0,                 //状态
     * "NewBuy": 6,                    //新入库
     * "BuyState": 2,                  //状态
     * "NewWarning": 0,                //库存预警
     * "WarningState": 0               //状态
     */

    private int NewSell;
    private int SellState;
    private int NewUser;
    private int UserState;
    private int NewBuy;
    private int BuyState;
    private int NewWarning;
    private int WarningState;

    public int getNewSell() {
        return NewSell;
    }

    public void setNewSell(int NewSell) {
        this.NewSell = NewSell;
    }

    public int getSellState() {
        return SellState;
    }

    public void setSellState(int SellState) {
        this.SellState = SellState;
    }

    public int getNewUser() {
        return NewUser;
    }

    public void setNewUser(int NewUser) {
        this.NewUser = NewUser;
    }

    public int getUserState() {
        return UserState;
    }

    public void setUserState(int UserState) {
        this.UserState = UserState;
    }

    public int getNewBuy() {
        return NewBuy;
    }

    public void setNewBuy(int NewBuy) {
        this.NewBuy = NewBuy;
    }

    public int getBuyState() {
        return BuyState;
    }

    public void setBuyState(int BuyState) {
        this.BuyState = BuyState;
    }

    public int getNewWarning() {
        return NewWarning;
    }

    public void setNewWarning(int NewWarning) {
        this.NewWarning = NewWarning;
    }

    public int getWarningState() {
        return WarningState;
    }

    public void setWarningState(int WarningState) {
        this.WarningState = WarningState;
    }


}
