package com.eason.here;

import android.content.Intent;
import android.os.Bundle;

import com.eason.here.main_activity.MainActivity;
import com.eason.here.util.CommonUtil;
import com.eason.here.util.SharePreferencesUtil;
import com.eason.here.util.OnLoginListener;

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
        autoLogin();
    }

    /**
     * 自动登录，如果没有登录过就，定时跳转
     */
    private void autoLogin(){

        String username = SharePreferencesUtil.getUserLoginUsername();
        String password = SharePreferencesUtil.getUserLoginPassword();
        if (!CommonUtil.isEmptyString(username)&&!CommonUtil.isEmptyString(password)){
            CommonUtil.login(username, password, "", new OnLoginListener() {

                @Override
                public void loginListener() {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            });
        }else{

            TimerTask task = new TimerTask() {

                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            };

            new Timer().schedule(task,2000);
        }


    }
}
