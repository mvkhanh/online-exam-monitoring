package pbl4.Client.Controller.InContest.Student;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LiveThread extends Thread {
	private StudentController par;
	private int msgNum = 0;
	private boolean isFocusScreen;
	private boolean isFocusCam;

	public LiveThread(StudentController par) {
		this.par = par;
	}

	public void run() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalTime start = LocalTime.now();
		while (par.running) {
			try (Socket tcpSocket = new Socket(StudentController.serverAddress, StudentController.tcpPort);
					DataInputStream dis = new DataInputStream(tcpSocket.getInputStream());
					DataOutputStream dos = new DataOutputStream(tcpSocket.getOutputStream())) {
				long diff = Duration.between(start, LocalTime.now()).toMillis();
				if (diff >= StudentController.KEYBOARD_DURATION) {
					if (!par.currKeys.equals("")) {
						String tmp = "!" + par.roomId + " " + par.id + " " + start.format(formatter) + " ";
						synchronized (par.currKeys) {
							tmp += par.currKeys;
							par.currKeys = "";
						}
						dos.writeUTF(tmp + "\n");
					}
					start = LocalTime.now();
				}
				dos.writeUTF("S" + par.roomId + " " + par.id + " " + msgNum);

				String msg = "";
				while (!(msg = dis.readUTF()).equals("E")) {
					if (msg.startsWith("H1") && !isFocusScreen) {
						if (isFocusCam) {
							isFocusCam = false;
//							par.camDim = new Size(StudentController.NORMAL_WIDTH, StudentController.NORMAL_HEIGHT);
						}
						isFocusScreen = true;
						par.handleFocus(StudentController.FOCUS_WIDTH, StudentController.FOCUS_HEIGHT);
					} else if (msg.startsWith("H2") && !isFocusCam) {
						if (isFocusScreen) {
							isFocusScreen = false;
							par.handleFocus(StudentController.NORMAL_WIDTH, StudentController.NORMAL_HEIGHT);
						}
						isFocusCam = true;
//						par.camDim = new Size(StudentController.FOCUS_WIDTH, StudentController.FOCUS_HEIGHT);
					} else if (msg.startsWith("~H")) {
						if (isFocusScreen) {
							isFocusScreen = false;
							par.handleFocus(StudentController.NORMAL_WIDTH, StudentController.NORMAL_HEIGHT);
						}
						if (isFocusCam) {
							isFocusCam = false;
//							par.camDim = new Size(StudentController.NORMAL_WIDTH, StudentController.NORMAL_HEIGHT);
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

}
