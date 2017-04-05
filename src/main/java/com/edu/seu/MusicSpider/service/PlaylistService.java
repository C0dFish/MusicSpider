package com.edu.seu.MusicSpider.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;

import com.edu.seu.MusicSpider.SpiderUtils;

public class PlaylistService {
  public static final String GET_URL= "http://music.163.com/";
  public int statusCode;
  private static final Logger LOGGER = Logger.getLogger(PlaylistService.class);

  //获取首页歌单列表链接 
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
          conn.setReadTimeout(20000);
          //设置连接超时时间
          conn.setConnectTimeout(20000);
          conn.connect();
          statusCode = conn.getResponseCode();
          if (200 == statusCode) {
        	  rd  = new BufferedReader( new InputStreamReader(conn.getInputStream(), "UTF-8"));
              while ((line = rd.readLine()) != null ) {
                  sb.append(line);
              }
              FirstPageResponse = sb.toString();
		}else {
			LOGGER.error("请求首页错误，"+"错误类型：  "+statusCode);
			SpiderUtils.loggerErrors(GET_URL, statusCode);
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
  
}
