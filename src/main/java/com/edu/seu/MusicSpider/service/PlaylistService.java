package com.edu.seu.MusicSpider.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;

import com.edu.seu.MusicSpider.SpiderUtiils.LogErrors;
import com.edu.seu.MusicSpider.SpiderUtiils.RestClient;

/**
 * @creat time 2017年4月5日 下午11:36:08
 * @author hao zhang
 * @version 1.0
 * @comment 
 */
public class PlaylistService {
  public static final String GET_URL= "http://music.163.com/";
  public int statusCode;
  private static final Logger LOGGER = Logger.getLogger(PlaylistService.class);

  
 /**
 * @return 获取种子链接页面源码
 */
public String GetFirstPage(){
      HttpURLConnection conn = null;
      BufferedReader rd = null ;
      StringBuilder sb = new StringBuilder ();
      String line = null ;
      String FirstPageResponse = null;
      try {
          conn = (HttpURLConnection) new URL(GET_URL).openConnection();
          conn.setRequestMethod("GET");
          conn.setDoInput(true);
          //设置读超时时间
          conn.setReadTimeout(200000);
          //设置连接超时时间
          conn.setConnectTimeout(200000);
          conn.connect();
          statusCode = conn.getResponseCode();
          if (200 == statusCode) {
        	  rd  = new BufferedReader( new InputStreamReader(conn.getInputStream(), "UTF-8"));
              while ((line = rd.readLine()) != null ) {
                  sb.append(line);
              }
              FirstPageResponse = sb.toString();
		}else {
			System.out.println("nio");
			LOGGER.error("请求首页错误，"+"错误类型：  "+statusCode);
			LogErrors.loggerErrors(GET_URL);
		}          
      } catch (MalformedURLException e) {
          e.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      }finally{      
          try {
              if(rd != null){
                  rd.close();
              }
              if(conn != null){
                  conn.disconnect();
              }
          } catch (IOException e) {
              e.printStackTrace();
          }
      }
      LOGGER.debug("请求成功");
      return FirstPageResponse;
  }
     


     /**
     * @param path 请求路径
     * @return 歌单列表风格详情页源码
     */
    public String GetPlayListDetail(String path) {
    	String response;
    	RestClient restClient = new RestClient();
    	String url = GET_URL+path.substring(1);
    	response = restClient.doGet(url);
		return response;		
	}
    
    
    /**
     * @param path 请求路径
     * @return 歌单列表页源码
     */
    public String GetPlayListSourceCode(String path) {
    	String response;
    	RestClient restClient = new RestClient();
    	String url = GET_URL+path.substring(1);
    	response = restClient.doGet(url);
		return response;		
	}
    
    
    /**
     * @param path
     * @return 歌曲列表页源码
     */
    public String GetPlayListDetailSourceCode(String path) {
    	String response;
    	RestClient restClient = new RestClient();
    	String url = GET_URL+path.substring(1);
    	response = restClient.doGet(url);
		return response;		
	}
    
    
    /**
     * @param path
     * @return 歌曲详情页源码
     */
    public String GetSongDetailSourceCode(String path) {
    	String response;
    	RestClient restClient = new RestClient();
    	String url = GET_URL+path.substring(1);
    	response = restClient.doGet(url);
		return response;		
	}
    
  
}
