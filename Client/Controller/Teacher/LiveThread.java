package pbl4.Client.Controller.Teacher;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import pbl4.Client.Constant;

public class LiveThread extends Thread {
	private TeacherController par;
	private int msgNum = 0;

	public LiveThread(TeacherController par) {
		this.par = par;
	}

	public void run() {
		while (par.running) {
			try (Socket tcpSocket = new Socket(Constant.serverAddress, Constant.tcpPort);
					DataInputStream dis = new DataInputStream(tcpSocket.getInputStream());
					DataOutputStream dos = new DataOutputStream(tcpSocket.getOutputStream())) {
				String output = "T" + par.roomId + " " + msgNum;
				dos.writeUTF(output);
				String msg = "";
				while (!(msg = dis.readUTF()).equals("E")) {
					if (msg.startsWith("N")) {
						int i = msg.indexOf(" ");
						int studentNum = Integer.parseInt(msg.substring(1, i));
						String studentName = msg.substring(i + 1);
						par.addStudent(studentNum, studentName);
					} else if (msg.startsWith("D")) {
						int studentNum = Integer.parseInt(msg.substring(1));
						par.deleteStudent(studentNum);
					} else if (msg.startsWith("M")) {
						par.addText(msg.substring(1));
						msgNum++;
					} else if (msg.startsWith("K")) {
						par.addKeyLog(msg.substring(1));
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
