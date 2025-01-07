package com.ltd.coders.software.music.system.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class AlbumsByArtistNameTest {

	private static final String ARTIST_NAME = "Oasis";

	@Autowired
	protected WebApplicationContext webApplicationContext;
	@Autowired
	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void findAlbumDataForArtistNameTest() throws Exception {
		mockMvc.perform(get("/v1/music/service/search/albums/?artistName=" + ARTIST_NAME)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}

	@Test
	public void findAlbumDataForArtistNameWithNoArtistNameTest() throws Exception {
		mockMvc.perform(get("/v1/music/service/search/albums/?artistName=").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpStatus.NO_CONTENT.value()));
	}
}
