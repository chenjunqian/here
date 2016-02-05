package com.eason.marker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import com.eason.marker.emchat.EMChatUtil;
import com.eason.marker.http_util.HttpRequest;
import com.eason.marker.main_activity.MainActivity;
import com.eason.marker.model.LoginStatus;
import com.eason.marker.util.CommonUtil;
import com.eason.marker.util.LogUtil;
import com.eason.marker.util.SharePreferencesUtil;
import com.eason.marker.util.OnLoginListener;
import com.eason.marker.util.WidgetUtil.GreenToast;

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
            CommonUtil.login(username, password, "", new OnLoginListener() {

                @Override
                public void loginListener() {
                    new Timer().schedule(task,2000);
                }

                @Override
                public void loginFailedListener(int info) {
                    GreenToast.makeText(SplashActivity.this,"登录失败", Toast.LENGTH_SHORT).show();
                    new Timer().schedule(task,2000);
                }
            });

            //登录环信SDK
            EMChatManager.getInstance().login(username, password, new EMCallBack() {//回调
                @Override
                public void onSuccess() {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            EMGroupManager.getInstance().loadAllGroups();
                            EMChatManager.getInstance().loadAllConversations();
                            EMChatUtil.isConnectedEMChatServer = true;
                            LogUtil.d("SplashActivity", "登陆聊天服务器成功！");
                        }
                    });
                }

                @Override
                public void onProgress(int progress, String status) {

                }

                @Override
                public void onError(int code, String message) {
                    EMChatUtil.isConnectedEMChatServer = false;
                    LogUtil.d("SplashActivity", "登陆聊天服务器失败！"+" code "+code+" message : "+message);
                }
            });

        }else{
            new Timer().schedule(task,2000);
        }


    }
}
