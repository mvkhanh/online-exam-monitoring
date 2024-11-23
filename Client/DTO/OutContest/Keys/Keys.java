package pbl4.Client.DTO.OutContest.Keys;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class Keys extends JFrame {
	public static Map<Character, Character> shiftMap = new HashMap<>();
	static {
		shiftMap.put('1', '!');
		shiftMap.put('2', '@');
		shiftMap.put('3', '#');
		shiftMap.put('4', '$');
		shiftMap.put('5', '%');
		shiftMap.put('6', '^');
		shiftMap.put('7', '&');
		shiftMap.put('8', '*');
		shiftMap.put('9', '(');
		shiftMap.put('0', ')');
		shiftMap.put('-', '_');
		shiftMap.put('=', '+');
		shiftMap.put('[', '{');
		shiftMap.put(']', '}');
		shiftMap.put('\\', '|');
		shiftMap.put(';', ':');
		shiftMap.put('\'', '"');
		shiftMap.put(',', '<');
		shiftMap.put('.', '>');
		shiftMap.put('/', '?');
		shiftMap.put('`', '~');
	}

	public JTextArea ta;
	public String s = "";
	public boolean isPlaying;
	public JButton start;
	public Thread simulation;
	public double speed = 1;

	public Keys(int participant_id) {

		setLayout(new BorderLayout());
		setBounds(200, 200, 500, 500);

		JPanel topPn = new JPanel(new FlowLayout());
		
		start = new JButton("▶");
		topPn.add(start);
		topPn.add(new JLabel("Tốc độ mô phỏng:"));
		JComboBox<Double> comboBox = new JComboBox<>(new Double[] { 0.25, 0.5, 1.0, 2.0, 5.0, 10.0, 20.0 });
		comboBox.setSelectedItem(1.0);
		comboBox.setEditable(true);
		topPn.add(comboBox);
		
		ta = new JTextArea();
		ta.setEditable(false);

		add(topPn, BorderLayout.NORTH);
		add(ta, BorderLayout.CENTER);
		setVisible(true);

		start.addActionListener(e -> {
			if (start.getText().equals("▶")) {
				start.setText("⏸");
				isPlaying = true;
				if (simulation == null)
					startThread();
			} else {
				isPlaying = false;
				start.setText("▶");
			}
		});
		comboBox.addActionListener(e -> {
			speed = (double)comboBox.getSelectedItem();
		});

	}

	private void startThread() {
		simulation = new SimulationThread(this);
		simulation.start();
	}


//	public void nativeKeyPressed(NativeKeyEvent e) {
//		char c = NativeKeyEvent.getKeyText(e.getKeyCode()).charAt(0);
//		if (e.getKeyCode() == 0xe36)
//			c = '⇧';
//		if (c == 'Q') {
//			System.out.println(s);
//			s = "";
//		} else {
//			s += System.currentTimeMillis() + " " + c + " ";
//		}
//	}
//
//	public void nativeKeyReleased(NativeKeyEvent e) {
//		char c = NativeKeyEvent.getKeyText(e.getKeyCode()).charAt(0);
//		if (e.getKeyCode() == 0xe36)
//			c = '⇧';
//		if (c == '⇧' || c == '⌘' || c == '⌥' || c == '⌃') {
//			s += System.currentTimeMillis() + " " + c + " ";
//		}
//	}

}
//|| c == '⌘' || c == '⌥' || c == '⇥' || c == '⌃' || c == '⇪' || c == '⏎' || e.getKeyCode() == 0xe36
//Command, option, tab, control, 

//Khi nhan can them vao: tat ca phim
//Khi tha can them vao: shift, control, command, option
//←→↑↓
//⏎⇧3⇧INCLUDE⇧,⇧IOSTREAM⇧.⇧⏎USINGSF␣NAESE␣STD;⏎⏎INT␣MAIN⇧9⇧⇧0⇧⇧[⇧⏎⇥INT␣A␣=␣3;⏎COUT␣⇧,,⇧␣A␣⇧,,⇧␣⇧,⇧⌫ENDL;⏎↑↑↑↑↑→→→→→→→⌫⌫→→→→→→⌫⌫⌫MESPACE→→→→→⏎⏎VOID␣B⇧90[⇧⏎⇥INT␣A=␣3.⌫;⏎COUT␣⇧,,␣⇧A␣⇧,,␣⇧ENDL;
