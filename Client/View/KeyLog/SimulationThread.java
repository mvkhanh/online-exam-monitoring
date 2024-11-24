package pbl4.Client.View.KeyLog;

import java.util.ArrayList;

public class SimulationThread extends Thread {
	private KeyLog par;
	private String[] lines = new String[200];
	int currLine = 0;
	int cursor = 0;
	int idx = 0;
	boolean isCapital = false;
	boolean isShift = false;

	public SimulationThread(KeyLog par) {
		this.par = par;
	}

	public void run() {
		ArrayList<Double> times = par.times;
		ArrayList<Character> keys = par.keys;
		lines[0] = "";
		while (idx < keys.size()) {
			if (par.isPlaying) {
				char c = keys.get(idx);
				double duration = -1;
				if (idx < keys.size() - 1)
					duration = times.get(idx + 1) - times.get(idx);
				readS(c, duration);
				idx++;
			} else {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		StringBuilder finalText = new StringBuilder();
		for (String line : lines) {
			if (line != null) {
				finalText.append(line).append("\n");
			}
		}
		par.ta.setText(finalText.toString());
		par.isPlaying = false;
		par.simulation = null;
		par.start.setText("▶");
	}

	public void readS(char c, double duration) {

		switch (c) {
		case '⇧':
			isCapital = !isCapital;
			isShift = !isShift;
			break;
		case '⇪':
			isCapital = !isCapital;
			break;
		case '⇥':
			lines[currLine] = insertAt(lines[currLine], cursor, '\t');
			cursor++;
			break;
		case '⏎':
			down1Line(lines, currLine, cursor);
			currLine++;
			cursor = 0;
			break;
		case '⌫':
			if (cursor > 0) {
				lines[currLine] = deleteAt(lines[currLine], cursor - 1);
				cursor--;
			} else if (currLine > 0) {
				currLine--;
				cursor = lines[currLine].length() - 1;
			}
			break;
		case '␣':
			lines[currLine] = insertAt(lines[currLine], cursor, ' ');
			cursor++;
			break;
		case '←':
			if (cursor > 0)
				cursor--;
			else if (currLine > 0) {
				currLine--;
				cursor = lines[currLine].length() - 1;
			}
			break;
		case '→':
			if (cursor < lines[currLine].length())
				cursor++;
			else if (lines[currLine + 1] != null) {
				currLine++;
				cursor = 0;
			}
			break;
		case '↑':
			if (currLine > 0) {
				currLine--;
				cursor = Math.min(lines[currLine].length(), cursor);
			} else
				cursor = 0;
			break;
		case '↓':
			if (lines[currLine + 1] != null) {
				currLine++;
				cursor = Math.min(lines[currLine].length(), cursor);
			} else
				cursor = lines[currLine].length();
			break;
		default:
			char tmp = c;
			if (KeyLog.shiftMap.get(c) != null && isShift)
				tmp = KeyLog.shiftMap.get(c);
			else if (Character.isLetter(c) && !isCapital)
				tmp = Character.toLowerCase(c);

			lines[currLine] = insertAt(lines[currLine], cursor, tmp);
			cursor++;
			break;
		}
		updateText(lines, currLine, cursor);
		if (duration != -1) {
			try {
				Thread.sleep((long) (duration / par.speed));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	private String insertAt(String s, int idx, char c) {
		return s.substring(0, idx) + c + s.substring(idx);
	}

	private String deleteAt(String s, int idx) {
		return s.substring(0, idx) + s.substring(idx + 1);
	}

	private void down1Line(String[] lines, int currLine, int cursor) {
		for (int i = lines.length - 1; i >= 0; i--) {
			if (lines[i] == null)
				continue;
			if (i <= currLine)
				break;
			lines[i + 1] = lines[i];
		}
		lines[currLine + 1] = lines[currLine].substring(cursor);
		lines[currLine] = lines[currLine].substring(0, cursor);
	}

	private void updateText(String[] lines, int currLine, int cursor) {
		StringBuilder display = new StringBuilder();
		for (int i = 0; i < lines.length; i++) {
			if (lines[i] == null)
				break;
			if (i == currLine) {
				display.append(lines[i], 0, cursor).append("|").append(lines[i].substring(cursor)).append("\n");
			} else {
				display.append(lines[i]).append("\n");
			}

		}
		par.ta.setText(display.toString());
	}
}
