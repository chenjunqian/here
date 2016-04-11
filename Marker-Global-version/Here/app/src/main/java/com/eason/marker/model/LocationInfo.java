package com.eason.marker.model;

/**
 * Created by Eason on 10/29/15.
 */
public class LocationInfo {

    private static String cityName = "";
    private static String cityCode = "";
    private static String address = "";
    private static Double lat = 0.0;
    private static Double lon =0.0;

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        LocationInfo.address = address;
    }

    public static String getCityCode() {
        return cityCode;
    }

    public static void setCityCode(String cityCode) {
        LocationInfo.cityCode = cityCode;
    }

    public static String getCityName() {
        return cityName;
    }

    public static void setCityName(String cityName) {
        LocationInfo.cityName = cityName;
    }

    public static Double getLat() {
        return lat;
    }

    public static void setLat(Double lat) {
        LocationInfo.lat = lat;
    }

    public static Double getLon() {
        return lon;
    }

    public static void setLon(Double lon) {
        LocationInfo.lon = lon;
    }
}
