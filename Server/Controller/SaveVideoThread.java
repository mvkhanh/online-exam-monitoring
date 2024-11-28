package pbl4.Server.Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Queue;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import pbl4.Server.Constant;

public class SaveVideoThread extends Thread {

	Queue<byte[]> imageBytes;
	int length; // số lượng ảnh
	int typeVideo;
	String participant_id;
	Size size;
	int fps;

	public SaveVideoThread(Queue<byte[]> imageBytes, int length, int typeVideo, String participant_id, Size size) {
		super();
		this.imageBytes = imageBytes;
		this.length = length;
		this.typeVideo = typeVideo;
		this.participant_id = participant_id;
		this.size = size;
		if (typeVideo == 1)
			this.fps = 30;
		else
			this.fps = 14;
	}

	@Override
	public void run() {
		try {
			processFunc();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	void processFunc() throws Exception {
		String folderPath = Constant.FILE_LOCATION + File.separator + "Record" + File.separator + participant_id
				+ File.separator;
		File folder = new File(folderPath);
		if (!folder.exists() && !folder.mkdirs()) {
			System.err.println("Không thể tạo thư mục dự án: " + folderPath);
			return;
		}

		// Đường dẫn lưu các ảnh tạm
		String tempDir = folderPath + "temp_images" + typeVideo + File.separator;
		File dir = new File(tempDir);
		if (!dir.exists())
			dir.mkdirs();

		// Ghi từng mảng byte ra file ảnh (định dạng JPG)
		for (int i = 0; i < length; i++) {
			if (imageBytes.size() > 0) {
				String outputPath = tempDir + String.format("frame_%05d.jpg", i); // Đổi thành JPG
				try (FileOutputStream fos = new FileOutputStream(outputPath)) {
					fos.write(imageBytes.poll());
				}
			} else {
				--i;
				Thread.sleep(1);
			}
		}

		// Đường dẫn đến video tạm và video đầu ra
		String tempVideoPath = folderPath + "temp_video" + typeVideo + ".mp4"; // Video mới tạo
		String finalVideoPath = folderPath + "output" + typeVideo + ".mp4"; // Video cuối cùng

		// Tạo video từ các ảnh tạm
		System.out.println("Creating video...");
		String ffmpegCommand = String.format(
				"ffmpeg -y -framerate " + fps
						+ " -i %sframe_%%05d.jpg -c:v libx264 -pix_fmt yuv420p -movflags +faststart %s",
				tempDir, tempVideoPath);
		Process process = Runtime.getRuntime().exec(ffmpegCommand);

//		 In đầu ra lỗi từ FFmpeg để debug nếu cần
		try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
			String line;
			while ((line = errorReader.readLine()) != null) {
				System.err.println(line);
			}
		}

		process.waitFor();

		// Kiểm tra xem video tạm có được tạo thành công hay không
		File tempVideoFile = new File(tempVideoPath);
		if (!tempVideoFile.exists()) {
			System.err.println("Failed to create temporary video.");
			return;
		}

		// Kiểm tra video cuối cùng đã tồn tại hay chưa
		File finalVideoFile = new File(finalVideoPath);
		if (finalVideoFile.exists()) {
			// Tạo file danh sách video cần ghép
			String concatListPath = "concat_list" + participant_id + "_" + typeVideo + ".txt";
			try (PrintWriter writer = new PrintWriter(concatListPath)) {
				writer.println("file '" + finalVideoPath.replace("\\", "/") + "'");
				writer.println("file '" + tempVideoPath.replace("\\", "/") + "'");
			}

			// Ghép video
			System.out.println("Appending video...");
			String concatCommand = String.format("ffmpeg -y -f concat -safe 0 -i %s -c copy %s", concatListPath,
					folderPath + "temp_output" + typeVideo + ".mp4");
			Process concatProcess = Runtime.getRuntime().exec(concatCommand);

			// In log lỗi FFmpeg
			try (BufferedReader errorReader = new BufferedReader(
					new InputStreamReader(concatProcess.getErrorStream()))) {
				String line;
				while ((line = errorReader.readLine()) != null) {
					System.err.println(line);
				}
			}

			concatProcess.waitFor();

			// Xóa video cũ và thay thế bằng video mới
			new File(finalVideoPath).delete();
			new File(folderPath + "temp_output" + typeVideo + ".mp4").renameTo(finalVideoFile);
			new File(concatListPath).delete();
		} else {
			// Nếu video cuối cùng chưa tồn tại, chỉ cần đổi tên video tạm
			tempVideoFile.renameTo(finalVideoFile);
		}

		// Xóa các file ảnh tạm
		for (File file : dir.listFiles()) {
			file.delete();
		}
		dir.delete();

		// Xóa video tạm
		tempVideoFile.delete();

		System.out.println("Video updated successfully: " + finalVideoPath);
	}
}
