package com.example.mycloudmusic.util;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.mycloudmusic.R;

/**
 * 通知相关工具类
 */
public class NotificationUtil {

    private static final String IMPORTANCE_LOW_CHANNERL_ID = "IMPORTANCE_LOW_CHANNERL_ID";
    /**
     * 通知管理器实例
     */
    private static NotificationManager notificationManager;

    /**
     * 获取一个设置service为前台的通知
     * 他只是一个测试通知
     * 无任何实际意义
     * @return
     */
    public static Notification getServiceForeground(Context context) {
        //渠道是8.0中新增的
        //简单来说就是
        //为了给通知分组
        //例如：我们这个应用有聊天消息
        //还有其他广告推送消息
        //那么如果要把这个应用做好
        //这两种类型的通知就应该设置不同的渠道
        //一个是：聊天消息渠道；优先级为紧急
        //另一个是：推送广告通知；优先级为高
        //好处是用户可以屏蔽某部分通知
        //还能不错过重要的通知
        //当然这只是Google希望大家遵循这个规则
        //但往往实际情况下
        //也会把广告设置为紧急的
        //因为推送广告就是要让用户看到

        //获取通知管理器
        getNotificationManager(context);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //创建渠道
            //可以多次创建
            //但Id一样只会创建一个
            NotificationChannel channel=new NotificationChannel(IMPORTANCE_LOW_CHANNERL_ID,"重要通知", NotificationManager.IMPORTANCE_LOW);
            //创建一个渠道
            notificationManager.createNotificationChannel (channel);
        }



        //创建一个通知
        //内容随便写
        //通知的配置有很多
        //这里就不在讲解了
        Notification notification=new NotificationCompat.Builder (context,IMPORTANCE_LOW_CHANNERL_ID)

                //通知标题
                .setContentTitle ("我们是爱学啊")

                //通知内容
                .setContentText ("人生苦短，我们只做好课！")

                //通知小图标
                .setSmallIcon (R.mipmap.ic_launcher)

                //通知大图标
                .setLargeIcon (BitmapFactory.decodeResource (context.getResources (),R.mipmap.ic_launcher))

                //创建一个通知
                .build ();

        //返回
        return notification;
    }

    /**
     * 获取通知管理器
     * @param context
     */
    private static void getNotificationManager(Context context) {
        if(notificationManager == null){
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
    }

    /**
     * 显示通知
     * @param id
     * @param notification
     */
    public static void showNotification(int id, Notification notification) {
        notificationManager.notify(id, notification);
    }
}
