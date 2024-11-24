package pbl4.Client.Controller.Teacher;

import java.io.IOException;
import java.net.DatagramPacket;

import pbl4.Client.Constant;
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
				DatagramPacket receivePacket = new DatagramPacket(new byte[Constant.PACKET_SIZE], Constant.PACKET_SIZE);
				par.udpSocket.receive(receivePacket);
				par.packets.add(new Packet(receivePacket.getLength(), receivePacket.getData()));
				if (!isProcessing) {
					new ProcessThread(par, true).start();
					for (int i = 0; i < Constant.PROCESS_THREADS - 1; i++)
						new ProcessThread(par, false).start();
					isProcessing = true;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
