package PBL4.Teacher.Controller;

import java.io.IOException;
import java.net.DatagramPacket;

import PBL4.Teacher.Model.Packet;
import PBL4.Teacher.View.Teacher;

public class ReceiveThread extends Thread {
	private Teacher par;
	private boolean isProcessing = false;

	public ReceiveThread(Teacher par) {
		this.par = par;
	}

	public void run() {
		while (true) {
			try {
				DatagramPacket receivePacket = new DatagramPacket(new byte[1104], 1104);
				par.udpSocket.receive(receivePacket);
				par.packets.add(new Packet(receivePacket.getLength(), receivePacket.getData()));
				if (!isProcessing) {
					for (int i = 0; i < Teacher.PROCESS_THREADS; i++)
						new ProcessThread(par).start();
					isProcessing = true;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
