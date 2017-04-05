package com.edu.seu.MusicSpider.SpiderUtiils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;




/**
 * @creat time 2017年4月5日 下午10:48:08
 * @author hao zhang
 * @version 1.0
 * @comment 
 */
public class RestClient {	
	/**
	 * @param URL 请求链接
	 * @return  请求响应
	 */
	public String doGet(String URL){
	        BufferedReader in = null;        
	        StringBuilder result = new StringBuilder(); 
	        try {
	            URL url = new URL(URL);
	            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	            conn.setRequestMethod("GET");
	            //Get请求不需要DoOutPut
	            conn.setDoOutput(false);
	            conn.setDoInput(true);
	            //设置连接超时时间和读取超时时间
	            conn.setConnectTimeout(10000);
	            conn.setReadTimeout(10000);
	            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	            //连接服务器  
	            conn.connect();  
	            // 取得输入流，并使用Reader读取  
	            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result.append(line);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        //关闭输入流
	        finally{
	            try{
	                if(in!=null){
	                    in.close();
	                }
	            }
	            catch(IOException ex){
	                ex.printStackTrace();
	            }
	        }
	        return result.toString();
	    }
	
	
	
	/**
	 * @param URL 请求链接
	 * @param requestPara 请求参数
	 * @return 请求返回
	 */
	public String doPost(String URL,HashMap<String, String> requestPara ){
	        OutputStreamWriter out = null;
	        BufferedReader in = null;        
	        StringBuilder result = new StringBuilder(); 
	        try {
	            URL url = new URL(URL);
	            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	            conn.setRequestMethod("Post");
	            // 发送POST请求必须设置为true
	            conn.setDoOutput(true);
	            conn.setDoInput(true);
	            //设置连接超时时间和读取超时时间
	            conn.setConnectTimeout(10000);
	            conn.setReadTimeout(10000);
	            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

	            out = new OutputStreamWriter(conn.getOutputStream());  
	            // POST的请求参数写在正文中
	            for(String key:requestPara.keySet()){
	                out.write(key + "=" + requestPara.get(key) + "&");  
	            }
	            out.flush();  
	            out.close();
	            // 取得输入流，并使用Reader读取  
	            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result.append(line);
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        //关闭输出流、输入流
	        finally{
	            try{
	                if(out!=null){
	                    out.close();
	                }
	                if(in!=null){
	                    in.close();
	                }
	            }
	            catch(IOException ex){
	                ex.printStackTrace();
	            }
	        }
	        return result.toString();
	    }
	

}

