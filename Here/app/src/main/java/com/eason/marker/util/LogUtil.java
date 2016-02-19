package com.eason.marker.util;

import android.util.Log;

/**
 * 统一使用的Log工具类
 * Created by Eason on 9/17/15.
 */
public class LogUtil {

    /**
     * 当应用打包时，将debug该为false
     */
    private static boolean debug = false;

    public static void d(String tag,String msg){
        if (!debug)return;
        Log.d(tag,msg);
    }

    public static void e(String tag,String msg){
        if (!debug)return;
        Log.e(tag,msg);
    }

}
