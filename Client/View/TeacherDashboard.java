/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pbl4.Client.View;

import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.net.URL;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import pbl4.Client.DTO.OutContest.Participant;
import pbl4.Client.DTO.OutContest.ParticipantTableModel;
import pbl4.Client.DTO.OutContest.Test;
import pbl4.Client.DTO.OutContest.TestTableModel;

/**
 *
 * @author DELL
 */
public class TeacherDashboard extends javax.swing.JFrame {

	pbl4.Client.Controller.Teacher.DashboardController teacherController;

	public TeacherDashboard(pbl4.Client.Controller.Teacher.DashboardController teacherController) {
		this.teacherController = teacherController;

		initComponents();
	}

	private void initComponents() {

		jPanel1 = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		trangchu = new JButton();
		lsct = new JButton();
		nguoidung = new JButton();
		dangxuat = new JButton();
		mainPanel = new javax.swing.JPanel();
		trangchuForm = new javax.swing.JPanel();
		tencuocthi = new javax.swing.JTextField();
		trangchu_batdau = new javax.swing.JButton();
		lsctForm = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		lsct_jtable = new javax.swing.JTable();
		lsct_xemchitiet = new javax.swing.JButton();
		nguoidungForm = new javax.swing.JPanel();
		jLabel3 = new javax.swing.JLabel();
		password = new javax.swing.JPasswordField();
		capnhat = new javax.swing.JButton();
		ctctForm = new javax.swing.JPanel();
		jScrollPane4 = new javax.swing.JScrollPane();
		jTextArea1 = new javax.swing.JTextArea();
		xemtientrinhtai = new JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jPanel1.setBackground(new java.awt.Color(255, 255, 255));
		jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

		jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);


		jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel2.setText("Chào mừng bạn, " + teacherController.user.getUsername());

