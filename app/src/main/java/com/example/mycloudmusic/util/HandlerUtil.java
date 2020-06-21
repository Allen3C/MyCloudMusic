package com.example.mycloudmusic.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import static com.example.mycloudmusic.util.Constant.MESSAGE_PROGRESS;

/**
 * Handler工具类
 */
public class HandlerUtil    {
//    /**
//     * 创建一个Handler
//     * 用来将事件转换到主线程
//     */
//    private static Handler handler = new Handler(Looper.getMainLooper()){
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what){
//                case MESSAGE_PROGRESS:
//                    //播放进度回调
//                    break;
//            }
//        }
//    };

    /**
     * 判断是否在主线程
     * @return
     */
    public static boolean isMainThread(){
        return Looper.myLooper() == Looper.getMainLooper();
    }

//    /**
//     * 发送消息
//     * @param what
//     */
//    public static void sendMessage(int what) {
//        handler.obtainMessage(what).sendToTarget();
//    }
//
//    /**
//     * 获取handler
//     * @return
//     */
//    public static Handler getHandler() {
//        return handler;
//    }
}
