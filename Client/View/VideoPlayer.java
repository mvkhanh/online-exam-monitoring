package pbl4.Client.View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import pbl4.Client.Controller.Teacher.DashboardController;
import pbl4.Client.DTO.OutContest.VideoModel;

public class VideoPlayer extends JFrame {

	public static Dimension thisComputerDimesion = Toolkit.getDefaultToolkit().getScreenSize();

	DashboardController par;

	public VideoModel videoModel;

	int changeFps;

	private int currentIndex = 0; // from 0 to totalFrame
	private int currentFps = 0; // viewed video's fps
	private boolean isPlaying = false;

	// ok
	private void fastAction() {
		if (currentFps == videoModel.fps + changeFps) {
			currentFps = videoModel.fps;
			slow.setEnabled(true);
		} else {
			currentFps = videoModel.fps - changeFps;
			fast.setEnabled(false);
		}
	}

	// ok
	private void slowAction() {
		if (currentFps == videoModel.fps - changeFps) {
			currentFps = videoModel.fps;
			fast.setEnabled(true);
		} else {
			currentFps = videoModel.fps + changeFps;
			slow.setEnabled(false);
		}
	}

	// ok
	private void skip5Action() {
		currentIndex += videoModel.fps * 5;
		if (currentIndex >= videoModel.totalFrame)
			currentIndex = videoModel.totalFrame - 1;
		slider.setValue((currentIndex + 1) / videoModel.fps);
	}

	// ok
	private void back5Action() {
		currentIndex -= videoModel.fps * 5;
		if (currentIndex < 0)
			currentIndex = 0;
		slider.setValue((currentIndex + 1) / videoModel.fps);
	}

	private void playAction() {
		new viewProcess().start();
	}

	class viewProcess extends Thread {
		@Override
		public void run() {
			isPlaying = true;
			pause.setEnabled(true);
			play.setEnabled(false);
			while (isPlaying && currentIndex < videoModel.totalFrame) {
				slider.setValue((currentIndex + 1) / videoModel.fps);

				if (currentIndex >= videoModel.videoData.size()) {
					screen.setIcon(null);
					screen.setText("Loading");
					try {
						Thread.sleep(1);
					} catch (Exception ex) {

					}
					screen.setText("");
					continue;
				} else {
					screen.setIcon(new ImageIcon(videoModel.videoData.get(currentIndex++)));
					try {
//						System.out.println(currentFps);
						Thread.sleep(currentFps);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
			play.setEnabled(true);
			pause.setEnabled(false);
		}
	}

	private void pauseAction() {
		isPlaying = false;
		pause.setEnabled(false);
	}

	private void sliderAction() {
		currentIndex = slider.getValue() * videoModel.fps - 1;
		if (currentIndex < 0)
			currentIndex = 0;
		else if (currentIndex >= videoModel.totalFrame)
			currentIndex = videoModel.totalFrame - 1;
	}

	public VideoPlayer(VideoModel videoModel, DashboardController par)
			throws HeadlessException {
		this.videoModel = videoModel;
		this.par = par;

		slider = new JSlider(0, videoModel.totalFrame / videoModel.fps, 0);
		screen.setPreferredSize(thisComputerDimesion);
		currentFps = videoModel.fps;

		changeFps = videoModel.fps / 2;

		// Gắn ActionListener cho các nút
		fast.addActionListener(e -> fastAction());
		slow.addActionListener(e -> slowAction());
		skip5.addActionListener(e -> skip5Action());
		back5.addActionListener(e -> back5Action());
		play.addActionListener(e -> playAction());
		pause.addActionListener(e -> pauseAction());
		slider.addChangeListener(e -> sliderAction());
		back.addActionListener(e -> backAction());

		this.setLayout(new BorderLayout());

		JPanel panel1 = new JPanel(new GridLayout(2, 1, 0, 0));
		JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));

		panel2.add(slider);
		panel3.add(fast);
		panel3.add(slow);
		panel3.add(skip5);
		panel3.add(back5);
		panel3.add(play);
		panel3.add(pause);
		panel3.add(back);

		panel1.add(panel2);
		panel1.add(panel3);

		slider.setPreferredSize(new Dimension(400, 50));

		setTitle("Xem video");

		JPanel panel4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel4.add(screen);
		add(panel4, BorderLayout.CENTER);
		add(panel1, BorderLayout.SOUTH);

		pause.setEnabled(false);

		pack();
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
//		System.out.println(chooseFolderPath());
	}

	private void backAction() {
		par.view.setVisible(true);
		dispose();
	}

	JSlider slider;
	JLabel screen = new JLabel("", SwingConstants.CENTER);

	JButton fast = new JButton("Fast");
	JButton slow = new JButton("Slow");
	JButton skip5 = new JButton("+5");
	JButton back5 = new JButton("-5");
	JButton play = new JButton("Play");
	JButton pause = new JButton("Pause");
	JButton back = new JButton("Back");
}
