package com.example.mycloudmusic.network;

import com.chuckerteam.chucker.api.ChuckerInterceptor;
import com.example.mycloudmusic.AppContext;
import com.example.mycloudmusic.domain.Ad;
import com.example.mycloudmusic.domain.BaseModel;
import com.example.mycloudmusic.domain.Session;
import com.example.mycloudmusic.domain.Sheet;
import com.example.mycloudmusic.domain.Song;
import com.example.mycloudmusic.domain.User;
import com.example.mycloudmusic.domain.response.BaseResponse;
import com.example.mycloudmusic.domain.response.DetailResponse;
import com.example.mycloudmusic.domain.response.ListResponse;
import com.example.mycloudmusic.util.Constant;
import com.example.mycloudmusic.util.LogUtil;
import com.example.mycloudmusic.util.PreferenceUtil;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.Path;

/**
 * 网络请求接口包装类
 * 对外部提供一个和框架无关的接口
 */
public class Api {
    private static final String TAG = "Api";
    /**
     * Api单例字段
     */
    private static Api instance;

    /**
     * Service单例
     */
    private final Service service;

    /**
     * 私有构造方法
     */
    private Api() {
        //初始化okhttp
        OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();

        //公共请求参数
        okhttpClientBuilder.addNetworkInterceptor(chain -> {
            //获取到偏好设置工具类
            PreferenceUtil sp = PreferenceUtil.getInstance(AppContext.getInstance());

            //获取到request
            Request request = chain.request();

            if (sp.isLogin()) {
                //登录了

                //获取出用户Id和token
                String user = sp.getUserId();
                String session = sp.getSession();

                //打印日志是方便调试
                LogUtil.d(TAG, "Api user:" + user + "," + session);

                //将用户id和token设置到请求头
                request = request.newBuilder()
                        .addHeader("User", user)
                        .addHeader("Authorization", session)
                        .build();
            }

            //继续执行网络请求
            return chain.proceed(request);
        });

        if (LogUtil.isDebug) {
            //调试模式

            //创建OKHttp日志拦截器
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

            //设置日志等级
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

            //添加到网络框架中
            okhttpClientBuilder.addInterceptor(interceptor);

            //添加Stetho抓包拦截器
            okhttpClientBuilder.addNetworkInterceptor(new StethoInterceptor());

            //添加Chucker实现应用内显示网络请求拦截器
            okhttpClientBuilder.addInterceptor(new ChuckerInterceptor(AppContext.getInstance()));
        }



        //初始化retrofit
        Retrofit retrofit = new Retrofit.Builder()
                //让retrofit使用okhttp
                .client(okhttpClientBuilder.build())

                //api地址
                .baseUrl(Constant.ENDPOINT)

                //适配rxjava
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

                //使用gson解析json
                //包括请求参数和响应
                .addConverterFactory(GsonConverterFactory.create())

                //创建retrofit
                .build();

        //创建service
        service = retrofit.create(Service.class);
    }

    /**
     * 返回当前对象的唯一实例
     *
     * 单例设计模式
     * 由于移动端很少有高并发
     * 所以这个就是简单判断
     *
     * @return
     */
    public static Api getInstance() {
        if (instance == null) {
            instance = new Api();
        }
        return instance;
    }

    /**
     * 歌单列表
     *
     * @return
     */
    public Observable<ListResponse<Sheet>> sheets() {
        return service.sheets()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 歌单详情
     *
     * @param id
     * @return
     */
    public Observable<DetailResponse<Sheet>> sheetDetail(String id) {
        return service.sheetDetail(id)
                //这两个是固定写法
                //在子线程执行
                .subscribeOn(Schedulers.io())
                //在主线程观察
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 注册
     * @param data
     * @return
     */
    public Observable<DetailResponse<BaseModel>> register(User data){
        return service.register(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 登录
     */
    public Observable<DetailResponse<Session>> login(User data){
        return service.login(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 找回密码
     * @param data
     * @return
     */
    public Observable<BaseResponse> resetPassword(User data){
        return service.resetPassword(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 发送短信验证码
     * @param data
     * @return
     */
    public Observable<DetailResponse<BaseModel>> sendSMSCode(User data){
        return service.sendSMSCode(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 发送邮箱验证码
     * @param data
     * @return
     */
    public Observable<DetailResponse<BaseModel>> sendEmailCode(User data){
        return service.sendEmailCode(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 用户详情
     * @param id
     * @param nickname
     * @return
     */
    public Observable<DetailResponse<User>> userDetail(String id, String nickname) {
        //添加查询参数
        HashMap<String, String> data = new HashMap<>();
        //如果昵称不为空才添加
        if(StringUtils.isNotBlank(nickname)){
            data.put(Constant.NICKNAME, nickname);
        }
        return service.userDetail(id, data)
                //这两个是固定写法
                //在子线程执行
                .subscribeOn(Schedulers.io())
                //在主线程观察
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 用户详情
     * @param id
     * @return
     */
    public Observable<DetailResponse<User>> userDetail(String id){
        return userDetail(id, null);
    }

    /**
     * 单曲
     * @return
     */
    public Observable<ListResponse<Song>> songs(){
        return service.songs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 广告列表
     * @return
     */
    public Observable<ListResponse<Ad>> ads(){
        return service.ads()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 收藏歌单
     * @param id
     * @return
     */
    public Observable<Response<Void>> collect(String id){
        return service.collect(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 取消收藏歌单
     * @param id
     * @return
     */
    public Observable<Response<Void>> deleteCollect(String id){
        return service.deleteCollect(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}