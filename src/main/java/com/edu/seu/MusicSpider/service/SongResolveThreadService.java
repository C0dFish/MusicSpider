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
import com.edu.seu.MusicSpider.SpiderUtiils.SpiderApi;
import com.edu.seu.MusicSpider.model.Song;

/**
 * @creat time 2017年4月16日 下午1:11:09
 * @author hao zhang
 * @version 1.0
 * @comment 
 */
public class SongResolveThreadService extends Thread{

	public String linkHref = null;
	public String name = null;
	
    
	String responseFirstPageSourceCode ;//首页源码
	String responsePlaylistSourceCode ;//歌单风格源码
	String responsePlayListCode ;//歌单列表页源码
	String responsePlayListDetailCode ;//歌单详情页源码
	String responseSongDetailCode;//歌曲详情列表
	String responseLyricCode;//歌曲歌词
	String lyric;

	HashMap<String, String> songStyle= new HashMap<String, String>();
	HashMap<String, String> playList= new HashMap<String, String>();
	HashMap<String, String> playListDetail= new HashMap<String, String>();
	HashMap<String, String> crawlMap = new HashMap<String, String>();

	ExecutorService pool = Executors.newCachedThreadPool();
	RegularExp regularExp = new RegularExp();
	DBService dbService = new DBService();
	SpiderApi spiderApi = new SpiderApi();

	LogProcess logProcess = new LogProcess();
	PlaylistService playlistService = new PlaylistService();

	Song song = new Song();

	public SongResolveThreadService(String playlistName,String href){
		linkHref = href;
		name = playlistName;
	}
	
	@Override
	public void run() {

		//获取歌曲详情页源码
		if (!crawlMap.containsKey(linkHref)) {
			responseSongDetailCode =  playlistService.GetSongDetailSourceCode(linkHref); 
			logProcess.LogProcessUtil("获取歌单详情页源码......"+"\r\n"+responseSongDetailCode);

			//获取歌曲详情信息
			song = regularExp.findSongDetail(responseSongDetailCode,linkHref,name);
			
			//由于歌词位动态加载，进行独立解析
			responseLyricCode = spiderApi.GetLrcJson(linkHref.substring(9));
			lyric = spiderApi.GetLrc(responseLyricCode);
			song.setLyric(lyric);
			
			System.out.println("歌曲名称：    "+song.getName()+"        "+"歌手：    "+song.getSinger()+"        "+"歌曲URL：    "
					+song.getUrl()+"        "+"所属歌单链接：    "+song.getAlbumLink()+"        "+"所属歌单：    "+song.getAlbum());

			//插入数据到数据库
			dbService.addData(song);

			//将爬取过的网页放入HashMap中，避免重复爬取降低效率及数据重复
			crawlMap.put(linkHref, name);
		}

	}

}
