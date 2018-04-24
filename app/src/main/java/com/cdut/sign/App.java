package com.cdut.sign;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cdut.sign.util.WorkInfor;
import android.app.Application;

public class App extends Application {

	//个人信息
	private Map<String, String> personalInforMap = new HashMap<>();
	//考勤记录
	private Map<String, String> attendanceRecordMap = new HashMap<>();
	//工作总结
	private static List<WorkInfor> workList = new ArrayList<>();
	
	public void setPersonalInforMap(Map<String, String> map)
	{
		this.personalInforMap = map;
	}
	
	public Map<String, String> getPersonalInforMap()
	{
		return personalInforMap;
	}
	
	public void setAttendanceRecordMap(Map<String, String> map)
	{
		this.attendanceRecordMap = map;
	}
	
	public Map<String, String> getAttendanceRecordMap()
	{
		return attendanceRecordMap;
	}
	
	public static void setWorkList(List<WorkInfor> list)
	{
		workList = list;
	}

	public static List<WorkInfor> getWorkList() {
		return workList;
	}
}
