package pbl4.Client.Controller.InContest.Student;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import pbl4.Client.DTO.InContest.Student.ImageModel;

public class CaptureThread extends Thread {
	private StudentController par;

	public CaptureThread(StudentController par) {
		this.par = par;
	}

	public void run() {
		Rectangle capture = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		Robot r = null;
		try {
			r = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		while (true) {
			ImageModel tmp = par.imgModel;
			BufferedImage fullImage = r.createScreenCapture(capture);
			tmp.g2d.drawImage(fullImage, 0, 0, tmp.img.getWidth(), tmp.img.getHeight(), null);
		}
//		MH: 42763100
//		Cam: 2292900
		
//		MH: 4251500
//		Cam: 2135300
	}
}
