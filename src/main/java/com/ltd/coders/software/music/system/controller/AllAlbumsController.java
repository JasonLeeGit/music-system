package com.ltd.coders.software.music.system.controller;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ltd.coders.software.music.system.entity.Album;
import com.ltd.coders.software.music.system.entity.AlbumResponse;
import com.ltd.coders.software.music.system.service.IAlbumService;

@RestController
@RequestMapping("/v1/music/service")
public class AllAlbumsController {

	private IAlbumService albumService;

	// TODO validation on form object as well as validation in HTML
	// add unit tests
	private AllAlbumsController(IAlbumService albumService) {
		this.albumService = albumService;
	}

	@GetMapping("/all/albums")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<List<AlbumResponse>> fetchAllAlbumDataOrderedByArtistName() throws SQLException {
		List<Album> albums = albumService.findAllAlbumsOrderedByArtistName();
		List<AlbumResponse> albumResponses = new ArrayList<>();
		if (albums != null) {
			for (Album album : albums) {

				Blob imageAlbumCoverBlob = album.getImageAlbumCover();
				if (imageAlbumCoverBlob != null) {
					byte[] imageAlbumCoverBytes = imageAlbumCoverBlob.getBytes(1, (int) imageAlbumCoverBlob.length());

					AlbumResponse response = new AlbumResponse(album.getId(), album.getArtistName(),
							album.getAlbumName(), album.getYearReleased(), album.getAlbumPrice(), imageAlbumCoverBytes);

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
