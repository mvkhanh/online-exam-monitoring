package pbl4.Server.Controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pbl4.Server.Server;
import pbl4.Server.DAO.ParticipantDAO;
import pbl4.Server.DAO.TestsDAO;
import pbl4.Server.DAO.UsersDAO;
import pbl4.Server.DTO.ClientModel;
import pbl4.Server.DTO.Room;
import pbl4.Server.Entity.Participant;
import pbl4.Server.Entity.Test;
import pbl4.Server.Utils.Service;


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
			case 'C': // C<udpPort> <id> <tencuocthi>
				int id = createRoom(message);
				output.writeUTF(id + "");
				break;

			case 'J': // J<roomId> <udpPort> <studentName>
				joinRoom(message, output);
				break;

			case 'H': // H<roomId> <studentNum>
				msges = message.split(" ");
				focus(msges);
				break;

			case 'M': // M<roomId> <text>
				if (!texting(message))
					output.writeUTF("Q");
				break;
				
			case 'S':
				checkStudent(message, output);
				break;
				
			case 'T':
				checkTeacher(message, output);
				break;
				
			case '!': // !<roomId> <...>
				takeKeys(message);
				message = input.readUTF();
				checkStudent(message, output);
				break;
				
			case 'E':
				handleListTestRequest(message, output);
				break;
				
			case 'P':
				handleListParticipant(message, output);
				break;
				
			// login : "L,username,password"
			case 'L':
				handleLogin(message, output);
				break;
			// register : "R,username,password"
			case 'R':
				handleRegister(message, output);
				break;
			// user update
			case 'U':
				handleUpdateUserRequest(message);
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
				e.printStackTrace();
			}
		}
	}

	private int createRoom(String msg) {
		int i = msg.indexOf(" ");
		int udpPort = Integer.valueOf(msg.substring(1, i));
		int j = msg.indexOf(" ", i + 1);
		String user_id = msg.substring(i + 1, j);
		String tencuocthi = msg.substring(j + 1);
		int roomId = TestsDAO.addTest(user_id, tencuocthi);
		Room room = new Room(address, udpPort, tencuocthi);
		Server.rooms.put(roomId, room);
		return roomId;
	}

	private void joinRoom(String msg, DataOutputStream output) throws Exception {
		int i = msg.indexOf(" ");
		int j = msg.indexOf(" ", i + 1);
		int roomId = Integer.valueOf(msg.substring(1, i));
		Room room = Server.rooms.get(roomId);
		if (room != null) {
			String name = msg.substring(j + 1);
			int studentId = ParticipantDAO.addParticipant(roomId, name);
			int studentNum = room.getNewStudentId().getAndIncrement();
			room.getStudentNums().put(address.toString() + msg.substring(i + 1, j), studentNum);
			room.getStudents().put(studentId, new ClientModel(studentNum));
			room.getForFocus().put(studentNum * 2, studentId * 2);
			room.getForFocus().put(studentNum * 2 + 1, studentId * 2 + 1);
			room.getNames().add(Map.entry(studentNum, msg.substring(j + 1)));
			output.writeUTF("Y" + studentId + " " + room.getTeachername());
		}
		else output.writeUTF("N");
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

	private boolean texting(String msg) throws UnsupportedEncodingException {
		int i = msg.indexOf(" ");
		int roomId = Integer.valueOf(msg.substring(1, i));
		Room room = Server.rooms.get(roomId);
		if (room != null) {
			room.getChatHistory().add(msg.substring(i + 1));
			return true;
		}
		return false;
	}
	
	private void checkStudent(String message, DataOutputStream dos) throws Exception{
		int i = message.indexOf(" ");
		int j = message.indexOf(" ", i + 1);
		int roomId = Integer.valueOf(message.substring(1, i));
		Room room = Server.rooms.get(roomId);
		if (room != null) {
			int studentId =	Integer.parseInt(message.substring(i + 1, j));
			int msgNum = Integer.parseInt(message.substring(j + 1));
			room.getStudents().get(studentId).setTime(System.currentTimeMillis());
			
			if(studentId * 2 == room.getFocusAddress()) {
				dos.writeUTF("H1");
			}
			else if(studentId * 2 + 1 == room.getFocusAddress()) {
				dos.writeUTF("H2");
			}
			else dos.writeUTF("~H");
			ArrayList<String> chatHistory = room.getChatHistory();
			while(msgNum < chatHistory.size()) {
				dos.writeUTF("M" + chatHistory.get(msgNum));
				msgNum++;
			}
		}
		//Q neu teacher da thoat
		dos.writeUTF("E");
	}
	
	private void checkTeacher(String msg, DataOutputStream dos) throws Exception{
		int i = msg.indexOf(" ");
		int roomId = Integer.valueOf(msg.substring(1, i));
		Room room = Server.rooms.get(roomId);
		if (room != null) {
			int msgNum = Integer.parseInt(msg.substring(i + 1));
			room.getTeacher().setTime(System.currentTimeMillis());
			
			while(!room.getNames().isEmpty()) {
				Map.Entry<Integer, String> entry = room.getNames().poll();
				dos.writeUTF("N" + entry.getKey() + " " + entry.getValue());
			}
			
			while(!room.getQuittedStudents().isEmpty()) {
				Integer quit = room.getQuittedStudents().poll();
				dos.writeUTF("D" + quit);
			}
			
			ArrayList<String> chatHistory = room.getChatHistory();
			while(msgNum < chatHistory.size()) {
				dos.writeUTF("M" + chatHistory.get(msgNum));
				msgNum++;
			}
			while(!room.getKeys().isEmpty()) {
				dos.writeUTF("K" + room.getKeys().poll());
			}
		}
		dos.writeUTF("E");
	}
	
	private void takeKeys(String msg) {
		int i = msg.indexOf(" ");
		int j = msg.indexOf(" ", i + 1);
		int roomId = Integer.valueOf(msg.substring(1, i));
		Room room = Server.rooms.get(roomId);
		if(room != null) {
			int id = Integer.valueOf(msg.substring(i + 1, j));
			int studentNum = room.getStudents().get(id).getStudentNum();
			String txt = msg.substring(j + 1);
			room.getKeys().add(studentNum + " " + txt);
			Service.saveToFile(id, txt);
		}
	}

	private void handleUpdateUserRequest(String msg) {
		String splitMsg[] = msg.split(",");
		UsersDAO.updatePassword(splitMsg[1], splitMsg[2]);
	}

	private void handleLogin(String msg, DataOutputStream dos) throws IOException {
		String splitMsg[] = msg.split(",");
		String username = splitMsg[1];
		String password = splitMsg[2];
		int user_id = UsersDAO.login(username, password);
		if (user_id != -1) {
			dos.writeUTF("Y," + user_id);
		} else {
			dos.writeUTF("N");
		}
	}

	private void handleRegister(String msg, DataOutputStream dos) throws IOException {
		String splitMsg[] = msg.split(",");
		String username = splitMsg[1];
		String password = splitMsg[2];
		int user_id = UsersDAO.register(username, password);
		if (user_id != -1) {
			dos.writeUTF("Y," + user_id);
		} else {
			dos.writeUTF("N");
		}
	}

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private void handleListTestRequest(String msg, DataOutputStream dos) throws IOException {
		String[] splitMsg = msg.split(",");
		String user_id = splitMsg[1];
		List<Test> listData = TestsDAO.listTest(user_id);
		StringBuilder sendMsg = new StringBuilder();
		if (!listData.isEmpty()) {
			for (int i = 0; i < listData.size(); i++) {
				Test t = listData.get(i);
				sendMsg.append(t.getId()).append(",").append(t.getName()).append(",")
						.append(dateFormat.format(t.getCreated_at())).append(",");

				if (i != listData.size() - 1)
					sendMsg.append("|");
			}
			dos.writeUTF(sendMsg.toString());
		} else {
			dos.writeUTF("0");
		}
	}
	
	private void handleListParticipant(String msg, DataOutputStream dos) throws IOException {
		String[] splitMsg = msg.split(",");
		String test_id = splitMsg[1];
		List<Participant> listData = ParticipantDAO.listParticipant(test_id);
		StringBuilder sendMsg = new StringBuilder();
		if (!listData.isEmpty()) {
			for (int i = 0; i < listData.size(); i++) {
				Participant t = listData.get(i);
				sendMsg.append(t.getId()).append(",").append(t.getName()).append(",");
				if (i != listData.size() - 1)
					sendMsg.append("|");
			}
			dos.writeUTF(sendMsg.toString());
		} else {
			dos.writeUTF("0");
		}
	}
}
