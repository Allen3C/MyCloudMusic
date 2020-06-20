package com.example.mycloudmusic.network;

import com.example.mycloudmusic.domain.Ad;
import com.example.mycloudmusic.domain.BaseModel;
import com.example.mycloudmusic.domain.Session;
import com.example.mycloudmusic.domain.Sheet;
import com.example.mycloudmusic.domain.SheetDetailWrapper;
import com.example.mycloudmusic.domain.SheetListWrapper;
import com.example.mycloudmusic.domain.Song;
import com.example.mycloudmusic.domain.User;
import com.example.mycloudmusic.domain.response.BaseResponse;
import com.example.mycloudmusic.domain.response.DetailResponse;
import com.example.mycloudmusic.domain.response.ListResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
     * 注册
     * @param data
     * @return
     */
    @POST("v1/users")
    Observable<DetailResponse<BaseModel>> register(@Body User data);

    /**
     *登录
     */
    @POST("v1/sessions")
    Observable<DetailResponse<Session>> login(@Body User data);

    /**
     * 找回密码
     * @param data
     * @return
     */
    @POST("v1/users/reset_password")
    Observable<BaseResponse> resetPassword(@Body User data);

    /**
     * 发送短信验证码
     * @param data
     * @return
     */
    @POST("v1/codes/request_sms_code")
    Observable<DetailResponse<BaseModel>> sendSMSCode(@Body User data);

    /**
     * 发送邮箱验证码
     * @param data
     * @return
     */
    @POST("v1/codes/request_email_code")
    Observable<DetailResponse<BaseModel>> sendEmailCode(@Body User data);

    /**
     * 用户详情
     * @param id
     * @param data
     * @return
     */
    @GET("v1/users/{id}")
    Observable<DetailResponse<User>> userDetail(@Path("id") String id, @QueryMap Map<String, String> data);

    /**
     * 单曲
     * @return
     */
    @GET("v1/songs")
    Observable<ListResponse<Song>> songs();

    /**
     * 广告列表
     * @return
     */
    @GET("v1/ads")
    Observable<ListResponse<Ad>> ads();

    /**
     * 收藏歌单
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("v1/collections")
    Observable<Response<Void>> collect(@Field("sheet_id") String id);

    /**
     * 取消收藏歌单
     * @param id
     * @return
     */
    @DELETE("v1/collections/{id}")
    Observable<Response<Void>> deleteCollect(@Path("id") String id);
}
