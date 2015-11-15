package com.eason.here.main_activity;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.CircleOptions;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.eason.here.BaseFragment;
import com.eason.here.HttpUtil.HttpRequest;
import com.eason.here.HttpUtil.HttpResponseHandler;
import com.eason.here.R;
import com.eason.here.model.ErroCode;
import com.eason.here.model.LocationInfo;
import com.eason.here.model.LoginStatus;
import com.eason.here.model.Post;
import com.eason.here.model.PostList;
import com.eason.here.publish_location_activity.PublishActivity;
import com.eason.here.util.CommonUtil;
import com.eason.here.util.WidgetUtil.GreenToast;

import java.util.List;

/**
 * Created by Eason on 9/6/15.
 */
public class MainMapFragment extends BaseFragment implements LocationSource, AMapLocationListener {

    private final String TAG = "MainMapFragment";
    private MapView mapView;
    private AMap aMap;
    private LocationSource.OnLocationChangedListener mListener;
    private LocationManagerProxy mAMapLocationManager;
    private MarkerOptions myMarkOption;
    private Marker myMark;
    private LatLng userCurrentLatLng;

    private Double geoLat;
    private Double geoLon;

    private ImageButton publishButton;

    private boolean isFirstGetPost = true;

    /**
     * 标记的样式
     */
    private static final int MYSELF_MARK = 0X1;
    private static final int OTHER_MARK = 0X2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main_map_layout, container, false);

        //显示地图
        mapView = (MapView) rootView.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
        setUpMap();
        //跳转至发帖页
        publishButton = (ImageButton) rootView.findViewById(R.id.main_map_publish_button);
        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!LoginStatus.getIsUserMode()) {
                    GreenToast.makeText(getActivity(), "请先登录", Toast.LENGTH_LONG).show();
                    return;
                } else if (CommonUtil.isEmptyString(LocationInfo.getAddress()) ||
                        CommonUtil.isEmptyString(LocationInfo.getCityName()) ||
                        CommonUtil.isEmptyString(String.valueOf(LocationInfo.getLat()))) {

                    GreenToast.makeText(getActivity(), "没有获取到您的位置信息", Toast.LENGTH_LONG).show();
                    return;
                }
                getActivity().startActivity(new Intent(getActivity(), PublishActivity.class));
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 获取附近的标签，在MainActivity中刷新调用
     */
    public void getPost() {
        HttpResponseHandler getPostHandler = new HttpResponseHandler() {
            @Override
            public void getResult() {
                super.getResult();
                MainActivity mainActivity = (MainActivity) getActivity();
                if (this.resultVO == null) {
                    mainActivity.getHandler().sendEmptyMessage(new Message().what = ErroCode.ERROR_CODE_REQUEST_FORM_INVALID);
                } else if (this.resultVO.getStatus() == ErroCode.ERROR_CODE_CLIENT_DATA_ERROR) {
                    mainActivity.getHandler().sendEmptyMessage(new Message().what = MainActivity.NONE_VALID_POST);
                } else if (this.resultVO.getStatus() == ErroCode.ERROR_CODE_CORRECT) {
                    PostList postList = (PostList) this.result;
                    if (postList == null) return;
                    List<Post> postListItem = postList.getPostList();
                    for (int i = 0; i < postListItem.size(); i++) {
                        Post post = postListItem.get(i);
                        setMark(OTHER_MARK, post);
//                        LogUtil.d(TAG,post.getTag());
                    }
                }
            }
        };

        HttpRequest.getPost(LocationInfo.getLon(), LocationInfo.getLat(), LocationInfo.getCityName(), getPostHandler);
    }

    public void setUpMap() {
        mAMapLocationManager = LocationManagerProxy.getInstance(getActivity());
        //mAMapLocationManager.setGpsEnable(false);
            /*
             * mAMapLocManager.setGpsEnable(false);
			 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true Location
			 * API定位采用GPS和网络混合定位方式
			 * ，第一个参数是定位provider，第二个参数时间最短是2000毫秒，第三个参数距离间隔单位是米，第四个参数是定位监听者
			 */
        mAMapLocationManager.requestLocationData(
                LocationProviderProxy.AMapNetwork, 60000, 1, this);

        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.drawable.location_marker));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.TRANSPARENT);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
//        myLocationStyle.anchor(int,int)//设置小蓝点的锚点
        myLocationStyle.strokeWidth(0.0f);// 设置圆形的边框粗细
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        //标记信息窗口点击事件
        aMap.setOnInfoWindowClickListener(new AMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                marker.hideInfoWindow();
            }
        });

        setMark(MYSELF_MARK, null);
    }

    /**
     * 在地图上设置标记图标
     *
     * @param type 图标的样式
     */
    private void setMark(int type, Post post) {

        if (type == OTHER_MARK) {
            myMarkOption = new MarkerOptions();
            //添加用户覆盖物
            LatLng postCurrentLatLng = new LatLng(post.getLatitude(), post.getLongitude());
            myMarkOption.anchor(0.5f, 0.5f).
                    position(postCurrentLatLng).title(post.getTag()).snippet(post.address).draggable(false);
            myMark = aMap.addMarker(myMarkOption);

        }else{
            //暂时还没有设计用户定位图标
        }
    }


    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onLocationChanged(AMapLocation aLocation) {
        if (mListener != null && aLocation != null) {
            mListener.onLocationChanged(aLocation);// 显示系统小蓝点

            //获取位置信息
            geoLat = aLocation.getLatitude();
            geoLon = aLocation.getLongitude();
            LocationInfo.setLat(geoLat);
            LocationInfo.setLon(geoLon);
            LocationInfo.setCityName(aLocation.getCity());
            LocationInfo.setCityCode(aLocation.getCityCode());
            LocationInfo.setAddress(aLocation.getAddress());
            Log.d("MainActivity", "geoLat : " + geoLat + " geoLon : " + geoLon +
                    " City Name : " + aLocation.getCity() + "  City Code : " + aLocation.getCityCode() + "  Address : " + aLocation.getAddress());

            if (geoLon == null || geoLat == null) return;
            //添加用户覆盖物
            userCurrentLatLng = new LatLng(geoLat, geoLon);
            if (myMark != null) {
                myMark.destroy();
            }

            //设置圆形范围，颜色等参数
            aMap.addCircle(new CircleOptions().center(userCurrentLatLng).radius(1000).strokeColor(Color.GREEN).strokeWidth(1.0f));
            //设置缩放级别
            aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(userCurrentLatLng, 18, 0, 30)));

            if (isFirstGetPost) {
                getPost();
                isFirstGetPost = false;
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mAMapLocationManager == null) {
            mAMapLocationManager = LocationManagerProxy.getInstance(getActivity());
            //mAMapLocationManager.setGpsEnable(false);
			/*
			 * mAMapLocManager.setGpsEnable(false);
			 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true Location
			 * API定位采用GPS和网络混合定位方式
			 * ，第一个参数是定位provider，第二个参数时间最短是2000毫秒，第三个参数距离间隔单位是米，第四个参数是定位监听者
			 */
            mAMapLocationManager.requestLocationData(
                    LocationProviderProxy.AMapNetwork, 2000, 10, this);
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mAMapLocationManager != null) {
            mAMapLocationManager.removeUpdates(this);
            mAMapLocationManager.destroy();
        }
        mAMapLocationManager = null;
    }
}
