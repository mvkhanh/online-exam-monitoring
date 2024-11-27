package pbl4.Server.Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import pbl4.Server.Constant;

public class Service {

	public static void saveKeyLog(int participantId, String msg) {
		String folderPath = Constant.FILE_LOCATION + File.separator + "Keyboard";
		File folder = new File(folderPath);
		if (!folder.exists() && !folder.mkdirs()) {
			System.out.println("Can not create project's directory");
		}
		String filePath = folderPath + File.separator + participantId + ".txt";
		try (FileWriter fileWriter = new FileWriter(filePath, true)) {
			fileWriter.write(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Mat byteArrayToMat(byte[] byteArray) {
        // Tạo một Mat từ mảng byte (tải hình ảnh từ mảng byte)
        Mat mat = new Mat(1, byteArray.length, CvType.CV_8U);
        mat.put(0, 0, byteArray);
        return Imgcodecs.imdecode(mat, Imgcodecs.IMREAD_COLOR); // Đọc ảnh màu từ dữ liệu
    }
	//1 ham nhan vao participant id va danh sach cac mang byte -> luu thanh 1 file mp4
	
//	public static 
}
