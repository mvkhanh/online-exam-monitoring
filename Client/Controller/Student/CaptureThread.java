package pbl4.Client.Controller.Student;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import pbl4.Client.DTO.InContest.ScreenImageDTO;

public class CaptureThread extends Thread {
	private StudentController par;
	private boolean isScreen;

	public static int camWidth;
	public static int camHeight;

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
			par.screenQueue.add(fullImage);
			tmp.g2d.drawImage(fullImage, 0, 0, tmp.img.getWidth(), tmp.img.getHeight(), null);
		}
	}

	private void captureCam() {
		VideoCapture camera = new VideoCapture(0);
		camWidth = (int) camera.get(org.opencv.videoio.Videoio.CAP_PROP_FRAME_WIDTH);
		camHeight = (int) camera.get(org.opencv.videoio.Videoio.CAP_PROP_FRAME_HEIGHT);
		par.camImg = new Mat();
		while (par.running) {
			Mat frame = new Mat();
			camera.read(frame);
			par.camQueue.add(frame);
			par.frame = frame;
		}
		camera.release();
	}
}

//		MH: 42763100
//		Cam: 2292900

//		MH: 4251500
//		Cam: 2135300