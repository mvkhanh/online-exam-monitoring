package PBL4.Student.Controller;

import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import PBL4.Student.View.Student2;

public class TCPThread extends Thread {
	private Student2 par;
	private int msgNum = 0;
	
	public TCPThread(Student2 par) {
		this.par = par;
	}

	public void run() {
		String msg;
		while (true) {
			try (Socket socket = new Socket(Student2.serverAddress, Student2.tcpPort);
					DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
					DataInputStream dis = new DataInputStream(socket.getInputStream())) {
				dos.writeUTF("S" + par.roomId + " " + par.id + " " + msgNum);
				boolean isMsg = false;
				while(!(msg = dis.readUTF()).equals("E")) {
					if (msg.startsWith("H"))
						handleFocus(Student2.FOCUS_WIDTH, Student2.FOCUS_HEIGHT);
					else if (msg.startsWith("~H"))
						handleFocus(Student2.NORMAL_WIDTH, Student2.NORMAL_HEIGHT);
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

	private void handleFocus(int width, int height) {
		par.g2d.dispose();
		par.img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		par.g2d = par.img.createGraphics();
		par.g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
	}
}
