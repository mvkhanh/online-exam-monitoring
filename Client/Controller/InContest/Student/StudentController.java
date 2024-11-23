package pbl4.Client.Controller.InContest.Student;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import pbl4.Client.Controller.InContest.InContestBaseController;
import pbl4.Client.DTO.InContest.Student.ImageModel;
import pbl4.Client.View.InContest.Student.Student;

public class StudentController extends InContestBaseController {
//	static {
//	System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//}
//	
	public static final int NORMAL_WIDTH = 450;
	public static final int NORMAL_HEIGHT = 300;
	public static final int FOCUS_WIDTH = 900;
	public static final int FOCUS_HEIGHT = 600;
	public static final long KEYBOARD_DURATION = 10000; // 5 phut: 300000
	
	public Student view;
	public ImageModel imgModel = new ImageModel(NORMAL_WIDTH, NORMAL_HEIGHT);
//	public Size camDim = new Size(NORMAL_WIDTH, NORMAL_HEIGHT);
//	public Mat camImg = new Mat();
	public String currKeys = "";

	public StudentController() {
		super();
		view = new Student(this);
	}

	public String joinRoom(String name, String roomId) {
		String msg = "J" + roomId + " " + udpSocket.getLocalPort() + " " + name, res = null;
		try (Socket tcpSocket = new Socket(serverAddress, tcpPort);
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
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return res;
	}
	
	public void startThreads() {
		threads.add(new CaptureThread(this));
		threads.getLast().start();
//		threads.add(new CaptureThread2(this));
//		threads.getLast().start();
		threads.add(new SendThread(this));
		threads.getLast().start();
//		threads.add(new SendThread2(this));
//		threads.getLast().start();
		threads.add(new LiveThread(this));
		threads.getLast().start();
	}
	
	public void handleFocus(int width, int height) {
		ImageModel img = new ImageModel(width, height);
		ImageModel curr = imgModel;
		imgModel = img;
		curr.g2d.dispose();
	}
	
	public void addText(String txt) {
		view.addText(txt);
	}
}
