package com.example.mycloudmusic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.mycloudmusic.R;
import com.example.mycloudmusic.util.LogUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 忘记密码界面
 */
public class ForgetPasswordActivity extends BaseTitleActivity {

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
    }
}
