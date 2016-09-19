package com.eason.marker.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.eason.marker.R;
import com.eason.marker.emchat.EMChatUtil;
import com.eason.marker.http_util.HttpRequest;
import com.eason.marker.controller.main_activity.MainActivity;
import com.eason.marker.model.LoginStatus;
import com.eason.marker.util.CommonUtil;
import com.eason.marker.util.OnLoginListener;
import com.eason.marker.util.SharePreferencesUtil;
import com.eason.marker.view.GreenToast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Eason on 9/22/15.
 */
public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_layout);
        initAppUtil(SplashActivity.this);
        autoLogin();
    }

    /**
     * 初始化工具类
     * @param context
     */
    private void initAppUtil(Context context){
        SharePreferencesUtil.init(context);
        LoginStatus.init(context);
        HttpRequest.initRequestQueue(context);
        EMChatUtil.init();
    }

    /**
     * 自动登录，如果没有登录过就，定时跳转
     */
    private void autoLogin(){

        String username = SharePreferencesUtil.getUserLoginUsername();
        String password = SharePreferencesUtil.getUserLoginPassword();

        final TimerTask task = new TimerTask() {

            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        };

        if (!CommonUtil.isEmptyString(username)&&!CommonUtil.isEmptyString(password)){
            //登录应用
            LoginStatus.login(username, password, "", new OnLoginListener() {

                @Override
                public void loginListener() {
                    new Timer().schedule(task,2000);
                }

                @Override
                public void loginFailedListener(int info) {
                    GreenToast.makeText(SplashActivity.this,getResources().getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
                    new Timer().schedule(task,2000);
                }
            });

        }else{
            new Timer().schedule(task,2000);
        }


    }
}
