package pbl4.Client.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import pbl4.Client.Controller.Teacher.TeacherController;
import pbl4.Client.View.Utils.ChatPanel;
import pbl4.Client.View.Utils.LogPanel;

public class TeacherInContest extends JFrame {

	public TeacherController controller;
	
	public ArrayList<JLabel> cameraScreens = new ArrayList<JLabel>();
	public JPanel cameras;
	public ChatPanel chatPn;
	public LogPanel keyPn;

	public TeacherInContest(TeacherController controller) {
		this.controller = controller;
		setLayout(new BorderLayout());

		JPanel topPn = new JPanel(new FlowLayout());
		topPn.add(new JLabel("Contest name: " + controller.name));
		topPn.add(new JLabel("||"));
		topPn.add(new JLabel("Room ID: " + controller.roomId));

		JScrollPane mainPn = new JScrollPane();
		cameras = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
		cameras.setPreferredSize(new Dimension(500, 10000));
		mainPn.setViewportView(cameras);
		mainPn.setBorder(null);

		JPanel rightPn = new JPanel(new BorderLayout());
		rightPn.setPreferredSize(new Dimension(350, 1000));
		keyPn = new LogPanel("Keyboard");
		rightPn.add(keyPn, BorderLayout.CENTER);
		chatPn = new ChatPanel(controller);
		rightPn.add(chatPn, BorderLayout.SOUTH);
		
		JButton ketthuc = new JButton("Kết thúc");
		JPanel bottomCenterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		bottomCenterPanel.add(ketthuc);
		ketthuc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.endStream();
			}
		});

		JPanel center = new JPanel(new BorderLayout());
		
		center.add(bottomCenterPanel, BorderLayout.SOUTH);
		center.add(mainPn, BorderLayout.CENTER);
		center.add(topPn, BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);
		add(rightPn, BorderLayout.EAST);

		setTitle("Màn hình cuộc thi");
		
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void addCameraScreen() {
		JLabel cameraScreen = new JLabel();
		cameraScreen.setBorder(new LineBorder(Color.BLACK, 1));
		cameraScreen.setHorizontalTextPosition(JLabel.CENTER);
		cameraScreen.setVerticalTextPosition(JLabel.TOP);

		cameraScreens.add(cameraScreen);
		cameras.add(cameraScreen);
		cameraScreen.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				controller.focus(cameraScreens.indexOf(cameraScreen));
			}
		});
	}

	public void removeCameraScreen(int studentNum) {
		cameras.remove(cameraScreens.get(studentNum));
		repaint();
	}
	
	public void setImage(byte[] img, int cameraNum) {
		while(cameraNum >= cameraScreens.size()) addCameraScreen();
		JLabel screen = cameraScreens.get(cameraNum);
		screen.setIcon(new ImageIcon(img));
	}
	
	public void addStudent(int studentNum, String name) {
		while (studentNum * 2 + 1 >= cameraScreens.size())
			addCameraScreen();
		cameraScreens.get(studentNum * 2).setText(studentNum + ". " + name + " - MH");
		cameraScreens.get(studentNum * 2 + 1).setText(studentNum + ". " + name + " - Face cam");
	}
	
	public void deleteStudent(int studentNum) {
		removeCameraScreen(studentNum * 2);
		removeCameraScreen(studentNum * 2 + 1);
	}
	
	public void addText(String txt) {
		chatPn.addText(txt);
	}
	
	public void addKeyLog(int studentNum, String duration, String keys) {
		String cameraText = cameraScreens.get(studentNum * 2).getText();
		int i = cameraText.indexOf(' ');
		int j = cameraText.indexOf(' ', i + 1);
		String msg = duration + ": " + studentNum + ". " + cameraText.substring(i + 1, j) + " has typed: " + keys;
		keyPn.addText(msg);
	}
}
