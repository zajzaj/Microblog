package com.example.util;

import java.io.IOException;  
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;  
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;  
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;  
import org.apache.http.client.methods.HttpPost;  
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;  
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;  


import android.util.Log;
  
public class HttpUtil {  
    // ����URL   
    //public static final String BASE_URL="http://192.168.1.100:8080/HttpTest/login";

	/**
	 * ��Post��ʽ����������������
	 * @param url
	 * @return
	 */
	public String doPost(String url,String chose,List<BasicNameValuePair> paramsList){
	  
		

	   String responseStr = "";
	   
		   try{
			//ע��
		    HttpPost httpRequest = new HttpPost(url);
	        HttpParams params = new BasicHttpParams();
	        ConnManagerParams.setTimeout(params, 1000); //�����ӳ��л�ȡ���ӵĳ�ʱʱ��
	        HttpConnectionParams.setConnectionTimeout(params, 3000);//ͨ��������������������ӵĳ�ʱʱ��
	        HttpConnectionParams.setSoTimeout(params, 5000);//����Ӧ���ݵĳ�ʱʱ��
	        httpRequest.setParams(params);
	         
	        //���濪ʼ���������������ݣ�ʹ��BasicNameValuePair
	        
	        UrlEncodedFormEntity mUrlEncodeFormEntity = new UrlEncodedFormEntity(paramsList, HTTP.UTF_8);
	        httpRequest.setEntity(mUrlEncodeFormEntity);
	         
	        ////////////////////////////////////////////////
	        HttpClient httpClient = new DefaultHttpClient();
	        HttpResponse httpResponse = httpClient.execute(httpRequest);
	        final int ret = httpResponse.getStatusLine().getStatusCode();
	        Log.i("ret",String.valueOf(ret));
	        if(ret == HttpStatus.SC_OK){
	            responseStr = EntityUtils.toString(httpResponse.getEntity(), HTTP.UTF_8);
	            Log.i("responseStr",String.valueOf(responseStr));
	        }else
	        {
	            responseStr = "-1";
	        }
	   	  }
		   catch (UnsupportedEncodingException e) {
	           // TODO Auto-generated catch block
	           e.printStackTrace();
	       } catch (ClientProtocolException e) {
	           // TODO Auto-generated catch block
	           e.printStackTrace();
	       } catch (IOException e) {
	           // TODO Auto-generated catch block
	           e.printStackTrace();
	       }

	  
	        return responseStr;
	 
	    }
   
}  