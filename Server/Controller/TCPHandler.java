<<<<<<< HEAD
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
=======
package pbl4.Server.Controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import pbl4.Server.Constant;
import pbl4.Server.Server;
import pbl4.Server.DAO.ParticipantDAO;
import pbl4.Server.DAO.TestDAO;
import pbl4.Server.DAO.UsersDAO;
import pbl4.Server.DTO.ClientModel;
import pbl4.Server.DTO.Room;
import pbl4.Server.Entity.Participant;
import pbl4.Server.Entity.Test;
import pbl4.Server.Entity.User;
import pbl4.Server.Utils.Service;
>>>>>>> 3.3

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
<<<<<<< HEAD
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
=======
			case 'C': // C<udpPort> <id> <tencuocthi>
				int id = createRoom(message);
				output.writeUTF(id + "");
				break;

			case 'J': // J<roomId> <udpPort> <studentName>
				joinRoom(message, output);
>>>>>>> 3.3
				break;

			case 'H': // H<roomId> <studentNum>
				msges = message.split(" ");
				focus(msges);
				break;

			case 'M': // M<roomId> <text>
				if (!texting(message))
					output.writeUTF("Q");
				break;

<<<<<<< HEAD
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
=======
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

			case 'L': // login : "L,username,password"
				handleLogin(message, output);
				break;

			case 'R': // register : "R,username,password"
				handleRegister(message, output);
				break;

			case 'U': // user update
				handleUpdateUserRequest(message);
				break;

			case 'K': // Lay file ban phim cua participant ve cho teacher xem
				getKeys(message.substring(1), output);
				break;

			case 'Q': // Teacher bam nut ket thuc
				endStream(message);
				break;

			case 'V': // luu video
				saveVideo(message, input);
>>>>>>> 3.3
				break;
			default:
				break;
			}

		} catch (Exception e) {
<<<<<<< HEAD
			e.printStackTrace();
=======
//			e.printStackTrace();
>>>>>>> 3.3
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
<<<<<<< HEAD
				// TODO Auto-generated catch block
=======
>>>>>>> 3.3
				e.printStackTrace();
			}
		}
	}

