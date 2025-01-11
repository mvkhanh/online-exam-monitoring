package pbl4.Client.View.Utils;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class LogPanel extends JPanel{
	
	public JTextArea logArea;
	
	public LogPanel(String name) {
		super();
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(250, 300));
		this.add(new JLabel(name), BorderLayout.NORTH);
		this.logArea = new JTextArea();
		this.logArea.setEditable(false);
		this.add(new JScrollPane(logArea), BorderLayout.CENTER);
	}
	
	public void addText(String msg) {
		this.logArea.append(msg);
	}
}
