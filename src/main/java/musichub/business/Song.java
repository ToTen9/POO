package musichub.business;

import org.w3c.dom.*;

 
public class Song extends AudioElement {
	private Genre genre;
	
	/**
	 * 
	 * @param title
	 * @param artist
	 * @param length
	 * @param uid
	 * @param content
	 * @param genre
	 */
	public Song (String title, String artist, int length, String uid, String content, String genre) {
		super (title, artist, length, uid, content);
		this.setGenre(genre);
	}
	
	/**
	 * 
	 * @param title
	 * @param artist
	 * @param length
	 * @param content
	 * @param genre
	 */
	public Song (String title, String artist, int length, String content, String genre) {
		super (title, artist, length, content);
		this.setGenre(genre);
	}
	
	/**
	 * 
	 * @param xmlElement
	 * @throws Exception
	 */
	public Song (Element xmlElement) throws Exception {
		super(xmlElement);
		try {
			this.setGenre(xmlElement.getElementsByTagName("genre").item(0).getTextContent());
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	/**
	 * 
	 * @param genre
	 */
	public void setGenre (String genre) {	
		switch (genre.toLowerCase()) {
			case "jazz":
			default:
				this.genre = Genre.JAZZ;
				break;
			case "classic":
				this.genre = Genre.CLASSIC;
				break;
			case "hiphop":
				this.genre = Genre.HIPHOP;
				break;
			case "rock":
				this.genre = Genre.ROCK;
				break;
			case "pop":
				this.genre = Genre.POP;
				break;
			case "rap":
				this.genre = Genre.RAP;
				break;				
		}
	} 

	/**
	 * 
	 * @return
	 * 		The genre of the song
	 */
	public String getGenre () {
		return genre.getGenre();
	}
	
	/**
	 * 
	 * @return
	 * 		The genre of the song to the client or server via the cli
	 */
	public String toString() {
		return super.toString() + ", Genre = " + getGenre() + "\n";
	}
	
	/**
	 * 	
	 * @param document
	 * @param parentElement
	 */
	public void createXMLElement(Document document, Element parentElement) {
		// song element
        Element song = document.createElement("song");

		super.createXMLElement(document, song);
		
		Element genreElement = document.createElement("genre");
        genreElement.appendChild(document.createTextNode(genre.getGenre()));
        song.appendChild(genreElement);
		
		parentElement.appendChild(song);
		return;
	}
}