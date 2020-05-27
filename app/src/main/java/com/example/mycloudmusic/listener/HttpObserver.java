package com.example.mycloudmusic.listener;

import com.example.mycloudmusic.activity.BaseCommonActivity;
import com.example.mycloudmusic.domain.response.BaseResponse;
import com.example.mycloudmusic.util.HttpUtil;
import com.example.mycloudmusic.util.LoadingUtil;
import com.example.mycloudmusic.util.LogUtil;

import io.reactivex.disposables.Disposable;

/**
 * 网络请求Observer
 */
public abstract class HttpObserver<T> extends ObserverAdapter<T> {
    private static final String TAG = "HttpObserver";
    private boolean isShowLoading;
    private BaseCommonActivity activity;

    /**
     * 无参构造
     */
    public HttpObserver(){

    }

    /**
     *有参构造
     */
    public HttpObserver(BaseCommonActivity activity, boolean isShowLoading){
        this.activity = activity;
        this.isShowLoading = isShowLoading;
    }

    /**
     * 请求成功
     *
     * @param data
     */
    public abstract void onSucceeded(T data);

    /**
     * 请求失败
     *
     * @param t
     * @param e
     * @return
     */
    public boolean onFailed(T t, Throwable e) {
        return false;
    }

    @Override
    public void onSubscribe(Disposable d) {
        super.onSubscribe(d);
        if(isShowLoading){
            //显示加载对话框
            LoadingUtil.showLoading(activity);
        }
    }

    @Override
    public void onNext(T t) {
        super.onNext(t);
        LogUtil.d(TAG, "onNext:" + t);

        //检查是否需要隐藏对话框
        checkHideLoading();

        if (isSucceeded(t)) {
            //请求正常
            onSucceeded(t);
        } else {
            //请求出错了,处理错误
            handlerRequest(t, null);
        }
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        LogUtil.d(TAG, "onError:" + e.getLocalizedMessage());

        //检查是否需要隐藏对话框
        checkHideLoading();

        //处理错误
        handlerRequest(null, e);
    }

    /**
     * 网络请求是否成功
     *
     * @param t
     * @return
     */
    private boolean isSucceeded(T t) {
        if (t instanceof BaseResponse) {
            //判断具体业务请求是否成功
            BaseResponse response = (BaseResponse) t;
            //状态码为0表示成功
            //这是和服务端的一个规定
            return response.getStatus() == 0;
        }
        return false;
    }

    /**
     * 处理错误网络请求
     *
     * @param data
     * @param error
     */
    protected void handlerRequest(T data, Throwable error) {
        if(onFailed(data, error)){
            //回调了请求失败方法
            //并且该方法返回了true
            //返回true就表示外部手动处理错误
            //那框架内部就不用做任何事情了
        }else {
            //返回false就在框架处理
            HttpUtil.handlerRequest(data, error);
        }
    }

    /**
     * 检查是否需要隐藏对话框
     */
    protected void checkHideLoading(){
        if(isShowLoading){
            LoadingUtil.hideLoading();
        }
    }
}