import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import musichub.business.Album;
import musichub.business.Song;

class AlbumTest {
	
	Song s;
	Album a;
	
	//Before each because we have to test 
	@BeforeEach
	void setUp() throws Exception {
		a = new Album("Nevermind", "Nirvana", 3540, "1991-09-12");
		s = new Song("Smells Like Teen Spirit", "Nirvana", 302, "content", "rock");
	}
	
	@Test
	void addSongTest() {
		// Test addSong
		a.addSong(s.getUUID());
		// And see if it has been loaded correctly 
		// By doing this we also test getSong 
		String songUid = "["+s.getUUID().toString()+"]";
		String songOfAlbum = a.getSongs().toString();
		assertEquals(songUid, songOfAlbum);
	}
	
	@Test
	void getsTest() {
		// Test GetTitle
		assertEquals("Nevermind", a.getTitle());
		// Test GetDate
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse("1991-09-12");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		assertEquals(date, a.getDate());
	}
}