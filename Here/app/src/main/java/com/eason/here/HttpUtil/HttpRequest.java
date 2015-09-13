package com.eason.here.HttpUtil;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.eason.here.model.User;

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

    public static <T> void baseHttpPostRequest(String url, final Map<String, String> map, final HttpResponseHandler httpResponseHandler, final Class<T> tClass) {
        if (queue == null){
            Log.e(TAG,"The volley queue is null");
            return;
        }

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, "volley http response Error : " + volleyError.getMessage(), volleyError);
            }
        };

        Response.Listener listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d("MainActivity", "response : " + s);
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
     * 登录
     * @param username
     * @param password
     * @param pushKey
     * @param tClass
     * @param httpResponseHandler
     * @param <T>
     */
    public static <T> void login(String username, String password, String pushKey,
                                 Class<T> tClass, HttpResponseHandler httpResponseHandler) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("username", username);
        map.put("password", password);
        map.put("pushKey", pushKey);

        baseHttpPostRequest(HttpConfig.String_Url_Login, map, httpResponseHandler, tClass);

    }

    /**
     * 注册
     * @param username
     * @param password
     * @param pushKey
     * @param nickname
     * @param tClass
     * @param httpResponseHandler
     * @param <T>
     */
    public static <T> void register(String username, String password, String pushKey, String nickname,
                                    Class<T> tClass, HttpResponseHandler httpResponseHandler) {

        Map<String,String> map = new HashMap<String,String>();
        map.put("username", username);
        map.put("password", password);
        map.put("pushKey", pushKey);
        map.put("nickname",nickname);

        baseHttpPostRequest(HttpConfig.String_Url_Register,map,httpResponseHandler, User.class);
    }

    public static <T> void checkUserIsExist(String username ,Class<T> tClass,
                                            HttpResponseHandler httpResponseHandler){

        Map<String,String> map = new HashMap<String,String>();
        map.put("username",username);

        baseHttpPostRequest(HttpConfig.String_Url_Check_User_Is_Exist,map,httpResponseHandler,tClass);
    }

}
