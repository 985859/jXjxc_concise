package com.yiande.jxjxc.myInterface;

/**
 * @Description: 无返回值 事件操作
 * @Author: hukui
 * @Date: 2020/7/17 10:33
 */
public interface EventConsume<T> {
    void accept(T t);
}
