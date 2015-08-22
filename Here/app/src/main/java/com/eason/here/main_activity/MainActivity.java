package com.eason.here.main_activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.eason.here.HttpUtil.HttpRequest;
import com.eason.here.HttpUtil.HttpResponseHandler;
import com.eason.here.R;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HttpRequest.initRequestQueue(this);

        HttpRequest.login("haha","123456","female","22222",null,new HttpResponseHandler(){
            @Override
            public void getResult() {

            }
        });
    }

}
