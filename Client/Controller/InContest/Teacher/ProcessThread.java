package pbl4.Client.Controller.InContest.Teacher;

import java.util.ArrayList;

import pbl4.Client.DTO.InContest.Teacher.ImageModel;
import pbl4.Client.DTO.InContest.Teacher.Packet;

public class ProcessThread extends Thread{
	public static TeacherController par;
	public static boolean isProcessing;
	public ProcessThread(TeacherController par) {
		ProcessThread.par = par;
		isProcessing = false;
	}
	
	public void run() {
		while (true) {
			Packet packet = par.packets.poll();
			if (packet == null) {
				try {
					Thread.sleep(0, 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;
			}
			byte[] receiveData = packet.getData();
			int studentNum = receiveData[4] == 1 ? receiveData[0] * 2 : receiveData[0] * 2 + 1;
			int total = receiveData[1];
			int imageNum = receiveData[2];
			int packetNum = receiveData[3];
			ArrayList<ImageModel> studentImages = par.images.get(studentNum);
			if (studentImages == null) {
				studentImages = new ArrayList<>();
				int max_size = receiveData[4] == 1 ? TeacherController.MAX_IMAGES_SCREEN : TeacherController.MAX_IMAGES_CAM;
				while (studentImages.size() <= max_size)
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
				isProcessing = true;
				Thread cThread = new CheckThread(par);
				par.threads.add(cThread);
				cThread.start();
				for(int i = 0; i < TeacherController.VIEW_THREADS; i++) {
					Thread vThread = new ViewThread(par);
					par.threads.add(vThread);
					vThread.start();
				}
			}
		}
	}
}
