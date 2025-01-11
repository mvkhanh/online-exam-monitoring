package pbl4.Client.Controller.Teacher;

import java.util.ArrayList;
import java.util.Map;

import pbl4.Client.Constant;
import pbl4.Client.DTO.InContest.Teacher.ImageModel;

public class CheckThread extends Thread {
	private TeacherController par;

	public CheckThread(TeacherController par) {
		this.par = par;
	}

	public void run() {
		while (par.running) {
			if (par.images.size() == 0)
				try {
					Thread.sleep(0, 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			for (Map.Entry<Integer, ArrayList<ImageModel>> entry : par.images.entrySet()) {
				int studentNum = entry.getKey();
				ArrayList<ImageModel> studentImages = entry.getValue();
				for (int i = 0; i < studentImages.size(); i++) {
					ImageModel img = studentImages.get(i);
					if (img == null)
						continue;
					if (img.getTotal() == img.getData().size()) {
						while (studentNum >= par.viewList.size())
							par.viewList.add(null);
						par.viewList.set(studentNum, img);
						studentImages.set(i, null);
						break;
					} else if (System.currentTimeMillis() - img.getTime() > Constant.TIMEOUT) {
						studentImages.set(i, null);
					}
				}
			}
		}
	}
}