		trangchu.setBackground(new java.awt.Color(204, 204, 204));
		trangchu.setForeground(new java.awt.Color(51, 51, 51));
		trangchu.setLabel("Trang chủ");
		trangchu.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				trangchuActionPerformed(evt);
			}
		});

		lsct.setBackground(new java.awt.Color(204, 204, 204));
		lsct.setLabel("Lịch sử cuộc thi");
		lsct.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				lsctActionPerformed(evt);
			}
		});

		nguoidung.setBackground(new java.awt.Color(204, 204, 204));
		nguoidung.setLabel("Người dùng");
		nguoidung.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				nguoidungActionPerformed(evt);
			}
		});
		
		xemtientrinhtai.setBackground(new java.awt.Color(204, 204, 204));
		xemtientrinhtai.setLabel("Xem tiến trình tải");
		xemtientrinhtai.addActionListener(e -> {
			teacherController.viewDownloadProgress();
		});

		dangxuat.setBackground(new java.awt.Color(255, 51, 51));
		dangxuat.setForeground(new java.awt.Color(51, 51, 51));
		dangxuat.setLabel("Đăng xuất");
		dangxuat.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				dangxuatActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(lsct, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(trangchu, javax.swing.GroupLayout.Alignment.TRAILING,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(nguoidung, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
				.addComponent(xemtientrinhtai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
				.addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(dangxuat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
						jPanel1Layout.createSequentialGroup().addContainerGap(70, Short.MAX_VALUE).addComponent(jLabel1,
								javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(70, 70, 70)));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addGap(30, 30, 30)
						.addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 141,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(30, 30, 30)
						.addComponent(trangchu, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(1, 1, 1)
						.addComponent(lsct, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(1, 1, 1)
						.addComponent(nguoidung, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(1, 1, 1)
						.addComponent(xemtientrinhtai, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 167, Short.MAX_VALUE)
						.addComponent(dangxuat, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
								javax.swing.GroupLayout.PREFERRED_SIZE)));

		getContentPane().add(jPanel1, java.awt.BorderLayout.LINE_START);

		jPanel1.setBackground(java.awt.Color.decode("#e6f7ff")); // Xám nhạt
		
		try {
		    URL imageUrl = new URL("https://static.vecteezy.com/system/resources/previews/000/439/863/non_2x/vector-users-icon.jpg");
		    ImageIcon originalIcon = new ImageIcon(imageUrl); // Tải ảnh từ URL
		    
		    // Thêm ComponentListener để đợi JLabel sẵn sàng
		    jLabel1.addComponentListener(new java.awt.event.ComponentAdapter() {
		        @Override
		        public void componentResized(java.awt.event.ComponentEvent evt) {
		            // Thay đổi kích thước hình ảnh khi JLabel đã được hiển thị
		            int width = jLabel1.getWidth();
		            int height = jLabel1.getHeight();
		            if (width > 0 && height > 0) {
		                Image scaledImage = originalIcon.getImage().getScaledInstance(
		                        width, height, Image.SCALE_SMOOTH);
		                ImageIcon resizedIcon = new ImageIcon(scaledImage);
		                jLabel1.setIcon(resizedIcon);
		            }
		        }
		    });
		} catch (Exception e) {
		    e.printStackTrace(); // Xử lý nếu có lỗi xảy ra
		}

		
		mainPanel.setBackground(new java.awt.Color(51, 51, 51));
		mainPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		mainPanel.setName(""); // NOI18N
		mainPanel.setLayout(new java.awt.CardLayout());

		tencuocthi.setText("Tên cuộc thi");
		tencuocthi.setToolTipText("");
		tencuocthi.setAutoscrolls(false);
		tencuocthi.setPreferredSize(new java.awt.Dimension(200, 22));

		trangchu_batdau.setText("Bắt đầu");
		trangchu_batdau.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				trangchu_batdauActionPerformed(evt);
			}
		});
		
		// Tăng kích thước font chữ
		tencuocthi.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 15)); // Font Arial, kiểu chữ thường, cỡ 18
		trangchu_batdau.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 15)); // Font Arial, kiểu chữ đậm, cỡ 18


		javax.swing.GroupLayout trangchuFormLayout = new javax.swing.GroupLayout(trangchuForm);
		trangchuForm.setLayout(trangchuFormLayout);
		trangchuFormLayout
		        .setHorizontalGroup(trangchuFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                .addGroup(trangchuFormLayout.createSequentialGroup().addGap(234, 234, 234)
		                        .addComponent(tencuocthi, javax.swing.GroupLayout.PREFERRED_SIZE, 350, // Tăng chiều rộng
		                                javax.swing.GroupLayout.PREFERRED_SIZE)
		                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		                        .addComponent(trangchu_batdau, javax.swing.GroupLayout.PREFERRED_SIZE, 100, // Tăng chiều rộng
		                                javax.swing.GroupLayout.PREFERRED_SIZE)
		                        .addContainerGap(180, Short.MAX_VALUE))); // Giảm containerGap để bù kích thước tăng
		trangchuFormLayout.setVerticalGroup(trangchuFormLayout
		        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		        .addGroup(trangchuFormLayout.createSequentialGroup().addGap(246, 246, 246)
		                .addGroup(trangchuFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		                        .addComponent(tencuocthi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, // Tăng chiều cao
		                                javax.swing.GroupLayout.PREFERRED_SIZE)
		                        .addComponent(trangchu_batdau, javax.swing.GroupLayout.PREFERRED_SIZE, 30, // Tăng chiều cao
		                                javax.swing.GroupLayout.PREFERRED_SIZE))
		                .addContainerGap(340, Short.MAX_VALUE)));


		mainPanel.add(trangchuForm, "TrangChu");

		lsct_jtable.setBorder(javax.swing.BorderFactory.createCompoundBorder());
