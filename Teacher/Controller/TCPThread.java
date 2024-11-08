package PBL4.Teacher.Controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import PBL4.Student.View.Student2;
import PBL4.Teacher.View.Teacher;

public class TCPThread extends Thread{
	private Teacher par;
	int msgNum = 0;
	public TCPThread(Teacher par) {
		this.par = par;
	}
	
	public void run() {
		String msg;
		while (true) {
			try (Socket socket = new Socket(Teacher.serverAddress, Teacher.tcpPort);
					DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
					DataInputStream dis = new DataInputStream(socket.getInputStream())) {
				dos.writeUTF("T" + par.roomId + " " + msgNum);
				boolean isMsg = false;
				while(!(msg = dis.readUTF()).equals("E")) {
					if (msg.startsWith("D"))
						par.removeCameraScreen(Integer.parseInt(msg.substring(1)));
					else if(msg.startsWith("mvk")) isMsg = true;
					else if(msg.startsWith("~mvk")) isMsg = false;
					else if(isMsg) {
						par.chatArea.append(msg);
						msgNum++;
					}
					//Q: Giao vien da thoat
				}
				Thread.sleep(100);
			} catch (Exception e1) {
			}
		}
	}
}
