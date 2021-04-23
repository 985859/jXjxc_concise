package com.yiande.jxjxc.myInterface;

/**
 * @Description: 类型转换转换 有返回值操作
 * @Author: hukui
 * @Date: 2020/7/17 10:37
 */
public interface EventTransform<T, R> {
    R transform(T t);
}
