package com.example.mycloudmusic.domain.response;

/**
 * 通用网络请求响应模型
 * 这两个字段是和服务端协商好的，不能随便更改
 */
public class BaseResponse {
    /**
     * 状态码
     *
     * 只有发生了错误才会有
     */
    private Integer status;


    /**
     * 出错的提示信息
     *
     * 发生了错误不一定有
     */
    private String message;

    public Integer getStatus() {
        return status;
    }

    public BaseResponse setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public BaseResponse setMessage(String message) {
        this.message = message;
        return this;
    }
}
