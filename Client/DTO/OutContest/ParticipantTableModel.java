package pbl4.Client.DTO.OutContest;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ParticipantTableModel extends AbstractTableModel {
	public int test_id;
	public String test_name;
	private List<Participant> data;
	private final String[] columnNames = { "ID", "Name"};

	public ParticipantTableModel(List<Participant> data, int test_id, String test_name) {
		this.data = data;
		this.test_id = test_id;
		this.test_name = test_name;
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Participant test = data.get(rowIndex);
        switch (columnIndex) {
            case 0: // Cột ID
                return test.getId();
            case 1: // Cột Name
                return test.getName();
            default:
                return null; // Không có cột nào khác
        }
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	public Participant getParticipantAt(int rowIndex) {
		return data.get(rowIndex);
	}
}
