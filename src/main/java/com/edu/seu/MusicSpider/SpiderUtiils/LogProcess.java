/**
 * 
 */
package com.edu.seu.MusicSpider.SpiderUtiils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @creat time 2017年4月12日 下午9:44:06
 * @author hao zhang
 * @version 1.0
 * @comment 
 */
public class LogProcess {
	static File file = new File("D:\\logs\\爬虫执行日志.txt");
	static File file1 = new File("D:\\logs\\请求错误日志.txt");
	
	/**
	 * @param log  日志记录
	 */
	public  void LogProcessUtil(String log) {
		try {
			FileWriter fileWriter = new FileWriter(file,true);
			fileWriter.write(log+"\r\n"+"\r\n\r\n\r\n\r\n"+"-----------------------------");
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * @param URL 错误请求链接
	 * @param statusCode 错误请求码
	 */
	public static void loggerErrors(String URL) {
		try {
			FileWriter fileWriter = new FileWriter(file1);
			fileWriter.append("请求网页错误，错误网页地址："+URL.toString()+"\r\n");
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
