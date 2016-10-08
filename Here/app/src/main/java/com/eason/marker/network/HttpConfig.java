package com.eason.marker.network;

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

    //根据时间获取用户帖子
    public static final String String_Url_Get_Post_By_Time = Url_Domain + "get_current_post/";

    //根据时间获取用户帖子
    public static final String String_Url_Only_Get_Post_By_Time = Url_Domain + "get_current_one_hour_post/";

    //根据时间获取用户帖子
    public static final String String_Url_Get_Current_Post_Only = Url_Domain + "get_the_current_post/";

    //根据帖子id和用户username删除帖子
    public static final String String_Url_Delete_Post_By_Id = Url_Domain + "delete_post_by_id/";

    //举报帖子
    public static final String String_Url_Report_Post = Url_Domain + "report_post/";

    //提交App建议
    public static final String String_Url_Report_Issue = Url_Domain + "report_issue/";

    //由userid获取用户信息
    public static final String String_Url_Get_User_Info_By_UserId = Url_Domain + "get_user_info_by_useid/";

    //由用户userid获取notificationMessa
    public static final String String_Url_Get_Notification_Message_By_UserName = Url_Domain + "get_notification_message_by_username/";

    //获取系统通知
    public static final String String_Url_Get_System_Notification_Message = Url_Domain + "get_system_notification_message/";

    //上传推送Id
    public static final String String_Url_Update_Push_Key = Url_Domain + "update_push_key/";
}
