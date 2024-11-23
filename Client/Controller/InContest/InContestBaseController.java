package pbl4.Client.Controller.InContest;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.ArrayList;

import pbl4.Client.Controller.BaseController;

public class InContestBaseController extends BaseController{
	public static int udpPort = 9999;
	public String roomId;
	public String id;
	public String name;
	public DatagramSocket udpSocket;
	public boolean running = true;
	
	public InContestBaseController() {
		super();
		try {
			udpSocket = new DatagramSocket();
		} catch (IOException e) {
			System.exit(1);
		}
	}
	
	public void sendMessage(String msg) {
		if (!msg.isEmpty()) {
			try (Socket socket = new Socket(serverAddress, tcpPort);
					DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {
				dos.writeUTF("M" + roomId + " " + name + ": " + msg + "\n");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
