package musichub.business;

import java.lang.Exception;

public class NoAlbumFoundException extends Exception {

	/**
	 * 
	 * @param msg
	 */
	public NoAlbumFoundException (String msg) {
		super(msg);
	}
}