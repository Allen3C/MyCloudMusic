package com.example.mycloudmusic.listener;

import com.example.mycloudmusic.R;
import com.example.mycloudmusic.domain.response.BaseResponse;
import com.example.mycloudmusic.util.LogUtil;
import com.example.mycloudmusic.util.ToastUtil;

import org.apache.commons.lang3.StringUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

/**
 * 网络请求Observer
 */
public abstract class HttpObserver<T> extends ObserverAdapter<T> {
    private static final String TAG = "HttpObserver";

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
    public void onNext(T t) {
        super.onNext(t);
        LogUtil.d(TAG, "onNext:" + t);

        if (isSucceeded(t)) {
            //请求正常
            onSucceeded(t);
        } else {
            //请求出错了,处理错误
            requestErrorHandler(t, null);
        }
    }


    @Override
    public void onError(Throwable e) {
        super.onError(e);
        LogUtil.d(TAG, "onError:" + e.getLocalizedMessage());

        //处理错误
        requestErrorHandler(null, e);
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
            //没有状态码表示成功
            //这是和服务端的一个规定
            return response.getStatus() == null;
        }
        return false;
    }

    /**
     * 处理错误网络请求
     *
     * @param data
     * @param error
     */
    protected void requestErrorHandler(T data, Throwable error) {
        if(onFailed(data, error)){
            //回调了请求失败方法
            //并且该方法返回了true
            //返回true就表示外部手动处理错误
            //那框架内部就不用做任何事情了
        }else {
            //返回false就在框架处理
            //先处理有异常的请求
            if(error != null){
                //判断错误类型
                if (error instanceof UnknownHostException) {
                    ToastUtil.errorShortToast(R.string.error_network_unknown_host);
                } else if (error instanceof ConnectException) {
                    ToastUtil.errorShortToast(R.string.error_network_connect);
                } else if (error instanceof SocketTimeoutException) {
                    ToastUtil.errorShortToast(R.string.error_network_timeout);
                } else if (error instanceof HttpException) {
                    HttpException exception = (HttpException) error;
                    //获取响应码
                    int code = exception.code();
                    if (code == 401) {
                        ToastUtil.errorShortToast(R.string.error_network_not_auth);
                    } else if (code == 403) {
                        ToastUtil.errorShortToast(R.string.error_network_not_permission);
                    } else if (code == 404) {
                        ToastUtil.errorShortToast(R.string.error_network_not_found);
                    } else if (code >= 500) {
                        ToastUtil.errorShortToast(R.string.error_network_server);
                    } else {
                        ToastUtil.errorShortToast(R.string.error_network_unknown);
                    }
                } else {
                    ToastUtil.errorShortToast(R.string.error_network_unknown);
                }
            }else{
                if(data instanceof BaseResponse){
                    //判断具体业务请求是否成功
                    BaseResponse response = (BaseResponse) data;
                    if(StringUtils.isNotBlank(response.getMessage())){
                        //没有错误提示信息
                        ToastUtil.errorShortToast(R.string.error_network_unknown);
                    }else{
                        //有错误提示信息
                        ToastUtil.errorShortToast(response.getMessage());
                    }
                }
            }
        }
    }
}