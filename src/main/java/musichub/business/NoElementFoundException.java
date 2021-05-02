package musichub.business;

import java.lang.Exception;

public class NoElementFoundException extends Exception {

	/**
	 * 
	 * @param msg
	 */
	public NoElementFoundException (String msg) {
		super(msg);
	}
}