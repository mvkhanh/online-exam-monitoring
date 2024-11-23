//package pbl4.Client.Controller.InContest.Student;
//
//import org.opencv.core.Mat;
//import org.opencv.imgproc.Imgproc;
//import org.opencv.videoio.VideoCapture;
//
//public class CaptureThread2 extends Thread{
//	StudentController par;
//
//	public CaptureThread2(StudentController par) {
//		this.par = par;
//	}
//
//	public void run() {
//		VideoCapture camera = new VideoCapture(0);
//		Mat frame = new Mat();
//		par.camImg = new Mat();
//		while (par.running) {
////			long start = System.nanoTime();
////			start = System.nanoTime();
//			camera.read(frame);
//			Imgproc.resize(frame, par.camImg, par.camDim);
////			System.out.println("Cam: " + (System.nanoTime() - start));
//		}
//	}
//}





