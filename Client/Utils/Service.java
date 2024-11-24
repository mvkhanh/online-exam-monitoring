package pbl4.Client.Utils;

import javax.swing.JOptionPane;

public class Service {
	public static boolean checkCredentials(String username, String password) {
		if (username.equals("") || password.equals("")) {
			showAlert("Bạn chưa nhập tài khoản hoặc mật khẩu", "Thông báo");
			return false;
		}
		return true;
	}
	
	public static void showAlert(String msg, String title) {
		JOptionPane.showMessageDialog(null, 
	            msg, 
	            title, 
	            JOptionPane.INFORMATION_MESSAGE);
	}

	public static int confirmAlert(String message, String title) {
		return JOptionPane.showConfirmDialog(
	            null,
	            message,
	            title,
	            JOptionPane.YES_NO_OPTION,
	            JOptionPane.QUESTION_MESSAGE
	        );
	}
}
