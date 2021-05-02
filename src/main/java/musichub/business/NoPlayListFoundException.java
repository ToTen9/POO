package musichub.business;

import java.lang.Exception;

public class NoPlayListFoundException extends Exception {

	/**
	 * 
	 * @param msg
	 */
	public NoPlayListFoundException (String msg) {
		super(msg);
	}
}