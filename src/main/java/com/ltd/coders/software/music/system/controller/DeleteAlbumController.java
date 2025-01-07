package com.ltd.coders.software.music.system.controller;

import java.sql.SQLException;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ltd.coders.software.music.system.entity.Album;
import com.ltd.coders.software.music.system.service.IAlbumService;

@RestController
@RequestMapping("/v1/music/service")
public class DeleteAlbumController {
	
	private IAlbumService albumService;

	private DeleteAlbumController(IAlbumService albumService) {
		this.albumService = albumService;
	}
	
	@DeleteMapping(value = "/delete/album/")
	@CrossOrigin(origins = "http://localhost:3000")
	public void deleteAlbumById(@RequestParam("id") int id) throws SQLException {
		Optional<Album> album = albumService.findById(id);
		if(album.isPresent()) {
			albumService.deleteAlbumById(album.get());
		}
	}
}
