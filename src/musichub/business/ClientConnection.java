package musichub.business;

public class ClientConnection
{
	public static void start ()
	{
		SimpleClient c1 = new SimpleClient();
		c1.connect("localhost");
	}
}