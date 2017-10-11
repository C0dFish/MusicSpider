/**
 * 
 */
package com.edu.seu.MusicSpider.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.edu.seu.MusicSpider.SpiderUtiils.LogProcess;
import com.edu.seu.MusicSpider.SpiderUtiils.RegularExp;

/**
 * @creat time 2017年4月16日 下午1:10:11
 * @author hao zhang
 * @version 1.0
 * @comment 
 */
public class PlayListThreadService extends Thread{
	
	public String linkHref = null;
	public String name = null;
	
	public static final String GET_URL= "http://music.163.com/";
	
	String responseFirstPageSourceCode ;//首页源码
	String responsePlaylistSourceCode ;//歌单风格源码
	String responsePlayListCode ;//歌单列表页源码
	String responsePlayListDetailCode ;//歌单详情页源码
	String responseSongDetailCode;//歌曲详情列表
	
	HashMap<String, String> songStyle= new HashMap<String, String>();
	HashMap<String, String> playList= new HashMap<String, String>();
	HashMap<String, String> playListDetail= new HashMap<String, String>();

	LogProcess logProcess = new LogProcess();

    PlaylistService playlistService = new PlaylistService();
    RegularExp regularExp = new RegularExp();
    ExecutorService pool = Executors.newCachedThreadPool();
	
	public PlayListThreadService(String playlistName,String href){
		linkHref = href;
		name = playlistName;
	}
		
    @Override
	public void run() {
    	   	
       //获取歌单列表详情页源码
       responsePlayListDetailCode = playlistService.GetPlayListDetailSourceCode(linkHref);
        
      //获取歌单详情页歌曲信息
       playListDetail = regularExp.findSongListDetail(responsePlayListDetailCode);
       
       Iterator iterator =playListDetail.entrySet().iterator();
       while (iterator.hasNext()) {
			Map.Entry entry = (Entry) iterator.next();
			String key = entry.getKey().toString();
			String value = entry.getValue().toString();
			Thread songThread = new SongResolveThreadService(key,value);
			pool.execute(songThread);			
		}
	}
	

}
