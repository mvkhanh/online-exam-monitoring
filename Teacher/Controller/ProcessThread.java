package PBL4.Teacher.Controller;

import java.util.ArrayList;

import PBL4.Teacher.Model.ImageModel;
import PBL4.Teacher.Model.Packet;
import PBL4.Teacher.View.Teacher;

public class ProcessThread extends Thread{
	public static Teacher par;
	public static boolean isProcessing;
	public ProcessThread(Teacher par) {
		ProcessThread.par = par;
	}
	
	public void run() {
		while (true) {
			Packet packet = par.packets.poll();
			if (packet == null) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				continue;
			}
			byte[] receiveData = packet.getData();
			int studentNum = receiveData[0];
			int total = receiveData[1];
			int imageNum = receiveData[2];
			int packetNum = receiveData[3];
			ArrayList<ImageModel> studentImages = par.images.get(studentNum);
			if (studentImages == null) {
				studentImages = new ArrayList<>();
				while (studentImages.size() <= Teacher.MAX_IMAGES)
					studentImages.add(null);
				ImageModel image = new ImageModel(total, System.currentTimeMillis());
				image.getData().put(packetNum, packet);
				studentImages.set(imageNum, image);
				par.images.put(studentNum, studentImages);
			} else {
				ImageModel image = studentImages.get(imageNum);
				if (image == null) {
					image = new ImageModel();
					image.setTotal(total);
					studentImages.set(imageNum, image);
				}
				image.setTime(System.currentTimeMillis());
				image.getData().put(packetNum, packet);
			}
			if(!isProcessing) {
				new CheckThread(par).start();
				for(int i = 0; i < Teacher.VIEW_THREADS; i++)
					new ViewThread(par).start();
			}
		}
	}
}
