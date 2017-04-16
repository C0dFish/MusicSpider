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
import com.edu.seu.MusicSpider.model.Song;

/**
 * @creat time 2017年4月16日 下午1:11:09
 * @author hao zhang
 * @version 1.0
 * @comment 
 */
public class SongResolveThread extends Thread{

	public String linkHref = null;
	public String name = null;

	String responseFirstPageSourceCode ;//首页源码
	String responsePlaylistSourceCode ;//歌单风格源码
	String responsePlayListCode ;//歌单列表页源码
	String responsePlayListDetailCode ;//歌单详情页源码
	String responseSongDetailCode;//歌曲详情列表

	HashMap<String, String> songStyle= new HashMap<String, String>();
	HashMap<String, String> playList= new HashMap<String, String>();
	HashMap<String, String> playListDetail= new HashMap<String, String>();

	ExecutorService pool = Executors.newCachedThreadPool();
	RegularExp regularExp = new RegularExp();
	DBService dbService = new DBService();

	LogProcess logProcess = new LogProcess();
	PlaylistService playlistService = new PlaylistService();

	Song song = new Song();

	public SongResolveThread(String playlistName,String href){
		linkHref = href;
		name = playlistName;
	}

	@Override
	public void run() {
		//获取歌单详情页源码
		responseSongDetailCode =  playlistService.GetSongDetailSourceCode(linkHref); 
		logProcess.LogProcessUtil("获取歌单详情页源码......"+"\r\n"+responseFirstPageSourceCode);

		//获取歌曲详情信息
		song = regularExp.findSongDetail(responseSongDetailCode,linkHref,name);
		System.out.println("歌曲名称：    "+song.getName()+"        "+"歌手：    "+song.getSinger()+"        "+"歌曲URL：    "
				+song.getUrl()+"        "+"所属歌单链接：    "+song.getAlbumLink()+"        "+"所属歌单：    "+song.getAlbum());

		//插入数据到数据库
		dbService.addGoddess(song);

	}

}
