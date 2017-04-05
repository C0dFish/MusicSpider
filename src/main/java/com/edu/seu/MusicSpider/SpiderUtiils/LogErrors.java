package com.edu.seu.MusicSpider.SpiderUtiils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;


/**
 * @creat time 2017年4月5日 下午10:56:24
 * @author hao zhang
 * @version 1.0
 * @comment 控制台输出错误日志，并归档至文本日志中
 */
public class LogErrors {
	static File file = new File("D:\\请求日志.txt");
	/**
	 * @param URL 错误请求链接
	 * @param statusCode 错误请求码
	 */
	public static void loggerErrors(String URL,int statusCode) {
		try {
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.append("请求网页错误，错误码：  "+statusCode+"  请求网页地址："+URL.toString());
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
