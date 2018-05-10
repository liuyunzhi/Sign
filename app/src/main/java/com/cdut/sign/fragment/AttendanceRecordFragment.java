package com.cdut.sign.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cdut.sign.SignApplication;
import com.cdut.sign.R;
import com.cdut.sign.adapter.MyExpandableListAdapter;
import com.cdut.sign.util.ChildInfor;
import com.cdut.sign.util.GroupInfor;
import com.cdut.sign.util.ItemInfor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

public class AttendanceRecordFragment extends Fragment {

	private MyExpandableListAdapter listAdapter = null;   
    private ExpandableListView recordList;
    private List<GroupInfor> group;
    private SignApplication signApplication;
    private Map<String, String> map;
    
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
    	super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_attendance_record, container, false);
		
		recordList = (ExpandableListView) view.findViewById(R.id.record_list);   
		group = new ArrayList<GroupInfor>();
		signApplication = (SignApplication)getActivity().getApplication();
		map = signApplication.getAttendanceRecordMap();
		
//		Set<String> set = map.keySet();
//		for (String key : set)
//		{
//			//遍历map
//			//月份信息
			ItemInfor item = new ItemInfor();
			//仅供测试
			item.setGroupImage(R.drawable.cyberplayer_listbtn_pressed);
			item.setGroupDate("2016年7月");
//			if (key.equals("") )
//			{
//				item.setGroupImage(map.get(key));
//			}
//			if (key.equals("") )
//			{
//				item.setGroupDate(map.get(key));
//			}
			//每天的考勤信息
	        List<ChildInfor> childList = new ArrayList<ChildInfor>();
	        ChildInfor child = new ChildInfor(); 
	        //仅供测试
	        child.setsignImage(R.drawable.success);
	        child.setsignOutImage(R.drawable.success);
	        child.setSignDay("6日");
	        child.setSignOutDay("6日");
	        child.setSignTime("18:20:00");
	        child.setSignOutTime("18:20:00");
//	        if (key.equals("") )
//			{
//	        	child.setsignImage(map.get(key));
//			}
//	        if (key.equals("") )
//			{
//	        	child.setsignOutImage(map.get(key));
//			}
//	        if (key.equals("") )
//			{
//	        	child.setSignDay(map.get(key));
//			}
//	        if (key.equals("") )
//			{
//	        	child.setSignOutDay(map.get(key));
//			}
//	        if (key.equals("") )
//			{
//	        	child.setSignTime(map.get(key));
//			}
//	        if (key.equals("") )
//			{
//	        	child.setSignOutTime(map.get(key));
//			}
	        childList.add(child);
	        //每月成一组
	        GroupInfor recordGroup = new GroupInfor(item, childList);   
	        group.add(recordGroup);
//		}
  
        listAdapter = new MyExpandableListAdapter(getActivity(), group);   
        recordList.setAdapter(listAdapter);
        
    	return view;
	}
}
