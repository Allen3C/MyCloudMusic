package com.example.mycloudmusic.network;

import com.example.mycloudmusic.domain.Sheet;
import com.example.mycloudmusic.domain.SheetDetailWrapper;
import com.example.mycloudmusic.domain.SheetListWrapper;
import com.example.mycloudmusic.domain.User;
import com.example.mycloudmusic.domain.response.DetailResponse;
import com.example.mycloudmusic.domain.response.ListResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * 网络接口配置
 * <p>
 * 之所以调用接口还能返回数据
 * 是因为Retrofit框架内部处理了
 */
public interface Service {

    /**
     * 歌单列表
     * Observable 是固定写法，因为用了RxJava
     * @return
     */
    @GET("v1/sheets")
    Observable<ListResponse<Sheet>> sheets();

    /**
     * 歌单详情
     *
     * @param id
     * @return
     */
    @GET("v1/sheets/{id}")
    Observable<DetailResponse<Sheet>> sheetDetail(@Path("id") String id);

    /**
     * 用户详情
     * @param id
     * @param data
     * @return
     */
    @GET("v1/users/{id}")
    Observable<DetailResponse<User>> userDetail(@Path("id") String id, @QueryMap Map<String, String> data);
}
