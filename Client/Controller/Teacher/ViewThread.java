package pbl4.Client.Controller.Teacher;

import java.util.Map;

import pbl4.Client.Constant;
import pbl4.Client.DTO.InContest.Teacher.ImageModel;
import pbl4.Client.DTO.InContest.Teacher.Packet;

public class ViewThread extends Thread {
	public static TeacherController par;

	public ViewThread(TeacherController par) {
		ViewThread.par = par;
	}

	public void run() {
		while (par.running) {
			try {
				for(int i = 0; i < par.viewList.size(); i++) {
					if(par.viewList.get(i) != null) {
						ImageModel tmp = par.viewList.get(i);
						par.viewList.set(i, null);
						byte[] completeData = assemblePackets(tmp.getData());
						par.setImage(completeData, i);
					}
				}
				try {
					Thread.sleep(0, 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} catch (NullPointerException e) {

			}
		}
	}

	public static byte[] assemblePackets(Map<Integer, Packet> packets) {
		int totalLength = 0, header = Constant.PACKET_SIZE - Constant.IMAGE_SEGMENT;
		for (Packet packet : packets.values()) {
			if (packet != null)
				totalLength += packet.getLength() - header;
		}
		byte[] completeData = new byte[totalLength];
		int currentPos = 0;
		for (int i = 0; i < packets.size(); i++) {
			Packet packet = packets.get(i);
			if (packet != null) {
				
				System.arraycopy(packet.getData(), header, completeData, currentPos, packet.getLength() - header);
				currentPos += packet.getLength() - header;
			}
		}
		return completeData;
	}
}
