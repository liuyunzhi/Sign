package com.cdut.sign.fragment;

import java.util.Map;

import com.amap.api.services.geocoder.GeocodeSearch;
import com.cdut.sign.SignApplication;
import com.cdut.sign.R;
import com.cdut.sign.activity.SuccessActivity;
//import com.cdut.sign.net.SendPost;
import com.cdut.sign.servise.AMapLocationService;
import com.cdut.sign.util.GeocoderUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HomePageFragment extends Fragment {

	private Button button;
	private ImageView imageHead;
	private TextView name;
	private TextView department;
	private TextView signTime;
	private TextView signPosition;
	private TextView currentPosition;
	private SignApplication signApplication;
	//个人信息
	private Map<String, String> personalInforMap;
	//广播消息
	private MyBroadcast broadcast;
	//逆地理编码时将地理坐标转换为中文地址（地名描述）的功能。
	private GeocodeSearch geocoderSearch;
	private GeocoderUtil geocoderUtil;
	private String addressName;
	private Double latitude;
	private Double longitude;
	private String city;
	private String district;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_homepage, container, false);
		button = (Button) view.findViewById(R.id.button_home_page);
		imageHead = (ImageView) view.findViewById(R.id.image_head_home_page);
		department = (TextView) view.findViewById(R.id.department_home_page);
		name = (TextView) view.findViewById(R.id.name_home_page);
		signTime = (TextView) view.findViewById(R.id.sign_time);
		signPosition = (TextView) view.findViewById(R.id.sign_position);
		currentPosition = (TextView) view.findViewById(R.id.current_position);
		
		signApplication = (SignApplication)getActivity().getApplication();
		//获取数据
		personalInforMap = signApplication.getPersonInfoMap();
		//显示数据
//		imageHead.setImageDrawable(map.get("img"));
//		department.setText(map.get("company"));
//		name.setText(map.get("name"));
//		signTime.setText(map.get("time"));
//		signPosition.setText(map.get("address"));
		//启用逆地理编码
		geocoderSearch = new GeocodeSearch(getActivity());
		geocoderUtil = new GeocoderUtil(geocoderSearch);
		//初始定位
		startLocationService();
		
		button.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				//再次定位
				startLocationService();
//				//访问服务器
//	        	Map<String, String> returnInfor = WebServicePost.executeHttpPost(String.valueOf(latitude), String.valueOf(longitude));
//				//将当前坐标上传服务器,判断考勤是否成功
//				if (returnInfor.get("resCode") == 100)
				if (true)//仅供测试
				{
					Intent intent = new Intent(getActivity(),SuccessActivity.class);
					intent.putExtra("city", city);
					intent.putExtra("dtc", district);
					startActivity(intent);
				}
			}
		});
		
		return view;
	}
	
	//实施定位
	private void startLocationService()
	{
		//启动定位服务
		getActivity().startService(new Intent(getActivity(), AMapLocationService.class));
		//注册广播
		broadcast = new MyBroadcast();
		IntentFilter filter=new IntentFilter();
		filter.addAction("com.cdut.sign.action.AMAP_LOCTION");
		getActivity().registerReceiver(broadcast, filter);
	}
	
	// 获取广播数据
	private class MyBroadcast extends BroadcastReceiver
	{
		@Override
		public void onReceive(Context context, Intent intent) 
		{
			Bundle bundle = intent.getExtras();
			if (bundle != null)
			{
				latitude = bundle.getDouble("lat");
				longitude = bundle.getDouble("lon");
				addressName = bundle.getString("add");
				city = bundle.getString("city");
				district = bundle.getString("dtc");
				updateLocation(addressName);
			}
			else
			{
				Toast.makeText(getActivity(), "定位失败", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	//更新位置信息
	public void updateLocation(String address)
	{
		//将当前坐标填写于“当前地点”
		currentPosition.setText(address);
	}
}
