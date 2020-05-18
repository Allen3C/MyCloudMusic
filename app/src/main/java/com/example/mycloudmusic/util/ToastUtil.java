package com.example.mycloudmusic.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.example.mycloudmusic.activity.BaseCommonActivity;

import es.dmoral.toasty.Toasty;

/**
 * Toast工具类
 */
public class ToastUtil {
    private static Context context;

    /**
     * 初始化，获得全局context
     * @param context
     */
    public static void init(Context context) {
        //因为是静态方法，所以不能写this
        ToastUtil.context = context;
    }

    /**
     * 显示短时间错误Toast
     * @param id
     */
    public static void errorShortToast(@StringRes int id) {
        Toasty.error(context, id, Toasty.LENGTH_SHORT).show();
    }
    /**
     * 显示长时间错误Toast
     * @param id
     */
    public static void errorLongToast(@StringRes int id) {
        Toasty.error(context, id, Toasty.LENGTH_LONG).show();
    }

    /**
     * 短时间正确Toast
     * @param id
     */
    public static void successShortToast(@StringRes int id) {
        Toasty.success(context, id, Toasty.LENGTH_LONG).show();
    }


}
