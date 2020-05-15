package com.example.mycloudmusic.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mycloudmusic.MainActivity;
import com.example.mycloudmusic.R;

/**
 * 引导界面，只有下载之后第一次打开显示
 */
public class GuideActivity extends BaseCommonActivity implements View.OnClickListener {
    private Button bt_login_or_register;
    private Button bt_enter;
    private static final String TAG = "GuideActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
    }

    @Override
    protected void initViews() {
        super.initViews();
        //隐藏状态栏
        hideStatusBar();

        bt_login_or_register = findViewById(R.id.bt_login_or_register);
        bt_enter = findViewById(R.id.bt_enter);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        bt_login_or_register.setOnClickListener(this);
        bt_enter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_login_or_register:
                Log.e(TAG, "register");
                startActivityAfterFinnishThis(LoginOrRegisterActivity.class);
                break;
            case R.id.bt_enter:
                Log.e(TAG, "enter");
                startActivityAfterFinnishThis(MainActivity.class);
                break;
        }
    }
}
