package com.cdut.sign;

import android.app.Application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cdut.sign.util.WorkInfor;

public class SignApplication extends Application {

    private String apiUrl;
    private Map<String, String> personInfoMap;
    private Map<String, String> attendanceRecordMap;
    private static List<WorkInfor> workList;

    @Override
    public void onCreate() {
        super.onCreate();
        personInfoMap = new HashMap<>();
        attendanceRecordMap = new HashMap<>();
        workList = new ArrayList<>();
        apiUrl = "http://39.106.160.99/api/index";
    }

    public void setPersonInfoMap(Map<String, String> personInfoMap) {
        this.personInfoMap = personInfoMap;
    }

    public Map<String, String> getPersonInfoMap() {
        return personInfoMap;
    }

    public void setAttendanceRecordMap(Map<String, String> map) {
        this.attendanceRecordMap = map;
    }

    public Map<String, String> getAttendanceRecordMap() {
        return attendanceRecordMap;
    }

    public static void setWorkList(List<WorkInfor> list) {
        workList = list;
    }

    public static List<WorkInfor> getWorkList() {
        return workList;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }
}
