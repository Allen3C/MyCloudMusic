package com.example.mycloudmusic.util;

import com.example.mycloudmusic.R;
import com.example.mycloudmusic.domain.response.BaseResponse;

import org.apache.commons.lang3.StringUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

/**
 * 网络请求相关工具类
 */
public class HttpUtil {
    /**
     * 网络请求错误处理
     * @param data
     * @param error
     */
    public static void handlerRequest(Object data, Throwable error) {
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
                if(StringUtils.isBlank(response.getMessage())){
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
