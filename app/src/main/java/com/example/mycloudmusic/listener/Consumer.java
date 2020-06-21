package com.example.mycloudmusic.listener;

/**
 * 消费者接口
 *
 * 名字可以随便取
 * 只是Java中是这样命名的
 * 但他的类只有在API为24才能使用
 * 所以我们就自定义一个接口
 * @param <T>
 */
public interface Consumer<T> {
    /**
     * 方法名也可随便定义
     * @param t
     */
    void accept(T t);
}
