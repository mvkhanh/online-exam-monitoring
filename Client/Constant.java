package pbl4.Client;

import java.io.IOException;
import java.net.InetAddress;

public class Constant {
	
	//Server
	public static String IP = "localhost";
	public static InetAddress serverAddress;
	public static final int tcpPort = 8888;
	public static final int udpPort = 9999;
	public static final int PACKET_SIZE = 1104;

	static {
		try {
			serverAddress = InetAddress.getByName(IP);
		} catch (IOException e) {
			System.exit(1);
		}
	}

	//Student
	public static final int NORMAL_WIDTH = 450;
	public static final int NORMAL_HEIGHT = 300;
	public static final int FOCUS_WIDTH = 900;
	public static final int FOCUS_HEIGHT = 600;
	public static final long KEYBOARD_DURATION = 10000; // 5 phut: 300000
	public static final int IMAGE_SEGMENT = 1100;
	public static final int MAX_SCREENS = 4;
	public static final int MAX_CAMS = 8;
	
	//Teacher
	public static final int PROCESS_THREADS = 3;
	public static final int VIEW_THREADS = 3;
	public static final long TIMEOUT = 4;
}
