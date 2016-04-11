package com.eason.marker.main_activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.eason.marker.BaseFragment;
import com.eason.marker.R;
import com.eason.marker.http_util.HttpConfig;
import com.eason.marker.http_util.HttpRequest;
import com.eason.marker.http_util.HttpResponseHandler;
import com.eason.marker.model.ErroCode;
import com.eason.marker.model.IntentUtil;
import com.eason.marker.model.LocationInfo;
import com.eason.marker.model.LoginStatus;
import com.eason.marker.model.Post;
import com.eason.marker.model.PostList;
import com.eason.marker.model.User;
import com.eason.marker.profile_activity.ProfileActivity;
import com.eason.marker.publish_location_activity.PublishActivity;
import com.eason.marker.util.CommonUtil;
import com.eason.marker.util.LogUtil;
import com.eason.marker.util.PermissionUtils;
import com.eason.marker.util.WidgetUtil.CircleImageView;
import com.eason.marker.util.WidgetUtil.GreenToast;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Eason on 9/6/15.
 */
public class MainMapFragment extends BaseFragment implements
        OnMapReadyCallback,
        LocationSource.OnLocationChangedListener,
        LocationListener,
        GoogleMap.OnCameraChangeListener,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private static final String TAG = "MainMapFragment";
    private GoogleMap googleMap;
    private MapView mapView;
    private Location lastLocation;
    private Marker myMark;
    private LocationManager locationManager;
    private String currentCityName = " ";
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;
    private ImageButton publishButton;

    private LatLng currentCameraLatLng;
    private Double geoLat;
    private Double geoLon;

    private Handler handler = new Handler();
    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    /**
     * 标记的样式
     */
    private static final int MYSELF_MARK = 0X1;
    private static final int OTHER_MARK = 0X2;

    private boolean isFirstGetPost = true;
    private boolean mapIsTouch = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main_map_layout, container, false);

        mapView = (MapView) rootView.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        publishButton = (ImageButton) rootView.findViewById(R.id.main_map_publish_button);
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
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        initMap();
        enableMyLocation();
    }

    private void initMap() {
        this.googleMap.getUiSettings().setZoomControlsEnabled(true);
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            /**
             //    ActivityCompat#requestPermissions
             // here to request the missing permissions, and then overriding
             //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
             //                                          int[] grantResults)
             // to handle the case where the user grants the permission. See the documentation
             // for ActivityCompat#requestPermissions for more details.
             */
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this);

        googleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());
        googleMap.setOnCameraChangeListener(this);

        mapView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        mapIsTouch = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        mapIsTouch = false;
                        break;
                }

                return false;
            }
        });
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission((AppCompatActivity) getActivity(), LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (googleMap != null) {
            // Access to the location has been granted to the app.
            googleMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        //Move camera to my location on the map
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
        googleMap.animateCamera(cameraUpdate);
        /**
         * Store my location info
         */
        geoLat = location.getLatitude();
        geoLon = location.getLongitude();
        setUserLocationInfo(geoLat, geoLon);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            /**
             //    ActivityCompat#requestPermissions
             // here to request the missing permissions, and then overriding
             //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
             //                                          int[] grantResults)
             // to handle the case where the user grants the permission. See the documentation
             // for ActivityCompat#requestPermissions for more details.
             */
            return;
        }
        locationManager.removeUpdates(this);

        if (isFirstGetPost && (LocationInfo.getLat() != 0.0 || LocationInfo.getLon() != 0.0)) {
            getPost();
            isFirstGetPost = false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            initMap();
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
        }
    }

    /**
     * Set user's location information to LocationInfo
     *
     * @param latitude
     * @param longitude
     */
    private void setUserLocationInfo(double latitude, double longitude) {
        Geocoder gcd = new Geocoder(getActivity(), Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = gcd.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses!=null&&addresses.size() > 0) {
            LocationInfo.setLat(latitude);
            LocationInfo.setLon(longitude);
            LocationInfo.setCityName(addresses.get(0).getAdminArea());
            LocationInfo.setCityCode(addresses.get(0).getFeatureName());
            for (int i = 0; i <= addresses.get(0).getMaxAddressLineIndex(); i++) {
                String address = addresses.get(0).getAddressLine(i);
                LocationInfo.setAddress(LocationInfo.getAddress() + " "+address);
                LogUtil.e(TAG, "LocationInfo address : " + LocationInfo.getAddress());
            }
        }
    }

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
     * when map camera move get more post
     */
    private void getMorePost(double lon, double lat, String cityName, int num) {

        HttpResponseHandler getPostHandler = new HttpResponseHandler() {
            @Override
            public void getResult() {
                super.getResult();
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

    public void setMarker() {
        if (MainActivity.postListItem == null) return;
        for (int i = 0; i < MainActivity.postListItem.size(); i++) {
            Post post = MainActivity.postListItem.get(i);
            setMark(OTHER_MARK, post);
        }
    }

    /**
     * 在地图上设置标记图标
     *
     * @param type 图标的样式
     */
    private void setMark(int type,final Post post) {

        if (type == OTHER_MARK) {
            //添加用户覆盖物
            final LatLng postCurrentLatLng = new LatLng(post.getLatitude(), post.getLongitude());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    myMark = googleMap.addMarker(new MarkerOptions().
                            position(postCurrentLatLng).title(post.getUsername()).snippet(post.getTag() + "@@" + post.getAddress() + "@@" + post.getTime()).
                            draggable(false).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_ic)));
                }
            });

        } else {
            //暂时还没有设计用户定位图标
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        double offsetLat = 0.0;
        double offsetLon = 0.0;
        if (currentCameraLatLng!=null){
            offsetLat = Math.abs(currentCameraLatLng.latitude - cameraPosition.target.latitude);
            offsetLon = Math.abs(currentCameraLatLng.longitude - cameraPosition.target.longitude);
        }

        currentCameraLatLng = new LatLng(cameraPosition.target.latitude,
                cameraPosition.target.longitude);

        Geocoder gcd = new Geocoder(getActivity(), Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = gcd.getFromLocation(cameraPosition.target.latitude, cameraPosition.target.longitude, 1);
            currentCityName = addresses.get(0).getLocality();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }

        //if (addresses!=null&&addresses.size() > 0 && !currentCityName.equals(addresses.get(0).getAdminArea())) {
        if (addresses!=null&&offsetLat>0.1 || offsetLon > 0.1 &&!mapIsTouch){

            if (CommonUtil.isEmptyString(currentCityName) || currentCityName.equals(LocationInfo.getCityName())) {
                return;
            } else {
                getMorePost(currentCameraLatLng.longitude, currentCameraLatLng.latitude, currentCityName, 20);
            }

        }
    }

    class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        // These a both viewgroups containing an ImageView with id "badge" and two TextViews with id
        // "title" and "snippet".
        private final View mWindow;

        private final View mContents;

        CustomInfoWindowAdapter() {
            mWindow = getActivity().getLayoutInflater().inflate(R.layout.map_post_info_layout, null);
            mContents = getActivity().getLayoutInflater().inflate(R.layout.map_post_info_layout, null);
        }

        /**
         * Demonstrates customizing the info window and/or its contents.
         *
         * @param marker
         * @return
         */
        @Override
        public View getInfoWindow(Marker marker) {
            renderInfoWindow(marker, mWindow);
            return mWindow;
        }

        @Override
        public View getInfoContents(Marker marker) {
            renderInfoWindow(marker, mContents);
            return mContents;
        }

        /**
         * customize infoWindow
         *
         * @param marker
         * @param view
         */
        private void renderInfoWindow(Marker marker, View view) {
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

            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Intent intent = new Intent(getActivity(), ProfileActivity.class);
                    intent.putExtra("username", title);
                    getActivity().startActivity(intent);
                }
            });
        }
    }
}
