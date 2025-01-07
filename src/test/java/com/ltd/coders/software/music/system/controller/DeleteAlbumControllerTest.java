package com.ltd.coders.software.music.system.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.ltd.coders.software.music.system.repository.IAlbumRepository;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class DeleteAlbumControllerTest {

	private static final int EXPECTED_RESULTS_SIZE_AFTER_DELETION = 69;
	private static final int ID_TO_DELETE = 2;
	@Autowired
	protected WebApplicationContext webApplicationContext;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private IAlbumRepository albumRepository;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void deleteAlbumByIdTest() throws Exception {
		mockMvc.perform(
				delete("/v1/music/service/delete/album/?id=" + ID_TO_DELETE).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
		assert (albumRepository.findAllAlbumsByOrderByArtistName().size() == EXPECTED_RESULTS_SIZE_AFTER_DELETION);

	}
}
