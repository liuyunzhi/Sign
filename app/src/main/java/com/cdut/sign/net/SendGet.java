package com.cdut.sign.net;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendGet {

    //通过GET方式获取HTTP服务器数据
    public static String executeHttpGet(String domain, String params) {
        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;
        StringBuffer result = null;
        try {
            String path = domain + "?" + params;
            URL url = new URL(path);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestProperty("Accept", "application/json");
            httpURLConnection.setRequestMethod("GET");
            // 设置连接超时
            httpURLConnection.setConnectTimeout(5000);
            // 设置读取超时
            httpURLConnection.setReadTimeout(8000);
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == 200) {
                bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String line;
                result = new StringBuffer();
                while ((line = bufferedReader.readLine()) != null) {
                    //转化为UTF-8的编码格式
                    line = new String(line.getBytes("UTF-8"));
                    result.append(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 意外退出时进行连接关闭保护
            if (httpURLConnection != null) {
//                httpURLConnection.dinputStreamconnect();
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }
}
