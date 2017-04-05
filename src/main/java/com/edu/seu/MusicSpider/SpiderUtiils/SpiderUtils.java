package com.edu.seu.MusicSpider.SpiderUtiils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

public class SpiderUtils {
	
	//控制台输出错误日志，并归档至文本日志中
	static File file = new File("D:\\请求日志.txt");
	public static void loggerErrors(String getUrl,int statusCode) {
		try {
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.append("请求网页错误，错误码：  "+statusCode+"  请求网页地址："+getUrl.toString());
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
