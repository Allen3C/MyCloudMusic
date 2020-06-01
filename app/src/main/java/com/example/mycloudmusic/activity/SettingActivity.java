package com.example.mycloudmusic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mycloudmusic.AppContext;
import com.example.mycloudmusic.R;

import butterknife.OnClick;

/**
 * 设置界面
 */
public class SettingActivity extends BaseTitleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    /**
     * 退出按钮点击
     */
    @OnClick(R.id.bt_logout)
    public void onLogoutClick() {
        //退出
        AppContext.getInstance().logout();

    }
}
