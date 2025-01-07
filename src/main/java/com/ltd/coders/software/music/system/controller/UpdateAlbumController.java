package com.ltd.coders.software.music.system.controller;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ltd.coders.software.music.system.entity.Album;
import com.ltd.coders.software.music.system.entity.AlbumResponse;
import com.ltd.coders.software.music.system.service.IAlbumService;

@RestController
@RequestMapping("/v1/music/service")
public class UpdateAlbumController {

	private IAlbumService albumService;
	
	private UpdateAlbumController(IAlbumService albumService) {
		this.albumService = albumService;
	}
	
	@PutMapping(value = "/update/album/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<AlbumResponse> updateEditedAlbum(@RequestParam MultipartFile file,
			@RequestParam("id") int id, @RequestParam("artistName") String artistName,
			@RequestParam("albumName") String albumName, @RequestParam("yearReleased") String yearReleased,
			@RequestParam("albumPrice") String albumPrice) throws IOException, SerialException, SQLException {
		
		// fetch album
		Optional<Album> album = albumService.findById(id);
		if(album.isPresent()) {
			// update album
			if (file != null) {
				album.get().setArtistName(artistName);
				album.get().setAlbumName(albumName);
				album.get().setYearReleased(yearReleased);
				album.get().setAlbumPrice(albumPrice);
				album.get().setImageAlbumCover(convertImageFromBytesToBlob(file));
			}

			//save album to database
			Album savedAlbum = null;
			if(validateAlbum(album.get())) {
				savedAlbum = albumService.save(album.get());
			}
			//create response object to return
			if (savedAlbum != null) {
				AlbumResponse response;
				if (savedAlbum.getImageAlbumCover() != null) {
					response = new AlbumResponse(
							savedAlbum.getId(), savedAlbum.getArtistName(), 
							savedAlbum.getAlbumName(), savedAlbum.getYearReleased(), 
							savedAlbum.getAlbumPrice(),  convertFromBlobToBytes(savedAlbum.getImageAlbumCover()));
				} else {
					// do we need this if we have to add an image on the front end?
					response = new AlbumResponse(
							savedAlbum.getId(), savedAlbum.getArtistName(), 
							savedAlbum.getAlbumName(), savedAlbum.getYearReleased(), 
							savedAlbum.getAlbumPrice(), null);
				}
				return ResponseEntity.ok(response);
			} else {
				//failed to save to DB
				return ResponseEntity.noContent().build();
			}
		}
		//could not find album id in DB
		return ResponseEntity.notFound().build();
	
	}
	
	private byte[] convertFromBlobToBytes(Blob imageAlbumCoverBlob) throws SQLException {
		byte[] imageAlbumCoverBytes = imageAlbumCoverBlob.getBytes(1, (int) imageAlbumCoverBlob.length());
		return imageAlbumCoverBytes;
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
