package com.edu.seu.MusicSpider;

import com.edu.seu.MusicSpider.service.PlaylistService;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {   
    	String responseFirstPageSourceCode ;
        PlaylistService playlistService = new PlaylistService();
        responseFirstPageSourceCode = playlistService.GetFirstPage();
        System.out.println(responseFirstPageSourceCode);
    }
}
