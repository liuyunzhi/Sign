//package com.cdut.sign.net;
//
//import java.io.ByteArrayOutputStream;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.params.CoreConnectionPNames;
//import org.apache.http.protocol.HTTP;
//
//@SuppressWarnings("deprecation")
//public class SendPost {
//
//	private static String IP = " ";//服务器IP
//
//	public static Map<String, String> executeHttpPost(String account, String pwd)
//	{
//		String path = "http://" + IP + "/student/login";
//		Map<String, String> map = new HashMap<String, String>();
//
//		try
//		{
//			Map<String,String> params = new HashMap<String,String>();
//			params.put("account",account);
//			params.put("pwd", pwd);
//
//
//			return map;//sendPOSTRequest(path,params);
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	private static String sendPOSTRequest(String path, Map<String, String> params) throws Exception
//	{
//		HttpPost  post = new HttpPost(path);
//		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
//		HttpClient httpClient = new DefaultHttpClient();
//
//		for (Map.Entry<String, String> entry : params.entrySet())
//		{
//			pairs.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
//		}
//
//		post.setEntity(new UrlEncodedFormEntity(pairs, HTTP.UTF_8));
//		//请求超时
//		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
//		//读取超时
//		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);
//		HttpResponse response = httpClient.execute(post);
//
//		if (response.getStatusLine().getStatusCode() == 200)
//		{
//			return  getInfor(response);
//		}
//		else
//		{
//			return null;
//		}
//	}
//
//	private static String getInfor(HttpResponse response) throws Exception
//	{
//		HttpEntity entity = response.getEntity();
//        InputStream is = entity.getContent();
//        // 将输入流转化为byte型
//        byte[] data = read(is);
//        // 转化为字符串
//        return new String(data, "UTF-8");
//	}
//
//	// 将输入流转化为byte型
//    public static byte[] read(InputStream inStream) throws Exception {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        byte[] buffer = new byte[1024];
//        int len = 0;
//        while ((len = inStream.read(buffer)) != -1) {
//            outputStream.write(buffer, 0, len);
//        }
//        inStream.close();
//        return outputStream.toByteArray();
//    }
//}
