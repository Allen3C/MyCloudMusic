package com.example.mycloudmusic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.mycloudmusic.R;
import com.example.mycloudmusic.util.StringUtil;
import com.example.mycloudmusic.util.ToastUtil;

import org.apache.commons.lang3.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册界面
 */
public class RegisterActivity extends BaseTitleActivity {

    @BindView(R.id.et_nickname)
    EditText et_nickname;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_confirm_password)
    EditText et_confirm_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @OnClick(R.id.bt_register)
    public void onRegisterClick(){
        //获取昵称
        String nickname = et_nickname.getText().toString().trim();
        if(StringUtils.isBlank(nickname)){
            ToastUtil.errorShortToast(R.string.enter_nickname);
            return;
        }
        //判断昵称格式
        if(StringUtil.isNickname(nickname)){
            ToastUtil.errorShortToast(R.string.error_nickname_format);
        }

        //获取手机号
        String phone = et_phone.getText().toString().trim();
        if(StringUtils.isBlank(phone)){
            ToastUtil.errorShortToast(R.string.enter_phone);
            return;
        }
        //判断手机号格式
        if(!StringUtil.isPhone(phone)){
            ToastUtil.errorShortToast(R.string.error_phone_format);
        }

        //获取邮箱
        String email = et_email.getText().toString().trim();
        if(StringUtils.isBlank(email)){
            ToastUtil.errorShortToast(R.string.enter_email);
            return;
        }
        //判断邮箱格式
        if(!StringUtil.isEmail(email)){
            ToastUtil.errorShortToast(R.string.error_email_format);
            return;
        }

        //获取密码
        String password = et_password.getText().toString().trim();
        if(StringUtils.isBlank(password)){
            ToastUtil.errorShortToast(R.string.enter_password);
            return;
        }
        //判断密码格式
        if(!StringUtil.isPassword(password)){
            ToastUtil.errorShortToast(R.string.error_password_format);
            return;
        }

        //获取确认密码
        String confirmPassword = et_confirm_password.getText().toString().trim();
        if(StringUtils.isBlank(confirmPassword)){
            ToastUtil.errorShortToast(R.string.enter_confirm_password);
            return;
        }
        //判断确认密码格式
        if(!StringUtil.isPassword(confirmPassword)){
            ToastUtil.errorShortToast(R.string.error_confirm_password_format);
            return;
        }

        //判断密码和确认密码是否一致
        if(!password.equals(confirmPassword)){
            ToastUtil.errorShortToast(R.string.error_confirm_password);
            return;
        }
    }
}
