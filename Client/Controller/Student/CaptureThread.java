package pbl4.Client.Controller.Student;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import pbl4.Client.Constant;
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
		Constant.screenWidth = capture.width;
		Constant.screenHeight = capture.height;
		Robot r = null;
		try {
			r = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		while (par.running) {
			ScreenImageDTO tmp = par.imgModel;
			BufferedImage fullImage = r.createScreenCapture(capture);
//			par.screenQueue.add(fullImage);
			tmp.g2d.drawImage(fullImage, 0, 0, tmp.img.getWidth(), tmp.img.getHeight(), null);
		}
	}
	
	private void captureCam() {
//		VideoCapture camera = new VideoCapture(0);
//		Constant.camWidth = (int) camera.get(org.opencv.videoio.Videoio.CAP_PROP_FRAME_WIDTH);
//		Constant.camHeight = (int) camera.get(org.opencv.videoio.Videoio.CAP_PROP_FRAME_HEIGHT);
//		par.camImg = new Mat();
//		while (par.running) {
//			Mat frame = new Mat();
//			camera.read(frame);
//			par.camQueue.add(frame);
//			par.frame = frame;
//		}
//		camera.release();
	}
}
