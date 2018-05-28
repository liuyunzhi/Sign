package com.cdut.sign.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.cdut.sign.R;
import com.cdut.sign.SignApplication;
import com.cdut.sign.net.SendPost;
import com.cdut.sign.util.Json;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class AdActivity extends Activity {

    private SignApplication signApplication;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
        signApplication = (SignApplication) getApplication();
        setContentView(R.layout.activity_ad);
        new signApplicationThread().start();
    }

    /**
     * 请求全局数据子进程
     */
    private class signApplicationThread extends Thread {

        @Override
        public void run() {
            Map<String, String> personInfoMap = signApplication.getPersonInfoMap();
            // 请求当前课程
            String params = "params={\"id\":\"" + personInfoMap.get("id") + "\"}";
            String jsonString = SendPost.executeHttpPost(signApplication.getApiUrl(), "getCurrentCourse", params);
            try {
                Json response = new Json(jsonString);
                if (response.get("code").equals("000000")) {
                    Json data = new Json(response.get("data"));
                    Map<String, String> currentCourseMap = new HashMap<>();
                    currentCourseMap.put("id", data.get("id"));
                    currentCourseMap.put("name", data.get("name"));
                    currentCourseMap.put("position", data.get("position"));
                    currentCourseMap.put("time", data.get("time"));
                    currentCourseMap.put("longitude", data.get("longitude"));
                    currentCourseMap.put("latitude", data.get("latitude"));
                    currentCourseMap.put("teacher", data.get("teacher"));
                    signApplication.setCurrentCourseMap(currentCourseMap);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            // 启动主页
                            Intent intent = new Intent(AdActivity.this, MainActivity.class);
                            startActivity(intent);
                            AdActivity.this.finish();
                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
