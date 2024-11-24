package pbl4.Server.Controller;

import java.util.Map;

import pbl4.Server.Constant;
import pbl4.Server.Server;
import pbl4.Server.DTO.ClientModel;
import pbl4.Server.DTO.Room;

public class CleanThread implements Runnable {
	public void run() {
		while (true) {
			for (Map.Entry<Integer, Room> entry : Server.rooms.entrySet()) {
				Room room = entry.getValue();
				if (System.currentTimeMillis() - room.getTeacher().getTime() > Constant.TIMEOUT) {
					Server.rooms.remove(entry.getKey());
					continue;
				}
				for (ClientModel student : room.getStudents().values()) {
					if (System.currentTimeMillis() - student.getTime() > Constant.TIMEOUT) {
						room.getQuittedStudents().add(student.getStudentNum());
					}
				}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
