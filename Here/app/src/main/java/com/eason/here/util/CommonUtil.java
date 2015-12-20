package com.eason.here.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.util.Log;

import com.eason.here.HttpUtil.HttpRequest;
import com.eason.here.HttpUtil.LoginHandler;
import com.eason.here.model.ErroCode;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Eason on 8/28/15.
 */
public class CommonUtil {

    /**
     * 判断字符串是否是空，若为空则返回true
     *
     * @param str
     * @return
     */
    public static boolean isEmptyString(String str) {
        if (str == null || str.length() == 0) {
            return true;
        } else {
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

    private static long mLastClickTime = 0;

    /**
     * 防止视图重复点击
     *
     * @return boolean
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - mLastClickTime;
        if (0 < timeD && timeD < 500) {
            return true;
        }

        mLastClickTime = time;

        return false;
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

    /**
     * 从给定的路径加载图片，并指定是否自动旋转方向
     *
     * @param imgPath         图片路径
     * @param adjustOritation 是否自动旋转
     * @return Bitmap 图片bm
     */
    public static Bitmap loadBitmap(String imgPath, boolean adjustOritation) {
        if (!adjustOritation) {
            return loadBitmap(imgPath);
        } else {
            Bitmap bm = loadBitmap(imgPath);
            int degrees = getImgRotateDegree(imgPath);
            return rotateImg(bm, degrees);
        }
    }

    /**
     * 从给定路径加载图片
     */
    public static Bitmap loadBitmap(String imgPath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(imgPath, options);
        return bitmap;
    }

    /**
     * 从给定的路径加载图片，并指定是否自动旋转方向
     *
     * @param imgBm 图片
     * @return Bitmap 图片bm
     */
    public static Bitmap rotateImg(Bitmap imgBm, int degrees) {
        if (degrees != 0) {
            // 旋转图片
            Matrix m = new Matrix();
            m.postRotate(degrees);
            imgBm = Bitmap.createBitmap(imgBm, 0, 0, imgBm.getWidth(),
                    imgBm.getHeight(), m, true);
        }
        return imgBm;
    }

    public static int getImgRotateDegree(String imgPath) {
        int degree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imgPath);
        } catch (IOException e) {
            Log.e("CommonUtil", "图片信息获取失败", e);
            exif = null;
        }
        if (exif != null) {
            // 读取图片中相机方向信息
            int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);
            // 计算旋转角度
            switch (ori) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                default:
                    degree = 0;
                    break;
            }
        }
        return degree;
    }

    /**
     * 从路径获取文件名称
     *
     * @param @param  filePath
     * @param @return
     * @return String    返回类型
     * @Title: getFileNameFromFilePath
     */
    public static String getFileNameFromFilePath(String filePath) {
        String result = filePath;
        if (filePath.indexOf("/") > 0) {
            String[] fileArr = filePath.split("/");
            result = fileArr[fileArr.length - 1];
        }

        return result;
    }

    /**
     * 由时间戳获取年月日,如果时间是今天，则转换为 HH:mm
     *
     * @param currentTimeMillis
     * @return
     */
    public static String formatTimeMillis(long currentTimeMillis) {
        boolean sameYear ;
        String todaySDF = "HH:mm";
        String yesterdaySDF = "昨天";
        String beforeYesterdaySDF = "前天";
        String otherSDF = "MM-dd";
        String otherYearSDF = "yyyy-MM-dd";
        SimpleDateFormat sfd ;
        String time ;

        Calendar dateCalendar = Calendar.getInstance();
        Date date = new Date(Long.valueOf(currentTimeMillis));
        dateCalendar.setTime(date);

        Date now = new Date();
        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.setTime(now);
        todayCalendar.set(Calendar.HOUR_OF_DAY, 0);
        todayCalendar.set(Calendar.MINUTE, 0);

        if (dateCalendar.get(Calendar.YEAR) == todayCalendar.get(Calendar.YEAR)) {
            sameYear = true;
        } else {
            sameYear = false;
        }

        sfd = new SimpleDateFormat(todaySDF);
        if (dateCalendar.after(todayCalendar)) {// 判断是不是今天
            time = sfd.format(date);
            return time;
        } else {
            todayCalendar.add(Calendar.DATE, -1);
            if (dateCalendar.after(todayCalendar)) {// 判断是不是昨天
                time = yesterdaySDF+" "+sfd.format(date);//格式为 昨天 HH:mm
                return time;
            }
            todayCalendar.add(Calendar.DATE, -2);
            if (dateCalendar.after(todayCalendar)) {// 判断是不是前天
                time = beforeYesterdaySDF+" "+sfd.format(date);//格式为 前天 HH:mm
                return time;
            }
        }

        if (sameYear) {
            sfd = new SimpleDateFormat(otherSDF);
            time = sfd.format(date);
        } else {
            sfd = new SimpleDateFormat(otherYearSDF);
            time = sfd.format(date);
        }

        return time;
    }

}
