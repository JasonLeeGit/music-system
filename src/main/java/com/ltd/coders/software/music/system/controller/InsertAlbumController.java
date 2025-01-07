package com.ltd.coders.software.music.system.controller;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ltd.coders.software.music.system.entity.Album;
import com.ltd.coders.software.music.system.entity.AlbumErrorResponse;
import com.ltd.coders.software.music.system.service.IAlbumService;

@RestController
@RequestMapping("/v1/music/service")
public class InsertAlbumController {

	private IAlbumService albumService;

	private InsertAlbumController(IAlbumService albumService) {
		this.albumService = albumService;
	}

	@PostMapping(value = "/insert/album/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<Object> insertAlbum(@RequestParam MultipartFile file,
			@RequestParam("artistName") String artistName, @RequestParam("albumName") String albumName,
			@RequestParam("yearReleased") String yearReleased, @RequestParam("albumPrice") String albumPrice)
			throws IOException, SerialException, SQLException {

		Album album = new Album();
		album.setArtistName(artistName);
		album.setAlbumName(albumName);
		album.setYearReleased(yearReleased);
		album.setAlbumPrice(albumPrice);
		album.setImageAlbumCover(convertImageFromBytesToBlob(file));

		Album insertedAlbum = null;
		if(validateAlbum(album)) {
			insertedAlbum = albumService.insertAlbum(album);
		}
		if (insertedAlbum != null) {
			return ResponseEntity.ok(insertedAlbum);
		} else {
			return ResponseEntity.status(406).body(new AlbumErrorResponse("406",
					"Error failed to insert: " + albumName + ", album already exists in the database"));
		}
	}

	private Blob convertImageFromBytesToBlob(MultipartFile file) throws IOException, SerialException, SQLException {
		Blob imageBlob = null;
		if (!file.isEmpty()) {
			byte[] imageBytes = file.getBytes();
			imageBlob = new SerialBlob(imageBytes);
		}
		return imageBlob;
	}

	private boolean validateAlbum(Album album) {
		return (!album.getArtistName().isEmpty() && !album.getAlbumName().isEmpty()) ;	
	}
}
