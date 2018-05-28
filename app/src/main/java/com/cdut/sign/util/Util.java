package com.cdut.sign.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
    private static final double EARTH_RADIUS = 6378.140;

    private static double radius(double d) {
        return d * Math.PI / 180.0;
    }

    public static double GetDistance(double lat1, double lng1, double lat2, double lng2) {
        double radiusLat1 = radius(lat1);
        double radiusLat2 = radius(lat2);
        double radiusLng1 = radius(lng1);
        double radiusLng2 = radius(lng2);
        
        double latDiff = radiusLat1 - radiusLat2;
        double lngDiff = radiusLng1 - radiusLng2;
        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(latDiff / 2), 2) + Math.cos(radiusLat1) * Math.cos(radiusLat2) * Math.pow(Math.sin(lngDiff / 2), 2)));
        distance = distance * EARTH_RADIUS;
        return distance;
    }

    public static Date stringToDate(String time){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return formatter.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static String dateToString(Date data) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(data);
    }
}
