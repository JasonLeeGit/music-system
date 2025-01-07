package com.ltd.coders.software.music.system.entity;

import org.apache.tomcat.util.codec.binary.Base64;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AlbumResponse {

	private Integer id;

	private String artistName;

	private String albumName;

	private String yearReleased;

	private String albumPrice;
	
	private String imageAlbumCover;
	
	@SuppressWarnings("deprecation")
	public AlbumResponse(Integer id, String artistName, String albumName, String released, 
			String albumPrice, byte[] imageAlbumCover) {
		this.id = id;
		this.artistName = artistName;
		this.albumName = albumName;
		this.yearReleased = released;
		this.albumPrice = albumPrice;
		this.imageAlbumCover = imageAlbumCover != null ? Base64.encodeBase64String(imageAlbumCover) : null;
	}
}
