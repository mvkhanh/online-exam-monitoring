package pbl4.Client.View.KeyLog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

public class KeyLog extends JFrame {
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
	ArrayList<Double> times = new ArrayList<>();
	ArrayList<Character> keys = new ArrayList<>();
	public String raw = "";
	public boolean isPlaying;
	public JButton start;
	public Thread simulation;
	public double speed = 1;

	public KeyLog(String s) {
		refine(s);
		setLayout(new BorderLayout());
		setBounds(200, 200, 500, 500);

		JTabbedPane tabbedPane = new JTabbedPane();
		
		JPanel rawTab = createRawTab();
		tabbedPane.addTab("Raw", rawTab);
		JPanel simulationTab = createSimulationTab();
		tabbedPane.addTab("Mô phỏng", simulationTab);
		
		add(tabbedPane, BorderLayout.CENTER);
		setVisible(true);
	}
	private JPanel createSimulationTab() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel topPn = new JPanel(new FlowLayout());
        start = new JButton("▶");
        topPn.add(start);
        topPn.add(new JLabel("Tốc độ mô phỏng:"));
        JComboBox<Double> comboBox = new JComboBox<>(new Double[]{0.25, 0.5, 1.0, 2.0, 5.0, 10.0, 20.0});
        comboBox.setSelectedItem(1.0);
        comboBox.setEditable(true);
        topPn.add(comboBox);

        ta = new JTextArea();
        ta.setEditable(false);

        panel.add(topPn, BorderLayout.NORTH);
        panel.add(new JScrollPane(ta), BorderLayout.CENTER);

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
            speed = (double) comboBox.getSelectedItem();
        });

        return panel;
    }

    private JPanel createRawTab() {
        JPanel panel = new JPanel(new BorderLayout());

        JTextArea rawTextArea = new JTextArea();
        rawTextArea.setEditable(false);
        rawTextArea.setText(raw);

        panel.add(new JScrollPane(rawTextArea), BorderLayout.CENTER);
        return panel;
    }
	private void startThread() {
		simulation = new SimulationThread(this);
		simulation.start();
	}
	
	private void refine(String s) {
		String[] lines = s.split("\n");
		for(String line : lines) {
			String[] splits = line.split(" ");
			raw += splits[0] + "-" + splits[1] + ": ";
			for(int i = 2; i < splits.length; i += 2) {
				times.add(Double.valueOf(splits[i]));
				keys.add(splits[i + 1].charAt(0));
				raw += splits[i + 1] + " ";
			}
			raw += "\n";
		}
	}
}
//|| c == '⌘' || c == '⌥' || c == '⇥' || c == '⌃' || c == '⇪' || c == '⏎' || e.getKeyCode() == 0xe36
//Command, option, tab, control, 

//Khi nhan can them vao: tat ca phim
//Khi tha can them vao: shift, control, command, option
//←→↑↓
