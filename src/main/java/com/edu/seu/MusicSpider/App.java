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
        PlaylistService playlistService = new PlaylistService();
        playlistService.GetFirstPage();
    }
}
