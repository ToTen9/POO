package musichub.business;

import java.io.*;  
import java.net.*; 

public class ServerConnection
{

	public static void start () {
		AbstractServer as = new FirstServer();
		String ip = "localhost";
		as.connect(ip);
		
	}
}