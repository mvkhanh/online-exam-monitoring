package pbl4.Client.Controller.Teacher;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
import pbl4.Client.View.Home;
import pbl4.Client.View.TeacherDashboard;
import pbl4.Client.View.VideoPlayer;
import pbl4.Client.View.KeyLog.KeyLog;

public class DashboardController {
	public User user;
	private DatagramSocket udpStreamSocket;
	public TeacherDashboard view;

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

	public void getVideoData(int typeVideo) {

		VideoModel tmp = new VideoModel();

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
			new receiveVideo(tmp.totalFrame, dis, tmp.videoData, soc, dos).start();
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
			new downloadThread(camera).start();
		}
		else {
			if(screen == null)
				getVideoData(typeVideo);
			new downloadThread(screen).start();
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

class receiveVideo extends Thread {
	int totalFrame;
	DataInputStream dis;
	List<byte[]> videoData;
	DataOutputStream dos;
	Socket soc;

	public receiveVideo(int totalFrame, DataInputStream dis, List<byte[]> videoData, Socket soc, DataOutputStream dos) {
		super();
		this.totalFrame = totalFrame;
		this.dis = dis;
		this.videoData = videoData;
		this.soc = soc;
		this.dos = dos;
	}

	@Override
	public void run() {
		for (int i = 0; i < totalFrame; i++) {
			try {
				int bytesLength = dis.readInt();
				if (bytesLength > 0) {
					byte[] receiveImageBytes = new byte[bytesLength];

					dis.readFully(receiveImageBytes);

					videoData.add(receiveImageBytes);
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

class downloadThread extends Thread {

	public static void saveImage(byte[] imageBytes, String filePath) throws IOException {
		File file = new File(filePath);
		try (FileOutputStream fos = new FileOutputStream(file)) {
			fos.write(imageBytes);
		}
	}

	public static void createVideo(String imagesDirectory, String outputVideoPath, int framerate)
			throws IOException {
		// Câu lệnh FFmpeg
		String ffmpegCommand = String.format(
				"ffmpeg -framerate %d -i %s/frame%%d.jpg -c:v libx264 -pix_fmt yuv420p -movflags +faststart %s",
				framerate, imagesDirectory, outputVideoPath);

		Process process = Runtime.getRuntime().exec(ffmpegCommand);

		try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
			String line;
			while ((line = errorReader.readLine()) != null) {
				System.err.println(line);
			}
		}

		try {
			int exitCode = process.waitFor();
			if (exitCode == 0) {
				JOptionPane.showMessageDialog(null, "Video đã được tạo thành công: " + outputVideoPath,
						"Thành công", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Lỗi khi tạo video, mã thoát: " + exitCode, "Lỗi",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new IOException("Quá trình bị gián đoạn", e);
		}
	}

	public VideoModel videoModel;
	
	public downloadThread(VideoModel videoModel) {
		this.videoModel = videoModel;
	}

	@Override
	public void run() {

		String folderPathChoosen = chooseFolderPath();

		if (folderPathChoosen != null) {
			JOptionPane.showMessageDialog(null, "Video đang được tải tại thư mục " + folderPathChoosen,
					"Thông báo tải video", JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			JOptionPane.showMessageDialog(null, "Bạn chưa chọn thư mục",
					"Thông báo tải video", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		String folderPath = folderPathChoosen + File.separator;

		// thư mục lưu ảnh tạm thời
		String imagesDirectory = folderPath + "images";
		File imagesDirectoryFile = new File(imagesDirectory);
		for (int i = 0; imagesDirectoryFile.exists(); i++) {
			imagesDirectory = folderPath + "images" + i;
			imagesDirectoryFile = new File(imagesDirectory);
		}
		imagesDirectoryFile.mkdir();

		// lưu từng ảnh
		for (int i = 0; i < videoModel.totalFrame; i++) {
			try {
				if (i < videoModel.videoData.size()) {
					String filePath = String.format("%s/frame%d.jpg", imagesDirectory, i);
					saveImage(videoModel.videoData.get(i), filePath);
				} else {
					Thread.sleep(1);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		// Tạo video từ ảnh
		String outputVideoPath = folderPath + "output.mp4";
		File outputVideoFile = new File(outputVideoPath);
		for (int i = 0; outputVideoFile.exists(); i++) {
			outputVideoPath = folderPath + "output.mp4" + i;
			outputVideoFile = new File(outputVideoPath);
		}
		try {
			createVideo(imagesDirectory, outputVideoPath, videoModel.fps);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		for(File file : imagesDirectoryFile.listFiles()) {
			file.delete();
		}
		
		imagesDirectoryFile.delete();
	}
	
	public static String chooseFolderPath() {
		// Tạo JFileChooser
		JFileChooser chooser = new JFileChooser();

		// Chỉ cho phép chọn thư mục
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		// Hiển thị hộp thoại chọn thư mục
		int result = chooser.showOpenDialog(null);

		if (result == JFileChooser.APPROVE_OPTION) {
			// Lấy thư mục đã chọn
			File selectedDirectory = chooser.getSelectedFile();
			return selectedDirectory.getAbsolutePath();

		} else {
			return null;
		}
	}
}
