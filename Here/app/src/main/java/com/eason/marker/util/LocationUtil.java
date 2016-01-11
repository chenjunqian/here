package com.eason.marker.util;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import java.util.List;
import java.util.Locale;

/**
 * 获取经纬度工具类
 * Created by Eason on 9/30/15.
 */
public class LocationUtil {

    private static final String TAG = "LocationUtil";
    private static String cityName = "";
    private static Context context;
    private static float lat = 0;
    private static float lon = 0;

    /**
     * @param context
     * @return
     */
    public static void startGetLocation(Context context) {
        LocationUtil.context = context;
        LocationManager locationManager;
        String serviceName = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) context
                .getSystemService(serviceName);
        // String provider = LocationManager.GPS_PROVIDER;
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        String provider = locationManager.getBestProvider(criteria, true);
        if (CommonUtil.isEmptyString(provider)) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);
        while (location == null) {
            location = locationManager.getLastKnownLocation(provider);
        }

        setLocationInfo(location);

        locationManager.requestLocationUpdates(provider, 2000, 10,
                locationListener);

        locationManager.removeUpdates(locationListener);

    }

    /**
     * 由location 获取经纬度
     *
     * @param location
     */
    private static void setLocationInfo(Location location) {

        if (location != null) {
            lat = (float) location.getLatitude();
            lon = (float) location.getLongitude();
            setCityName(context,lat,lon);
            LogUtil.d(TAG, "获取位置信息成功");
        } else {
            LogUtil.d(TAG, "获取位置信息失败");
        }

    }

    /**
     * 获取城市名
     * @param context
     * @param lat
     * @param lon
     */
    private static void setCityName(Context context, double lat, double lon) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            // 取得地址相关的一些信息\经度、纬度
            List<Address> addresses = geocoder.getFromLocation(lat, lon, 1);
            StringBuilder sb = new StringBuilder();
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                sb.append(address.getLocality()).append("\n");
                cityName = sb.toString();
            }
        } catch (Exception e) {

        }
    }

    /**
     * 获取纬度
     *
     * @return
     */
    public static float getLatitude() {
        return lat;
    }

    /**
     * 获取经度
     *
     * @return
     */
    public static float getLongitude() {
        return lon;
    }

    /**
     * 获取当前城市名
     *
     * @return
     */
    public static String getCityName() {
        return cityName;
    }

    private static LocationListener locationListener = new LocationListener() {

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
        public void onLocationChanged(Location location) {
            setLocationInfo(location);
        }
    };
}
