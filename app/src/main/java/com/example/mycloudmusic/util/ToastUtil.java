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
    /**
     * 显示短时间错误Toast
     * @param context
     * @param id
     */
    public static void errorShortToast(@NonNull Context context, @StringRes int id) {
        Toasty.error(context, id, Toasty.LENGTH_SHORT).show();
    }
    /**
     * 显示长时间错误Toast
     * @param context
     * @param id
     */
    public static void errorLongToast(@NonNull Context context, @StringRes int id) {
        Toasty.error(context, id, Toasty.LENGTH_LONG).show();
    }

    /**
     * 短时间正确Toast
     * @param context
     * @param id
     */
    public static void successShortToast(@NonNull Context context, @StringRes int id) {
        Toasty.success(context, id, Toasty.LENGTH_LONG).show();
    }
}
