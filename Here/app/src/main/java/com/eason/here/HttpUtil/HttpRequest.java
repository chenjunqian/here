package com.eason.here.HttpUtil;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.eason.here.model.Post;
import com.eason.here.model.PostList;
import com.eason.here.model.PostTag;
import com.eason.here.model.User;
import com.eason.here.util.LogUtil;

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
        map.put("time",String.valueOf(System.currentTimeMillis()));
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
     * @param httpResponseHandler
     */
    public static void getPostTag(final HttpResponseHandler httpResponseHandler){
        Map<String, String> map = new HashMap<String, String>();
        map.put("tag", "tag");
        baseHttpPostRequest(HttpConfig.String_Url_Get_Post_Tag, map, httpResponseHandler, PostTag.class);
    }

    /**
     * 获取帖子列表
     * @param lon
     * @param lat
     * @param city
     * @param httpResponseHandler
     */
    public static void getPost(double lon ,double lat ,String city ,HttpResponseHandler httpResponseHandler){
        Map<String, String> map = new HashMap<String, String>();
        map.put("longitude", String.valueOf(lon));
        map.put("latitude", String.valueOf(lat));
        map.put("city", city);
        baseHttpPostRequest(HttpConfig.String_Url_Get_Post, map, httpResponseHandler, PostList.class);
    }

    /**
     * 获取用户信息
     * @param username
     * @param httpResponseHandler
     */
    public static void getUserByUsername(String username,HttpResponseHandler httpResponseHandler){
        Map<String, String> map = new HashMap<String, String>();
        map.put("username",username);
        baseHttpPostRequest(HttpConfig.String_Url_Get_User_Info_By_Username, map, httpResponseHandler, User.class);
    }
}
