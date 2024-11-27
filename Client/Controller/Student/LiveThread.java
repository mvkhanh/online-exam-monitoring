package pbl4.Client.Controller.Student;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.opencv.core.Size;

import pbl4.Client.Constant;

public class LiveThread extends Thread {
	private StudentController par;
	private int msgNum = 0;
	private boolean isFocusScreen;
	private boolean isFocusCam;
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	public LiveThread(StudentController par) {
		this.par = par;
	}

	public void run() {
		LocalTime start = LocalTime.now();
		while (par.running) {
			try (Socket tcpSocket = new Socket(Constant.serverAddress, Constant.tcpPort);
					DataInputStream dis = new DataInputStream(tcpSocket.getInputStream());
					DataOutputStream dos = new DataOutputStream(tcpSocket.getOutputStream())) {
				String keyLogMsg = checkForKeyLog(start);
				if (keyLogMsg != null) {
					start = LocalTime.now();
					dos.writeUTF(keyLogMsg);
				}
				dos.writeUTF("S" + par.roomId + " " + par.id + " " + msgNum);

				String msg = "";
				while (!(msg = dis.readUTF()).equals("E")) {
					if (msg.startsWith("H1") && !isFocusScreen) {
						if (isFocusCam) {
							isFocusCam = false;
							par.camDim = new Size(Constant.NORMAL_WIDTH, Constant.NORMAL_HEIGHT);
						}
						isFocusScreen = true;
						par.handleFocus(Constant.FOCUS_WIDTH, Constant.FOCUS_HEIGHT);
					} else if (msg.startsWith("H2") && !isFocusCam) {
						if (isFocusScreen) {
							isFocusScreen = false;
							par.handleFocus(Constant.NORMAL_WIDTH, Constant.NORMAL_HEIGHT);
						}
						isFocusCam = true;
						par.camDim = new Size(Constant.FOCUS_WIDTH, Constant.FOCUS_HEIGHT);
					} else if (msg.startsWith("~H")) {
						if (isFocusScreen) {
							isFocusScreen = false;
							par.handleFocus(Constant.NORMAL_WIDTH, Constant.NORMAL_HEIGHT);
						}
						if (isFocusCam) {
							isFocusCam = false;
							par.camDim = new Size(Constant.NORMAL_WIDTH, Constant.NORMAL_HEIGHT);
						}

					} else if (msg.startsWith("M")) {
						par.addText(msg.substring(1));
						msgNum++;
					} else if (msg.startsWith("Q")) {
						par.running = false;
						break;
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		par.endStream();
	}

	private String checkForKeyLog(LocalTime start) {
		long diff = Duration.between(start, LocalTime.now()).toMillis();
		if (diff / Constant.KEYBOARD_DURATION > 0 && diff % Constant.KEYBOARD_DURATION <= 5000) {
			if (!par.currKeys.equals("")) {
				String tmp = "!" + par.roomId + " " + par.id + " " + start.format(formatter) + " " + LocalTime.now().format(formatter) + " ";
				synchronized (par.currKeys) {
					tmp += par.currKeys;
					par.currKeys = "";
				}
				return tmp + "\n";
			}
		}
		return null;
	}
}