//		lsct_jtable.setColumnSelectionAllowed(true);
		jScrollPane1.setViewportView(lsct_jtable);
		lsct_jtable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

		lsct_xemchitiet.setBackground(new java.awt.Color(153, 255, 153));
		lsct_xemchitiet.setText("Xem chi tiết");
		lsct_xemchitiet.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				lsct_xemchitietActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout lsctFormLayout = new javax.swing.GroupLayout(lsctForm);
		lsctForm.setLayout(lsctFormLayout);
		lsctFormLayout.setHorizontalGroup(lsctFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(lsctFormLayout.createSequentialGroup().addContainerGap().addComponent(jScrollPane1)
						.addContainerGap())
				.addGroup(lsctFormLayout.createSequentialGroup().addGap(392, 392, 392).addComponent(lsct_xemchitiet)
						.addContainerGap(367, Short.MAX_VALUE)));
		lsctFormLayout.setVerticalGroup(lsctFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(lsctFormLayout.createSequentialGroup().addContainerGap()
						.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 534,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(lsct_xemchitiet).addContainerGap(39, Short.MAX_VALUE)));

		mainPanel.add(lsctForm, "LSCT");

		cardLayout = (CardLayout) mainPanel.getLayout();

		jLabel3.setText("Mật khẩu mới:");

		capnhat.setBackground(new java.awt.Color(153, 255, 153));
		capnhat.setText("Cập nhật");
		capnhat.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				capnhatActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout nguoidungFormLayout = new javax.swing.GroupLayout(nguoidungForm);
		nguoidungForm.setLayout(nguoidungFormLayout);
		nguoidungFormLayout
				.setHorizontalGroup(nguoidungFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(nguoidungFormLayout.createSequentialGroup().addContainerGap().addComponent(jLabel3)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 193,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(capnhat).addContainerGap(489, Short.MAX_VALUE)));
		nguoidungFormLayout.setVerticalGroup(nguoidungFormLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(nguoidungFormLayout.createSequentialGroup().addContainerGap()
						.addGroup(nguoidungFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel3)
								.addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(capnhat))
						.addContainerGap(585, Short.MAX_VALUE)));

		mainPanel.add(nguoidungForm, "NguoiDung");


		jTextArea1.setColumns(20);
		jTextArea1.setRows(5);
		jScrollPane4.setViewportView(jTextArea1);

		javax.swing.GroupLayout ctctFormLayout = new javax.swing.GroupLayout(ctctForm);
		ctctForm.setLayout(ctctFormLayout);

		// Tạo các nút
		JButton backJButton = new JButton("Quay lại");
		JButton detailsJButton = new JButton("Xem chi tiết");

		// Thêm sự kiện cho nút quay lại
		backJButton.addActionListener(e -> {
			cardLayout.show(mainPanel, "LSCT");
		});

		// Thêm sự kiện cho nút xem chi tiết
		detailsJButton.addActionListener(e -> {

			int row = ctct_jtable.getSelectedRow();
			if (row == -1) {
				JOptionPane.showMessageDialog(null, "Vui lòng chọn một hàng trước khi xem chi tiết!", "Thông báo",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			participant_id = ptcp_model.getParticipantAt(row).getId();
			participant_name = ptcp_model.getParticipantAt(row).getName();

			cardLayout.show(mainPanel, "DetailForm");
		});

		// Tạo bảng với ParticipantTableModel
		ctct_jtable = new JTable();
		ctct_jtable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		JScrollPane tableScrollPane = new JScrollPane(ctct_jtable);

		// Thiết lập bố cục ngang
		ctctFormLayout.setHorizontalGroup(ctctFormLayout.createSequentialGroup().addContainerGap()
				.addGroup(ctctFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(ctctFormLayout.createSequentialGroup()
								.addComponent(backJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(detailsJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addComponent(tableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE))
				.addContainerGap());

		// Thiết lập bố cục dọc
		ctctFormLayout.setVerticalGroup(ctctFormLayout.createSequentialGroup().addContainerGap()
				.addGroup(ctctFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(backJButton).addComponent(detailsJButton))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
				.addComponent(tableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
				.addContainerGap());

		mainPanel.add(ctctForm, "CTCT");

		JPanel detailForm = new JPanel();
		javax.swing.GroupLayout detailFormLayout = new javax.swing.GroupLayout(detailForm);
		detailForm.setLayout(detailFormLayout);

		// Tạo nút "Quay lại"
		JButton backJButton1 = new JButton("Quay lại");
		backJButton1.addActionListener(e -> {
			cardLayout.show(mainPanel, "CTCT"); // Quay lại màn hình "CTCT"
			teacherController.camera = null;
			teacherController.screen = null;
		});

		JButton downloadClipJButton = new JButton("Download");
		downloadClipJButton.addActionListener(e -> {
			teacherController.downloadVideo(2);
		});
		
		// Tạo nút dạng "clip"
		JButton clipJButton = new JButton("Record screen"); // Biểu tượng clip
		clipJButton.addActionListener(e -> {
			teacherController.getVideoView(2);
		});
		
		JPanel clipPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		clipPanel.add(new JLabel("Screen:"));
		clipPanel.add(clipJButton);
		clipPanel.add(downloadClipJButton);

		JButton downloadCameraJButton = new JButton("Download");
		downloadCameraJButton.addActionListener(e -> {
			teacherController.downloadVideo(1);
		});
		
		JButton clipCameraJButton = new JButton("Record camera");
		clipCameraJButton.addActionListener(e -> {
			teacherController.getVideoView(1);
		});
		
		JPanel cameraPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		cameraPanel.add(new JLabel("Camera:"));
		cameraPanel.add(clipCameraJButton);
		cameraPanel.add(downloadCameraJButton);
		
		JButton downloadFileJButton = new JButton("Download");
		downloadFileJButton.addActionListener(e -> {

		});

		// Tạo nút dạng "file txt"
		JButton fileJButton = new JButton("Keyboard log"); // Biểu tượng file txt
		fileJButton.addActionListener(e -> {
			teacherController.showKeys(participant_id);
		});
		
		JPanel filePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		filePanel.add(new JLabel("File: "));
		filePanel.add(fileJButton);
		filePanel.add(downloadFileJButton);

		// Horizontal Group: Các panel căn trái và cách lề ra một chút
		detailFormLayout.setHorizontalGroup(
		    detailFormLayout
		        .createSequentialGroup()
		        .addGroup(detailFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		            .addComponent(backJButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addComponent(clipPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addComponent(cameraPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addComponent(filePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
		        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE) // Để giữ khoảng cách phải
		);


		// Vertical Group: Các panel sẽ được xếp tuần tự từ trên xuống
		detailFormLayout.setVerticalGroup(
		    detailFormLayout.createSequentialGroup()
		        .addContainerGap()
		        .addComponent(backJButton1) // Nút "Quay lại" ở trên cùng
		        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
		        .addComponent(clipPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
		        .addComponent(cameraPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
		        .addComponent(filePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		);





		// Thêm form này vào mainPanel
		mainPanel.add(detailForm, "DetailForm");

		getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);

		this.setVisible(true);

		setTitle("Giáo viên");
		
		pack();
	}

	private void trangchuActionPerformed(java.awt.event.ActionEvent evt) {
		cardLayout.show(mainPanel, "TrangChu");
	}

	private void lsctActionPerformed(java.awt.event.ActionEvent evt) {
		cardLayout.show(mainPanel, "LSCT");

		List<Test> data = teacherController.getListLSCT();

		lsct_model = new TestTableModel(data);
		lsct_jtable.setModel(lsct_model);
	}

	private void nguoidungActionPerformed(java.awt.event.ActionEvent evt) {
		cardLayout.show(mainPanel, "NguoiDung");
	}

	private void dangxuatActionPerformed(java.awt.event.ActionEvent evt) {
		teacherController.logout();
	}

	private void capnhatActionPerformed(java.awt.event.ActionEvent evt) {
		teacherController.capnhatmatkhau(password.getText());
	}

	private void lsct_xemchitietActionPerformed(java.awt.event.ActionEvent evt) {
		int selectedRow = lsct_jtable.getSelectedRow();

		if (selectedRow != -1) {
			Test selectedTest = lsct_model.getTestAt(selectedRow);
			int test_id = selectedTest.getId();
			cardLayout.show(mainPanel, "CTCT");
			List<Participant> data = teacherController.getListParticipant(test_id);
			ptcp_model = new ParticipantTableModel(data, test_id, selectedTest.getName());
			ctct_jtable.setModel(ptcp_model);
		} else {
			JOptionPane.showMessageDialog(null, "Vui lòng chọn một hàng trước khi xem chi tiết!", "Thông báo",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
	}

	private void trangchu_batdauActionPerformed(java.awt.event.ActionEvent evt) {
		teacherController.batdau(tencuocthi.getText());
	}

	private pbl4.Client.DTO.OutContest.TestTableModel lsct_model;
	public pbl4.Client.DTO.OutContest.ParticipantTableModel ptcp_model;
	private java.awt.CardLayout cardLayout;
	private javax.swing.JButton capnhat;
	private javax.swing.JPanel ctctForm;
	private javax.swing.JButton dangxuat;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane4;
	private javax.swing.JTextArea jTextArea1;
	private javax.swing.JTextField tencuocthi;
	private javax.swing.JButton lsct;
	private javax.swing.JPanel lsctForm;
	private javax.swing.JTable lsct_jtable;
	private javax.swing.JTable ctct_jtable;
	private javax.swing.JButton lsct_xemchitiet;
	private javax.swing.JPanel mainPanel;
	private javax.swing.JButton nguoidung;
	private javax.swing.JPanel nguoidungForm;
	private javax.swing.JPasswordField password;
	private javax.swing.JButton trangchu;
	private javax.swing.JPanel trangchuForm;
	private javax.swing.JButton trangchu_batdau;
	private javax.swing.JButton xemtientrinhtai;
	public Integer participant_id = null;
	public String participant_name = null;

	public static void main(String[] args) {
		new TeacherDashboard(null);
	}
}
