package pbl4.Client.Controller.InContest.Student;

import java.io.ByteArrayOutputStream;
import java.net.DatagramPacket;

import javax.imageio.ImageIO;

public class SendThread extends Thread{
	private StudentController par;
	public static final int PACKET_SIZE = 1100;
	private byte[] tmp = new byte[PACKET_SIZE + 5];
	public static final int MAX_IMAGES = 4;
	
	public SendThread(StudentController par) {
		this.par = par;
		tmp[0] = Byte.valueOf(par.roomId);
		tmp[4] = 1;
	}
	
	public void run() {
		int currImg = 0;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while (par.running) {
			try {
				baos.reset();
				ImageIO.write(par.imgModel.img, "jpg", baos);
				byte[] image = baos.toByteArray();
				int size = (image.length + PACKET_SIZE - 1) / PACKET_SIZE;
				int j = 5, packetNum = 0;
				tmp[1] = (byte)size;
				tmp[2] = (byte)currImg;
				for(int i = 1; i <= image.length; i++) {
					tmp[j++] = image[i - 1];
					if(i % PACKET_SIZE == 0 || i == image.length) {
						tmp[3] = (byte)packetNum++;
						par.udpSocket.send(new DatagramPacket(tmp, j, StudentController.serverAddress, StudentController.udpPort));
						j = 5;
					}
				}
				currImg = (currImg + 1) % MAX_IMAGES;
				Thread.sleep(1);
			}
			catch(Exception ex) {}
		}
	}
}
