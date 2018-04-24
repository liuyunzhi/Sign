package com.cdut.sign.activity;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.OnMapClickListener;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource.OnLocationChangedListener;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.cdut.sign.R;
import com.cdut.sign.util.GeocoderUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class LocationMapActivity extends Activity {

	//地图对象的操作方法与接口
	private AMap aMap;
	//显示地图的视图（View）
	private MapView mMapView;
	//地图定位最新位置
	private OnLocationChangedListener mListener;
	//定位参数设置，通过这个类可以对定位的相关参数进行设置
	//在AMapLocationClient进行定位时需要这些参数
	private AMapLocationClientOption mLocationOption;
	//标记
	private MarkerOptions markerOption;
	//声明AMapLocationClient类对象,进行定位操作
	private AMapLocationClient mLocationClient;
	//逆地理编码时将地理坐标转换为中文地址（地名描述）的功能。
	private GeocodeSearch geocoderSearch;
	private GeocoderUtil geocoderUtil;
	private Intent intent;
	private String addressName;
	private Button okButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location_map);
		// 获取地图控件引用
		mMapView = (MapView) findViewById(R.id.map);
		// 在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
		mMapView.onCreate(savedInstanceState);
		//初始化AMap对象
		if (aMap == null) {
			aMap = mMapView.getMap();
			setUpMap();
		}
		intent = getIntent();
		addressName = intent.getStringExtra("address");
		okButton = (Button) findViewById(R.id.ok_button);
		okButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				intent.putExtra("address", addressName);
				LocationMapActivity.this.setResult(0, intent);
				LocationMapActivity.this.finish();
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mMapView.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
		mMapView.onPause();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// 在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState
		// (outState)，实现地图生命周期管理
		mMapView.onSaveInstanceState(outState);
	}
	
	@Override
    public void onBackPressed() {
		intent.putExtra("address", addressName);
		setResult(0, intent);
		super.onBackPressed();
    }
	
	/**
	 * 设置一些amap的属性
	 */
	private void setUpMap() {
		geocoderSearch = new GeocodeSearch(this);
		geocoderUtil = new GeocoderUtil(geocoderSearch);
		//对定位的相关参数进行设置
		mLocationOption = new AMapLocationClientOption();
		//设置为高精度定位模式
		mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
		//设置是否只定位一次,默认为false
		mLocationOption.setOnceLocation(true);
		//初始化定位
		mLocationClient = new AMapLocationClient(this);
		//定位回调监听
		mLocationClient.setLocationListener(new AMapLocationListener() {
			
			@Override
			public void onLocationChanged(AMapLocation location) {
				
				if (mListener != null && location != null) 
				{
			         if (location != null && location.getErrorCode() == 0) 
			         {
			             mListener.onLocationChanged(location);//显示系统小蓝点
			         } 
			         else 
			         {
			             String errText = "定位失败" + location.getErrorCode()+ ": " + location.getErrorInfo();
			             Toast.makeText(LocationMapActivity.this, errText, Toast.LENGTH_SHORT).show();
			         }
			     }
			}
		});
		//设置定位参数
		mLocationClient.setLocationOption(mLocationOption);
		aMap.setLocationSource(new LocationSource() {
			
			@Override
			public void deactivate() {
				mListener = null;
				if (mLocationClient != null) {
					mLocationClient.stopLocation();
					mLocationClient.onDestroy();
				}
				mLocationClient = null;
			}
			
			@Override
			public void activate(OnLocationChangedListener listener) {
				mListener = listener;
				if (mLocationClient != null) {
					// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
					// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
					// 在定位结束后，在合适的生命周期调用onDestroy()方法
					// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
					mLocationClient.startLocation();
				}
			}
		});// 设置定位监听
		aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
		aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_FOLLOW);
		aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
		aMap.setOnMapClickListener(new OnMapClickListener() {
			
			@Override
			public void onMapClick(LatLng point) {
				//坐标转换
				addressName = geocoderUtil.transformGeocoder(point);
				//从当前可视区域转换到一个指定位置的可视区域的过程
				aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(point, 15));
				addMarkersToMap(point);
			}
		});// 对amap添加单击地图事件监听器
	}

	/**
	 * 在地图上添加marker
	 */
	private void addMarkersToMap(LatLng point) {
		//清空之前的标记
		aMap.clear();
		markerOption = new MarkerOptions();
		markerOption.position(point);
		markerOption.anchor(0.5f, 0.5f);
		aMap.addMarker(markerOption);
		if (! addressName.equals(""))
		{
			Toast.makeText(LocationMapActivity.this, addressName, Toast.LENGTH_LONG).show();
		}
	}
}
