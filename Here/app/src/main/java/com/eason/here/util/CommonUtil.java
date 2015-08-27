package com.eason.here.util;

/**
 * Created by Eason on 8/28/15.
 */
public class CommonUtil {

    /**
     * 判断字符串是否是空，若为空则返回true
     * @param str
     * @return
     */
    public static boolean isEmptyString(String str){
        if (str==null||str.length()==0){
            return true;
        }else{
            return false;
        }
    }
}
