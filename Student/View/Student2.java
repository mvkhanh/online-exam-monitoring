package PBL4.Student.View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import PBL4.Student.Controller.CaptureThread;
import PBL4.Student.Controller.SendThread;
import PBL4.Student.Controller.TCPThread;

public class Student2 extends JFrame {

	public static final int NORMAL_WIDTH = 450;
	public static final int NORMAL_HEIGHT = 300;
	public static final int FOCUS_WIDTH = 900;
	public static final int FOCUS_HEIGHT = 600;

	public static InetAddress serverAddress;
	public static int tcpPort = 8888;
	public static int udpPort = 9999;
	public int roomId;
	public String name;
	public DatagramSocket udpSocket;

	public BufferedImage img = new BufferedImage(NORMAL_WIDTH, NORMAL_HEIGHT, BufferedImage.TYPE_INT_RGB);
	public Graphics2D g2d = img.createGraphics();
	public JTextArea chatArea;
	public String id;

	public static void main(String[] args) {
		new Student2();
	}

	public Student2() {

		try {
			serverAddress = InetAddress.getByName("localhost");
			udpSocket = new DatagramSocket();
		} catch (Exception e) {
			System.exit(1);
		}

		setLayout(new BorderLayout());

		JLabel cameraScreen = new JLabel();
		cameraScreen.setBounds(0, 0, 640, 480);

		JPanel topPn = new JPanel(new FlowLayout());
		JLabel lbName = new JLabel("Name:");
		JTextField nameTf = new JTextField(10);
		JLabel lbRoomId = new JLabel("Room ID:");
		JTextField roomIdTf = new JTextField(10);
//		roomId.setBounds(0, 480, 80, 40);
		JButton button = new JButton("Join");
//		button.setBounds(300, 480, 80, 40);
		JLabel lbWarning = new JLabel("Khong ton tai phong");
		lbWarning.setVisible(false);
		topPn.add(lbName);
		topPn.add(nameTf);
		topPn.add(lbRoomId);
		topPn.add(roomIdTf);
		topPn.add(button);
		topPn.add(lbWarning);

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				roomId = Integer.valueOf(roomIdTf.getText());
				name = nameTf.getText();
				String msg = "J" + roomId + " " + udpSocket.getLocalPort() + " " + name;
				try (Socket tcpSocket = new Socket(serverAddress, tcpPort);
						DataOutputStream dos = new DataOutputStream(tcpSocket.getOutputStream());
						DataInputStream dis = new DataInputStream(tcpSocket.getInputStream());) {
					dos.writeUTF(msg);
					roomIdTf.setEditable(false);
					nameTf.setEditable(false);
					button.setEnabled(false);
					msg = dis.readUTF();
					if (msg.contains("Y")) {
						id = msg.substring(1);
						lbName.setVisible(false);
						lbRoomId.setVisible(false);
						lbWarning.setVisible(false);
						startCapture();
					} else if (msg.contains("N")) {
						roomIdTf.setEditable(true);
						nameTf.setEditable(true);
						button.setEnabled(true);
						lbWarning.setVisible(true);
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

        JPanel chatPanel = new JPanel(new BorderLayout());
        chatPanel.setPreferredSize(new Dimension(250, 560));

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane chatScrollPane = new JScrollPane(chatArea);
        chatPanel.add(chatScrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        JTextField chatInput = new JTextField(15);
        JButton sendButton = new JButton("Send");
        inputPanel.add(chatInput, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        chatPanel.add(inputPanel, BorderLayout.SOUTH);
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = chatInput.getText();
                if (!message.isEmpty()) {
                	chatInput.setText("");
                	try (Socket socket = new Socket(serverAddress, tcpPort);
                			DataOutputStream dos = new DataOutputStream(socket.getOutputStream())){
                		dos.writeUTF("M" + roomId + " " + name + ": " + message + "\n");
                	} catch (IOException e1) {
						e1.printStackTrace();
					}
                }
            }
        });

        add(cameraScreen, BorderLayout.CENTER);
        add(topPn, BorderLayout.NORTH);
        add(chatPanel, BorderLayout.EAST);
		
		setSize(new Dimension(1000, 700));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void startCapture() {
		new CaptureThread(this).start();
		new SendThread(this).start();
		new TCPThread(this).start();
	}
}