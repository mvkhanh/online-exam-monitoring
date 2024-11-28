package pbl4.Client.View;

import pbl4.Client.Controller.AuthenticationController;

public class Register extends javax.swing.JFrame {

	public Register() {
		initComponents();
	}

	private void initComponents() {

		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		password = new javax.swing.JPasswordField();
		username = new javax.swing.JTextField();
		register = new javax.swing.JButton();
		jLabel3 = new javax.swing.JLabel();
		login = new javax.swing.JButton();
		jLabel4 = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setAutoRequestFocus(false);
		setResizable(false);

		jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
		jLabel1.setText("Tài khoản:");

		jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
		jLabel2.setText("Mật khẩu:");

		register.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
		register.setText("Đăng ký");
		register.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				registerActionPerformed(evt);
			}
		});

		jLabel3.setText("Đã có tài khoản?");

		login.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
		login.setText("Đăng nhập");
		login.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				loginActionPerformed(evt);
			}
		});

		jLabel4.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
		jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel4.setText("Ứng dụng livestream giám sát cuộc thi online");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup()
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jLabel4,
								javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGroup(layout.createSequentialGroup().addGap(48, 48, 48).addGroup(layout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
								.addGroup(layout.createSequentialGroup().addComponent(jLabel3)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(login))
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(layout.createSequentialGroup().addGap(126, 126, 126).addComponent(
												register, javax.swing.GroupLayout.PREFERRED_SIZE, 124,
												javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGroup(layout.createSequentialGroup()
												.addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 69,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 238,
														javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGroup(layout.createSequentialGroup()
												.addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 69,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 238,
														javax.swing.GroupLayout.PREFERRED_SIZE))))))
				.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addGap(28, 28, 28)
				.addComponent(
						jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addGap(41, 41, 41)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				.addGap(18, 18, 18).addComponent(register).addGap(58, 58, 58)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(login)
						.addComponent(jLabel3))
				.addContainerGap(109, Short.MAX_VALUE)));

		setTitle("Đăng ký");
		
		pack();
	}

	private void registerActionPerformed(java.awt.event.ActionEvent evt) {
		if(AuthenticationController.handleRegisterAction(username.getText(), password.getText()))
			this.dispose();
	}

	private void loginActionPerformed(java.awt.event.ActionEvent evt) {
		this.dispose();
		new Login().setVisible(true);
	}

	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JButton login;
	private javax.swing.JPasswordField password;
	private javax.swing.JButton register;
	private javax.swing.JTextField username;
}
