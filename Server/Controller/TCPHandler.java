package PBL4.Server.Controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Queue;

import PBL4.Server.Model.ClientModel;
import PBL4.Server.Model.Room;

public class TCPHandler implements Runnable {
	private Socket socket;
	private InetAddress address;

	public TCPHandler(Socket socket) {
		this.socket = socket;
		this.address = socket.getInetAddress();
	}

	@Override
	public void run() {
		try (DataInputStream input = new DataInputStream(socket.getInputStream());
				DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
			String message = input.readUTF();
			char msgCode = message.charAt(0);
			String[] msges;
			switch (msgCode) {
			case 'C': // C<udpPort> <teacherName>
				int[] id = createRoom(message);
				output.writeUTF(id[0] + " " + id[1]);
				break;

			case 'J': // J<roomId> <udpPort> <studentName>
				int studentId = joinRoom(message);
				if (studentId != 0)
					output.writeUTF("Y" + studentId);
				else
					output.writeUTF("N");
				break;

			case 'H': // H<roomId> <studentNum>
				msges = message.split(" ");
				focus(msges);
				break;

			case 'M': // M<roomId> <text>
				if (!texting(message))
					output.writeUTF("Q");
				break;

			case 'S': // I am alive cua student: S<roomId> <id> <STT tin nhan>
				// Kiem tra tin nhan, focus
				msges = message.split(" ");
				if (!checkStudent(msges, output))
					output.writeUTF("Q");
				break;

			case 'T': // I am alive cua teacher: T<roomId> <STT tin nhan>
				// Kiem tra tin nhan, hoc sinh da thoat
				msges = message.split(" ");
				if (!checkTeacher(msges, output))
					output.writeUTF("Q");
				break;
			default:
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private int[] createRoom(String msg) {
		int i = msg.indexOf(" ");
		int udpPort = Integer.valueOf(msg.substring(1, i));
		int roomId = Server.NEW_ROOM_ID.getAndIncrement();
		Room room = new Room(address, udpPort, msg.substring(i + 1), Server.ID.getAndIncrement());
		Server.rooms.put(roomId, room);
		return new int[] { roomId, room.getId() };
	}

	private int joinRoom(String msg) {
		int i = msg.indexOf(" ");
		int j = msg.indexOf(" ", i + 1);
		int roomId = Integer.valueOf(msg.substring(1, i));
		Room room = Server.rooms.get(roomId);
		if (room != null) {
			int studentId = Server.ID.getAndIncrement();
			int studentNum = room.getNewStudentId().getAndIncrement();
			room.getStudentNums().put(address.toString() + msg.substring(i + 1, j), studentNum);
			room.getStudents().put(studentId, new ClientModel(studentNum));
			room.getForFocus().put(studentNum, studentId);
			return studentId;
		}
		return 0;
	}

	private void focus(String[] msges) {
		int studentNum = Integer.parseInt(msges[1]);
		Room room = Server.rooms.get(Integer.parseInt(msges[0].substring(1)));
		int id = room.getForFocus().get(studentNum);
		if (id == room.getFocusAddress())
			room.setFocusAddress(-1);
		else
			room.setFocusAddress(id);
	}

	private boolean texting(String msg) {
		int i = msg.indexOf(" ");
		int roomId = Integer.valueOf(msg.substring(1, i));
		Room room = Server.rooms.get(roomId);
		if (room != null) {
			room.getChatHistory().add(msg.substring(i + 1));
			return true;
		}
		return false;
	}

	private void sendMessage(ArrayList<String> chatHistory, int msgNum, DataOutputStream output) throws Exception {
		// tin nhan thi gui ki hieu bat dau, gui cac tin nhan con thieu, gui ki hieu ket
		// thuc
		if (msgNum < chatHistory.size()) {
			output.writeUTF("mvk");
			while (msgNum < chatHistory.size())
				output.writeUTF(chatHistory.get(msgNum++));

			output.writeUTF("mvk");
		}
	}

	private boolean checkStudent(String[] msges, DataOutputStream output) throws Exception {
		int roomId = Integer.valueOf(msges[0].substring(1));
		Room room = Server.rooms.get(roomId);
		if (room != null) {
			int id = Integer.parseInt(msges[1]);
			room.getStudents().get(id).setTime(System.currentTimeMillis());
			// Xu ly focus
			if (id == room.getFocusAddress())
				output.writeUTF("H");
			else
				output.writeUTF("~H");

			sendMessage(room.getChatHistory(), Integer.parseInt(msges[2]), output);
			output.writeUTF("E");
			return true;
		}
		return false;
	}

	private boolean checkTeacher(String[] msges, DataOutputStream output) throws Exception {
		int roomId = Integer.valueOf(msges[0].substring(1));
		Room room = Server.rooms.get(roomId);
		if (room != null) {
			room.getTeacher().setTime(System.currentTimeMillis());
			// Xoa cac hoc sinh da thoat
			Queue<Integer> quitted = room.getQuittedStudents();
			while (!quitted.isEmpty())
				output.writeUTF("D" + quitted.poll());

			sendMessage(room.getChatHistory(), Integer.parseInt(msges[1]), output);
			output.writeUTF("E");
			return true;
		}
		return false;
	}
}