<<<<<<< HEAD
	private int[] createRoom(String msg) {
		int i = msg.indexOf(" ");
		int udpPort = Integer.valueOf(msg.substring(1, i));
		int roomId = Server.NEW_ROOM_ID.getAndIncrement();
		Room room = new Room(address, udpPort, msg.substring(i + 1), Server.ID.getAndIncrement());
		Server.rooms.put(roomId, room);
		return new int[] { roomId, room.getId() };
	}

	private int joinRoom(String msg) {
=======
	private int createRoom(String msg) {
		int i = msg.indexOf(" ");
		int udpPort = Integer.valueOf(msg.substring(1, i));
		int j = msg.indexOf(" ", i + 1);
		String user_id = msg.substring(i + 1, j);
		String tencuocthi = msg.substring(j + 1);
		int roomId = TestDAO.addTest(user_id, tencuocthi);
		Room room = new Room(address, udpPort, tencuocthi);
		Server.rooms.put(roomId, room);
		return roomId;
	}

	private void joinRoom(String msg, DataOutputStream output) throws Exception {
>>>>>>> 3.3
		int i = msg.indexOf(" ");
		int j = msg.indexOf(" ", i + 1);
		int roomId = Integer.valueOf(msg.substring(1, i));
		Room room = Server.rooms.get(roomId);
		if (room != null) {
<<<<<<< HEAD
			int studentId = Server.ID.getAndIncrement();
			int studentNum = room.getNewStudentId().getAndIncrement();
			room.getStudentNums().put(address.toString() + msg.substring(i + 1, j), studentNum);
			room.getStudents().put(studentId, new ClientModel(studentNum));
			room.getForFocus().put(studentNum, studentId);
			return studentId;
		}
		return 0;
=======
			String name = msg.substring(j + 1);
			int studentId = ParticipantDAO.addParticipant(roomId, name);
			int studentNum = room.getNewStudentId().getAndIncrement();
			room.getStudentNums().put(address.toString() + msg.substring(i + 1, j), studentNum);
			room.getStudents().put(studentId, new ClientModel(studentNum));
			room.getForFocus().put(studentNum * 2, studentId * 2);
			room.getForFocus().put(studentNum * 2 + 1, studentId * 2 + 1);
			room.getNames().add(Map.entry(studentNum, msg.substring(j + 1)));
			output.writeUTF("Y" + studentId + " " + room.getTeachername());
		} else
			output.writeUTF("N");
>>>>>>> 3.3
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

<<<<<<< HEAD
	private boolean texting(String msg) {
=======
	private boolean texting(String msg) throws UnsupportedEncodingException {
>>>>>>> 3.3
		int i = msg.indexOf(" ");
		int roomId = Integer.valueOf(msg.substring(1, i));
		Room room = Server.rooms.get(roomId);
		if (room != null) {
			room.getChatHistory().add(msg.substring(i + 1));
			return true;
		}
		return false;
	}

<<<<<<< HEAD
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
=======
	private void checkStudent(String message, DataOutputStream dos) throws Exception {
		int i = message.indexOf(" ");
		int j = message.indexOf(" ", i + 1);
		int roomId = Integer.valueOf(message.substring(1, i));
		Room room = Server.rooms.get(roomId);
		if (room != null) {
			int studentId = Integer.parseInt(message.substring(i + 1, j));
			int msgNum = Integer.parseInt(message.substring(j + 1));
			room.getStudents().get(studentId).setTime(System.currentTimeMillis());

			if (studentId * 2 == room.getFocusAddress()) {
				dos.writeUTF("H1");
			} else if (studentId * 2 + 1 == room.getFocusAddress()) {
				dos.writeUTF("H2");
			} else
				dos.writeUTF("~H");
			ArrayList<String> chatHistory = room.getChatHistory();
			while (msgNum < chatHistory.size()) {
				dos.writeUTF("M" + chatHistory.get(msgNum));
				msgNum++;
			}
			dos.writeUTF("E");
		} else
			dos.writeUTF("Q");
	}

	private void checkTeacher(String msg, DataOutputStream dos) throws Exception {
		int i = msg.indexOf(" ");
		int roomId = Integer.valueOf(msg.substring(1, i));
		Room room = Server.rooms.get(roomId);
		if (room != null) {
			int msgNum = Integer.parseInt(msg.substring(i + 1));
			room.getTeacher().setTime(System.currentTimeMillis());

			while (!room.getNames().isEmpty()) {
				Map.Entry<Integer, String> entry = room.getNames().poll();
				dos.writeUTF("N" + entry.getKey() + " " + entry.getValue());
			}

			while (!room.getQuittedStudents().isEmpty()) {
				Integer quit = room.getQuittedStudents().poll();
				dos.writeUTF("D" + quit);
			}

			ArrayList<String> chatHistory = room.getChatHistory();
			while (msgNum < chatHistory.size()) {
				dos.writeUTF("M" + chatHistory.get(msgNum));
				msgNum++;
			}
			while (!room.getKeys().isEmpty()) {
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
		if (room != null) {
			int id = Integer.valueOf(msg.substring(i + 1, j));
			int studentNum = room.getStudents().get(id).getStudentNum();
			String txt = msg.substring(j + 1);
			room.getKeys().add(studentNum + " " + txt);
			Service.saveKeyLog(id, txt);
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
		User user = UsersDAO.login(username, password);
		if (user != null) {
			dos.writeUTF("Y," + user.getId());
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
		List<Test> listData = TestDAO.listTests(user_id);
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
		List<Participant> listData = ParticipantDAO.listParticipants(test_id);
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

	private void getKeys(String participant_id, DataOutputStream dos) throws IOException {
		String filePath = Constant.FILE_LOCATION + File.separator + "Keyboard" + File.separator + participant_id
				+ ".txt";
		String s = Files.readString(Paths.get(filePath));
		dos.writeUTF(s);
	}

	private void endStream(String message) {
		Server.rooms.remove(Integer.valueOf(message.substring(1)));
	}

	private void saveVideo(String message, DataInputStream input) {
		int length = 0, width = 0, height = 0;
		try {
			width = input.readInt();
			height = input.readInt();
			length = input.readInt();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		String splitMsg[] = message.split(",");
		int typeVideo = Integer.parseInt(splitMsg[1]);
		String participant_id = splitMsg[2];
		Queue<byte[]> imageBytes = new ConcurrentLinkedQueue<byte[]>();
		new SaveVideoThread(imageBytes, length, typeVideo, participant_id, width, height).start();;
		for (int i = 0; i < length; i++) {
			try {
				int bytesLength = input.readInt();
				if (bytesLength > 0) {
					byte[] receiveImageBytes = new byte[bytesLength];

					input.readFully(receiveImageBytes);

					imageBytes.add(receiveImageBytes);
				}
			} catch (IOException ex) {
				ex.printStackTrace();
				break;
			}
		}
>>>>>>> 3.3
	}
}
