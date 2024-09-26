package PBL4;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

class CreateRoomProc extends Thread {
	private static DatagramSocket soc;
	private InetAddress address;
	private Integer port;
	private DatagramPacket imagePacket = null;
	private int roomId;
	private Map<String, Integer> joiners = new HashMap<>();
	private int idJoin = 0;
	
	public CreateRoomProc(DatagramPacket packet, int id) throws SocketException {
		if(soc == null) soc = new DatagramSocket(9876);
		this.address = packet.getAddress();
		this.port = packet.getPort();
		this.roomId = id; // Co the khong can 
		//Gui roomId ve cho teacher
		byte[] data = String.valueOf(roomId).getBytes();
		try {
			soc.send(new DatagramPacket(data, data.length, address, port));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Lỗi khi gửi roomId ve teacher");
		}
	}

	@Override
	public void run() {
		while(true) {
			try {				
				if(imagePacket != null) {
					String student_address = imagePacket.getAddress().toString() + " " + String.valueOf(imagePacket.getPort());
					
					byte[] image = new String(imagePacket.getData(), 0, imagePacket.getLength()).strip().split(" ")[2].getBytes();
					//Thong diep se co dang:"I studentId image". Phia teacher se lay ra image va cap nhat vao o studentId
					byte[] msg = ("I " + joiners.get(student_address).toString() + " ").getBytes();
					byte[] data = combineArrays(msg, image);
					soc.send(new DatagramPacket(data, data.length, address, port));
					imagePacket = null;
				}
			}
			catch(Exception e) {
				System.out.println("loi khi gui anh");
			}
		}
	}
	
	public void addJoiner(String student_address) {
		if(!joiners.containsKey(student_address)) joiners.put(student_address, idJoin++);
	}
	
	private static byte[] combineArrays(byte[] array1, byte[] array2) {
        // Khởi tạo mảng mới với kích thước là tổng của hai mảng
        byte[] combined = new byte[array1.length + array2.length];

        // Sao chép phần tử từ mảng đầu tiên vào mảng gộp
        System.arraycopy(array1, 0, combined, 0, array1.length);
        
        // Sao chép phần tử từ mảng thứ hai vào mảng gộp
        System.arraycopy(array2, 0, combined, array1.length, array2.length);

        return combined;
    }

	public Map<String, Integer> getJoiners() {
		return joiners;
	}

	public void setJoiners(Map<String, Integer> joiners) {
		this.joiners = joiners;
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

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int id) {
		this.roomId = id;
	}

}