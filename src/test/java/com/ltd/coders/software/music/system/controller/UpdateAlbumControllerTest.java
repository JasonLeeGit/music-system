package com.ltd.coders.software.music.system.controller;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.ltd.coders.software.music.system.entity.Album;
import com.ltd.coders.software.music.system.service.IAlbumService;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UpdateAlbumControllerTest {

	private static final String UPDATED_ALBUM_NAME = "UpdatedAlbumName";
	private MockMultipartFile file;
	@Autowired
	protected WebApplicationContext webApplicationContext;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private IAlbumService albumService;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		file = new MockMultipartFile("file", "album cover.jpg", MediaType.MULTIPART_FORM_DATA_VALUE,
				"image_test_data".getBytes());
	}

	@Test
	public void validUpdateEditedAlbumTest() throws Exception {
		MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.multipart("/v1/music/service/update/album/?id=1&artistName=Oasis&albumName=" + UPDATED_ALBUM_NAME
						+ "&yearReleased=1994&albumPrice=9.99");

		builder.with(new RequestPostProcessor() {
			@Override
			public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
				request.setMethod("PUT");
				return request;
			}
		});

		mockMvc.perform(builder.file(file).accept(MediaType.MULTIPART_FORM_DATA_VALUE));

		Optional<Album> album = albumService.findById(1);

		assertTrue(album.get().getAlbumName().equals(UPDATED_ALBUM_NAME));
	}

	@Test
	public void inValidUpdateEditedAlbumWithNoArtistNameTest() throws Exception {
		MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.multipart("/v1/music/service/update/album/?id=1&artistName=&albumName=" + UPDATED_ALBUM_NAME
						+ "&yearReleased=1994&albumPrice=9.99");

		builder.with(new RequestPostProcessor() {
			@Override
			public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
				request.setMethod("PUT");
				return request;
			}
		});

		mockMvc.perform(builder.file(file).accept(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(status().is(HttpStatus.NO_CONTENT.value()));
	}

	@Test
	public void inValidUpdateEditedAlbumWithNoAlbumNameTest() throws Exception {
		MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart(
				"/v1/music/service/update/album/?id=1&artistName=Oasis&albumName=&yearReleased=1994&albumPrice=9.99");

		builder.with(new RequestPostProcessor() {
			@Override
			public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
				request.setMethod("PUT");
				return request;
			}
		});

		mockMvc.perform(builder.file(file).accept(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(status().is(HttpStatus.NO_CONTENT.value()));
	}

	@Test
	public void UpdateEditedAlbumWhenAlbumIDNotFoundTest() throws Exception {
		MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.multipart("/v1/music/service/update/album/?id=0&artistName=&albumName=" + UPDATED_ALBUM_NAME
						+ "&yearReleased=1994&albumPrice=9.99");

		builder.with(new RequestPostProcessor() {
			@Override
			public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
				request.setMethod("PUT");
				return request;
			}
		});

		mockMvc.perform(builder.file(file).accept(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(status().is(HttpStatus.NOT_FOUND.value()));
	}

}
