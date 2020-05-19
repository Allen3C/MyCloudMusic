package com.example.mycloudmusic.network;

import com.example.mycloudmusic.domain.SheetDetailWrapper;
import com.example.mycloudmusic.domain.SheetListWrapper;
import com.example.mycloudmusic.util.Constant;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求接口包装类
 * 对外部提供一个和框架无关的接口
 */
public class Api {
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
    public Observable<SheetListWrapper> sheets() {
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
    public Observable<SheetDetailWrapper> sheetDetail(String id) {
        return service.sheetDetail(id)
                //这两个是固定写法
                //在子线程执行
                .subscribeOn(Schedulers.io())
                //在主线程观察
                .observeOn(AndroidSchedulers.mainThread());
    }
}