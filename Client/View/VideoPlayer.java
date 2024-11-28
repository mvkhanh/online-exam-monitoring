package pbl4.Client.View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import pbl4.Client.Controller.Teacher.DashboardController;

public class VideoPlayer extends JFrame {

	public static Dimension thisComputerDimesion = Toolkit.getDefaultToolkit().getScreenSize();

	DashboardController par;

	// receive video data
	private List<byte[]> frameData;
	private int totalFrame;
	private int fps; // received video's fps

	int changeFps;

	private int currentIndex = 0; // from 0 to totalFrame
	private int currentFps = 0; // viewed video's fps
	private boolean isPlaying = false;

	// ok
	private void fastAction() {
		if (currentFps == fps + changeFps) {
			currentFps = fps;
			slow.setEnabled(true);
		} else {
			currentFps = fps - changeFps;
			fast.setEnabled(false);
		}
	}

	// ok
	private void slowAction() {
		if (currentFps == fps - changeFps) {
			currentFps = fps;
			fast.setEnabled(true);
		} else {
			currentFps = fps + changeFps;
			slow.setEnabled(false);
		}
	}

	// ok
	private void skip5Action() {
		currentIndex += fps * 5;
		if (currentIndex >= totalFrame)
			currentIndex = totalFrame - 1;
		slider.setValue((currentIndex + 1) / fps);
	}

	// ok
	private void back5Action() {
		currentIndex -= fps * 5;
		if (currentIndex < 0)
			currentIndex = 0;
		slider.setValue((currentIndex + 1) / fps);
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
			while (isPlaying && currentIndex < totalFrame) {
				slider.setValue((currentIndex + 1) / fps);

				if (currentIndex >= frameData.size()) {
					screen.setIcon(null);
					screen.setText("Loading");
					try {
						Thread.sleep(1);
					} catch (Exception ex) {

					}
					screen.setText("");
					continue;
				} else {
					screen.setIcon(new ImageIcon(frameData.get(currentIndex++)));
					try {
						System.out.println(currentFps);
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
		currentIndex = slider.getValue() * fps - 1;
		if (currentIndex < 0)
			currentIndex = 0;
		else if (currentIndex >= totalFrame)
			currentIndex = totalFrame - 1;
	}

	public VideoPlayer(List<byte[]> frameData, int totalFrame, int fps, DashboardController par)
			throws HeadlessException {
		this.frameData = frameData;
		this.totalFrame = totalFrame;
		this.fps = fps;
		this.par = par;

		slider = new JSlider(0, totalFrame / fps, 0);
		screen.setPreferredSize(thisComputerDimesion);
		currentFps = fps;

		changeFps = fps / 2;

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

		JPanel panel1 = new JPanel(new GridLayout(2, 1));
		JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

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
