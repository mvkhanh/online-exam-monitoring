package pbl4.Client.Controller.Student;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import pbl4.Client.Constant;

public class SendThread extends Thread {

	private StudentController par;
	private boolean isScreen;
	private byte[] tmp = new byte[Constant.PACKET_SIZE];

	public SendThread(StudentController par, boolean isScreen) {
		this.par = par;
		this.isScreen = isScreen;
		tmp[0] = Byte.valueOf(par.roomId);
	}

	// 1 byte cho roomId, 3 bit cho STT cua anh, 1 bit cho camera hay screen, 7 bit
	// cho size, 7 bit cho STT packet
	public void run() {
		int currImg = 0;
		int header = Constant.PACKET_SIZE - Constant.IMAGE_SEGMENT;
		while (par.running) {
			try {
				byte[] image = isScreen ? compressScreen() : compressCam();
				int size = (image.length + Constant.IMAGE_SEGMENT - 1) / Constant.IMAGE_SEGMENT;
				int j = header, packetNum = 0;
				tmp[1] = (byte) size;
				tmp[2] = (byte) (currImg * Constant.MAX_CAMS + (isScreen ? 1 : 0)); // Chon maxCam vi maxCam lon hon
				for (int i = 1; i <= image.length; i++) {
					tmp[j++] = image[i - 1];
					if (i % Constant.IMAGE_SEGMENT == 0 || i == image.length) {
						tmp[3] = (byte) packetNum++;
						par.udpSocket.send(new DatagramPacket(tmp, j, Constant.serverAddress, Constant.udpPort));
						j = header;
					}
				}
				currImg = (currImg + 1) % (isScreen ? Constant.MAX_SCREENS : Constant.MAX_CAMS);
				Thread.sleep(1);
			} catch (Exception ex) {
			}
		}
	}

	private byte[] compressScreen() throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(par.imgModel.img, "jpg", baos);
		return baos.toByteArray();
	}

	private byte[] compressCam() {
		Imgproc.resize(par.frame, par.camImg, par.camDim);
		Mat matTemp = par.camImg;
		MatOfByte buffer = new MatOfByte();
		Imgcodecs.imencode(".jpg", matTemp, buffer);
		byte[] data = buffer.toArray();
		par.view.cameraScreen.setIcon(new ImageIcon(data));
		return data;
//		return null;
	}
}
