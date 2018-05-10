package com.cdut.sign.net;

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class SendPost {

    public static String executeHttpPost(String domain, String method, String params) {

        String result = "";
        try {
            String path = domain + "?method=" + method;
            URL url = new URL(path);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            // 设置接收类型
            httpURLConnection.setRequestProperty("accept", "application/json");
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setUseCaches(false);
            // 向服务器发送数据
            if (params != null && !TextUtils.isEmpty(params)) {
                // 设置文件长度
                httpURLConnection.setRequestProperty("Content-Length", String.valueOf(params.length()));
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream()));
                bufferedWriter.write(params);
                bufferedWriter.close();
            }
            // 获取服务器响应
            if (httpURLConnection.getResponseCode() == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                result = reader.readLine();
                reader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return result;
    }
}
