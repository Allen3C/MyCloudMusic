package com.example.mycloudmusic.util;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mycloudmusic.R;

/**
 * 图片相关工具类
 */
public class ImageUtil {
    /**
     * 显示头像
     *
     * @param activity
     * @param view
     * @param uri
     */
    public static void showAvatar(Activity activity, ImageView view, String uri) {
        if (TextUtils.isEmpty(uri)) {
            //没有头像

            //显示默认头像
            //iv_avatar.setImageResource(R.drawable.placeholder);

            showCircle(activity, view, R.drawable.dnf);
        } else {
            //有头像

            if (uri.startsWith("http")) {
                //绝对路径
                showCircleFull(activity, view, uri);
            } else {
                //相对路径
                showCircle(activity, view, uri);
            }
        }
    }

    /**
     * 显示绝对路径图片
     *
     * @param activity
     * @param view
     * @param uri
     */
    public static void showFull(Activity activity, ImageView view, String uri) {
        //if (StringUtils.isBlank(uri)) {
        //    show(activity, view, R.drawable.placeholder);
        //} else {
        //    //获取功能配置
        //    RequestOptions options = getCommonRequestOptions();
        //
        //    //显示图片
        //    Glide.with(activity)
        //            .load(uri)
        //            .apply(options)
        //            .into(view);
        //}

        //获取功能配置
        RequestOptions options = getCommonRequestOptions();

        //显示图片
        Glide.with(activity)
                .load(uri)
                .apply(options)
                .into(view);
    }

    /**
     * 显示相对路径图片
     *
     * @param activity
     * @param view
     * @param uri
     */
    public static void show(Activity activity, ImageView view, String uri) {
        //将图片地址转为绝对路径
        uri = ResourceUtil.resourceUri(uri);

        showFull(activity, view, uri);
    }

    /**
     * 显示资源目录图片
     *
     * @param activity
     * @param view
     * @param resourceId
     */
    public static void show(Activity activity, ImageView view, @RawRes @DrawableRes @Nullable int resourceId) {
        //获取公共配置
        RequestOptions options = getCommonRequestOptions();

        Glide.with(activity)
                .load(resourceId)
                .apply(options)
                .into(view);
    }

    /**
     * 获取公共配置
     *
     * @return
     */
    private static RequestOptions getCommonRequestOptions() {
        //创建配置选项
        RequestOptions options = new RequestOptions();

        //占位图
        options.placeholder(R.drawable.dnf);

        //出错后显示的图片
        //包括：图片不存在等情况
        options.error(R.drawable.dnf);

        //从中心裁剪
        options.centerCrop();

        return options;
    }

    /**
     * 显示圆形相对路径图片
     *
     * @param activity
     * @param view
     * @param uri
     */
    public static void showCircle(Activity activity, ImageView view, String uri) {
        //将图片地址转为绝对地址
        uri=ResourceUtil.resourceUri(uri);

        //显示图片
        showCircleFull(activity, view, uri);
    }

    /**
     * 显示圆形绝对路径图片
     *
     * @param activity
     * @param view
     * @param name
     */
    public static void showCircleFull(Activity activity, ImageView view, String name) {
        //获取圆形图片通用的配置
        RequestOptions options = getCircleCommentRequestOptions();

        //显示图片
        Glide
                .with(activity)
                .load(name)
                .apply(options)
                .into(view);
    }

    /**
     * 显示圆形资源目录图片
     * @param activity
     * @param view
     * @param resourceId
     */
    public static void showCircle(Activity activity, ImageView view, @RawRes @DrawableRes @Nullable int resourceId){
        //获取圆形通用配置
        RequestOptions options = getCircleCommentRequestOptions();
        //显示图片
        Glide.with(activity)
                .load(resourceId)
                .apply(options)
                .into(view);
    }

    /**
     * 获取圆形图片通用的配置
     * @return
     */
    public static RequestOptions getCircleCommentRequestOptions() {
        //获取通用的配置
        RequestOptions options = getCommonRequestOptions();

        //圆形裁剪
        options.circleCrop();

        return options;
    }
}