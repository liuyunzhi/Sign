package com.cdut.sign.servise;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class AMapLocationService extends Service {
	
	//声明AMapLocationClient类对象
	public AMapLocationClient mLocationClient;
	//声明定位回调监听器
	public AMapLocationListener mLocationListener;
	//声明AMapLocationClientOption对象
	public AMapLocationClientOption mLocationOption;
	
	@Override
	public void onCreate() 
	{
		super.onCreate();
		mLocationListener = new AMapLocationListener() {
			
			@Override
			public void onLocationChanged(AMapLocation amapLocation) {
				if (amapLocation != null) {
				    if (amapLocation.getErrorCode() == 0) {
				    	// 发送广播
						Intent intent = new Intent();
						intent.putExtra("lat", amapLocation.getLatitude());
						intent.putExtra("lon", amapLocation.getLongitude());
						intent.putExtra("add", amapLocation.getAddress());
						intent.putExtra("city", amapLocation.getCity());
						intent.putExtra("dtc", amapLocation.getDistrict());
						intent.setAction("com.cdut.sign.action.AMAP_LOCTION");
						sendBroadcast(intent);
				    }
				}
			}
		};
		//初始化定位
		mLocationClient = new AMapLocationClient(getApplicationContext());
		//设置定位回调监听
		mLocationClient.setLocationListener(mLocationListener);
		//初始化AMapLocationClientOption对象
		mLocationOption = new AMapLocationClientOption();
		//设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
		mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
		//设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
		mLocationOption.setInterval(1000);
		//设置是否强制刷新WIFI，默认为true，强制刷新。
		mLocationOption.setWifiActiveScan(false);
		//给定位客户端对象设置定位参数
		mLocationClient.setLocationOption(mLocationOption);
		//启动定位
		mLocationClient.startLocation();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
