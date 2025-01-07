package com.ltd.coders.software.system.test.suit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.ActiveProfiles;

import com.ltd.coders.software.music.system.controller.AlbumsByArtistNameTest;
import com.ltd.coders.software.music.system.controller.AllAlbumsControllerTest;
import com.ltd.coders.software.music.system.controller.DeleteAlbumControllerTest;
import com.ltd.coders.software.music.system.controller.InsertAlbumControllerTest;
import com.ltd.coders.software.music.system.controller.UpdateAlbumControllerTest;

@ActiveProfiles("test")
@RunWith(Suite.class)
@SuiteClasses({
	AlbumsByArtistNameTest.class,
	AllAlbumsControllerTest.class,
	DeleteAlbumControllerTest.class,
	InsertAlbumControllerTest.class,
	UpdateAlbumControllerTest.class
})
public class JunitTestSuite {

}
