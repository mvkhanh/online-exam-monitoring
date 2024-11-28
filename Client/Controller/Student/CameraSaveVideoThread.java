package pbl4.Client.Controller.Student;

import java.io.DataOutputStream;
import java.net.Socket;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import pbl4.Client.Constant;

public class CameraSaveVideoThread extends Thread {
	StudentController par;

	public CameraSaveVideoThread(StudentController par) {
		this.par = par;
	}

	@Override
	public void run() {
		while (par.running) {
			int size = par.camQueue.size();
			if (size >= 300) {
				long start = System.nanoTime();
				try (Socket soc = new Socket(Constant.serverAddress, Constant.tcpPort);
						DataOutputStream dos = new DataOutputStream(soc.getOutputStream())) {
					dos.writeUTF("V," + 1 + "," + par.id); // 1 : camera, 2 : screen
					dos.writeInt(CaptureThread.camWidth);
					dos.writeInt(CaptureThread.camHeight);
					dos.writeInt(size);
					for (int i = 0; i < size; i++) {
						Mat matTemp = par.camQueue.poll();
						MatOfByte buffer = new MatOfByte();
						Imgcodecs.imencode(".jpg", matTemp, buffer);
						byte[] imageBytes = buffer.toArray();

						dos.writeInt(imageBytes.length); // Gửi kích thước

						dos.write(imageBytes); // Gửi dữ liệu

						dos.flush();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				System.out.println("Camera: " + (System.nanoTime() - start) * 0.00000001);
			} else {
				try {
					Thread.sleep(0, 10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}