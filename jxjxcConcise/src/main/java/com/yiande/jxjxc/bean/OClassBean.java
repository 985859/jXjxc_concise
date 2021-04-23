package com.yiande.jxjxc.bean;

import com.yiande.jxjxc.myInterface.IPickerData;
import com.yiande.jxjxc.myInterface.LevelData;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/9/16 17:05
 */
public class OClassBean extends RoleBean implements Serializable, IPickerData, LevelData {

    private static final long serialVersionUID = -2668548592683416886L;
    /**
     * OClass_ID : 27
     * OClass_Name : 搅拌机
     * OClass_Child : [{"OClass_ID":29,"OClass_Name":"JS强制性","OClass_Child":[]},{"OClass_ID":30,"OClass_Name":"JZM滚筒型","OClass_Child":[]}]
     * OClass_FatherID : 0
     * OClass_Flag : 1
     */

    private int OClass_ID;
    private String OClass_Name;
    private int OClass_FatherID;
    private int OClass_Flag;


    private List<OClassBean> OClass_Child;

    public int getOClass_ID() {
        return OClass_ID;
    }

    public void setOClass_ID(int OClass_ID) {
        this.OClass_ID = OClass_ID;

    }


    public String getOClass_Name() {
        return OClass_Name;
    }


    public void setOClass_Name(String OClass_Name) {
        this.OClass_Name = OClass_Name;
    }


    public List<OClassBean> getOClass_Child() {
        return OClass_Child;
    }


    public void setOClass_Child(List<OClassBean> OClass_Child) {
        this.OClass_Child = OClass_Child;
    }


    @Override
    public String getPickerViewText() {
        return getOClass_Name();
    }


    public int getOClass_FatherID() {
        return OClass_FatherID;
    }


    public void setOClass_FatherID(int OClass_FatherID) {
        this.OClass_FatherID = OClass_FatherID;
    }


    public int getOClass_Flag() {
        return OClass_Flag;
    }


    public void setOClass_Flag(int OClass_Flag) {
        this.OClass_Flag = OClass_Flag;
    }






    @Override
    public String getName() {
        return getOClass_Name();
    }

    @Override
    public String getID() {
        return String.valueOf(getOClass_ID());
    }

    @Override
    public List getChild() {
        return getOClass_Child();
    }

    @Override
    public int getId() {
        return getOClass_ID();
    }

    @Override
    public String getText() {
        return getOClass_Name();

    }


}
