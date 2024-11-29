package pbl4.Client.Controller.Teacher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.JLabel;

import pbl4.Client.DTO.OutContest.VideoModel;

public class DownloadVideoThread extends Thread {

	public static void saveImage(byte[] imageBytes, String filePath) throws IOException {
		File file = new File(filePath);
		try (FileOutputStream fos = new FileOutputStream(file)) {
			fos.write(imageBytes);
		}
	}

	// Hàm parseDuration để trích xuất tổng thời lượng video
	private static double parseDuration(String line) {
	    String durationRegex = "Duration: (\\d+):(\\d+):(\\d+\\.\\d+)";
	    Pattern pattern = Pattern.compile(durationRegex);
	    Matcher matcher = pattern.matcher(line);
	    if (matcher.find()) {
	        int hours = Integer.parseInt(matcher.group(1));
	        int minutes = Integer.parseInt(matcher.group(2));
	        double seconds = Double.parseDouble(matcher.group(3));
	        return hours * 3600 + minutes * 60 + seconds;
	    }
	    return 0;
	}

	// Hàm parseTime để trích xuất thời gian xử lý
	private static double parseTime(String line) {
	    String timeRegex = "time=(\\d+):(\\d+):(\\d+\\.\\d+)";
	    Pattern pattern = Pattern.compile(timeRegex);
	    Matcher matcher = pattern.matcher(line);
	    if (matcher.find()) {
	        int hours = Integer.parseInt(matcher.group(1));
	        int minutes = Integer.parseInt(matcher.group(2));
	        double seconds = Double.parseDouble(matcher.group(3));
	        return hours * 3600 + minutes * 60 + seconds;
	    }
	    return 0;
	}

	public static void createVideo(String imagesDirectory, String outputVideoPath, int framerate, JLabel label) throws IOException {
		// Câu lệnh FFmpeg
		String ffmpegCommand = String.format(
				"ffmpeg -framerate %d -i %s/frame%%d.jpg -c:v libx264 -pix_fmt yuv420p -movflags +faststart %s",
				framerate, imagesDirectory, outputVideoPath);

		Process process = Runtime.getRuntime().exec(ffmpegCommand);

		// Khởi tạo biến để lưu tiến độ
		double totalDuration = 0; // Tổng thời lượng video (giây)

		try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
			String line;
			while ((line = errorReader.readLine()) != null) {
				System.err.println(line);

				if (line.contains("Duration:")) {
	                totalDuration = parseDuration(line);
	            }
				
				if (line.contains("frame=") && line.contains("time=")) {
	                double currentTime = parseTime(line);
	                if (totalDuration > 0) {
	                    int progress = (int) Math.round((currentTime / totalDuration) * 100);
	                    if(progress == 99) progress = 100;
	                    String labelTxt[] = label.getText().split("\\|");
						label.setText(
								labelTxt[0] + "|" + labelTxt[1] + "| " + "Tiến độ tải xuống video: " + progress + "%");
					
	                }
	            }
			}
		}

	}

	public VideoModel videoModel;

	public DownloadVideoThread(VideoModel videoModel) {
		this.videoModel = videoModel;
	}

	@Override
	public void run() {

		String folderPathChoosen = chooseFolderPath();
		
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
			outputVideoPath = folderPath + "output" + i + ".mp4";
			outputVideoFile = new File(outputVideoPath);
		}
		try {
			createVideo(imagesDirectory, outputVideoPath, videoModel.fps, videoModel.label);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		for (File file : imagesDirectoryFile.listFiles()) {
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
