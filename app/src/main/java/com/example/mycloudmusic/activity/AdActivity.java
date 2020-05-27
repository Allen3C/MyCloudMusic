package com.example.mycloudmusic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mycloudmusic.R;

/**
 * 广告界面（启动界面后的广告）
 */
public class AdActivity extends BaseCommonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
    }

    @Override
    protected void initViews() {
        super.initViews();
        //全屏
        fullScreen();
    }
}
