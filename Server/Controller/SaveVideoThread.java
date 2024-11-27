package pbl4.Server.Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Queue;

import org.opencv.core.Size;

import pbl4.Server.Constant;

public class SaveVideoThread extends Thread {

	Queue<byte[]> imageBytes;
	int length; // số lượng ảnh
	int typeVideo;
	String participant_id;
	Size size;

	public SaveVideoThread(Queue<byte[]> imageBytes, int length, int typeVideo, String participant_id, Size size) {
		super();
		this.imageBytes = imageBytes;
		this.length = length;
		this.typeVideo = typeVideo;
		this.participant_id = participant_id;
		this.size = size;
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
	    String folderPath = Constant.FILE_LOCATION + File.separator + "Record" + File.separator + participant_id;
	    File folder = new File(folderPath);
	    if (!folder.exists() && !folder.mkdirs()) {
	        System.err.println("Không thể tạo thư mục dự án: " + folderPath);
	        return;
	    }

	    String tempDir = folderPath + File.separator + "temp_images" + typeVideo + "/";
	    File dir = new File(tempDir);
	    if (!dir.exists()) dir.mkdirs();

	    for (int i = 0; i < length; i++) {
	        if (imageBytes.size() > 0) {
	            String outputPath = tempDir + String.format("frame_%05d.jpg", i);
	            byte[] imageBytesData = imageBytes.poll();
	            try (FileOutputStream fos = new FileOutputStream(outputPath)) {
	                fos.write(imageBytesData);
	            }
	        } else {
	            Thread.sleep(10); // Nghỉ 10ms
	            --i;
	        }
	    }

	    // Đường dẫn video cuối cùng
	    String finalVideoPath = folderPath + File.separator + "output" + typeVideo + ".mp4";

	    // Kiểm tra xem video cũ có tồn tại không
	    File finalVideoFile = new File(finalVideoPath);
	    if (finalVideoFile.exists()) {
	        // Tạo file danh sách video cần ghép
	        String concatListPath = folderPath + File.separator + "concat_list" + typeVideo + ".txt";
	        try (PrintWriter writer = new PrintWriter(concatListPath)) {
	            writer.println("file '" + finalVideoPath.replace("\\", "/") + "'");
	            writer.println("file '" + tempDir.replace("\\", "/") + "frame_%05d.jpg'");
	        }

	        // Ghép video
	        System.out.println("Appending video...");
	        String concatCommand = String.format(
	            "ffmpeg -y -f concat -safe 0 -i %s -c copy %s",
	            concatListPath,
	            finalVideoPath
	        );
	        Process concatProcess = Runtime.getRuntime().exec(concatCommand);

	        // In log lỗi FFmpeg
	        try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(concatProcess.getErrorStream()))) {
	            String line;
	            while ((line = errorReader.readLine()) != null) {
	                System.err.println(line);
	            }
	        }

	        concatProcess.waitFor();
	        new File(concatListPath).delete();
	    } else {
	        // Tạo video mới từ các ảnh
	        System.out.println("Creating video...");
	        String ffmpegCommand = String.format(
	            "ffmpeg -y -framerate 30 -i %sframe_%%05d.jpg -c:v libx264 -pix_fmt yuv420p -movflags +faststart %s",
	            tempDir,
	            finalVideoPath
	        );
	        Process process = Runtime.getRuntime().exec(ffmpegCommand);

	        // In đầu ra lỗi từ FFmpeg
	        try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
	            String line;
	            while ((line = errorReader.readLine()) != null) {
	                System.err.println(line);
	            }
	        }

	        process.waitFor();
	    }

	    // Xóa các file ảnh tạm
	    for (File file : dir.listFiles()) {
	        file.delete();
	    }
	    dir.delete();

	    System.out.println("Video updated successfully: " + finalVideoPath);
	}


}
