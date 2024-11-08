package PBL4.Teacher.Controller;

import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import PBL4.Teacher.Model.ImageModel;
import PBL4.Teacher.Model.Packet;
import PBL4.Teacher.View.Teacher;

public class ViewThread extends Thread {
	private Teacher par;

	public ViewThread(Teacher par) {
		this.par = par;
	}

	public void run() {
		while (true) {
			if (par.currImage == null) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				continue;
			}
			ImageModel tmp = par.currImage;
			par.currImage = null;
			int studentNum = tmp.getStudentNum();
			while (studentNum >= par.cameraScreens.size())
				par.addCameraScreen();
			byte[] completeData = assemblePackets(tmp.getData());
			JLabel screen = par.cameraScreens.get(studentNum);
			screen.setIcon(new ImageIcon(completeData));
		}
	}

	public static byte[] assemblePackets(Map<Integer, Packet> packets) {
		int totalLength = 0;
		for (Packet packet : packets.values()) {
			if (packet != null)
				totalLength += packet.getLength() - 4;
		}
		byte[] completeData = new byte[totalLength];
		int currentPos = 0;
		for (int i = 0; i < packets.size(); i++) {
			Packet packet = packets.get(i);
			if (packet != null) {
				System.arraycopy(packet.getData(), 4, completeData, currentPos, packet.getLength() - 4);
				currentPos += packet.getLength() - 4;
			}
		}
		return completeData;
	}
}
