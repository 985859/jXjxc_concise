package com.yiande.jxjxc.myInterface;

import java.util.List;

public interface OnSelectListener<T> {
    void onSelect(T bean, List<T> data, boolean b);
}