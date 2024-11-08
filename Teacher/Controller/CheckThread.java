package PBL4.Teacher.Controller;

import java.util.ArrayList;
import java.util.Map;

import PBL4.Teacher.Model.ImageModel;
import PBL4.Teacher.View.Teacher;

public class CheckThread extends Thread{
	private Teacher par;
	
	public CheckThread(Teacher par) {
		this.par = par;
	}
	
	public void run() {
		while (true) {
			if (par.images.size() == 0)
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
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
						img.setStudentNum(studentNum);
						par.currImage = img;
						studentImages.set(i, null);
						break;
					} else if (System.currentTimeMillis() - img.getTime() > Teacher.TIMEOUT) {
						studentImages.set(i, null);
					}
				}
			}
		}
	}
}
