package com.eason.marker.controller.main_activity;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.eason.marker.controller.BaseFragment;
import com.eason.marker.R;
import com.eason.marker.network.HttpConfig;
import com.eason.marker.network.HttpRequest;
import com.eason.marker.network.HttpResponseHandler;
import com.eason.marker.model.ErroCode;
import com.eason.marker.model.IntentUtil;
import com.eason.marker.model.LocationInfo;
import com.eason.marker.model.LoginStatus;
import com.eason.marker.model.Post;
import com.eason.marker.model.PostList;
import com.eason.marker.model.User;
import com.eason.marker.controller.profile_activity.ProfileActivity;
import com.eason.marker.controller.publish_location_activity.PublishActivity;
import com.eason.marker.util.CommonUtil;
import com.eason.marker.view.CircleImageView;
import com.eason.marker.view.GreenToast;
import com.eason.marker.view.ProgressDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eason on 9/6/15.
 */
public class MainMapFragment extends BaseFragment implements LocationSource, AMapLocationListener,
        AMap.InfoWindowAdapter, AMap.OnCameraChangeListener, GeocodeSearch.OnGeocodeSearchListener {

    private final String TAG = "MainMapFragment";
    private MapView mapView;
    private AMap aMap;
    private LocationSource.OnLocationChangedListener mListener;
    private LocationManagerProxy mAMapLocationManager;
    private MarkerOptions myMarkOption;
    private Marker myMark;
    private LatLng userCurrentLatLng;
    private LatLonPoint currentCameraLatLng;
    private GeocodeSearch geocodeSearch;

    private Double geoLat;
    private Double geoLon;

    private FloatingActionButton publishButton;

    private boolean isFirstGetPost = true;

    /**
     * 标记的样式
     */
    private static final int MYSELF_MARK = 0X1;
    private static final int OTHER_MARK = 0X2;

    //获取当前地图视野的地理位置信息
    private final int GET_MAP_CAMERA_LOCATION_INFO = 0X3;

    private Message message = new Message();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_MAP_CAMERA_LOCATION_INFO:
                    if (!CommonUtil.isFastDoubleClick(2000) && shouldGetMorePost && currentCameraLatLng != null) {
                        RegeocodeQuery query = new RegeocodeQuery(currentCameraLatLng, 200, GeocodeSearch.AMAP);
                        geocodeSearch.getFromLocationAsyn(query);
                    }
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main_map_layout, container, false);

        //显示地图
        mapView = (MapView) rootView.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
        initMap();
        //跳转至发帖页
        publishButton = (FloatingActionButton) rootView.findViewById(R.id.main_map_publish_button);
        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!LoginStatus.getIsUserMode()) {
                    GreenToast.makeText(getActivity(), getResources().getString(R.string.please_login_first), Toast.LENGTH_LONG).show();
                    return;
                } else if (CommonUtil.isEmptyString(LocationInfo.getAddress()) ||
                        CommonUtil.isEmptyString(LocationInfo.getCityName()) ||
                        CommonUtil.isEmptyString(String.valueOf(LocationInfo.getLat()))) {

                    GreenToast.makeText(getActivity(), getResources().getString(R.string.can_not_get_your_location), Toast.LENGTH_LONG).show();
                    return;
                }
                getActivity().startActivityForResult(new Intent(getActivity(), PublishActivity.class), IntentUtil.TO_PUBLISH_PAGE);
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
        setMarker();
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

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.show();

        HttpResponseHandler getPostHandler = new HttpResponseHandler() {
            @Override
            public void getResult() {
                super.getResult();
                progressDialog.dismiss();
                MainActivity mainActivity = (MainActivity) getActivity();
                if (this.resultVO == null) {
                    mainActivity.getHandler().sendEmptyMessage(new Message().what = ErroCode.ERROR_CODE_REQUEST_FORM_INVALID);
                } else if (this.resultVO.getStatus() == ErroCode.ERROR_CODE_CLIENT_DATA_ERROR) {
                    mainActivity.getHandler().sendEmptyMessage(new Message().what = MainActivity.NONE_VALID_POST);
                } else if (resultVO.getStatus() == ErroCode.ERROR_CODE_CORRECT && resultVO.getResultData() != null) {
                    PostList postList = (PostList) this.result;
                    if (postList == null) {
                        return;
                    }
                    MainActivity.postListItem = postList.getPostList();
                    setMarker();
                }
            }
        };

        HttpRequest.getPost(LocationInfo.getLon(), LocationInfo.getLat(), LocationInfo.getCityName(), 20, getPostHandler);
    }

    /**
     * 加载更多的坐标，用于当用户移动地图视野时加载
     */
    private void getMorePost(double lon, double lat, String cityName, int num) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.show();

        HttpResponseHandler getPostHandler = new HttpResponseHandler() {
            @Override
            public void getResult() {
                super.getResult();
                progressDialog.dismiss();
                MainActivity mainActivity = (MainActivity) getActivity();
                if (resultVO.getStatus() == ErroCode.ERROR_CODE_CORRECT && resultVO.getResultData() != null) {
                    PostList postList = (PostList) this.result;
                    if (postList == null) {
                        return;
                    } else if (MainActivity.postListItem != null) {
                        MainActivity.postListItem.addAll(postList.getPostList());
                    }
                    setMarker();
                }
            }
        };
        HttpRequest.getPost(lon, lat, cityName, num, getPostHandler);
    }

    /**
     * 在地图上添加标记
     */
    public void setMarker() {
        if (MainActivity.postListItem == null) return;
        for (int i = 0; i < MainActivity.postListItem.size(); i++) {
            Post post = MainActivity.postListItem.get(i);
            setMark(OTHER_MARK, post);
        }
    }

    public void initMap() {
        mAMapLocationManager = LocationManagerProxy.getInstance(getActivity());
        //mAMapLocationManager.setGpsEnable(false);
            /*
             * mAMapLocManager.setGpsEnable(false);
			 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true Location
			 * API定位采用GPS和网络混合定位方式
			 * ，第一个参数是定位provider，第二个参数时间最短是2000毫秒，第三个参数距离间隔单位是米，第四个参数是定位监听者
			 */
        mAMapLocationManager.requestLocationData(
                LocationProviderProxy.AMapNetwork, 600000, 1, this);

        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.drawable.ic_location_on_18pt)).anchor(0.5f, 0.5f);// 设置小蓝点的图标
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

        /**
         * 当地图视野移动时
         */
        aMap.setOnCameraChangeListener(this);
        geocodeSearch = new GeocodeSearch(getActivity());
        geocodeSearch.setOnGeocodeSearchListener(this);

        //自定义点击显示的窗口信息
        aMap.setInfoWindowAdapter(this);

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
                    position(postCurrentLatLng).title(post.getUsername()).snippet(post.getTag() + "@@" + post.getAddress() + "@@" + post.getTime()).
                    draggable(false).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_ic));
            myMark = aMap.addMarker(myMarkOption);

        } else {
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

            if (isFirstGetPost && (LocationInfo.getLat() != 0.0 || LocationInfo.getLon() != 0.0)) {
                getPost();
                isFirstGetPost = false;
            }
        }
    }

    private boolean shouldGetMorePost = false;
    private int GET_POST_NUM = 20;

    /**
     * 当地图的可视区域改变时
     *
     * @param cameraPosition
     */
    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        shouldGetMorePost = false;
    }

    /**
     * 当地图的可视区域改变结束时
     *
     * @param cameraPosition
     */
    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        shouldGetMorePost = true;
        //latLonPoint参数表示一个Latlng，第二参数表示范围多少米，GeocodeSearch.AMAP表示是国测局坐标系还是GPS原生坐标系
        currentCameraLatLng = new LatLonPoint(cameraPosition.target.latitude,
                cameraPosition.target.longitude);
        //为了不频繁的请求数据，限定移动地图视野停止3秒后再获取数据
        handler.sendEmptyMessageDelayed(message.what = GET_MAP_CAMERA_LOCATION_INFO, 3000);
    }

    /**
     * 有高德地图，根据经纬度获取城市名，再获取帖子
     *
     * @param regeocodeResult
     * @param i
     */
    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        String cityName = regeocodeResult.getRegeocodeAddress().getCity();
        if (CommonUtil.isEmptyString(cityName)||cityName.equals(LocationInfo.getCityName())) {
            return;
        }  else {
            getMorePost(currentCameraLatLng.getLongitude(), currentCameraLatLng.getLatitude(), cityName, 20);
        }
    }

    /**
     * 有高德地图，根据经纬度获取城市名
     *
     * @param geocodeResult
     * @param i
     */
    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

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

    //自定义显示窗口
    @Override
    public View getInfoWindow(Marker marker) {

        View infoWindow = LayoutInflater.from(getActivity()).inflate(R.layout.map_post_info_layout, null);
        renderInfoWindow(marker, infoWindow);
        return infoWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    /**
     * 自定义infoWindow窗口
     *
     * @param marker
     * @param view
     */
    @TargetApi(21)
    private void renderInfoWindow(Marker marker, View view) {
        RelativeLayout infoWindowLayout = (RelativeLayout) view.findViewById(R.id.info_window_avatar_layout);
        final CircleImageView avatar = (CircleImageView) view.findViewById(R.id.info_window_avatar);
        final TextView nickname = (TextView) view.findViewById(R.id.info_window_nickname);
        final TextView tagView = (TextView) view.findViewById(R.id.info_window_post_tag_text_view);
        final TextView addressView = (TextView) view.findViewById(R.id.info_window_post_location_address);
        final TextView time = (TextView) view.findViewById(R.id.info_window_post_time);

        final String title = marker.getTitle();
        final String sniper = marker.getSnippet();

        HttpResponseHandler getUserInfoHandler = new HttpResponseHandler() {
            @Override
            public void getResult() {
                super.getResult();
                if (this.resultVO.getStatus() == ErroCode.ERROR_CODE_CORRECT) {
                    User user = (User) this.result;
                    //设置昵称内容
                    nickname.setText(user.getNickname());

                    /**
                     * 拆分由放在Marker内的信息，分别是post的tag内容和地址
                     */
                    String[] urlAndTag = sniper.split("@@");
                    List<String> urlAndTagList = new ArrayList<String>();
                    for (int i = 0; i < urlAndTag.length; i++) {
                        urlAndTagList.add(urlAndTag[i]);
                    }

                    HttpRequest.loadImage(avatar, HttpConfig.String_Url_Media + user.getAvatar(), 150, 150);

                    tagView.setText(urlAndTagList.get(0));
                    addressView.setText(urlAndTagList.get(1));
                    if (CommonUtil.isEmptyString(urlAndTagList.get(2)) || urlAndTagList.get(2).equals("null")) {
                        time.setText(CommonUtil.formatTimeMillis(System.currentTimeMillis()));
                    } else {
                        time.setText(CommonUtil.formatTimeMillis(Long.valueOf(urlAndTagList.get(2))));
                    }
                }
            }
        };

        HttpRequest.getUserByUsername(title, getUserInfoHandler);

        infoWindowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                intent.putExtra("username", title);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(),avatar,"avatar");
                getActivity().startActivity(intent,options.toBundle());
            }
        });
    }

}
