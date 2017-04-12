package com.edu.seu.MusicSpider.SpiderUtiils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.print.Doc;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @creat time 2017年4月5日 下午11:38:32
 * @author hao zhang
 * @version 1.0
 * @comment 正则匹配工具类
 */
public class RegularExp {
	
   /**
 * @param txt 待匹配源码
 * @return 匹配结果
 * @comment 查找歌单详情页匹配
 */
public String  findPlayListLink(String txt) {
	   
	   String unixpath1 = null;
	   String re1=".*?";	// Non-greedy match on filler
	   String re2="(\\/discover\\/playlist)";	// Unix Path 1
	   

	    Pattern p = Pattern.compile(re1+re2,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	    Matcher m = p.matcher(txt);
	    if (m.find())
	    {
	      unixpath1=m.group(1);
	    }
		 
	    return unixpath1;
}	
   

/**
* @param txt 待匹配源码
* @return 匹配结果
* @comment 查找歌单分类匹配
*/
   public HashMap<String, String> findPlayListType(String txt) {
    HashMap<String, String> songStyle= new HashMap<String, String>();
	Document doc = Jsoup.parse(txt);
	Elements body = doc.getElementsByClass("bd");
	Document moduleDoc = Jsoup.parse(body.toString());
	Elements fcb = moduleDoc.select(".f-cb");
	
	
	for (Element fcbli : fcb){
        Document fcbDoc = Jsoup.parse(fcbli.toString());
        Elements dd = fcbDoc.getElementsByTag("dd");
        
        for (Element ddli : dd) {
        	Elements links = ddli.getElementsByTag("a");
        	
        	for (Element link : links) {
        		  String linkHref = link.attr("href");
        		  String linkText = link.text();
        		  songStyle.put(linkText, linkHref);
        		}       	         
		}


    }
	return songStyle;
	
}
   
   
   /**
 * @param txt 歌单详情页源码
 * @return  歌单列表
 * @comment 解析歌单详情页歌曲 
 */
public HashMap<String,String> findPlayListDetail(String txt){
	HashMap<String, String> playList = new HashMap<String, String>();	
	Document doc = Jsoup.parse(txt);
	Elements body = doc.getElementsByTag("ul");
	
	for (Element element : body) {
		Document fcbDoc = Jsoup.parse(element.toString());
		Elements li = fcbDoc.getElementsByTag("li");
		for (Element lili : li) {
			Elements liliDetail = lili.getElementsByTag("a");
			String linkHref = liliDetail.get(0).attr("href");
			String linkText = liliDetail.get(0).attr("title");
			playList.put(linkText, linkHref);
		}
	} 
	return playList;
	   
   }

/**
* @param txt 歌单列表页源码
* @return  歌单链接
* @comment 解析歌单列表页歌曲列表
*/
public HashMap<String,String> findSongListDetail(String txt){
	HashMap<String, String> playList = new HashMap<String, String>();	
	Document doc = Jsoup.parse(txt);
	Elements body = doc.getElementsByTag("ul");
	Element element = body.get(0);
	Document fcbDoc = Jsoup.parse(element.toString());
	Elements li = fcbDoc.getElementsByTag("li");
	
	 for (Element lili : li) {
			Elements liliDetail = lili.getElementsByTag("a");
			String linkHref = liliDetail.attr("href");
			String linkText = liliDetail.text();
			playList.put(linkText, linkHref);
		}
	
	return playList;
	   
   }
	
}
