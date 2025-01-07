package com.ltd.coders.software.music.system.controller;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.ltd.coders.software.music.system.repository.IAlbumRepository;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class InsertAlbumControllerTest {

	private static final int PRE_INSERT_EXPECTED_RESULTS_SIZE = 11;
	private static final int EXPECTED_RESULTS_SIZE = 12;
	private static final String ARTIST_NAME = "Oasis";
	private static final String INSERTED_PARAMS = "?artistName=Oasis&albumName=Test&yearReleased=1994&albumPrice=9.99";

	@Autowired
	protected WebApplicationContext webApplicationContext;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private IAlbumRepository albumRepository;

	private MockMultipartFile file;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		file = new MockMultipartFile("file", "album cover.jpg", MediaType.MULTIPART_FORM_DATA_VALUE,
				"image_test_data".getBytes());
	}
	
	@Test
	public void validInsertAlbumTest() throws Exception {	
		assert (albumRepository.findByArtistName(ARTIST_NAME).size() == PRE_INSERT_EXPECTED_RESULTS_SIZE);

		mockMvc.perform(multipart(
				"/v1/music/service/insert/album/"+INSERTED_PARAMS)
				.file(file)
				.accept(MediaType.MULTIPART_FORM_DATA_VALUE));

		assertTrue (albumRepository.findByArtistName(ARTIST_NAME).size() == EXPECTED_RESULTS_SIZE);
	}
	
	@Test
	public void invalidInsertAlbumNoAlbumNameTest() throws Exception {
		file = new MockMultipartFile("file", "", MediaType.MULTIPART_FORM_DATA_VALUE,
				"image_test_data".getBytes());
		assert (albumRepository.findByArtistName(ARTIST_NAME).size() == 11);

		mockMvc.perform(multipart(
				"/v1/music/service/insert/album/?artistName=&albumName=Test&yearReleased=1994&albumPrice=9.99")
				.file(file)
				.accept(MediaType.MULTIPART_FORM_DATA_VALUE));

		assertTrue (albumRepository.findByArtistName(ARTIST_NAME).size() == 11);
	}
	
	
}
