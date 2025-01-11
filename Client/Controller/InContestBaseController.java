package pbl4.Client.Controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.Socket;

import pbl4.Client.Constant;

public class InContestBaseController{
	public String roomId;
	public String id;
	public String name;
	public DatagramSocket udpSocket;
	public boolean running = true;
	
	public void sendMessage(String msg) {
		if (!msg.isEmpty()) {
			try (Socket socket = new Socket(Constant.serverAddress, Constant.tcpPort);
					DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {
				dos.writeUTF("M" + roomId + " " + name + ": " + msg + "\n");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
