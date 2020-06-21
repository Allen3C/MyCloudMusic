package com.example.mycloudmusic.util;

import com.example.mycloudmusic.listener.Consumer;

import java.util.List;

/**
 * 列表工具类
 */
public class ListUtil {
    /**
     * 变量每一个接口
     *
     * @param datum
     * @param action
     * @param <T>
     */
    public static <T> void eachListener(List<T> datum, Consumer<T> action) {
        for (T listener : datum
        ) {
            //将列表中每一个对象传递给action
            action.accept(listener);
        }
    }
}
