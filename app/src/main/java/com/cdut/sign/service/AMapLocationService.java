package com.cdut.sign.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;

import java.util.HashMap;
import java.util.Map;

public class AMapLocationService extends Service {

    //声明AMapLocationClient类对象
    public AMapLocationClient client;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption option;
    //声明定位回调监听器
    public AMapLocationListener listener;
    private IBinder binder;
    private Map<String, String> result;

    @Override
    public void onCreate() {
        super.onCreate();
        binder = new LocationBinder();
        result = new HashMap<>();
        //初始化AMapLocationClientOption对象
        option = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        option.setLocationMode(AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        option.setInterval(1000);
        //初始化定位回调监听器
        listener = new AMapLocationListener() {

            @Override
            public void onLocationChanged(AMapLocation location) {
                if (location != null) {
                    if (location.getErrorCode() == 0) {
                        // 发送广播
                        Intent intent = new Intent();
                        intent.putExtra("lat", location.getLatitude());
                        intent.putExtra("lon", location.getLongitude());
                        intent.putExtra("add", location.getAddress());
                        intent.putExtra("city", location.getCity());
                        intent.putExtra("dtc", location.getDistrict());
                        intent.setAction("com.cdut.sign.action.AMAP_LOCTION");
                        sendBroadcast(intent);
                    } else {
                        Log.e("AmapError","location Error, ErrCode:"
                                + location.getErrorCode() + ", errInfo:"
                                + location.getErrorInfo());

                    }
                }
            }
        };
        //初始化定位
        client = new AMapLocationClient(getApplicationContext());
        //给定位客户端对象设置定位参数
        client.setLocationOption(option);
        //设置定位回调监听
        client.setLocationListener(listener);
        //启动定位
        client.startLocation();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class LocationBinder extends Binder {

    }

}
