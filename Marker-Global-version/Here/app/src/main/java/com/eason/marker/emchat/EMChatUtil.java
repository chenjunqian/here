package com.eason.marker.emchat;

import com.easemob.EMCallBack;
import com.easemob.EMConnectionListener;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.eason.marker.util.LogUtil;

/**
 * Created by Eason on 2/2/16.
 */
public class EMChatUtil {

    public static boolean isConnectedEMChatServer;

    public static void init(){
        EMChat.getInstance().setAutoLogin(false);
    }

    /**
     * 当环信断线时，设置自动连接
     */
    public static void autoReConnectEMChat(){
        //注册一个监听连接状态的listener
        EMChatManager.getInstance().addConnectionListener(new MyConnectionListener());
    }

    //实现ConnectionListener接口
    static class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
            //已连接到服务器
            isConnectedEMChatServer = true;
        }
        @Override
        public void onDisconnected(final int error) {
            isConnectedEMChatServer = false;
        }
    }

    public static void logoutEMChat(){
        //此方法为异步方法
        EMChatManager.getInstance().logout(new EMCallBack() {

            @Override
            public void onSuccess() {
                LogUtil.d("EMChatUtil", "登出成功！");
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {

                LogUtil.d("EMChatUtil", "登出失败！"+" code "+code+" message : "+message);
            }
        });
    }

}
