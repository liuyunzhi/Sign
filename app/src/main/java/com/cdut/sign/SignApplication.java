package com.cdut.sign;

import android.app.Application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cdut.sign.util.Note;

public class SignApplication extends Application {

    private String apiUrl;
    private Map<String, String> personInfoMap;
    private Map<String, String> currentCourseMap;
    private Map<String, String> signRecordMap;
    private static List<Note> noteList;

    @Override
    public void onCreate() {
        super.onCreate();
        personInfoMap = new HashMap<>();
        signRecordMap = new HashMap<>();
        noteList = new ArrayList<>();
        apiUrl = "http://39.106.160.99/api/index";
    }

    public void setPersonInfoMap(Map<String, String> personInfoMap) {
        this.personInfoMap = personInfoMap;
    }

    public Map<String, String> getPersonInfoMap() {
        return personInfoMap;
    }

    public void setSignRecordMap(Map<String, String> map) {
        this.signRecordMap = map;
    }

    public Map<String, String> getSignRecordMap() {
        return signRecordMap;
    }

    public static void setNoteList(List<Note> list) {
        noteList = list;
    }

    public static List<Note> getNoteList() {
        return noteList;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public Map<String, String> getCurrentCourseMap() {
        return currentCourseMap;
    }

    public void setCurrentCourseMap(Map<String, String> currentCourseMap) {
        this.currentCourseMap = currentCourseMap;
    }
}
