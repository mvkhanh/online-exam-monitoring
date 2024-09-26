package PBL4;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

public class Server {
	public static ArrayList<CreateRoomProc> rooms = new ArrayList<CreateRoomProc>();
	private static int id = 0;
	public static void main(String[] args) throws SocketException {
		DatagramSocket serverSocket = new DatagramSocket(9876);
		while (true) {
			try {
				byte[] receiveData = new byte[1024];
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				serverSocket.receive(receivePacket);
				String msg = new String(receivePacket.getData(), 0, receivePacket.getLength());
				//create room
				if(msg.equals("C")) {
					CreateRoomProc createRoomProc = new CreateRoomProc(serverSocket, receivePacket, id++);
					rooms.add(createRoomProc);
					createRoomProc.start();
				}
				//join room
				else if(msg.contains("J")) {
					int roomId = Integer.parseInt(msg.strip().split(" ")[2]);
					for(CreateRoomProc room : rooms) {
						if(room.getIdRoom() == roomId) {
							String idStudent = receivePacket.getAddress().toString() + String.valueOf(receivePacket.getPort());
							room.getJoins().add(idStudent);
						}
					}
				} 
				//image
				else if(msg.contains("S")) {
					int roomId = Integer.parseInt(msg.strip().split(" ")[2]);
					for(CreateRoomProc room : rooms) {
						if(room.getIdRoom() == roomId) {
							room.setImagePacket(receivePacket);
						}
					}
				}
				else {
					
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
