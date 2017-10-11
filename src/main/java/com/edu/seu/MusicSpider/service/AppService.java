package com.edu.seu.MusicSpider.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.sql.PooledConnection;

import com.edu.seu.MusicSpider.SpiderUtiils.LogProcess;
import com.edu.seu.MusicSpider.SpiderUtiils.RegularExp;
import com.edu.seu.MusicSpider.model.Song;

/**
 * @creat time 2017年4月5日 下午11:10:43
 * @author hao zhang
 * @version 1.0
 * @comment 程序测试入口
 */
public class AppService 
{
	public static int DEFAULT_PLAYLIST_NUMBER = 20;//默认获取歌单数量
	public static int DEFAULT_DEPTH = 5;//默认相似歌曲深度
	public static int DEFAULT_KEY = 1;//默认关键词序号
	public static int DEFAULT_PAGE_NUMMBER = 35;
	
	public static final String GET_URL= "http://music.163.com/";
	HashMap<String, String> songStyle= new HashMap<String, String>();
	
	
	/**
	 * 获取歌单类型列表 
	 * @return
	 */
	public LinkedHashMap<String, String> getPlaylistType() {
    	String responseFirstPageSourceCode ;//首页源码
    	String responsePlaylistSourceCode ;//歌单风格源码
    	String responsePlayListCode ;//歌单列表页源码
    	String responsePlayListDetailCode ;//歌单详情页源码
    	String responseSongDetailCode;//歌曲详情列表
    	String href ;
    	String subURL = null, name = null;
    	
    	LinkedHashMap<String, String> songStyle= new LinkedHashMap<String, String>();
    	LinkedHashMap<String, String> playList= new LinkedHashMap<String, String>();
    	LinkedHashMap<String, String> playListDetail= new LinkedHashMap<String, String>();
    	
        ExecutorService pool = Executors.newCachedThreadPool();
     	
    	Song song = new Song();
    	
    	
    	//用户自定义参数列表,当用户不输入时将采用默认参数
    	int playlistNumber;//获取歌单数量
    	int keyWord;//相似歌曲深度
    	int depth;//关键词序号
    	
    	LogProcess logProcess = new LogProcess();
    	
    	//获取首页歌单源码
        PlaylistService playlistService = new PlaylistService();
        responseFirstPageSourceCode = playlistService.GetFirstPage();
        logProcess.LogProcessUtil("获取首页歌单源码......"+"\r\n"+responseFirstPageSourceCode);
        
        //获取歌单页链接
        RegularExp regularExp = new RegularExp();
        href = regularExp.findPlayListLink(responseFirstPageSourceCode);
        
        //获取歌单列表页源码
        responsePlaylistSourceCode = playlistService.GetPlayListDetail(href);
        logProcess.LogProcessUtil("获取歌单列表页源码......"+"\r\n"+responseFirstPageSourceCode);
        
        //获取歌单所有类型信息
        songStyle = regularExp.findPlayListType(responsePlaylistSourceCode);
        
        return songStyle;
	}
	
	
	/**
	 * @param keyPlayList 歌曲类型
	 * @param playListNumber 获取个数
	 */
	public void MusicSpider(String keyPlayList,int playListNumber) {
		
		LinkedHashMap<String, String> songStyle = getPlaylistType();
		String responseFirstPageSourceCode ;//首页源码
    	String responsePlaylistSourceCode ;//歌单风格源码
    	String responsePlayListCode ;//歌单列表页源码
    	String responsePlayListDetailCode ;//歌单详情页源码
    	String responseSongDetailCode;//歌曲详情列表
    	String href ;
    	String subURL = null, name = null;
    	
    	LinkedHashMap<String, String> playList= new LinkedHashMap<String, String>();
    	LinkedHashMap<String, String> playListMore= new LinkedHashMap<String, String>();
    	LinkedHashMap<String, String> playListDetail= new LinkedHashMap<String, String>();
    	
    	PlaylistService playlistService = new PlaylistService();
    	LogProcess logProcess = new LogProcess();
        RegularExp regularExp = new RegularExp();
    	
        ExecutorService pool = Executors.newCachedThreadPool();
     	
    	Song song = new Song();
		responsePlayListCode = playlistService.GetPlayListSourceCode(songStyle.get(keyPlayList));
        logProcess.LogProcessUtil("获取歌单列表页源码......"+"\r\n"+responsePlayListCode);
        
        //获取歌单列表信息
        playList = regularExp.findPlayListDetail(responsePlayListCode);
        
        
        //判断用户输入的歌单数是否超过翻页阈值
        if (playListNumber > DEFAULT_PAGE_NUMMBER) {
        	for (int i = 0; i < playListNumber/35; i++) {
        		String morePageLink;
                String preLink = "/discover/playlist/?order=hot&";
                String string = songStyle.get(keyPlayList);
                String string2 = string.substring(20);
                String midLink = string2.replace("order=hot", "");
                String behindLink = "&limit=35&offset=";
                String offset = String.valueOf((i+1)*35);
                morePageLink = preLink+midLink+behindLink+offset;
                responsePlayListCode = playlistService.GetPlayListSourceCode(morePageLink);
                playListMore = regularExp.findPlayListDetail(responsePlayListCode);
                
                //加入统一的歌单Map里，便于管理
                Iterator iterator =playListMore.entrySet().iterator();
                while (iterator.hasNext()) {
                	Map.Entry entry = (Entry) iterator.next();
        			String key = entry.getKey().toString();
        			String value = entry.getValue().toString();
        			playList.put(key, value);	
        		}                                
                
			}
        
		}
        
        //线程池进行管理歌单解析
        Iterator iterator =playList.entrySet().iterator();
        for (int i = 0; i < playListNumber; i++) {
        	Map.Entry entry = (Entry) iterator.next();
			String key = entry.getKey().toString();
			String value = entry.getValue().toString();
			Thread thread = new PlayListThreadService(key,value);
			pool.execute(thread);	
		}
		
	}
	
}
