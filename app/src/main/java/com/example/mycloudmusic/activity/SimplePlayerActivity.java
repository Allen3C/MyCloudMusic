package com.example.mycloudmusic.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mycloudmusic.R;

/**
 * 简单播放器实现
 */
public class SimplePlayerActivity extends BaseTitleActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_player);
    }

    /**
     * 启动界面方法
     * @param activity 宿主activity
     */
    public static void start(Activity activity) {
        //创建意图
        //意图：就是要干什么
        Intent intent = new Intent(activity, SimplePlayerActivity.class);

        //启动界面
        activity.startActivity(intent);
    }
}
