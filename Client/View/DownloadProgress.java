package pbl4.Client.View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class DownloadProgress extends JFrame {

	List<JLabel> listData = new ArrayList<JLabel>();
	JPanel view = new JPanel();

	public DownloadProgress() {

		view.setLayout(new BoxLayout(view, BoxLayout.Y_AXIS));
		view.setPreferredSize(new Dimension(920, 2000));

		JScrollPane scrollPane = new JScrollPane(view);
		scrollPane.setPreferredSize(new Dimension(850, 400));

		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
			}
		});

		setTitle("Tiến trình tải");
		
		pack();
	}

	public void addProgress(JLabel label) {
		listData.add(label);
		view.add(label);
		view.add(Box.createVerticalStrut(2));
	}
}
