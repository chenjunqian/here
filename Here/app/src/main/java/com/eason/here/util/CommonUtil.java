package com.eason.here.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

import com.eason.here.HttpUtil.HttpRequest;
import com.eason.here.HttpUtil.LoginHandler;
import com.eason.here.model.ErroCode;
import com.eason.here.model.User;
import com.eason.here.util.WidgetUtil.OnLoginListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    //判断String类型是否是有效的邮箱
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@" +
                "((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    /**
     * 判断是否是正确的手机号码
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^1[3,5,7,8]\\d{9}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * Drawable转化为Bitmap
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
                .getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;

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
                    return;
                } else if (this.resultVO.getStatus() == ErroCode.ERROR_CODE_USER_OR_PASSWORD_INVALID) {
                    return;
                } else if (this.resultVO.getStatus() == ErroCode.ERROR_CODE_CORRECT) {
                    SharePreferencesUtil.saveUserLoginInfo(userAccount, userPassword);
                    loginListener.loginListener();
                }
            }
        };

        HttpRequest.login(userAccount, userPassword, pushKey, User.class, loginHandler);
    }
}
