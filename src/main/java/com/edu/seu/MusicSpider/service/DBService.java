/**
 * @creat time 2017年4月16日 下午4:54:03
 * @author hao zhang
 * @version 1.0
 * @comment 数据库CRUD操作类
 */
package com.edu.seu.MusicSpider.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.edu.seu.MusicSpider.SpiderUtiils.DBConnect;
import com.edu.seu.MusicSpider.model.Song;

public class DBService {

	Connection con=DBConnect.getConnection();

	//插入一条数据到数据库
	public void addGoddess(Song song){
		String sql="insert into song_info"
				+"(name,singer,lyric,album,albumLink,url,comment,add_time,update_time) "
				+"values("
				+" ?,?,?,?,?,?,?,?,?"
				+")";

		//预编译sql语句
		try {
			PreparedStatement psmt = con.prepareStatement(sql);

			//先对应SQL语句，给SQL语句传递参数
			psmt.setString(1, song.getName());
			psmt.setString(2, song.getSinger());
			psmt.setString(3, song.getLyric());
			psmt.setString(4, song.getAlbum());
			psmt.setString(5, song.getAlbumLink());
			psmt.setString(6, song.getUrl());
			psmt.setInt(7, song.getComment());
			psmt.setTimestamp(8, new Timestamp(System.currentTimeMillis())); 
			psmt.setDate(9, new Date(new java.util.Date().getTime()));

			//执行SQL语句
			psmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//更新一条数据到数据库
	public void updateGoddess(){

	}

	//从数据库库删除一条数据
	public void delGoddess(){

	}

	//从数据库批量查询数据
	public List<Song> query() throws Exception{
		return null;

	}

}
