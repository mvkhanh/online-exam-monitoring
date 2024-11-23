//package pbl4.Client.Controller.InContest.Student;
//
//import java.net.DatagramPacket;
//
//import org.opencv.core.Mat;
//import org.opencv.core.MatOfByte;
//import org.opencv.imgcodecs.Imgcodecs;
//
//public class SendThread2 extends Thread {
//	private StudentController par;
//	public static final int PACKET_SIZE = 1100;
//	private byte[] tmp = new byte[PACKET_SIZE + 5];
//	public static final int MAX_IMAGES = 8;
//
//	public SendThread2(StudentController par) {
//		this.par = par;
//		tmp[0] = Byte.valueOf(par.roomId);
//		tmp[4] = 0;
//	}
//
//	public void run() {
//		int currImg = 0;
//		while (par.running) {
//			try {
//				Mat matTemp = par.camImg;
//				MatOfByte buffer = new MatOfByte();
//				Imgcodecs.imencode(".jpg", matTemp, buffer);
//				byte[] image = buffer.toArray();
//				int size = (image.length + PACKET_SIZE - 1) / PACKET_SIZE;
//				int j = 5, packetNum = 0;
//				tmp[1] = (byte) size;
//				tmp[2] = (byte) currImg;
//				for (int i = 1; i <= image.length; i++) {
//					tmp[j++] = image[i - 1];
//					if (i % PACKET_SIZE == 0 || i == image.length) {
//						tmp[3] = (byte) packetNum++;
//						par.udpSocket.send(new DatagramPacket(tmp, j, StudentController.serverAddress, StudentController.udpPort));
//						j = 5;
//					}
//				}
//				currImg = (currImg + 1) % MAX_IMAGES;
//				Thread.sleep(1);
//			} catch (Exception ex) {
//			}
//		}
//	}
//}
//
//
//
//




