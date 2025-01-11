package pbl4.Server.Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Queue;

import pbl4.Server.Constant;

public class SaveVideoThread extends Thread {

	Queue<byte[]> imageBytes;
	int length; // số lượng ảnh
	int typeVideo;
	String participant_id;
	int width;
	int height;
	int fps;

	public SaveVideoThread(Queue<byte[]> imageBytes, int length, int typeVideo, String participant_id, int width,
			int height) {
		super();
		this.imageBytes = imageBytes;
		this.length = length;
		this.typeVideo = typeVideo;
		this.participant_id = participant_id;
		this.width = width;
		this.height = height;
		if (typeVideo == 0)
			this.fps = 30;
		else
			this.fps = 18;
	}

	@Override
	public void run() {
//		try {
//			String folderPath = Constant.FILE_LOCATION + File.separator + "Record-"
//					+ (typeVideo == 1 ? "Screen" : "Cam") + File.separator + participant_id + File.separator;
//			File folder = new File(folderPath);
//			if (!folder.exists() && !folder.mkdirs()) {
//				System.err.println("Không thể tạo thư mục dự án: " + folderPath);
//				return;
//			}
//
//			String tempDir = folderPath + "temp_images" + File.separator;
//			File dir = new File(tempDir);
//			if (!dir.exists())
//				dir.mkdirs();
//
//			for (int i = 0; i < length; i++) {
//				if (imageBytes.size() > 0) {
//					String outputPath = tempDir + String.format("frame_%05d.jpg", i);
//					try (FileOutputStream fos = new FileOutputStream(outputPath)) {
//						fos.write(imageBytes.poll());
//					}
//				} else {
//					--i;
//					Thread.sleep(0, 100000);
//				}
//			}
//
//			String tempVideoPath = folderPath + "temp_video.mp4";
//			String finalVideoPath = folderPath + "output.mp4";
//			
//			String ffmpegCommand = String.format(
//				    "/opt/homebrew/bin/ffmpeg -y -framerate " + fps 
//				    + " -i %sframe_%%05d.jpg -vf scale=trunc(iw/2)*2:trunc(ih/2)*2 -c:v libx264 -pix_fmt yuv420p -movflags +faststart %s",
//				    tempDir, tempVideoPath);
//			
//			Process process = Runtime.getRuntime().exec(ffmpegCommand);
//			try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
//				String line;
//				while ((line = errorReader.readLine()) != null) {
//					System.err.println(line);
//				}
//			}
//			process.waitFor();
//
//			File tempVideoFile = new File(tempVideoPath);
//			if (!tempVideoFile.exists()) {
//				System.err.println("Failed to create temporary video.");
//				return;
//			}
//
//			File finalVideoFile = new File(finalVideoPath);
//			if (finalVideoFile.exists()) {
//				String concatListPath = "concat_list" + participant_id + "_" + typeVideo + ".txt";
//				try (PrintWriter writer = new PrintWriter(concatListPath)) {
//					writer.println("file '" + finalVideoPath.replace("\\", "/") + "'");
//					writer.println("file '" + tempVideoPath.replace("\\", "/") + "'");
//				}
//
//				String concatCommand = String.format("/opt/homebrew/bin/ffmpeg -y -f concat -safe 0 -i %s -c copy %s", concatListPath,
//						folderPath + "temp_output.mp4");
//				Process concatProcess = Runtime.getRuntime().exec(concatCommand);
//				try (BufferedReader errorReader = new BufferedReader(
//						new InputStreamReader(concatProcess.getErrorStream()))) {
//					String line;
//					while ((line = errorReader.readLine()) != null) {
//						System.err.println(line);
//					}
//				}
//				concatProcess.waitFor();
//
//				new File(finalVideoPath).delete();
//				new File(folderPath + "temp_output.mp4").renameTo(finalVideoFile);
//				new File(concatListPath).delete();
//			} else {
//				tempVideoFile.renameTo(finalVideoFile);
//			}
//
//			for (File file : dir.listFiles()) {
//				file.delete();
//			}
//			dir.delete();
//			tempVideoFile.delete();
//
//			System.out.println("Video updated successfully: " + finalVideoPath);
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
		System.out.println("start");
		String folderPath = "videos/" + participant_id + "/";
		String videoPath = folderPath + "output.mp4";
		File videoFile = new File(videoPath);
		new File(folderPath).mkdirs();

		try {
			ProcessBuilder pb = new ProcessBuilder("/opt/homebrew/bin/ffmpeg", "-y", "-f", "image2pipe", "-vcodec",
					"mjpeg", "-r", String.valueOf(fps), "-i", "-", "-c:v", "libx264", "-pix_fmt", "yuv420p"
					);
			while(true) {
				if(imageBytes.size() > 0) {
					Process process = pb.start();
					OutputStream ffmpegInput = process.getOutputStream();
					while(imageBytes.size() > 0) {
						byte[] img = imageBytes.poll();
						ffmpegInput.write(img);
					}
					ffmpegInput.close();
					process.waitFor();
				}
				else {
					Thread.sleep(0, 100000);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}