package pbl4.Client.Controller.OutContest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import pbl4.Client.Controller.BaseController;
import pbl4.Client.DTO.OutContest.User;
import pbl4.Client.Utils.Service;

public class LoginController extends BaseController {

	public static boolean handleLoginAction(String username, String password) {
		if (!Service.checkCredentials(username, password))
			return false;
		String msg = "L," + username + "," + password, receiveMsg = null;
		try (Socket tcpSocket = new Socket(serverAddress, tcpPort);
				DataInputStream dis = new DataInputStream(tcpSocket.getInputStream());
				DataOutputStream dos = new DataOutputStream(tcpSocket.getOutputStream())) {
			dos.writeUTF(msg);
			receiveMsg = dis.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (receiveMsg.charAt(0) == 'Y') {
			int user_id = Integer.parseInt(receiveMsg.split(",")[1]);
			new TeacherController(new User(user_id, username, password));
			return true;
		} else {
			Service.showAlert("Bạn nhập sai mật khẩu hoặc tài khoản", "Thông báo");
			return false;
		}
	}

}
