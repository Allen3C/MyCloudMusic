package com.example.mycloudmusic.domain.response;

import java.util.List;

/**
 * 解析列表网络请求
 */
public class ListResponse<T> extends BaseResponse {
    /**
     * 定义一个列表
     *
     * 里面的对象使用了泛型
     */
    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public ListResponse<T> setData(List<T> data) {
        this.data = data;
        return this;
    }
}
