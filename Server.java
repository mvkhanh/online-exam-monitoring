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
				String msg = new String(receivePacket.getData(), 0, 1);
				//create room
				if(msg.equals("C")) {
					CreateRoomProc createRoomProc = new CreateRoomProc(receivePacket, id++);
					rooms.add(createRoomProc);
					createRoomProc.start();
				}
				//join room: "J 12345"
				else if(msg.contains("J")) {
					int roomId = Integer.parseInt(new String(receivePacket.getData(), 1, 30).strip());
					for(CreateRoomProc room : rooms) {
						if(room.getRoomId() == roomId) {
							String idStudent = receivePacket.getAddress().toString() + " " + String.valueOf(receivePacket.getPort());
							room.addJoiner(idStudent);
							//
							break;
						}
					}
				} 
				//image: S roomId image. Vd: S 12345 ...
				else if(msg.contains("S")) {
					String[] data = new String(receivePacket.getData(), 1, 30).strip().split(" ");
					int roomId = Integer.parseInt(data[0]);
					for(CreateRoomProc room : rooms) {
						if(room.getRoomId() == roomId) {
							room.setImagePacket(receivePacket);
							break;
						}
					}
				}
				else {
					
				}
			} catch (Exception ex) {
				System.out.println("loi khi server nhan du lieu");
			}
		}
	}
}
