package com.edu.seu.MusicSpider;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.sql.PooledConnection;

import com.edu.seu.MusicSpider.SpiderUtiils.LogProcess;
import com.edu.seu.MusicSpider.SpiderUtiils.RegularExp;
import com.edu.seu.MusicSpider.model.Song;
import com.edu.seu.MusicSpider.service.PlayListThread;
import com.edu.seu.MusicSpider.service.PlaylistService;

/**
 * @creat time 2017年4月5日 下午11:10:43
 * @author hao zhang
 * @version 1.0
 * @comment 程序测试入口
 */
public class App 
{
	public static int DEFAULT_PLAYLIST_NUMBER = 20;//默认获取歌单数量
	public static int DEFAULT_DEPTH = 5;//默认相似歌曲深度
	public static int DEFAULT_KEY = 1;//默认关键词序号
	
	public static final String GET_URL= "http://music.163.com/";
	
	
    public static void main( String[] args )
    {   
    	
    	String responseFirstPageSourceCode ;//首页源码
    	String responsePlaylistSourceCode ;//歌单风格源码
    	String responsePlayListCode ;//歌单列表页源码
    	String responsePlayListDetailCode ;//歌单详情页源码
    	String responseSongDetailCode;//歌曲详情列表
    	String href ;
    	String subURL = null, name = null;
    	
    	HashMap<String, String> songStyle= new HashMap<String, String>();
    	HashMap<String, String> playList= new HashMap<String, String>();
    	HashMap<String, String> playListDetail= new HashMap<String, String>();
    	
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
        
        //获取歌单列表页源码
        responsePlayListCode = playlistService.GetPlayListSourceCode("/discover/playlist/?cat=%E6%B0%91%E8%B0%A3");
        logProcess.LogProcessUtil("获取歌单列表页源码......"+"\r\n"+responseFirstPageSourceCode);
        
        //获取歌单列表信息
        playList = regularExp.findPlayListDetail(responsePlayListCode);
        
        //线程池进行管理歌单解析
        Iterator iterator =playList.entrySet().iterator();
        while (iterator.hasNext()) {
			Map.Entry entry = (Entry) iterator.next();
			String key = entry.getKey().toString();
			String value = entry.getValue().toString();
			Thread thread = new PlayListThread(key,value);
			pool.execute(thread);			
		}
//        
//        //获取歌单列表详情页源码
//        responsePlayListDetailCode = playlistService.GetPlayListDetailSourceCode("/playlist?id=373234807");
//        logProcess.LogProcessUtil("获取歌单列表详情页源码......"+"\r\n"+responseFirstPageSourceCode);
//        
//        //获取歌单详情页歌曲信息
//        playListDetail = regularExp.findSongListDetail(responsePlayListDetailCode);
//        
//        //获取歌单详情页源码
//        responseSongDetailCode =  playlistService.GetSongDetailSourceCode("/song?id=27598706"); 
//        logProcess.LogProcessUtil("获取歌单详情页源码......"+"\r\n"+responseFirstPageSourceCode);
//        
//        //获取歌曲详情信息
//        song = regularExp.findSongDetail(responseSongDetailCode,subURL,name);
//               
    }
}
