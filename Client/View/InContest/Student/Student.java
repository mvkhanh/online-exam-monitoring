package pbl4.Client.View.InContest.Student;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import pbl4.Client.Controller.InContest.Student.StudentController;
import pbl4.Client.View.Utils.ChatPanel;

public class Student extends JFrame implements NativeKeyListener {

	public StudentController controller;
	public ChatPanel chatPn;

	public Student(StudentController controller) {
		this.controller = controller;
		setLayout(new BorderLayout());

		JLabel cameraScreen = new JLabel();
		cameraScreen.setBounds(0, 0, 640, 480);

		JPanel topPn = new JPanel(new FlowLayout());
		JLabel lbName = new JLabel("Name:");
		JTextField nameTf = new JTextField(10);
		JLabel lbRoomId = new JLabel("Room ID:");
		JTextField roomIdTf = new JTextField(10);
		JButton button = new JButton("Join");
		JLabel lbWarning = new JLabel("Khong ton tai phong");
		JLabel teacher = new JLabel();
		lbWarning.setVisible(false);
		topPn.add(teacher);
		topPn.add(lbName);
		topPn.add(nameTf);
		topPn.add(lbRoomId);
		topPn.add(roomIdTf);
		topPn.add(button);
		topPn.add(lbWarning);

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				roomIdTf.setEditable(false);
				nameTf.setEditable(false);
				button.setEnabled(false);
				String msg = controller.joinRoom(nameTf.getText(), roomIdTf.getText());
				if (msg != null) {
					teacher.setText("Contest name: " + msg);
					lbName.setVisible(false);
					lbRoomId.setVisible(false);
					lbWarning.setVisible(false);
					startCapture();
				} else {
					roomIdTf.setEditable(true);
					nameTf.setEditable(true);
					button.setEnabled(true);
					lbWarning.setVisible(true);
				}
			}
		});

		chatPn = new ChatPanel(controller);

		add(cameraScreen, BorderLayout.CENTER);
		add(topPn, BorderLayout.NORTH);
		add(chatPn, BorderLayout.EAST);

		setSize(new Dimension(1000, 700));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void nativeKeyPressed(NativeKeyEvent e) {
		char c = NativeKeyEvent.getKeyText(e.getKeyCode()).charAt(0);
		if (e.getKeyCode() == 0xe36)
			c = '⇧';
		controller.currKeys += System.currentTimeMillis() + " " + c + " ";
	}

	public void nativeKeyReleased(NativeKeyEvent e) {
		char c = NativeKeyEvent.getKeyText(e.getKeyCode()).charAt(0);
		if (e.getKeyCode() == 0xe36)
			c = '⇧';
		if (c == '⇧' || c == '⌘' || c == '⌥' || c == '⌃')
			controller.currKeys += System.currentTimeMillis() + " " + c + " ";
	}

	private void startCapture() {
		try {
			GlobalScreen.registerNativeHook();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		GlobalScreen.addNativeKeyListener(this);
		controller.startThreads();
	}
	
	public void addText(String txt) {
		chatPn.addText(txt);
	}
}