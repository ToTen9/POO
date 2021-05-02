package musichub.business;

public enum Genre {
	JAZZ ("jazz"), CLASSIC ("classic"), HIPHOP ("hiphop"), ROCK ("rock"), POP("pop"), RAP("rap");
	private String genre;
	/**
	 * 
	 * @param genre
	 */
	private Genre (String genre) {
		this.genre = genre;
	}
	/**
	 * 
	 * @return
	 *		The genre
	 */
	public String getGenre() {
		return genre;
	}
}