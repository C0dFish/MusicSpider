package com.edu.seu.MusicSpider.SpiderUtiils;

import java.util.HashMap;
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
 * @
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
*/
   public HashMap<String, String> findPlayListDetail(String txt) {
	Document doc = Jsoup.parse(txt);
	Elements body = doc.getElementsByClass("bd");
	Document moduleDoc = Jsoup.parse(body.toString());
	Elements fcb = moduleDoc.select(".f-cb");
	for (Element fcbli : fcb){
		HashMap<String, String> songStyle= new HashMap<String, String>();
        Document fcbDoc = Jsoup.parse(fcbli.toString());
        Elements dd = fcbDoc.getElementsByTag("dd");
     
        for (Element ddli : dd) {
        	Document ddDoc = Jsoup.parse(dd.toString());
        	Elements aTag = ddDoc.getElementsByClass("s-fc1");
//        	for (Element aLi : aTag) {
        		String link = aTag.attr("href");
        		String catlog = aTag.attr("data-cat");
        		System.out.println("链接："+link+"    分类："+catlog);
			}
        	         
//		}


    }
	return null;
	
}
	
}
