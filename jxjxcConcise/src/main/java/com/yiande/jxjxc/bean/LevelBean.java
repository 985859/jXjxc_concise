package com.yiande.jxjxc.bean;

import com.mylibrary.api.interfaces.SpinnerData;
import com.yiande.jxjxc.myInterface.IPickerData;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/11/27 16:41
 */
public class LevelBean implements IPickerData, SpinnerData {
    private int level;
    private int id;
    private CharSequence name;


    public LevelBean() {
    }

    public LevelBean(int level, CharSequence name) {
        this.level = level;
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public CharSequence getName() {
        return name;
    }

    public void setName(CharSequence name) {
        this.name = name;
    }

    @Override
    public CharSequence getText() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return level;
    }

    @Override
    public String getPickerViewText() {
        return name.toString();
    }
}
