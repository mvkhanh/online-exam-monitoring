package pbl4.Client.Controller.Student;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import pbl4.Client.DTO.InContest.ScreenImageDTO;

public class CaptureThread extends Thread {
	private StudentController par;
	private boolean isScreen;

	public CaptureThread(StudentController par, boolean isScreen) {
		this.par = par;
		this.isScreen = isScreen;
	}

	public void run() {
		if (isScreen)
			captureScreen();
		else
			captureCam();
	}

	private void captureScreen() {
		Rectangle capture = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		Robot r = null;
		try {
			r = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		while (par.running) {
			ScreenImageDTO tmp = par.imgModel;
			BufferedImage fullImage = r.createScreenCapture(capture);
			tmp.g2d.drawImage(fullImage, 0, 0, tmp.img.getWidth(), tmp.img.getHeight(), null);
		}
	}
	
	private void captureCam() {
//		VideoCapture camera = new VideoCapture(0);
//		Mat frame = new Mat();
//		par.camImg = new Mat();
//		while (par.running) {
//			camera.read(frame);
//			Imgproc.resize(frame, par.camImg, par.camDim);
//		}
	}
}

//		MH: 42763100
//		Cam: 2292900

//		MH: 4251500
//		Cam: 2135300

// MH: gui lien tuc
// FC: 18 cai gui 1 cai
// 500 cai gui 1 cai -> MH: 21s, FC: 20s