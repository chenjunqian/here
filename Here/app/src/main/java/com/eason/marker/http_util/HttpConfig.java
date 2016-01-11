package com.eason.marker.http_util;

/**
 * Created by Eason on 9/9/15.
 */
public class HttpConfig {
    //本地测试环境，且是启动虚拟机的情况下
    public static final String Url_Domain = "http://42.96.208.219/";
//    public static final String Url_Domain = "http://10.0.2.2:8080/";

    //登录
    public static final String String_Url_Login = Url_Domain + "login/";

    //注册
    public static final String String_Url_Register = Url_Domain + "register/";

    //发帖上传位置信息
    public static final String String_Url_Update_Location = Url_Domain + "update_user_location/";

    //判断用户是否存在
    public static final String String_Url_Check_User_Is_Exist = Url_Domain + "check_user_is_exist/";

    //上传头像
    public static final String String_Url_Upload_Avatar = Url_Domain + "upload_avatar/";

    //获取发帖标签
    public static final String String_Url_Get_Post_Tag = Url_Domain + "get_post_tag/";

    //获取当前位置的帖子
    public static final String String_Url_Get_Post = Url_Domain + "get_post_by_location/";

    //由username获取用户信息
    public static final String String_Url_Get_User_Info_By_Username = Url_Domain + "get_user_info_by_username/";

    //修改用户信息
    public static final String String_Url_Change_User_Info = Url_Domain + "modify_user_info/";

    //下载图片前缀
    public static final String String_Url_Media = Url_Domain + "media/";

    //根据用户名获取用户帖子
    public static final String String_Url_Get_Post_By_Username = Url_Domain + "get_post_by_username/";
}