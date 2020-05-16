package com.example.mycloudmusic.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 偏好设置工具类
 * 是否登录了，是否显示引导界面，用户Id
 */
public class PreferenceUtil {
    /**
     * 偏好设置文件名称
     */
    private static final String NAME = "my_cloud_music";
    /**
     * 是否显示引导界面key
     */
    private static final String SHOW_GUIDE = "SHOW_GUIDE";

    private static PreferenceUtil instance;
    private final Context context;
    private final SharedPreferences sharedPreferences;

    public PreferenceUtil(Context context) {
        //保存上下文
        this.context = context.getApplicationContext();
        //获取偏好设置
        sharedPreferences = this.context.getSharedPreferences(NAME, Context.MODE_PRIVATE);

    }

    /**
     * 获取偏好设置单例
     * @param context
     * @return
     */
    public static PreferenceUtil getInstance(Context context){
        if(instance == null){
            instance = new PreferenceUtil(context);
        }
        return instance;
    }

    /**
     * 是否显示引导界面
     * @return
     */
    public boolean isShowGuide(){
        return sharedPreferences.getBoolean(SHOW_GUIDE, true);
    }
    /**
     * 设置是否显示引导界面
     */
    public void setShowGuide(boolean value){
        sharedPreferences.edit().putBoolean(SHOW_GUIDE, value).commit();
    }
}
