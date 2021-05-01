package musichub.main;

import musichub.business.*;

public class Main {
	public static void main(String[] args) {
		String ip = "localhost";
		if (args.length > 0) {
			if("server".equals(args[0])) {
				AbstractServer as = new FirstServer();
				as.connect(ip);				
			}
			if("client".equals(args[0])) {
				SimpleClient c1 = new SimpleClient();
				c1.connect(ip);
			}
		}
	}
}