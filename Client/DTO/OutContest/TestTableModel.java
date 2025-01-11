package pbl4.Client.DTO.OutContest;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class TestTableModel extends AbstractTableModel {
	private List<Test> data;
	private final String[] columnNames = { "ID", "Name", "Created At" };

	public TestTableModel(List<Test> data) {
		this.data = data;
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
		Test test = data.get(rowIndex);
        switch (columnIndex) {
            case 0: // Cột ID
                return test.getId();
            case 1: // Cột Name
                return test.getName();
            case 2: // Cột Created At
                return test.getCreated_at();
            default:
                return null; // Không có cột nào khác
        }
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	public Test getTestAt(int rowIndex) {
		return data.get(rowIndex);
	}
}
