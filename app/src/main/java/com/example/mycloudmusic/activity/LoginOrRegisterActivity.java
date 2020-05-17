package com.example.mycloudmusic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.mycloudmusic.R;

/**
 * 登录注册界面
 */
public class LoginOrRegisterActivity extends BaseCommonActivity implements View.OnClickListener {

    private static final String TAG = "LoginOrRegisterActivity";
    private Button bt_login;
    private Button bt_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_register);
    }

    @Override
    protected void initViews() {
        super.initViews();
        //内容显示到状态栏下面
        lightStatusBar();

        bt_login = findViewById(R.id.bt_login);
        bt_register = findViewById(R.id.bt_register);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        bt_login.setOnClickListener(this);
        bt_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //登录
            case R.id.bt_login:
                Log.e(TAG, "login");
                startActivity(LoginActivity.class);
                break;
                //注册
            case R.id.bt_register:
                Log.e(TAG, "register");
                startActivity(RegisterActivity.class);
                break;
        }
    }
}
