/**
 * 
 */
package com.edu.seu.MusicSpider.SpiderUtiils;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.codec.binary.Hex;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.alibaba.fastjson.JSONObject;
import com.edu.seu.MusicSpider.model.Song;
import com.edu.seu.MusicSpider.service.DBService;
import com.edu.seu.MusicSpider.service.PlaylistService;
import com.edu.seu.MusicSpider.service.SongResolveThreadService;



/**
 * @creat time 2017年4月23日 下午11:59:03
 * @author hao zhang
 * @version 1.0
 * @comment 
 */
public class SpiderApi {

	//获取lrc歌词
	public  String GetLrcJson(String sid) {
		String url = "http://music.163.com/api/song/lyric?os=pc&id=" + sid + "&lv=-1&kv=-1&tv=-1";
		String html = NetUtil.GetHtmlContent(url, true);
		if (html.contains("uncollected")) {
			return "";
		}
		return html;
	}

	//获取歌词详情
	public String GetLrc(String response) {
		JSONObject json = JSONObject.parseObject(response);
		Object jsonLyc;
		if (json.get("lrc") == null || json.get("lrc").equals("")) {
			return "";
		}else {
			jsonLyc = json.get("lrc");
		}

		return jsonLyc.toString();

	}

	//根据歌曲ID获取推荐歌曲
	public void getRecoSong(String songId ,int depth) {
		LinkedHashMap<String, String> recoList = new LinkedHashMap<String, String>();	
		HashMap<String, String> crawlMap = new HashMap<String, String>();
		PlaylistService playlistService = new PlaylistService();
		RegularExp regularExp = new RegularExp();
		ExecutorService pool = Executors.newCachedThreadPool();
		Queue<String> queue = new LinkedList<String>();
		Queue<String> queueSwap = new LinkedList<String>();
		DBService dbService = new DBService();
		Song song = new Song();
		
		String songName;
		String songSinger;
		String songUrl;
		String songAlbumLink;
		String songAlbum;
		
		
		String response = playlistService.GetSongDetailSourceCode(songId); 
		recoList = regularExp.findRecoSongDetail(response);

		Iterator iter = recoList.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();			
			String linkHref = entry.getValue().toString();
            queue.offer(linkHref);		
		}
        
		
		//设置两个交换队列，用于判断广度优先是否已经达到用户自定义的深度
        for (int i = 1; i < depth; i++) {
		   while (queue.peek() != null) {
			String link = queue.poll();
			if (!crawlMap.containsKey(link)) {
			song = regularExp.findSongDetail(link);
			if (song != null ) {
				songName = song.getName();
				songSinger = song.getSinger();
				songUrl = song.getUrl();
				songAlbumLink = song.getAlbumLink();
				songAlbum = song.getAlbum();
				
				System.out.println("歌曲名称：    "+song.getName()+"        "+"歌手：    "+song.getSinger()+"        "+"歌曲URL：    "
				+song.getUrl()+"        "+"所属歌单链接：    "+song.getAlbumLink()+"        "+"所属歌单：    "+song.getAlbum());
				
				dbService.addData(song);
				String responseCode = playlistService.GetSongDetailSourceCode(link);
				LinkedHashMap<String, String> recoListCopy = regularExp.findRecoSongDetail(responseCode);
				Iterator iterator = recoListCopy.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry entry = (Map.Entry) iterator.next();			
					String linkHref = entry.getValue().toString();
					queueSwap.offer(linkHref);			
				}	
				
				crawlMap.put(link, song.getName());
			}
			
		}
		   		   
		}
		       queue = queueSwap;
      }
        
	}

}
