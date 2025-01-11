package pbl4.Client.Controller.Student;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.imageio.ImageIO;

import pbl4.Client.Constant;

public class SaveVideoThread extends Thread {
	private StudentController par;
	private boolean isScreen;

	public SaveVideoThread(StudentController par, boolean isScreen) {
		this.par = par;
		this.isScreen = isScreen;
	}

	@Override
	public void run() {
		while (par.running) {
//			int size = isScreen ? par.screenQueue.size() : par.camQueue.size();
			int size = par.screenQueue.size();
			if (size >= 300) {
				try (Socket soc = new Socket(Constant.serverAddress, Constant.tcpPort);
						DataOutputStream dos = new DataOutputStream(soc.getOutputStream())) {
					dos.writeUTF("V," + (isScreen ? 1 : 0) + "," + par.id); // 0 : camera, 1 : screen
					dos.writeInt(isScreen ? Constant.screenWidth : Constant.camWidth);
					dos.writeInt(isScreen ? Constant.screenHeight : Constant.camHeight);
					dos.writeInt(size);
					for (int i = 0; i < size; i++) {
						byte[] imageBytes = isScreen ? getScreen() : getCam();
						dos.writeInt(imageBytes.length);
						dos.write(imageBytes);
						dos.flush();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private byte[] getScreen() {
		BufferedImage tmp = par.screenQueue.poll();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(tmp, "jpg", baos);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return baos.toByteArray();
	}
	
	private byte[] getCam() {
//		Mat matTemp = par.camQueue.poll();
//		MatOfByte buffer = new MatOfByte();
//		Imgcodecs.imencode(".jpg", matTemp, buffer);
//		return buffer.toArray();
		return null;
	}
}
