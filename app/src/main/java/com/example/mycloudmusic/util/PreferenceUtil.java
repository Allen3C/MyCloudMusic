package com.example.mycloudmusic.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

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
    private static final String SESSION = "SESSION";
    private static final String USER_ID = "USER_ID";

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
        putBoolean(SHOW_GUIDE, value);
    }

    /**
     *保存登录后的Session
     * @param value
     */
    public void setSession(String value) {
        putString(SESSION, value);
    }

    /**
     * 获取登录后的Session
     * @return
     */
    public String getSession(){
        return sharedPreferences.getString(SESSION, null);
    }

    /**
     * 保存用户id
     * @param value
     */
    public void setUserId(String value) {
        putString(USER_ID, value);
    }

    /**
     * 获取用户id
     * @return
     */
    public String getUserId(){
        return sharedPreferences.getString(USER_ID, null);
    }

    /**
     * 是否登录了
     * @return
     */
    public boolean isLogin() {
        return !TextUtils.isEmpty(getSession());
    }

    /**
     * 退出
     */
    public void logout() {
        delete(USER_ID);
        delete(SESSION);
    }

    //辅助方法
    /**
     * 保存字符串
     * @param key
     * @param value
     */
    private void putString(String key, String value) {
        sharedPreferences.edit().putString(key, value).commit();
    }
    /**
     * 保存boolean
     * @param key
     * @param value
     */
    private void putBoolean(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).commit();
    }
    /**
     * 删除内容
     * @param key
     */
    private void delete(String key) {
        sharedPreferences.edit().remove(key).commit();
    }
    //end 辅助方法
}
