package pbl4.Server.Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
	
	//1 ham nhan vao participant id va danh sach cac mang byte -> luu thanh 1 file mp4
}
