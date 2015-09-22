package com.eason.here.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 用于存储用户登录，设置的一些信息
 * Created by Eason on 8/28/15.
 */
public class SharePreferencesUtil {

    private static Context context = null;

    private static SharedPreferences share;
    private static SharedPreferences.Editor editor;

    public static void init(Context context){

        if (SharePreferencesUtil.context!=null)return;

        SharePreferencesUtil.context = context;
        share = context.getSharedPreferences("here",Context.MODE_PRIVATE);
        editor = share.edit();
    }


    /**
     * 保存用户登录的信息
     * @param username
     * @param password
     */
    public static void saveUserLoginInfo(String username,String password){
        editor.putString("username",username);
        editor.putString("password",password);
        editor.commit();
    }

    /**
     * 获取用户登录的账号
     * @return
     */
    public static String  getUserLoginUsername(){
        if (share==null)return null;

        return share.getString("username",null);

    }

    /**
     * 获取用户登录的密码
     * @return
     */
    public static String  getUserLoginPassword(){
        if (share==null)return null;

        return share.getString("password",null);

    }
}
