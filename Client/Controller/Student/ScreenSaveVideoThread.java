package pbl4.Client.Controller.Student;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import javax.imageio.ImageIO;

import pbl4.Client.Constant;

public class ScreenSaveVideoThread extends Thread {
	StudentController par;

	public ScreenSaveVideoThread(StudentController par) {
		this.par = par;
	}

	@Override
	public void run() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		while (par.running) {
			int size = par.screenQueue.size();
			if (size >= 300) {
				long start = System.nanoTime();
				try (Socket soc = new Socket(Constant.serverAddress, Constant.tcpPort);
						DataOutputStream dos = new DataOutputStream(soc.getOutputStream())) {
					dos.writeUTF("V," + 2 + "," + par.id); // 1 : camera, 2 : screen
					dos.writeInt(dim.width);
					dos.writeInt(dim.height);
					dos.writeInt(size);
					for (int i = 0; i < size; i++) {
						BufferedImage tmp = par.screenQueue.poll();
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ImageIO.write(tmp, "jpg", baos);
						byte[] imageBytes = baos.toByteArray();

						dos.writeInt(imageBytes.length); // Gửi kích thước

						dos.write(imageBytes); // Gửi dữ liệu

						dos.flush();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				System.out.println("Man hinh:" + (System.nanoTime() - start) * 0.00000001);
			} else {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}