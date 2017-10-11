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
	        String responseCookie1 = "_ntes_nnid=8e3afc28f78444585037c8705ec8c44e,1492444185195; _ntes_nuid=8e3afc28f78444585037c8705ec8c44e; playerid=10144749; J";
	        String responseCookie2 = "SESSIONID-WYYY=0YFIc1SWaPnq8AUqsQHCN9EEEu48WQxXIX9ETTv2jsRE8%2FJBZ%2FmF3T60gWP3QtRUI4w44qlWXK1kl11DTB3WihN55SOOvxMTnN5f8%5CoGZ%5CxrUXO%2BUN6";
	        String responseCookie3 = "HBlHlSsfGC0WsoBRQlAmIS7M2s9d%2B4EElxhsAxTi%2B2XYch3bWnZtKX2vGMq%2BJ%3A1493088420824; _iuqxldmzr_=32; NTES_SESS=ykUt00drymUcLnZvEMLpkhv.qvHQxB";
	        String responseCookie4 = "h344P566NgPfoOpvFRpWZ9_i6QG1.7LgALqT6Vgu9_FfzDwwXYeSLcaa70oZe4echg8C3vScaOuqDCZFVSLza2_juhs7MOEx.zjwhSbfiVRrZwhZ4VLYOtx2D1qbUI4jUSrrQlK4sWqMf";
	        String responseCookie5 = "lyePVBtCDFubT9; S_INFO=1493086779|0|3&80##|halehalechang; P_INFO=\"halehalechang@163.com|1493086779|0|unireg|00&99|null&null&null#jis&320100#1";
	        String responseCookie6 = "0#0#0|&0||halehalechang@163.com\"; __remember_me=true; MUSIC_U=cd1cbc6feca007a2d6e018619cc9a3145ad7bfae54577152e6fce019e4bfa67fd230838b83e9cf31";
	        String responseCookie7 = "6ba825ad3c7b208abbd801ccd0d6425a7955a739ab43dce1; __csrf=1d4f006f7c555d7254b29f71ebf7e055; jsessionid-cpta=zWVGOZIcSR5ypgDIPjkRIyjTaaPrBZDtBAVf1rcgT4iHvo7beCp2JnXnCFj%2FvwckMhl9blg7A4LDMhrxoIUqNJ%2FvmnNPAM0vNDQQwyBWxH7YrSQUJ68pewBEph9%5CbxYKMmPV08ESv1oqeXEixFFgA0Lyl%2BvTQrpA556cpUAQEeMC2T3u%3A1493087713933; c98xpt_=30; NETEASE_WDA_UID=473585198#|#1493086868679; __utma=94650624.218258325.1492444186.1493050495.1493086621.10; __utmb=94650624.4.10.1493086621; __utmc=94650624; __utmz=94650624.1493086621.10.7.utmcsr=baidu|utmccn=(organic)|utmcmd=organic";
	        String responseCookie = responseCookie1+responseCookie2+responseCookie3+responseCookie4+responseCookie5+responseCookie6+responseCookie7 ;
	        	
	        
	        try {
	            URL url = new URL(URL);
	            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	            conn.setRequestMethod("GET");
	            //Get请求不需要DoOutPut
	            conn.setDoOutput(false);
	            conn.setDoInput(true);
	            //设置连接超时时间和读取超时时间
	            conn.setConnectTimeout(200000);
	            conn.setReadTimeout(200000);
	            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	            conn.setRequestProperty("Cookie", responseCookie);
	            //连接服务器  
	            conn.connect();  
	            // 取得输入流，并使用Reader读取  
	            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result.append(line);
	            }
	        } catch (Exception e) {
	            System.out.println("网易云页面拒绝访问，页面访问超时，进行重试！");
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
	 * @return  请求响应
	 */
	public String doGetWithoutCookie(String URL){
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
	            conn.setConnectTimeout(200000);
	            conn.setReadTimeout(200000);
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
	            System.out.println("网易云页面拒绝访问，页面访问超时，进行重试！");
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

