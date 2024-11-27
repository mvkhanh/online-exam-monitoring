package pbl4.Server;

//ifconfig | grep "inet " | grep -v 127.0.0.1
//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
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.opencv.core.Core;

import pbl4.Server.Controller.CleanThread;
import pbl4.Server.Controller.TCPHandler;
import pbl4.Server.DTO.Room;

public class Server {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	public static Map<Integer, Room> rooms = new ConcurrentHashMap<>();

	public static void main(String[] args) {
		new Thread(new TCPServer()).start();
		new Thread(new UDPServer()).start();
		new Thread(new CleanThread()).start();
	}
}

//TCP Server class
class TCPServer implements Runnable {

	@Override
	public void run() {
		try (ServerSocket tcpServer = new ServerSocket(Constant.TCP_PORT)) {
			while (true) {
				Socket clientSocket = tcpServer.accept();
				new Thread(new TCPHandler(clientSocket)).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

//UDP Server class
class UDPServer implements Runnable {
	@Override
	public void run() {

		try (DatagramSocket udpSocket = new DatagramSocket(Constant.UDP_PORT)) {
			while (true) {
				try {
					byte[] receiveData = new byte[Constant.PACKET_SIZE];
					DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
					udpSocket.receive(receivePacket);
					String studentAddress = receivePacket.getAddress().toString() + receivePacket.getPort();
					Room room = Server.rooms.get((int) receiveData[0]);
					receiveData[0] = room.getStudentNums().get(studentAddress).byteValue();
					udpSocket.send(new DatagramPacket(receiveData, receivePacket.getLength(), room.getAddress(),
							room.getUdpPort()));
				} catch (NullPointerException e) {
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
