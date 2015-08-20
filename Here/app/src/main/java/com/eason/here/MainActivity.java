package com.eason.here;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.android.volley.Response;
import com.eason.here.HttpUtil.HttpRequest;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HttpRequest.initRequestQueue(this);

        HttpRequest.login("haha","123456","female","22222",new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d("MainActivity", "response : " + s);
            }
        });
    }

}
