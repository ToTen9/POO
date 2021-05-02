package musichub.business;

public enum Category {
	YOUTH ("youth"), NOVEL ("novel"), THEATER ("theater"), DOCUMENTARY ("documentary"), SPEECH("speech");
	private String category;
	/**
	 * 
	 * @param category
	 */
	private Category (String category) {
		this.category = category;
	}
	/**
	 * 
	 * @return
	 * 		The category
	 */
	public String getCategory() {
		return category;
	}
}