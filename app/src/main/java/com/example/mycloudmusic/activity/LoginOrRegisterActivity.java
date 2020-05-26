package com.example.mycloudmusic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.mycloudmusic.R;
import com.example.mycloudmusic.domain.User;
import com.example.mycloudmusic.domain.event.LoginSuccessEvent;
import com.example.mycloudmusic.util.Constant;
import com.example.mycloudmusic.util.HandlerUtil;
import com.example.mycloudmusic.util.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;

/**
 * 登录注册界面
 */
public class LoginOrRegisterActivity extends BaseCommonActivity {

    private static final String TAG = "LoginOrRegisterActivity";
    /**
     * 第三方登陆后用户信息
     */
    private User data;
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
    protected void initDatum() {
        super.initDatum();
        //注册通知
        EventBus.getDefault().register(this);
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
        //清除第三方登录信息
        data = null;

        toRegister();
    }

    /**
     * 跳转到注册界面
     */
    private void toRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);

        if(data != null){
            //传递用户数据
            intent.putExtra(Constant.DATA, data);
        }

        startActivity(intent);
    }

    /**
     * QQ第三方登录
     */
    @OnClick(R.id.iv_qq)
    public void onQQLoginClick() {
        //初始化具体的平台
        Platform platform = ShareSDK.getPlatform(QQ.NAME);

        //设置false表示使用SSO授权方式
        platform.SSOSetting(false);

        //回调信息
        //可以在这里获取基本的授权返回的信息
        platform.setPlatformActionListener(new PlatformActionListener() {
            /**
             * 登录成功了
             * @param platform
             * @param i
             * @param hashMap
             */
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                //登录成功了

                //就可以获取到昵称，头像，OpenId
                //该方法回调不是在主线程

                //从数据库获取信息
                //也可以通过user参数获取
                PlatformDb db = platform.getDb();

                data = new User();
                data.setNickname(db.getUserName());
                data.setAvatar(db.getUserIcon());
                data.setQq_id(db.getUserId());

                //跳转到注册界面
                toRegister();

               // LogUtil.d(TAG, "other login success:" + nickname + "," + avatar + "," + openId + "," + HandlerUtil.isMainThread());
            }

            /**
             * 登录失败了
             * @param platform
             * @param i
             * @param throwable
             */
            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                LogUtil.d(TAG, "other login error:" + throwable.getLocalizedMessage() + "," + HandlerUtil.isMainThread());
            }

            /**
             * 取消登录了
             * @param platform
             * @param i
             */
            @Override
            public void onCancel(Platform platform, int i) {
                LogUtil.d(TAG, "other login cancel:" + i + "," + HandlerUtil.isMainThread());
            }
        });

        //authorize与showUser单独调用一个即可
        //授权并获取用户信息
        platform.showUser(null);
    }

    /**
     * 登录成功事件
     * 接受该事件的目的是关闭该界面
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginSuccssEvent(LoginSuccessEvent event){
        finish();
    }

    @Override
    protected void onDestroy() {
        //取消注册
        EventBus.getDefault().unregister(this);

        super.onDestroy();
    }
}
