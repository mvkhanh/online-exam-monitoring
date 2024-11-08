package PBL4.Student.Controller;

import java.io.ByteArrayOutputStream;
import java.net.DatagramPacket;

import javax.imageio.ImageIO;

import PBL4.Student.View.Student2;

public class SendThread extends Thread{
	private Student2 par;
	public static final int PACKET_SIZE = 1100;
	private byte[] tmp = new byte[PACKET_SIZE + 4];
	public static final int MAX_IMAGES = 4;
	
	public SendThread(Student2 par) {
		this.par = par;
		tmp[0] = (byte)par.roomId;
	}
	
	public void run() {
		int currImg = 0;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while (true) {
			try {
				baos.reset();
				ImageIO.write(par.img, "jpg", baos);
				byte[] image = baos.toByteArray();
				int size = (image.length + PACKET_SIZE - 1) / PACKET_SIZE;
				int j = 4, packetNum = 0;
				tmp[1] = (byte)size;
				tmp[2] = (byte)currImg;
				for(int i = 1; i <= image.length; i++) {
					tmp[j++] = image[i - 1];
					if(i % PACKET_SIZE == 0 || i == image.length) {
						tmp[3] = (byte)packetNum++;
						par.udpSocket.send(new DatagramPacket(tmp, j, Student2.serverAddress, Student2.udpPort));
						j = 4;
					}
				}
				currImg = (currImg + 1) % MAX_IMAGES;
				Thread.sleep(1);
			}
			catch(Exception ex) {}
		}
	}
}
