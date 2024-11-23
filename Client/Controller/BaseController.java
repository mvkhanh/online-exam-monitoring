package pbl4.Client.Controller;

import java.io.IOException;
import java.net.InetAddress;

public class BaseController {
	public static InetAddress serverAddress;
	public static int tcpPort = 8888;
	
	static {
		try {
			serverAddress = InetAddress.getByName("localhost");
		} catch (IOException e) {
			System.exit(1);
		}
	}
}
