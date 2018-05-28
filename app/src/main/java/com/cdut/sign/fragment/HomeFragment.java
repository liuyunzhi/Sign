package com.cdut.sign.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.services.geocoder.GeocodeSearch;
import com.cdut.sign.R;
import com.cdut.sign.SignApplication;
import com.cdut.sign.activity.FailActivity;
import com.cdut.sign.activity.SuccessActivity;
import com.cdut.sign.service.AMapLocationService;
import com.cdut.sign.util.GeocoderUtil;
import com.cdut.sign.util.Util;

import java.util.Map;

public class HomeFragment extends Fragment {

    private Button button;
    private ImageView imageHead;
    private TextView signTime;
    private TextView signPosition;
    private TextView currentPosition;
    private SignApplication signApplication;
    //个人信息
    private Map<String, String> currentCourseMap;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        button = view.findViewById(R.id.button_home_page);
        imageHead = view.findViewById(R.id.image_head_home_page);
        signTime = view.findViewById(R.id.sign_time);
        signPosition = view.findViewById(R.id.sign_position);
        currentPosition = view.findViewById(R.id.current_position);

        signApplication = (SignApplication) getActivity().getApplication();
        //获取数据
        currentCourseMap = signApplication.getCurrentCourseMap();
        //显示数据
//		imageHead.setImageDrawable(map.get("img"));
        signTime.setText(currentCourseMap.get("time"));
        signPosition.setText(currentCourseMap.get("position"));
        //启用逆地理编码
        geocoderSearch = new GeocodeSearch(getActivity());
        geocoderUtil = new GeocoderUtil(geocoderSearch);
        //初始定位
        startLocationService();

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //再次定位
                startLocationService();
                //判断考勤是否成功
                Double distance = Util.GetDistance(latitude, longitude, Double.parseDouble(currentCourseMap.get("latitude")), Double.parseDouble(currentCourseMap.get("longitude")));
                Long timeDifference = Util.stringToDate(currentCourseMap.get("time")).getTime() - System.currentTimeMillis();
                if (distance < 0.05) {
                    if (timeDifference <= 300000.0) {
                        Intent intent = new Intent(getActivity(), SuccessActivity.class);
                        intent.putExtra("city", city);
                        intent.putExtra("dtc", district);
                        startActivity(intent);
                    } else if (timeDifference < 0) {
                        Intent intent = new Intent(getActivity(), FailActivity.class);
                        intent.putExtra("note", "迟到打卡");
                        intent.putExtra("city", city);
                        intent.putExtra("dtc", district);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getActivity(), FailActivity.class);
                        intent.putExtra("note", "未进入考勤时间");
                        intent.putExtra("city", city);
                        intent.putExtra("dtc", district);
                        startActivity(intent);
                    }
                } else {
                    Intent intent = new Intent(getActivity(), FailActivity.class);
                    intent.putExtra("note", "未进入考勤范围");
                    intent.putExtra("city", city);
                    intent.putExtra("dtc", district);
                    startActivity(intent);
                }
                Log.e("latitude", String.valueOf(latitude));
                Log.e("longitude", String.valueOf(longitude));
                Log.e("distance", String.valueOf(distance));
                Log.e("timeDifference", String.valueOf(timeDifference));
            }
        });

        return view;
    }

    //实施定位
    private void startLocationService() {
        //启动定位服务
        getActivity().startService(new Intent(getActivity(), AMapLocationService.class));
        //注册广播
        broadcast = new MyBroadcast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.cdut.sign.action.AMAP_LOCTION");
        getActivity().registerReceiver(broadcast, filter);
    }

    // 获取广播数据
    private class MyBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                latitude = bundle.getDouble("lat");
                longitude = bundle.getDouble("lon");
                addressName = bundle.getString("add");
                city = bundle.getString("city");
                district = bundle.getString("dtc");
                updateLocation(addressName);
            } else {
                Toast.makeText(getActivity(), "定位失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //更新位置信息
    public void updateLocation(String address) {
        //将当前坐标填写于“当前地点”
        currentPosition.setText(address);
    }
}
