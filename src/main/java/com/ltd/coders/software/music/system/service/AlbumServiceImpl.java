package com.ltd.coders.software.music.system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ltd.coders.software.music.system.entity.Album;
import com.ltd.coders.software.music.system.repository.IAlbumRepository;

@Service
public class AlbumServiceImpl implements IAlbumService {

	@Autowired
	private IAlbumRepository albumRepository;

	@Override
	@Cacheable("allArtists")
	public List<Album> findAllAlbumsOrderedByArtistName() {
		return albumRepository.findAllAlbumsByOrderByArtistName();
	}

	@Override
	@Cacheable("allArtists")
	public List<Album> findByArtistName(String artistName) {
		return albumRepository.findByArtistName(artistName);
	}

	@Override
	@Cacheable("allArtists")
	public Optional<Album> findById(int id) {
		return albumRepository.findById(id);
	}

	@Override
	@CacheEvict(value ="allArtists" , allEntries = true)
	public Album insertAlbum(Album album) {
		Album insertedAlbum = null;
		if(album.getArtistName() != null && album.getAlbumName() != null) {
			if (artistNotExistInDatabase(album.getArtistName(), album.getAlbumName())) {
				insertedAlbum = albumRepository.save(album);
			}
		}
		return insertedAlbum;
	}
	
	@Override
	@CacheEvict(value ="allArtists" , allEntries = true)
	public Album save(Album album) {
		return albumRepository.save(album);
	}
	
	private boolean artistNotExistInDatabase(String artistName, String albumName) {
		if(albumRepository.findByAlbumNameQuery(artistName, albumName) == null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Cacheable("allArtists")
	@CacheEvict(value ="allArtists" , allEntries = true)
	public void deleteAlbumById(Album album) {
		albumRepository.delete(album);
	}

}
