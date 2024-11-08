package PBL4.Teacher.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import PBL4.Teacher.Controller.ReceiveThread;
import PBL4.Teacher.Controller.TCPThread;
import PBL4.Teacher.Model.ImageModel;
import PBL4.Teacher.Model.Packet;

public class Teacher extends JFrame {

	public ArrayList<JLabel> cameraScreens = new ArrayList<JLabel>();
	public JPanel cameras;
	public JTextArea chatArea;

	public static final int PROCESS_THREADS = 3;
	public static final int VIEW_THREADS = 2;
	public static InetAddress serverAddress;
	public static int tcpPort = 8888;
	public static int udpPort = 9999;
	public String roomId;
	public String id;
	public String name;
	public DatagramSocket udpSocket;
	public Queue<Packet> packets = new ConcurrentLinkedQueue<>();
	public Map<Integer, ArrayList<ImageModel>> images = new ConcurrentHashMap<>();
	public static final int MAX_IMAGES = 4;
	public static final long TIMEOUT = 4;
	public ImageModel currImage;

	public static void main(String[] args) {
		new Teacher();
	}

	public Teacher() {
		setLayout(new BorderLayout());

		try {
			serverAddress = InetAddress.getByName("localhost");
			udpSocket = new DatagramSocket();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.exit(1);
		}
		
		JPanel topPn = new JPanel(new FlowLayout());
		JLabel lbName = new JLabel("Name:");
		JTextField nameTf = new JTextField(10);
		JButton button = new JButton("Create");
		topPn.add(lbName);
		topPn.add(nameTf);
		topPn.add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				name = nameTf.getText();
				String msg = "C" + udpSocket.getLocalPort() + " " + name;
				try(Socket tcpSocket = new Socket(serverAddress, tcpPort);
						DataOutputStream dos = new DataOutputStream(tcpSocket.getOutputStream());
						DataInputStream dis = new DataInputStream(tcpSocket.getInputStream())) {
					dos.writeUTF(msg);
					String[] tmp = dis.readUTF().split(" ");
					roomId = tmp[0];
					id = tmp[1];
					if (roomId != null) {
						JLabel idRoomLabel = new JLabel("Room id: " + roomId);
						lbName.setText(lbName.getText() + " " + name);
						topPn.remove(nameTf);
						topPn.remove(button);
						topPn.add(idRoomLabel);
						repaint();
						startThread();
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		cameras = new JPanel(new FlowLayout());
		
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
		
		add(cameras, BorderLayout.CENTER);
		add(topPn, BorderLayout.NORTH);
		add(chatPanel, BorderLayout.EAST);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				System.exit(0);
			}
		});

		setSize(new Dimension(1500, 1000));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void startThread() {
		new ReceiveThread(this).start();
		new TCPThread(this).start();
	}

	public void addCameraScreen() {
		JLabel cameraScreen = new JLabel();
		cameraScreen.setBorder(new LineBorder(Color.BLACK, 5));
		cameraScreens.add(cameraScreen);
		cameras.add(cameraScreen);
		cameraScreen.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int id = cameraScreens.indexOf(cameraScreen);
				try (Socket tcpSocket = new Socket(serverAddress, tcpPort);
						DataOutputStream dos = new DataOutputStream(tcpSocket.getOutputStream())){
					dos.writeUTF("H" + roomId + " " + id);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
	
	public void removeCameraScreen(int studentNum) {
		cameras.remove(cameraScreens.get(studentNum));
		repaint();
	}
}
