package com.eason.marker.model;

import android.content.Context;

import com.eason.marker.network.HttpRequest;
import com.eason.marker.network.LoginHandler;
import com.eason.marker.util.OnLoginListener;
import com.eason.marker.util.SharePreferencesUtil;

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
    /**
     * 登录请求
     *
     * @param userAccount
     * @param userPassword
     * @param pushKey
     */
    public static void login(final String userAccount, final String userPassword, String pushKey, final OnLoginListener loginListener) {

        LoginHandler loginHandler = new LoginHandler() {
            @Override
            public void getResult() {
                if (this.resultVO == null) {
                    loginListener.loginFailedListener(ErroCode.ERROR_CODE_RESPONSE_NULL);
                    return;
                } else if (this.resultVO.getStatus() == ErroCode.ERROR_CODE_USER_OR_PASSWORD_INVALID) {
                    loginListener.loginFailedListener(ErroCode.ERROR_CODE_USER_OR_PASSWORD_INVALID);
                    return;
                } else if (this.resultVO.getStatus() == ErroCode.ERROR_CODE_CORRECT) {
                    SharePreferencesUtil.saveUserLoginInfo(userAccount, userPassword);
                    loginListener.loginListener();
                }else{
                    loginListener.loginFailedListener(ErroCode.ERROR_CODE_CLIENT_DATA_ERROR);
                }
            }
        };

        HttpRequest.login(userAccount, userPassword, pushKey, loginHandler);
    }

    public static void logout(){
        user = null;
        SharePreferencesUtil.saveUserLoginInfo("","");
    }
}
