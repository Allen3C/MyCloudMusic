package com.example.mycloudmusic.util;

import android.app.Activity;
import android.app.ProgressDialog;

import com.example.mycloudmusic.R;
import com.example.mycloudmusic.activity.BaseCommonActivity;

/**
 * 加载提示框工具类
 */
public class LoadingUtil {

    private static ProgressDialog progressDialog;

    /**
     * 显示一个加载对话框，使用默认提示文字
     * @param activity
     */
    public static void showLoading(Activity activity) {
        showLoadign(activity, activity.getString(R.string.loading));
    }

    /**
     * 显示一个加载对话框，提示文字可以自定义
     * @param activity
     */
    private static void showLoadign(Activity activity, String message) {
        //判断Activity为空或者已经销毁
        if(activity == null || activity.isFinishing()){
            return;
        }
        //判断对话框是否已经显示，如果已经显示就不再显示
        if(progressDialog != null){
            return;
        }
        //创建一个进度对话框
        progressDialog = new ProgressDialog(activity);
//        //提示标题
//        progressDialog.setTitle("提示");
        //提示信息
        progressDialog.setMessage(message);
        //点击弹窗外部不会自动隐藏
        progressDialog.setCancelable(false);
        //显示
        progressDialog.show();
    }

    /**
     * 隐藏加载提示对话框
     */
    public static void hideLoading() {
        if(progressDialog != null && progressDialog.isShowing()){
            progressDialog.hide();
            progressDialog = null;
        }
    }
}
