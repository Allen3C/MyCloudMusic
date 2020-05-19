package com.example.mycloudmusic.listener;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 通用实现Observer里面的方法
 *
 * 目的是避免要实现所有方法
 */
public class ObserverAdapter<T> implements Observer<T> {
    /**
     * 开始订阅了执行（可以简单理解为开始执行前）
     * @param d
     */
    @Override
    public void onSubscribe(Disposable d) {

    }

    /**
     * 当前Observer执行完了，执行下一个Observer
     * @param t
     */
    @Override
    public void onNext(T t) {

    }

    /**
     * 发生了错误
     * @param e
     */
    @Override
    public void onError(Throwable e) {

    }

    /**
     * 回调了onNext方法后调用
     */
    @Override
    public void onComplete() {

    }
}
