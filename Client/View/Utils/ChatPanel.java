package pbl4.Client.View.Utils;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pbl4.Client.Controller.InContestBaseController;

public class ChatPanel extends LogPanel {

	public ChatPanel(InContestBaseController controller) {
		super("Chat");
		JPanel inputPanel = new JPanel(new BorderLayout());
		JTextField chatInput = new JTextField(15);
		JButton sendButton = new JButton("Send");
		inputPanel.add(chatInput, BorderLayout.CENTER);
		inputPanel.add(sendButton, BorderLayout.EAST);
		this.add(inputPanel, BorderLayout.SOUTH);
		chatInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	sendButton.doClick();
            }
        });
		sendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String msg = chatInput.getText();
				chatInput.setText("");
				controller.sendMessage(msg);
			}
		});
	}
}
