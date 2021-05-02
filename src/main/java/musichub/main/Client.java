package musichub.main;
import musichub.business.*;

public class Client {
/**
 * 
 * @param args
 * @see SimpleClient
 */
	public static void main (String[] args) {
		SimpleClient c1 = new SimpleClient();
		c1.connect("localhost");
	}
}
