package com.ltd.coders.software.music.system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ltd.coders.software.music.system.entity.Album;

@Service
public interface IAlbumService {

	Optional<Album> findById(int id);
	
	List<Album> findAllAlbumsOrderedByArtistName();
	
	List<Album> findByArtistName(String artistName);

	Album insertAlbum(Album album);

	Album save(Album album);
	
	void deleteAlbumById(Album album);

}
