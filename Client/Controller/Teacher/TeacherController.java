package pbl4.Client.Controller.Teacher;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import pbl4.Client.Constant;
import pbl4.Client.Controller.InContestBaseController;
import pbl4.Client.DTO.InContest.Teacher.ImageModel;
import pbl4.Client.DTO.InContest.Teacher.Packet;
import pbl4.Client.View.TeacherInContest;

public class TeacherController extends InContestBaseController{
	
	public Queue<Packet> packets = new ConcurrentLinkedQueue<>();
	public Map<Integer, ArrayList<ImageModel>> images = new ConcurrentHashMap<>();
	public ImageModel currImage;
	public ArrayList<ImageModel> viewList = new ArrayList<>();
	public TeacherInContest view;
	public DashboardController dashboardController;
		
	public TeacherController(DashboardController dashboardController, String name, String roomId, DatagramSocket udpSocket) {
		super();
		this.dashboardController = dashboardController;
		this.name = name;
		this.roomId = roomId;
		this.udpSocket = udpSocket;
		view = new TeacherInContest(this);
		new ReceiveThread(this).start();
		new LiveThread(this).start();
	}
	
	public void endStream() {
		running = false;
		try (Socket tcpSocket = new Socket(Constant.serverAddress, Constant.tcpPort);
				DataOutputStream dos = new DataOutputStream(tcpSocket.getOutputStream())) {
			dos.writeUTF("Q" + roomId);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		view.dispose();
		dashboardController.view.setVisible(true);
	}
	
	public void focus(int studentNum) {
		try (Socket tcpSocket = new Socket(Constant.serverAddress, Constant.tcpPort);
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
		String[] splits = txt.split(" ");
		int studentNum = Integer.parseInt(splits[0]);
		String start = splits[1];
		String end = splits[2];
		String res = "";
		for(int k = 4; k < splits.length; k += 2) res += splits[k];
		view.addKeyLog(studentNum, start + "-" + end, res + "\n");
	}
}
