package com.edu.seu.MusicSpider.model;

public class Song {
	public String name = null;
	   public String singer = null;
	   public String lyric = null;
	   public String album = null;
	   public String albumLink = null;
	   public String url = null;
	   public int comment = 0;
	   
   public String getAlbumLink() {
		return albumLink;
	}
   public void setAlbumLink(String albumLink) {
		this.albumLink = albumLink;
	}
	
   public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSinger() {
		return singer;
	}
	public void setSinger(String singer) {
		this.singer = singer;
	}
	public String getLyric() {
		return lyric;
	}
	public void setLyric(String lyric) {
		this.lyric = lyric;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getComment() {
		return comment;
	}
	public void setComment(int comment) {
		this.comment = comment;
	}

}
