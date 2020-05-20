package com.example.mycloudmusic.activity;

import com.example.mycloudmusic.AppContext;
import com.example.mycloudmusic.MainActivity;
import com.example.mycloudmusic.R;
import com.example.mycloudmusic.domain.Session;
import com.example.mycloudmusic.domain.User;
import com.example.mycloudmusic.domain.response.DetailResponse;
import com.example.mycloudmusic.listener.HttpObserver;
import com.example.mycloudmusic.network.Api;
import com.example.mycloudmusic.util.LogUtil;
import com.example.mycloudmusic.util.ToastUtil;

/**
 * 用户登录相关功能
 */
public class BaseLoginActivity extends BaseTitleActivity {
    private static final String TAG = "BaseLoginActivity";

    /**
     * 登录
     * @param phone
     * @param email
     * @param password
     */
    protected void login(String phone, String email, String password){
        User user = new User();
        //这里虽然同时传递了手机号和邮箱
        //但服务端登录的时候有先后顺序
        user.setPhone(phone);
        user.setEmail(email);
        user.setPassword(password);
        //调用登录接口
        Api.getInstance()
                .login(user)
                .subscribe(new HttpObserver<DetailResponse<Session>>() {
                    @Override
                    public void onSucceeded(DetailResponse<Session> data) {
                        LogUtil.d(TAG,"onLoginClick success:"+data.getData().getSession());

                        //把登录成功的事件通知到AppContext
                        AppContext.getInstance().login(sp, data.getData());

                        ToastUtil.successShortToast(R.string.login_success);

                        //关闭当前界面并启动主界面
                        startActivityAfterFinnishThis(MainActivity.class);
                    }
                });
    }
}
