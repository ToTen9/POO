import musichub.business.*;

import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AlbumTest {
	
	Song s;
	Album a;
	
	@BeforeEach
	void setUp() throws Exception {
		a = new Album("Nevermind", "Nirvana", 3540, "1991-09-12");
		s = new Song("Smells Like Teen Spirit", "Nirvana", 302, "content", "rock");
	}
	
	@Test
	void addSongTest() {
		a.addSong(s.getUUID());
		String songUid = "["+s.getUUID().toString()+"]";
		String songOfAlbum = a.getSongs().toString();
		assertEquals(songUid, songOfAlbum);
	}
	
	@Test
	void getsTest() {
		assertEquals("Nevermind", a.getTitle());
//		Date sdf = new SimpleDateFormat("yyyy-MM-dd");
//		sdf = sdf.parse("1991-09-12");
//		assertEquals("1991-09-12", a.getDate());
	}
}