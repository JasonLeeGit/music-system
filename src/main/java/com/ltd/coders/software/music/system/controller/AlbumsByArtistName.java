package com.ltd.coders.software.music.system.controller;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ltd.coders.software.music.system.entity.Album;
import com.ltd.coders.software.music.system.entity.AlbumResponse;
import com.ltd.coders.software.music.system.service.IAlbumService;

@RestController
@RequestMapping("/v1/music/service")
public class AlbumsByArtistName {

	private IAlbumService albumService;

	private AlbumsByArtistName(IAlbumService albumService) {
		this.albumService = albumService;
	}
	
	@GetMapping("/search/albums/")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<List<AlbumResponse>> findAlbumDataForArtistName(@RequestParam("artistName") String artistName)
			throws SQLException {
		List<Album> albums = albumService.findByArtistName(artistName);
		List<AlbumResponse> albumResponses = new ArrayList<>();

		if (albums != null && albums.size() > 0) {
			for (Album album : albums) {

				Blob photoBlob = album.getImageAlbumCover();
				if (photoBlob != null) {
					byte[] photoBytes = photoBlob.getBytes(1, (int) photoBlob.length());

					AlbumResponse response = new AlbumResponse(album.getId(), album.getArtistName(),
							album.getAlbumName(), album.getYearReleased(), album.getAlbumPrice(), photoBytes);

					albumResponses.add(response);
				} else {
					AlbumResponse response = new AlbumResponse(album.getId(), album.getArtistName(),
							album.getAlbumName(), album.getYearReleased(), album.getAlbumPrice(), null);

					albumResponses.add(response);
				}
			}
			return ResponseEntity.ok(albumResponses);
		} else {
			return ResponseEntity.noContent().build();
		}
	}
}
