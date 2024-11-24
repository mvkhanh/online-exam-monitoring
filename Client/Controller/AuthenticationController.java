package pbl4.Client.Controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import pbl4.Client.Constant;
import pbl4.Client.Controller.Teacher.DashboardController;
import pbl4.Client.DTO.OutContest.User;
import pbl4.Client.Utils.Service;

public class AuthenticationController {

	public static boolean handleLoginAction(String username, String password) {
		return authentication('L', username, password);
	}

	public static boolean handleRegisterAction(String username, String password) {
		return authentication('R', username, password);
	}

	private static boolean authentication(Character code, String username, String password) {
		if (!Service.checkCredentials(username, password))
			return false;
		String msg = code + "," + username + "," + password, receiveMsg = null;
		try (Socket tcpSocket = new Socket(Constant.serverAddress, Constant.tcpPort);
				DataInputStream dis = new DataInputStream(tcpSocket.getInputStream());
				DataOutputStream dos = new DataOutputStream(tcpSocket.getOutputStream())) {
			dos.writeUTF(msg);
			receiveMsg = dis.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (receiveMsg != null && receiveMsg.charAt(0) == 'Y') {
			int user_id = Integer.parseInt(receiveMsg.split(",")[1]);
			new DashboardController(new User(user_id, username, password));
			return true;
		} else {
			Service.showAlert(code == 'L' ? "Bạn nhập sai mật khẩu hoặc tài khoản" : "Tài khoản đã tồn tại", "Thông báo lỗi");
			return false;
		}
	}
}
