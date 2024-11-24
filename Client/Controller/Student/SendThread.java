package pbl4.Client.Controller.Student;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;

import javax.imageio.ImageIO;

import pbl4.Client.Constant;

public class SendThread extends Thread{
	
	private StudentController par;
	private boolean isScreen;
	private byte[] tmp = new byte[Constant.PACKET_SIZE];
	
	public SendThread(StudentController par, boolean isScreen) {
		this.par = par;
		this.isScreen = isScreen;
		tmp[0] = Byte.valueOf(par.roomId);
		tmp[4] = 1;
	}
	
	public void run() {
		int currImg = 0;
		while (par.running) {
			try {				
				byte[] image = isScreen ? compressScreen() : compressCam();
				int size = (image.length + Constant.IMAGE_SEGMENT - 1) / Constant.IMAGE_SEGMENT;
				int j = 5, packetNum = 0;
				tmp[1] = (byte)size;
				tmp[2] = (byte)currImg;
				for(int i = 1; i <= image.length; i++) {
					tmp[j++] = image[i - 1];
					if(i % Constant.IMAGE_SEGMENT == 0 || i == image.length) {
						tmp[3] = (byte)packetNum++;
						par.udpSocket.send(new DatagramPacket(tmp, j, Constant.serverAddress, Constant.udpPort));
						j = 5;
					}
				}
				currImg = (currImg + 1) % (isScreen ? Constant.MAX_SCREENS : Constant.MAX_CAMS);
				Thread.sleep(1);
			}
			catch(Exception ex) {}
		}
	}
	
	private byte[] compressScreen() throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(par.imgModel.img, "jpg", baos);
		return baos.toByteArray();
	}
	
	private byte[] compressCam() {
//		Mat matTemp = par.camImg;
//		MatOfByte buffer = new MatOfByte();
//		Imgcodecs.imencode(".jpg", matTemp, buffer);
//		return buffer.toArray();
		return null;
	}
}
