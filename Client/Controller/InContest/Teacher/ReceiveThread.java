package pbl4.Client.Controller.InContest.Teacher;

import java.io.IOException;
import java.net.DatagramPacket;

import pbl4.Client.DTO.InContest.Teacher.Packet;

public class ReceiveThread extends Thread {
	private TeacherController par;
	private boolean isProcessing = false;

	public ReceiveThread(TeacherController par) {
		this.par = par;
	}

	public void run() {
		while (par.running) {
			try {
				DatagramPacket receivePacket = new DatagramPacket(new byte[1105], 1105);
				par.udpSocket.receive(receivePacket);
				par.packets.add(new Packet(receivePacket.getLength(), receivePacket.getData()));
				if (!isProcessing) {
					for (int i = 0; i < TeacherController.PROCESS_THREADS; i++)
						new ProcessThread(par).start();
					isProcessing = true;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
