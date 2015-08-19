package com.eason.here.here;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;


public class MainActivity extends ActionBarActivity {

    private MapView mapView;
    private AMap aMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState){
        mapView = (MapView)findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.getMap();
    }
}
