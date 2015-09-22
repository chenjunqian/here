package com.eason.here.model;

import android.content.Context;

/**
 * 用户的登录状态
 *
 * Created by Eason on 8/28/15.
 */
public class LoginStatus {

    private static User user;

    private static String pushKey = "";

    private static Context context;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        LoginStatus.user = user;
    }

    public static String getPushKey() {
        return pushKey;
    }

    public static void setPushKey(String pushKey) {
        LoginStatus.pushKey = pushKey;
    }

    public static void logout(){
        user = null;
    }

    public static void init(Context context){
        LoginStatus.context = context;
    }

    /**
     * 检测当前是否是用户登录模式,如果是登录模式，返回true
     *
     * @return
     */
    public static boolean getIsUserMode(){
        if (user!=null){
            return true;
        }else{
            return false;
        }
    }

}
