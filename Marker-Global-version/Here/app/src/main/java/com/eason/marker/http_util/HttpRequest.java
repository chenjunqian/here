package com.eason.marker.http_util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.eason.marker.R;
import com.eason.marker.model.NotificationMessageList;
import com.eason.marker.model.Post;
import com.eason.marker.model.PostList;
import com.eason.marker.model.PostTag;
import com.eason.marker.model.User;
import com.eason.marker.util.LogUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Eason on 8/20/15.
 */
public class HttpRequest {

    private static final String TAG = "HttpRequest";

    private static RequestQueue queue;

    public static void initRequestQueue(Context context) {
        queue = Volley.newRequestQueue(context);
    }


    /**
     * Volley框架的网络请求
     *
     * @param url
     * @param map
     * @param httpResponseHandler
     * @param tClass
     * @param <T>
     */
    public static <T> void baseHttpPostRequest(String url, final Map<String, String> map,
                                               final HttpResponseHandler httpResponseHandler, final Class<T> tClass) {
        if (queue == null) {
            LogUtil.e(TAG, "The volley queue is null");
            return;
        }

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.e(TAG, "volley http response Error : " + volleyError.getMessage() + " " + volleyError);
            }
        };

        Response.Listener listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.d("HttpRequest", "response : " + s);
                httpResponseHandler.response(s, tClass);

            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };

        queue.add(stringRequest);
    }

    /**
     * 上传文件到服务器
     *
     * @param url
     * @param map
     * @param filePath
     * @param httpResponseHandler
     * @param tClass
     * @param <T>
     */
    public static <T> void uploadFileRequest(String url, final Map<String, String> map, String filePath,
                                             final HttpResponseHandler httpResponseHandler, final Class<T> tClass) {
        ThreadPoolUtils.execute(new UpLoadFileRunable<T>(url, map, filePath, "file", httpResponseHandler, tClass));
    }

    /**
     * 登录
     *
     * @param username
     * @param password
     * @param pushKey
     * @param httpResponseHandler
     */
    public static void login(String username, String password, String pushKey, HttpResponseHandler httpResponseHandler) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("username", username);
        map.put("password", password);
        map.put("pushKey", pushKey);

        baseHttpPostRequest(HttpConfig.String_Url_Login, map, httpResponseHandler, User.class);

    }

    /**
     * 注册
     *
     * @param username
     * @param password
     * @param pushKey
     * @param nickname
     * @param httpResponseHandler
     */
    public static void register(String username, String password, String pushKey, String nickname, String birthday, String gender,
                                HttpResponseHandler httpResponseHandler) {

        Map<String, String> map = new HashMap<String, String>();
        map.put("username", username);
        map.put("password", password);
        map.put("pushKey", pushKey);
        map.put("nickname", nickname);
        map.put("gender", gender);
        map.put("birthday", birthday);

        baseHttpPostRequest(HttpConfig.String_Url_Register, map, httpResponseHandler, User.class);
    }

    /**
     * 判断用户是否存在
     *
     * @param username
     * @param tClass
     * @param httpResponseHandler
     * @param <T>
     */
    public static <T> void checkUserIsExist(String username, Class<T> tClass,
                                            HttpResponseHandler httpResponseHandler) {

        Map<String, String> map = new HashMap<String, String>();
        map.put("username", username);

        baseHttpPostRequest(HttpConfig.String_Url_Check_User_Is_Exist, map, httpResponseHandler, tClass);
    }

    /**
     * 发帖
     *
     * @param longitude
     * @param latitude
     * @param city
     * @param username
     * @param tag
     */
    public static void uploadPostWithoutImage(String longitude, String latitude, String city, String cityCode, String address, String username, String tag,
                                              HttpResponseHandler httpResponseHandler) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("longitude", longitude);
        map.put("latitude", latitude);
        map.put("city", city);
        map.put("cityCode", cityCode);
        map.put("address", address);
        map.put("username", username);
        map.put("time", String.valueOf(System.currentTimeMillis()));
        map.put("tag", tag);

        baseHttpPostRequest(HttpConfig.String_Url_Update_Location, map, httpResponseHandler, Post.class);
    }

    /**
     * 上传头像
     *
     * @param username
     * @param imageName
     * @param path
     * @param httpResponseHandler
     */
    public static void uploadAvatar(String username, String imageName, String path, final HttpResponseHandler httpResponseHandler) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("username", username);
        map.put("avatar", imageName);

        uploadFileRequest(HttpConfig.String_Url_Upload_Avatar, map, path, httpResponseHandler, Object.class);
    }

    /**
     * 获取发帖标签
     *
     * @param httpResponseHandler
     */
    public static void getPostTag(final HttpResponseHandler httpResponseHandler) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("tag", "tag");
        baseHttpPostRequest(HttpConfig.String_Url_Get_Post_Tag, map, httpResponseHandler, PostTag.class);
    }

    /**
     * 获取帖子列表
     *
     * @param lon
     * @param lat
     * @param city
     * @param httpResponseHandler
     */
    public static void getPost(double lon, double lat, String city,int index, HttpResponseHandler httpResponseHandler) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("longitude", String.valueOf(lon));
        map.put("latitude", String.valueOf(lat));
        map.put("city", city);
        map.put("index", String.valueOf(index));
        baseHttpPostRequest(HttpConfig.String_Url_Get_Post, map, httpResponseHandler, PostList.class);
    }

    /**
     * 获取用户信息
     *
     * @param username
     * @param httpResponseHandler
     */
    public static void getUserByUsername(String username, HttpResponseHandler httpResponseHandler) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("username", username);
        baseHttpPostRequest(HttpConfig.String_Url_Get_User_Info_By_Username, map, httpResponseHandler, User.class);
    }

    /**
     * 修改用户信息
     *
     * @param username
     * @param password
     * @param gender
     * @param birthday
     * @param nickname
     * @param userid
     * @param httpResponseHandler
     */
    public static void modifyUserInfo(String username, String password, String gender,
                                      String birthday, String nickname, String userid,
                                      String simpleProfile,String longProfile,
                                      HttpResponseHandler httpResponseHandler) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("username", username);
        map.put("password", password);
        map.put("gender", gender);
        map.put("birthday", birthday);
        map.put("nickname", nickname);
        map.put("userid", userid);
        map.put("simpleProfile",simpleProfile);
        map.put("longProfile",longProfile);
        baseHttpPostRequest(HttpConfig.String_Url_Change_User_Info, map, httpResponseHandler, User.class);

    }

    /**
     * 限制图片的宽高，如果w，h的参数为0，则不压缩
     * @param imageView
     * @param url
     * @param w
     * @param h
     */
    public static void loadImage(ImageView imageView, String url,int w,int h) {

        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView,
                R.drawable.default_avatar_ori, R.drawable.default_avatar_ori);
        ImageLoader imageLoader = new ImageLoader(queue, new BitmapCache());

        if (w<=0||h<=0){
            imageLoader.get(url, listener);
        }else{
            imageLoader.get(url, listener,w,h);
        }
    }

    /**
     * 缓存下载图片
     */
    private static class BitmapCache implements ImageLoader.ImageCache {

        private LruCache<String, Bitmap> mCache;

        public BitmapCache() {
            int maxSize = 10 * 1024 * 1024;
            mCache = new LruCache<String, Bitmap>(maxSize) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    return bitmap.getRowBytes() * bitmap.getHeight();
                }
            };
        }

        @Override
        public Bitmap getBitmap(String url) {
            return mCache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            mCache.put(url, bitmap);
        }

    }

    /**
     * 根据用户名获取用户帖子
     * @param username
     * @param httpResponseHandler
     */
    public static void getPosyByUsername(String username,HttpResponseHandler httpResponseHandler){
        Map<String,String> map = new HashMap<String,String>();
        map.put("username", username);
        baseHttpPostRequest(HttpConfig.String_Url_Get_Post_By_Username, map, httpResponseHandler, PostList.class);
    }

    /**
     * 根据时间获取帖子
     * @param time
     * @param httpResponseHandler
     */
    public static void getpostByTime(String time,int index,HttpResponseHandler httpResponseHandler){
        Map<String,String> map = new HashMap<String,String>();
        map.put("time",time);
        map.put("index",String.valueOf(index));
        baseHttpPostRequest(HttpConfig.String_Url_Get_Post_By_Time, map, httpResponseHandler, PostList.class);
    }

    /**
     * 获取最近一小时的帖子
     * @param time
     * @param httpResponseHandler
     */
    public static void getOneHourPostOnly(String time,int index,HttpResponseHandler httpResponseHandler){
        Map<String,String> map = new HashMap<String,String>();
        map.put("time",time);
        map.put("index",String.valueOf(index));
        baseHttpPostRequest(HttpConfig.String_Url_Only_Get_Post_By_Time, map, httpResponseHandler, PostList.class);
    }

    /**
     * 获取最新的帖子
     * @param httpResponseHandler
     */
    public static void getCurrentPostOnly(int index,HttpResponseHandler httpResponseHandler){
        Map<String,String> map = new HashMap<String,String>();
        map.put("index",String.valueOf(index));
        baseHttpPostRequest(HttpConfig.String_Url_Get_Current_Post_Only, map, httpResponseHandler, PostList.class);
    }

    /**
     * 根据帖子id与用户username来删除帖子
     */
    public static void deletePostById(String username,String postid,HttpResponseHandler httpResponseHandler){
        Map<String,String> map = new HashMap<String,String>();
        map.put("username",username);
        map.put("postid",postid);
        baseHttpPostRequest(HttpConfig.String_Url_Delete_Post_By_Id, map, httpResponseHandler, Object.class);
    }

    /**
     * 举报帖子
     * @param content
     * @param username
     * @param postid
     * @param httpResponseHandler
     */
    public static void reportPost(String content,String username,String postid,HttpResponseHandler httpResponseHandler){
        Map<String,String> map = new HashMap<String,String>();
        map.put("reporter",username);
        map.put("postid",postid);
        map.put("content",content);
        map.put("time", String.valueOf(System.currentTimeMillis()));
        baseHttpPostRequest(HttpConfig.String_Url_Report_Post, map, httpResponseHandler, Object.class);
    }

    /**
     * 回馈用户建议
     * @param content
     * @param username
     * @param httpResponseHandler
     */
    public static void reportIssue(String content,String username,HttpResponseHandler httpResponseHandler){
        Map<String,String> map = new HashMap<String,String>();
        map.put("reporter",username);
        map.put("content",content);
        map.put("time", String.valueOf(System.currentTimeMillis()));
        baseHttpPostRequest(HttpConfig.String_Url_Report_Issue, map, httpResponseHandler, Object.class);
    }

    /**
     * 根据用户id获取用户信息
     * @param userid
     * @param httpResponseHandler
     */
    public static void getUserByUserId(String userid,HttpResponseHandler httpResponseHandler){
        Map<String,String> map = new HashMap<String,String>();
        map.put("userid",userid);
        baseHttpPostRequest(HttpConfig.String_Url_Get_User_Info_By_UserId, map, httpResponseHandler, User.class);
    }

    /**
     * 根据用户id获取通知
     * @param httpResponseHandler
     */
    public static void getSystemNotificationMessage(int index, HttpResponseHandler httpResponseHandler){
        Map<String,String> map = new HashMap<String,String>();
        map.put("index",String.valueOf(index));
        baseHttpPostRequest(HttpConfig.String_Url_Get_System_Notification_Message, map, httpResponseHandler, NotificationMessageList.class);
    }

    /**
     * 上传推送Id
     * @param username
     * @param httpResponseHandler
     */
    public static void updataPushKeyByUsername(String username,String pushkey, HttpResponseHandler httpResponseHandler){
        Map<String,String> map = new HashMap<String,String>();
        map.put("username",username);
        map.put("pushkey",pushkey);
        baseHttpPostRequest(HttpConfig.String_Url_Update_Push_Key, map, httpResponseHandler, Object.class);
    }
}
