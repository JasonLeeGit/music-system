package com.ltd.coders.software.music.system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ltd.coders.software.music.system.entity.Album;

@Repository("albumRepository")
public interface IAlbumRepository extends JpaRepository<Album, Integer> {
	
	@Query(value = "SELECT * FROM MUSIC_SCHEMA.ALBUM where artist_name = :artistName and album_name = :albumName", nativeQuery = true)
	Album findByAlbumNameQuery(@Param("artistName") String artistName, @Param("albumName") String albumName);
	
	List<Album> findByArtistName(String artistName);
	
	List<Album> findAllAlbumsByOrderByArtistName();

}
