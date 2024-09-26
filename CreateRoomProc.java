package PBL4;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class CreateRoomProc extends Thread {
	private ArrayList<String> joins = new ArrayList<String>();
	private DatagramSocket soc;
	private InetAddress address;
	private Integer port;
	private DatagramPacket imagePacket = null;
	private int idRoom;
	private Map<String, Integer> mp = new HashMap<>();
	private static int idJoin = 0;
	
	public CreateRoomProc(DatagramSocket soc, DatagramPacket packet, int id) {
		this.soc = soc;
		this.address = packet.getAddress();
		this.port = packet.getPort();
		this.idRoom = id;
	}

	@Override
	public void run() {
		while(true) {
			try {				
				if(imagePacket != null) {
					String student_address = imagePacket.getAddress().toString() + String.valueOf(imagePacket.getPort());
					byte[] data = imagePacket.getData();
					if(!mp.containsKey(student_address)) mp.put(student_address, idJoin++);
					byte[] msg = ("Student " + mp.get(student_address).toString()).getBytes();
					DatagramPacket packet = new DatagramPacket(msg, msg.length, address, port);
					soc.send(packet);
					packet.setData(data);
					packet.setLength(data.length);
					soc.send(packet);
					imagePacket = null;
				}
			}
			catch(Exception e) {
				
			}
		}
	}
	
	public ArrayList<String> getJoins() {
		return joins;
	}

	public void setJoins(ArrayList<String> joins) {
		this.joins = joins;
	}

	public DatagramSocket getSoc() {
		return soc;
	}

	public void setSoc(DatagramSocket soc) {
		this.soc = soc;
	}

	public DatagramPacket getImagePacket() {
		return imagePacket;
	}

	public void setImagePacket(DatagramPacket packet) {
		this.imagePacket = packet;
	}

	public InetAddress getAddress() {
		return address;
	}

	public void setAddress(InetAddress address) {
		this.address = address;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getIdRoom() {
		return idRoom;
	}

	public void setIdRoom(int id) {
		this.idRoom = id;
	}

}