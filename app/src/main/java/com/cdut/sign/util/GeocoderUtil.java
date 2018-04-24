package com.cdut.sign.util;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener;

public class GeocoderUtil {

	//逆地理编码时将地理坐标转换为中文地址（地名描述）的功能。
	private GeocodeSearch geocoderSearch;
	private LatLonPoint latLonPoint;
	private LatLng point;
	private String addressName;
	
	public GeocoderUtil(GeocodeSearch geocoderSearch)
	{
		this.geocoderSearch = geocoderSearch;
	}
	
	public String transformGeocoder(LatLng mPoint)
	{
		point = mPoint;
		latLonPoint = new LatLonPoint(point.latitude, point.longitude);
		geocoderSearch.setOnGeocodeSearchListener(new OnGeocodeSearchListener() {
			
			@Override
			public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
				if (rCode == 1000) 
				{
					if (result != null && result.getRegeocodeAddress() != null && result.getRegeocodeAddress().getFormatAddress() != null) 
					{
						addressName = result.getRegeocodeAddress().getFormatAddress() + "附近";
					} 
				} 
			}
			
			@Override
			public void onGeocodeSearched(GeocodeResult arg0, int arg1) {
				
			}
		});
		
		// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
		RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);
		// 设置同步逆地理编码请求
		geocoderSearch.getFromLocationAsyn(query);
		return addressName;
	}
}
