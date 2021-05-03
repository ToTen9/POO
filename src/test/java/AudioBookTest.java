import musichub.business.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AudioBookTest {

	AudioBook ab;
	
	@BeforeEach
	void setUp() throws Exception {
		ab = new AudioBook("The Four Winds","Kristin Hannah" , 12301, "content", "english", "novel");
	}

	@Test
	void simpleGetTests() {
		assertEquals("The Four Winds", ab.getTitle());
		assertEquals("Kristin Hannah", ab.getArtist());
		assertEquals("Title = The Four Winds, Artist = Kristin Hannah, Length = 12301, Content = content, Language = ENGLISH, Category = NOVEL\n", ab.toString());
		assertNotEquals("hello", ab.getTitle());
	}
	
	@Test
	void SetCategoryTests() {
		ab.setCategory("youth");
		assertNotEquals("Title = The Four Winds, Artist = Kristin Hannah, Length = 12301, Content = content, Language = ENGLISH, Category = NOVEL\n", ab.toString());
		assertEquals("Title = The Four Winds, Artist = Kristin Hannah, Length = 12301, Content = content, Language = ENGLISH, Category = YOUTH\n", ab.toString());
	}

	@Test
	void SetLanguageTest() {
		ab.setLanguage("french");
		assertNotEquals("Title = The Four Winds, Artist = Kristin Hannah, Length = 12301, Content = content, Language = ENGLISH, Category = NOVEL\n", ab.toString());
		assertEquals("Title = The Four Winds, Artist = Kristin Hannah, Length = 12301, Content = content, Language = FRENCH, Category = NOVEL\n", ab.toString());
	}
}
