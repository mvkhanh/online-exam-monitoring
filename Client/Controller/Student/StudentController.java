package pbl4.Client.Controller.Student;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.Socket;

import pbl4.Client.Constant;
import pbl4.Client.Controller.InContestBaseController;
import pbl4.Client.DTO.InContest.ScreenImageDTO;
import pbl4.Client.View.Home;
import pbl4.Client.View.StudentInContest;

public class StudentController extends InContestBaseController {
//	static {
//	System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//}
//	

	public StudentInContest view;
	public ScreenImageDTO imgModel = new ScreenImageDTO(Constant.NORMAL_WIDTH, Constant.NORMAL_HEIGHT);
//	public Size camDim = new Size(Constant.NORMAL_WIDTH, Constant.NORMAL_HEIGHT);
//	public Mat camImg = new Mat();
	public String currKeys = "";

	public StudentController() {
		try {
			udpSocket = new DatagramSocket();
		} catch (IOException e) {
			System.exit(1);
		}
		view = new StudentInContest(this);
	}

	public String joinRoom(String name, String roomId) {
		String msg = "J" + roomId + " " + udpSocket.getLocalPort() + " " + name, res = null;
		try (Socket tcpSocket = new Socket(Constant.serverAddress, Constant.tcpPort);
				DataOutputStream dos = new DataOutputStream(tcpSocket.getOutputStream());
				DataInputStream dis = new DataInputStream(tcpSocket.getInputStream());) {
			dos.writeUTF(msg);
			res = dis.readUTF();
			if (res.startsWith("Y")) {
				int i = res.indexOf(" ");
				id = res.substring(1, i);
				res = res.substring(i + 1);
				this.roomId = roomId;
				this.name = name;
			}
			else res = null;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return res;
	}
	
	public void startThreads() {
		new CaptureThread(this, true).start();
//		new CaptureThread(this, false).start();
		new SendThread(this, true).start();
//		new SendThread(this, false).start();
		new LiveThread(this).start();
	}
	
	public void handleFocus(int width, int height) {
		ScreenImageDTO img = new ScreenImageDTO(width, height);
		ScreenImageDTO curr = imgModel;
		imgModel = img;
		curr.g2d.dispose();
	}
	
	public void addText(String txt) {
		view.addText(txt);
	}
	
	public void endStream() {
		view.dispose();
		new Home().setVisible(true);;
	}
}
