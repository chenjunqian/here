package com.eason.here.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
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
}
