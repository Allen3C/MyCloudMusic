package com.example.mycloudmusic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.mycloudmusic.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录注册界面
 */
public class LoginOrRegisterActivity extends BaseCommonActivity {

    private static final String TAG = "LoginOrRegisterActivity";
//    @BindView(R.id.bt_login)
//    Button bt_login;
//    @BindView(R.id.bt_register)
//    Button bt_register;

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

//        bt_login = findViewById(R.id.bt_login);
//        bt_register = findViewById(R.id.bt_register);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
//        bt_login.setOnClickListener(this);
//        bt_register.setOnClickListener(this);
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            //登录
//            case R.id.bt_login:
//                Log.e(TAG, "login");
//                startActivity(LoginActivity.class);
//                break;
//                //注册
//            case R.id.bt_register:
//                Log.e(TAG, "register");
//                startActivity(RegisterActivity.class);
//                break;
//        }
//    }

    /**
     * 登录
     */
    @OnClick(R.id.bt_login)
    public void onLoginClick(){
        startActivity(LoginActivity.class);
    }

    /**
     * 注册
     */
    @OnClick(R.id.bt_register)
    public void onRegisterClick(){
        startActivity(RegisterActivity.class);
    }
}
