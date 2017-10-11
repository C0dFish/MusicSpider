package com.edu.seu.MusicSpider.SpiderUtiils;

import java.util.HashMap;

import com.edu.seu.MusicSpider.model.Song;
import com.edu.seu.MusicSpider.service.DBService;
import com.edu.seu.MusicSpider.service.PlaylistService;

/**
 * @creat time 2017年4月23日 下午8:54:38
 * @author hao zhang
 * @version 1.0
 * @comment 错误网页重试机制
 */
public class ErrorRetry {
	  private HashMap<String, Integer> retryMap = new HashMap<String, Integer>();
	  private LogErrors logErrors = new LogErrors();
	  private int retryTimes = 0;
	  String responseLyricCode;//歌曲歌词
      String lyric;
	  
	  
      /**
     * 重试错误的页面，如果重试次数小于3，重试，否则放弃重试
     * @param name
     * @param linkHref
     */
    public void retryErrorPages(String name,String linkHref) {
    	SpiderApi spiderApi = new SpiderApi();
    	retryMap.put(linkHref,retryTimes );
    	
    	if (retryMap.get(linkHref) < 3) {
    		String responseSongDetailCode;//歌曲详情列表
        	
        	Song song = new Song();
        	DBService dbService = new DBService();
        	RegularExp regularExp = new RegularExp();
        	LogProcess logProcess = new LogProcess();
        	PlaylistService playlistService = new PlaylistService();
        	  
        	//获取歌单详情页源码
      		responseSongDetailCode =  playlistService.GetSongDetailSourceCode(linkHref); 
      		logProcess.LogProcessUtil("获取歌单详情页源码......"+"\r\n"+responseSongDetailCode);

      		//获取歌曲详情信息
      		song = regularExp.findSongDetail(responseSongDetailCode,linkHref,name);
      		
      		responseLyricCode = spiderApi.GetLrcJson(linkHref.substring(9));
			lyric = spiderApi.GetLrc(responseLyricCode);
			song.setLyric(lyric);
      		
      		System.out.println("歌曲名称：    "+song.getName()+"        "+"歌手：    "+song.getSinger()+"        "+"歌曲URL：    "
      				+song.getUrl()+"        "+"所属歌单链接：    "+song.getAlbumLink()+"        "+"所属歌单：    "+song.getAlbum());

      		//插入数据到数据库
      		dbService.addData(song);
      		retryTimes++;
		}else {
			logErrors.loggerErrors(linkHref);
		}
    	
	}
}
