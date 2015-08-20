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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Eason on 8/20/15.
 */
public class HttpRequest {

    private static final String TAG = "HttpRequest";

    private static RequestQueue queue;

    public static void initRequestQueue(Context context){
        queue = Volley.newRequestQueue(context);
    }

    public static void baseHttpRequest(String url,final Map<String,String> map,Response.Listener<String> listener){
        if (queue==null)return;

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG,"volley http response Error : "+volleyError.getMessage(), volleyError);
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, listener, errorListener){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };

        queue.add(stringRequest);
    }


    public static void login(String username,String password,String gender,String pushKey,Response.Listener<String> listener){
        Map<String,String> map = new HashMap<String, String>();
        map.put("username",username);
        map.put("password",password);
        map.put("gender",gender);
        map.put("pushKey",pushKey);

        baseHttpRequest("http://www.baidu.com", map, listener);

    }
}
