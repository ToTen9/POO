package musichub.main;
import musichub.business.*;

public class Client {

	public static void main (String[] args) {
        String ip = "localhost";
		 SimpleClient c1 = new SimpleClient();
         c1.connect(ip);
	}
}
