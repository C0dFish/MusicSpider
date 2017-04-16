/**
 * @creat time 2017年4月16日 下午5:35:05
 * @author hao zhang
 * @version 1.0
 * @comment 数据库连接工具类
 */
package com.edu.seu.MusicSpider.SpiderUtiils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    
    private static final String URL="jdbc:mysql://localhost:3306/MusicSpider";
    private static final String NAME="music_spider";
    private static final String PASSWORD="admin";
    
    private static Connection conn=null;

    static{
        try {
            //1.加载驱动程序
            Class.forName("com.mysql.jdbc.Driver");
            //2.获得数据库的连接
            conn = DriverManager.getConnection(URL, NAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //对外提供一个方法来获取数据库连接
    public static Connection getConnection(){
        return conn;
    }
    
}