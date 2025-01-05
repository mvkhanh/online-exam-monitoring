package pbl4.Client.Controller.Teacher;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.net.DatagramSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import pbl4.Client.Constant;
import pbl4.Client.DTO.OutContest.Participant;
import pbl4.Client.DTO.OutContest.Test;
import pbl4.Client.DTO.OutContest.User;
import pbl4.Client.DTO.OutContest.VideoModel;
import pbl4.Client.Utils.Service;
import pbl4.Client.View.DownloadProgress;
import pbl4.Client.View.Home;
import pbl4.Client.View.TeacherDashboard;
import pbl4.Client.View.VideoPlayer;
import pbl4.Client.View.KeyLog.KeyLog;

public class DashboardController {
	public User user;
	private DatagramSocket udpStreamSocket;
	public TeacherDashboard view;

	public DownloadProgress downloadProgress = new DownloadProgress();
	
	public VideoModel camera = null;
	public VideoModel screen = null;

	public DashboardController(User user) {
		this.user = user;
		try {
			this.udpStreamSocket = new DatagramSocket();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		this.view = new TeacherDashboard(this);
	}

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public void viewDownloadProgress() {
		downloadProgress.setVisible(true);
	}
	
	public void getVideoData(int typeVideo) {

		VideoModel tmp = new VideoModel();

		tmp.name = "Tải xuống bản ghi " + (typeVideo == 1 ? "camera" : "màn hình") + " của học sinh '" + view.participant_name + "' có id là '" + view.participant_id + "' trong contest '" + view.ptcp_model.test_name + "'";
		tmp.label.setText(tmp.name + " | Tiến độ nhận dữ liệu video từ Server: 0% | Tiến độ tải xuống video: 0%");
		
		if (typeVideo == 1) {
			camera = tmp;
		} else {
			screen = tmp;
		}

		tmp.videoData = new ArrayList<byte[]>();

		String sendMsg = "G," + view.participant_id + "," + typeVideo;
		Socket soc;
		DataInputStream dis;
		DataOutputStream dos;

		try {
			soc = new Socket(Constant.serverAddress, Constant.tcpPort);
			dis = new DataInputStream(soc.getInputStream());
			dos = new DataOutputStream(soc.getOutputStream());
			dos.writeUTF(sendMsg);

			if (!dis.readBoolean()) {
				System.out.println("Khong tim thay video");
				soc.close();
				dis.close();
				dos.close();
				return;
			}

			tmp.totalFrame = dis.readInt();
			tmp.fps = dis.readInt();

//			new VideoPlayer(videoData, totalFrame, fps, this);
			new ReceiveVideoThread(tmp.totalFrame, dis, tmp.videoData, soc, dos, tmp.label).start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void getVideoView(int typeVideo) {
		if (typeVideo == 1) {
			if(camera == null)
				getVideoData(typeVideo);
			
			view.setVisible(false);
			
			new VideoPlayer(camera, this);
		} else {
			if(screen == null)
				getVideoData(typeVideo);
			
			view.setVisible(false);
			
			new VideoPlayer(screen, this);
		}
	}
	
	public void downloadVideo(int typeVideo) {
		if(typeVideo == 1) {
			if(camera == null)
				getVideoData(typeVideo);
			else
				camera.label.setText(camera.name + " | Tiến độ nhận dữ liệu video từ Server: 100% | Tiến độ tải xuống video: 0%");
			downloadProgress.addProgress(camera.label);
			new DownloadVideoThread(camera).start();
		}
		else {
			if(screen == null)
				getVideoData(typeVideo);
			else
				screen.label.setText(screen.name + " | Tiến độ nhận dữ liệu video từ Server: 100% | Tiến độ tải xuống video: 0%");
			downloadProgress.addProgress(screen.label);
			new DownloadVideoThread(screen).start();
		}
	}

	public List<Test> getListLSCT() {
		List<Test> listData = new ArrayList<Test>();
		String sendMsg = "E," + user.getId();
		try (Socket soc = new Socket(Constant.serverAddress, Constant.tcpPort);
				DataInputStream dis = new DataInputStream(soc.getInputStream());
				DataOutputStream dos = new DataOutputStream(soc.getOutputStream())) {
			dos.writeUTF(sendMsg);
			String receiveMsg = dis.readUTF();
			if (!"0".equals(receiveMsg)) {
				String[] splitMsg = receiveMsg.split("\\|");
				for (String s : splitMsg) {
					String[] data = s.split(",");
					Test t = new Test(Integer.parseInt(data[0]), user.getId(), data[1], dateFormat.parse(data[2]));
					listData.add(t);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return listData;
	}

	public void batdau(String tencuocthi) {
		try (Socket tcpStreamSocket = new Socket(Constant.serverAddress, Constant.tcpPort);
				DataOutputStream dos = new DataOutputStream(tcpStreamSocket.getOutputStream());
				DataInputStream dis = new DataInputStream(tcpStreamSocket.getInputStream())) {
			String msg = "C" + udpStreamSocket.getLocalPort() + " " + user.getId() + " " + tencuocthi, roomId = null;
			dos.writeUTF(msg);
			roomId = dis.readUTF();
			view.setVisible(false);
			new pbl4.Client.Controller.Teacher.TeacherController(this, tencuocthi, roomId, udpStreamSocket);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void capnhatmatkhau(String matkhau) {
		if (matkhau.equals("")) {
			if (Service.confirmAlert("Bạn chắc không", "Thông báo") == 0) {
				String msg = "U," + user.getId() + "," + matkhau;
				try (Socket tcpStreamSocket = new Socket(Constant.serverAddress, Constant.tcpPort);
						DataOutputStream dos = new DataOutputStream(tcpStreamSocket.getOutputStream());
						DataInputStream dis = new DataInputStream(tcpStreamSocket.getInputStream())) {
					dos.writeUTF(msg);
					Service.showAlert("Bạn đã đổi mật khẩu thành công", "Thông báo");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} else {
			Service.showAlert("Bạn chưa nhập mật khẩu mới", "Thông báo lỗi");
		}
	}

	public void logout() {
		view.dispose();
		new Home().setVisible(true);
	}

	public List<Participant> getListParticipant(int test_id) {
		List<Participant> listData = new ArrayList<>();
		String sendMsg = "P," + test_id;
		try (Socket soc = new Socket(Constant.serverAddress, Constant.tcpPort);
				DataInputStream dis = new DataInputStream(soc.getInputStream());
				DataOutputStream dos = new DataOutputStream(soc.getOutputStream())) {
			dos.writeUTF(sendMsg);
			String receiveMsg = dis.readUTF();
			if (!"0".equals(receiveMsg)) {
				String[] splitMsg = receiveMsg.split("\\|");
				for (String s : splitMsg) {
					String[] data = s.split(",");
					Participant t = new Participant(Integer.parseInt(data[0]), test_id, data[1]);
					listData.add(t);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return listData;
	}

	public void showKeys(Integer participant_id) {
		if (participant_id == null)
			return;
		String msg = "K" + participant_id;
		try (Socket soc = new Socket(Constant.serverAddress, Constant.tcpPort);
				DataInputStream dis = new DataInputStream(soc.getInputStream());
				DataOutputStream dos = new DataOutputStream(soc.getOutputStream())) {
			dos.writeUTF(msg);
			String receiveMsg = dis.readUTF();
			new KeyLog(receiveMsg);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
