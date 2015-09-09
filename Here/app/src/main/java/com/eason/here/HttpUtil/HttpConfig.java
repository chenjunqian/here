package com.eason.here.HttpUtil;

/**
 * Created by Eason on 9/9/15.
 */
public class HttpConfig {
    //本地测试环境，且是启动虚拟机的情况下
    public static final String Url_Domain = "http://10.0.2.2:3306/";

    //登录
    public static final String String_Url_Login = Url_Domain + "login";

    //注册
    public static final String String_Url_Register = Url_Domain + "register";

    //发帖上传位置信息
    public static final String String_Url_Update_Location = Url_Domain + "update_user_location";

    //判断用户是否存在
    public static final String String_Url_Check_User_Is_Exist = Url_Domain + "checkUserIsExist";
}
