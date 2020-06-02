package com.example.mycloudmusic.activity;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.mycloudmusic.R;
import com.example.mycloudmusic.util.LogUtil;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * 登录界面测试
 */
@RunWith(AndroidJUnit4.class)
public class LoginactivityTest {
    private static final String TAG = "LoginactivityTest";

    /**
     * 当前要测试的界面
     */
    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule = new ActivityTestRule<>(LoginActivity.class);

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
    @Test
    public void testLogin(){

        try {
            //等待1秒钟
            //因为应用启动要时间
            sleep(1000);

            //现在是登录页面

            //找到登录按钮
            //ViewInteraction btLogin = onView(withId(R.id.bt_login));
            ViewInteraction btLogin = findViewById(R.id.bt_login);

            //---------不输入用户名和密码
            //点击登录按钮
            //btLogin.perform(click());
            viewClick(btLogin);

            //确认提示是否正确
            checkToast(R.string.enter_username);

            //延迟1秒
            //因为toast有时间
            sleep(1000);

            //---------输入1位用户名
            //找到用户名输入框
            ViewInteraction etUsername = findViewById(R.id.et_username);

            //点击用户名输入框
            //输入用户名
            //并关闭软键盘
            //etNickname.perform(replaceText("1"), closeSoftKeyboard());
            editTextInput(etUsername,"1");

            //点击登录按钮
            viewClick(btLogin);

            //确认提示是否正确
            checkToast(R.string.error_username_format);

            //延迟1秒
            sleep(1000);

//            //---------输入正确的邮箱
//            //清除原来的用户名
//            //editTextInput(etUsername,"");
//            clearText(etUsername);
//
//            //输入用户名
//            editTextInput(etUsername,"895960601@qq.com");
//
//            //点击登录按钮
//            viewClick(btLogin);
//
//            //确认提示是否正确
//            checkToast(R.string.enter_password);
//
//            //延迟1秒
//            sleep(1000);
//
//            //---------输入1位密码
//            //找到密码输入框
//            ViewInteraction etPassword = findViewById(R.id.et_password);
//
//            //输入密码
//            editTextInput(etPassword,"i");
//
//            //点击登录按钮
//            viewClick(btLogin);
//
//            //确认提示是否正确
//            checkToast(R.string.error_password_format);
//
//            //延迟1秒
//            sleep(1000);
//
//            //---------输入和账号匹配的密码
//            //清除密码
//            clearText(etPassword);
//
//            //输入密码
//            editTextInput(etPassword,"19941026121x");
//
//            //点击登录
//            viewClick(btLogin);
//
//            //延时3秒
//            //因为有网络请求
//            sleep(3000);
//
//            //确认提示是否正确
//
//            //判断DrawerLayout是否存在
//            //因为首页有DrawerLayout
//            //如果有就表示登陆成功
//            //并进入了首页
//
//            //找到DrawerLayout
//            ViewInteraction dl = findViewById(R.id.dl);
//
//            //判断是否显示了
//            dl.check(matches(isDisplayed()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除EdiText
     * @param view
     */
    private void clearText(ViewInteraction view) {
        editTextInput(view, "");
    }

    /**
     * 延迟1秒钟
     * @param i
     * @throws InterruptedException
     */
    private void sleep(int i) throws InterruptedException {
        Thread.sleep(i);
    }

    /**
     * 点击按钮
     * @param view
     */
    private void viewClick(ViewInteraction view) {
        view.perform(click());
    }

    /**
     * EditText输入文本
     * @param view
     * @param string
     */
    private void editTextInput(ViewInteraction view, String string) {
        view.perform(replaceText(string), closeSoftKeyboard());
    }

    /**
     * 检查toast是否存在
     * @param resourceId
     */
    private void checkToast(int resourceId) {
        onView(withText(resourceId)).inRoot(RootMatchers.withDecorView(
                Matchers.not(activityTestRule.getActivity().getWindow().getDecorView())
        )).check(matches(isDisplayed()));
    }

    /**
     * 根据id查找控件
     * @param id
     * @return
     */
    private ViewInteraction findViewById(int id) {
        return onView(withId(id));
    }
}
