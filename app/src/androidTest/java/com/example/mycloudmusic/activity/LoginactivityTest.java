package com.example.mycloudmusic.activity;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.mycloudmusic.util.LogUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 登录界面测试
 */
@RunWith(AndroidJUnit4.class)
public class LoginactivityTest {
    private static final String TAG = "LoginactivityTest";

    /**
     * 每个测试方法都是独立的
     * 也就是说每个测试方法都会先显示当前界面
     */
    @Test
    public void testOther(){
        LogUtil.d(TAG, "testOther");
    }

    /**
     * 测试登录
     */
    public void testLogin(){

    }
}
