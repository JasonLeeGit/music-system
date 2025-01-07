package com.ltd.coders.software.music.system.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ltd.coders.software.music.system.entity.Album;
import com.ltd.coders.software.music.system.repository.IAlbumRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class AlbumServiceImplTest {

	private static final int EXPECTED_ALL_ALBUMS_RESULTS_SIZE = 70;
	private static final String UPDATED_ALBUM_NAME = "UpdatedAlbumName";
	private static final String ARTIST_NAME = "Oasis";
	private static final String ALBUM_NAME = "TestAlbumName";
	private static final String ALBUM_PRICE = "9.99";
	private static final String YEAR_RELEASED = "2024";
	private static final int ID = 70;
	private Album insertedAlbum;

	@Autowired
	private IAlbumRepository albumRepository;
	@Autowired
	private IAlbumService albumService;

	@Test
	public void findAllAlbumsOrderedByArtistNameTest() {
		List<Album> results = albumRepository.findAllAlbumsByOrderByArtistName();
		assertTrue(results.size() == EXPECTED_ALL_ALBUMS_RESULTS_SIZE);
	}

	@Test
	public void findByArtistNameTest() {
		List<Album> results = albumRepository.findByArtistName(ARTIST_NAME);
		assertTrue(results.size() == 11);
		assertEquals(results.get(0).getArtistName(), ARTIST_NAME);
	}

	@Test
	public void findByIdTest() {
		Optional<Album> result = albumRepository.findById(ID);
		assertEquals(result.get().getArtistName(), ARTIST_NAME);
	}

	@Test
	public void insertAlbumTest() {
		Album albumToInsert = new Album();

		albumToInsert.setArtistName(ARTIST_NAME);
		albumToInsert.setAlbumName(ALBUM_NAME);
		albumToInsert.setAlbumPrice(ALBUM_PRICE);
		albumToInsert.setYearReleased(YEAR_RELEASED);
		albumToInsert.setImageAlbumCover(convertImageFromBytesToBlob("3r45gvh47j7byknb6"));

		insertedAlbum = albumRepository.save(albumToInsert);
		assertNotNull(insertedAlbum);
		assertEquals(insertedAlbum.getAlbumName(), ALBUM_NAME);
	}

	@Test
	public void saveTest() {
		Optional<Album> album = albumService.findById(ID);
		album.get().setAlbumName(UPDATED_ALBUM_NAME);
		Album savedAlbum = albumRepository.save(album.get());
		assertEquals(savedAlbum.getAlbumName(), UPDATED_ALBUM_NAME);
	}

	private Blob convertImageFromBytesToBlob(String image) {
		Blob imageBlob = null;
		byte[] imageBytes = image.getBytes();
		try {
			imageBlob = new SerialBlob(imageBytes);
		} catch (SerialException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return imageBlob;
	}

	@After
	public void cleanUp() {
		if (insertedAlbum != null) {
			albumService.deleteAlbumById(insertedAlbum);
		}
	}

}
