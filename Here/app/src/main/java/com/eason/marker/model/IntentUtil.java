package com.eason.marker.model;

/**
 * 相关Intent的参数，方法都写在这个类里
 *
 * Created by Eason on 8/28/15.
 */
public class IntentUtil {

    /**
     * 由登录页面跳转去注册页面的requestCode
     */
    public static final int LOGIN_TO_REGISTER_REQUEST_CODE = 0x1;

    /**
     * 设置页的fragment
     */
    public static final int PROFLIE_FRAGMENT = 0X2;

    /**
     * 主页地图fragment
     */
    public static final int MAIN_MAP_FRAGMENT = 0x3;

    /**
     * 附近事件列表fragment
     */
    public static final int NEAR_USER_FRAGMENT = 0x4;

    /**
     * 由主页页跳转到登录呀
     */
    public static final int MAIN_TO_LOGIN_PAGE = 0x5;

    /**
     * 跳转到注册页面的个人信息填写Fragment
     */
    public static final int REGISTER_USER_INFO_PAGE = 0x6;

    /**
     * 跳转到注册页面的生日信息填写Fragment
     */
    public static final int REGISTER_USER_BIRTHDAY_PAGE = 0x7;

    /**
     * 跳转到系统相册
     */
    public static final int SYSTEM_ABLUM_REQUEST_CODE = 0x8;

    /**
     * 跳转到我的帖子的列表
     */
    public static final int MY_POST_FRAGMENT = 0x9;

    /**
     * 跳转到发帖也
     */
    public static final int TO_PUBLISH_PAGE = 0x12;

}