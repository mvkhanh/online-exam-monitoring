package PBL4.Student.Controller;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import PBL4.Student.View.Student2;

public class CaptureThread extends Thread{
	private Student2 par;
	
	public CaptureThread(Student2 par) {
		this.par = par;
	}
	public void run() {
		par.g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		Rectangle capture = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		Robot r = null;
		try {
			r = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		while (true) {
			BufferedImage fullImage = r.createScreenCapture(capture);
			par.g2d.drawImage(fullImage, 0, 0, par.img.getWidth(), par.img.getHeight(), null);
		}
	}
}
