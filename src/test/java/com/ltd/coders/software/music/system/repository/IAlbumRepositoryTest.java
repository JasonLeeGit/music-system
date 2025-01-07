package com.ltd.coders.software.music.system.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ltd.coders.software.music.system.entity.Album;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class IAlbumRepositoryTest {

	private static final int EXPECTED_RESULTS_SIZE_BY_NAME = 11;
	private static final int EXPECTED_ALL_ALBUMS_RESULTS_SIZE = 70;
	private static final String ARTIST_NAME = "Oasis";
	private static final String ALBUM_NAME = "Be Here Now";

	@Autowired
	private IAlbumRepository albumRepository;

	@Test
	public void findByAlbumNameQueryTest() {
		Album result = albumRepository.findByAlbumNameQuery(ARTIST_NAME, ALBUM_NAME);
		assertEquals(result.getArtistName(), ARTIST_NAME);
		assertEquals(result.getAlbumName(), ALBUM_NAME);
	}

	@Test
	public void findByArtistNameTest() {
		List<Album> results = albumRepository.findByArtistName(ARTIST_NAME);
		assertTrue(results.size() == EXPECTED_RESULTS_SIZE_BY_NAME);
	}

	@Test
	public void findAllAlbumsByOrderByArtistName() {
		List<Album> results = albumRepository.findAllAlbumsByOrderByArtistName();
		assertTrue(results.size() == EXPECTED_ALL_ALBUMS_RESULTS_SIZE);
	}
}
