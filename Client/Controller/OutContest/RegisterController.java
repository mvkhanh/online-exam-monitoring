package pbl4.Client.Controller.OutContest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import pbl4.Client.Controller.BaseController;
import pbl4.Client.DTO.OutContest.User;
import pbl4.Client.Utils.Service;
import pbl4.Client.View.OutContest.Teacher;

public class RegisterController extends BaseController{

	public static boolean handleRegisterAction(String username, String password) {
		if (!Service.checkCredentials(username, password))
			return false;
		String sendMsg = "R," + username + "," + password, receiveMsg = null;
		try (Socket tcpSocket = new Socket(serverAddress, tcpPort);
				DataInputStream dis = new DataInputStream(tcpSocket.getInputStream());
				DataOutputStream dos = new DataOutputStream(tcpSocket.getOutputStream())) {
			dos.writeUTF(sendMsg);
			receiveMsg = dis.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (receiveMsg != null && receiveMsg.charAt(0) == 'Y') {
			new TeacherController(new User(Integer.parseInt(receiveMsg.split(",")[1]), username, password));
			return true;
		} else {
			Service.showAlert("Tài khoản đã tồn tại", "Thông báo lỗi");
			return false;
		}
	}
}
