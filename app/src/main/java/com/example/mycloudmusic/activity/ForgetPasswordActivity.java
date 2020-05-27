package com.example.mycloudmusic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.mycloudmusic.R;
import com.example.mycloudmusic.domain.User;
import com.example.mycloudmusic.domain.response.BaseResponse;
import com.example.mycloudmusic.listener.HttpObserver;
import com.example.mycloudmusic.network.Api;
import com.example.mycloudmusic.util.LogUtil;
import com.example.mycloudmusic.util.StringUtil;
import com.example.mycloudmusic.util.ToastUtil;

import org.apache.commons.lang3.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 忘记密码界面
 */
public class ForgetPasswordActivity extends BaseLoginActivity {

    private static final String TAG = "ForgetPasswordActivity";
    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_code)
    EditText et_code;
    @BindView(R.id.bt_send_code)
    Button bt_send_code;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_confirm_password)
    EditText et_confirm_password;
    private String phone;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
    }

    /**
     * 发送验证码按钮点击
     */
    @OnClick(R.id.bt_send_code)
    public void onSendCodeClick() {
        LogUtil.d(TAG, "onSendCodeClick");
    }

    /**
     * 找回密码按钮点击
     */
    @OnClick(R.id.bt_forget_password)
    public void onResetPasswordClick() {
        LogUtil.d(TAG, "onResetPasswordClick");

        //用户名
        String username = et_username.getText().toString().trim();
        if (StringUtils.isBlank(username)) {
            ToastUtil.errorShortToast(R.string.enter_username);
            return;
        }
        if (!(StringUtil.isPhone(username) || StringUtil.isEmail(username))) {
            ToastUtil.errorShortToast(R.string.error_username_format);
            return;
        }

        //验证码
        String code = et_code.getText().toString().trim();
        if (StringUtils.isBlank(code)) {
            ToastUtil.errorShortToast(R.string.enter_code);
            return;
        }
        if (!StringUtil.isCode(code)) {
            ToastUtil.errorShortToast(R.string.error_code_format);
            return;
        }

        //密码
        String password = et_password.getText().toString().trim();
        if (StringUtils.isBlank(password)) {
            ToastUtil.errorShortToast(R.string.enter_password);
            return;
        }
        if (!StringUtil.isPassword(password)) {
            ToastUtil.errorShortToast(R.string.error_password_format);
            return;
        }

        //确认密码
        String confirmPassword = et_confirm_password.getText().toString().trim();
        if (StringUtils.isBlank(confirmPassword)) {
            ToastUtil.errorShortToast(R.string.enter_confirm_password);
            return;
        }

        //判断密码和确认密码是否一样
        if (!password.equals(confirmPassword)) {
            ToastUtil.errorShortToast(R.string.error_confirm_password);
            return;
        }

        //判断是手机号还是邮箱
        if (StringUtil.isPhone(username)) {
            //手机号
            phone = username;
        } else {
            //邮箱
            email = username;
        }

        //发送找回密码请求
        User data = new User();

        //将信息设置到对象上
        data.setPhone(phone);
        data.setEmail(email);
        data.setCode(code);
        data.setPassword(password);

        //调用接口
        Api.getInstance().resetPassword(data)
                .subscribe(new HttpObserver<BaseResponse>() {
                    @Override
                    public void onSucceeded(BaseResponse data) {
                        //重置成功

                        //调用父类登录方法
                        login(phone, email, password);
                    }
                });
    }
}
