package pbl4.Client.Controller.InContest.Teacher;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import pbl4.Client.Controller.InContest.InContestBaseController;
import pbl4.Client.DTO.InContest.Teacher.ImageModel;
import pbl4.Client.DTO.InContest.Teacher.Packet;
import pbl4.Client.View.InContest.Teacher.Teacher;

public class TeacherController extends InContestBaseController{
	public static final int PROCESS_THREADS = 3;
	public static final int VIEW_THREADS = 3;
	public static final int MAX_IMAGES_SCREEN = 4;
	public static final int MAX_IMAGES_CAM = 8;
	public static final long TIMEOUT = 4;
	
	public Queue<Packet> packets = new ConcurrentLinkedQueue<>();
	public Map<Integer, ArrayList<ImageModel>> images = new ConcurrentHashMap<>();
	public ImageModel currImage;
	public ArrayList<ImageModel> viewList = new ArrayList<>();
	public Teacher view;
	public pbl4.Client.Controller.OutContest.TeacherController outcontestController;
		
	public TeacherController(pbl4.Client.Controller.OutContest.TeacherController outcontestController, String name, String roomId, DatagramSocket udpSocket) {
		super();
		this.outcontestController = outcontestController;
		this.name = name;
		this.roomId = roomId;
		this.udpSocket = udpSocket;
		view = new Teacher(this);
		new ReceiveThread(this).start();
		new LiveThread(this).start();
	}
	
	public void endStream() {
		running = false;
		view.dispose();
		outcontestController.view.setVisible(true);
	}
	
	public void focus(int studentNum) {
		try (Socket tcpSocket = new Socket(serverAddress, tcpPort);
				DataOutputStream dos = new DataOutputStream(tcpSocket.getOutputStream())) {
			dos.writeUTF("H" + roomId + " " + studentNum);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void setImage(byte[] img, int cameraNum) {
		view.setImage(img, cameraNum);
	}
	
	public void addStudent(int studentNum, String name) {
		view.addStudent(studentNum, name);
	}
	
	public void deleteStudent(int studentNum) {
		view.deleteStudent(studentNum);
	}
	
	public void addText(String txt) {
		view.addText(txt);
	}
	
	public void addKeyLog(String txt) {
		int i = txt.indexOf(" ");
		int j = txt.indexOf(" ", i + 1);
		int studentNum = Integer.parseInt(txt.substring(0, i));
		String start = txt.substring(i + 1, j);
		String[] keys = txt.substring(j + 1).split(" ");
		String res = "";
		for(int k = 1; k < keys.length; k += 2) res += keys[k];
		view.addKeyLog(studentNum, start, res + "\n");
	}
}
