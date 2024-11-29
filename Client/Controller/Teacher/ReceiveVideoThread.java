package pbl4.Client.Controller.Teacher;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import javax.swing.JLabel;

public class ReceiveVideoThread extends Thread {
	int totalFrame;
	DataInputStream dis;
	List<byte[]> videoData;
	DataOutputStream dos;
	Socket soc;

	public ReceiveVideoThread(int totalFrame, DataInputStream dis, List<byte[]> videoData, Socket soc, DataOutputStream dos, JLabel label) {
		super();
		this.totalFrame = totalFrame;
		this.dis = dis;
		this.videoData = videoData;
		this.soc = soc;
		this.dos = dos;
		this.label = label;
	}

	JLabel label;
	
	@Override
	public void run() {
		for (int i = 0; i < totalFrame; i++) {
			try {
				int bytesLength = dis.readInt();
				if (bytesLength > 0) {
					byte[] receiveImageBytes = new byte[bytesLength];

					dis.readFully(receiveImageBytes);

					videoData.add(receiveImageBytes);
					
//					System.out.println(label.getText());
					
					String labelTxt[] = label.getText().split("\\|");
					
					label.setText(labelTxt[0] + "| " + "Tiến độ nhận dữ liệu video từ Server: " + (i+1)/totalFrame*100 + "% |" + labelTxt[2]);
				}
			} catch (IOException ex) {
				break;
			}
		}
		try {
			dis.close();
			dos.close();
			soc.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
