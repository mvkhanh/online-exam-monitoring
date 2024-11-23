package pbl4.Server.Utils;

import java.io.FileWriter;
import java.io.IOException;

import pbl4.Server.Server;

public class Service {
	
	public static void saveToFile(int participantId, String msg) {
		try (FileWriter fileWriter = new FileWriter(participantId + ".txt", true)) {
			fileWriter.write(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
