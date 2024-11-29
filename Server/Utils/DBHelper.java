package pbl4.Server.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBHelper {
	private final String dbName = "PBL4_2";
	private final String userID = "root";
	private final String password = "";
	private final String url = "jdbc:mysql://127.0.0.1:3306/" + dbName;
	private Connection conn;
	private PreparedStatement ps;
	private static DBHelper _Instance;

	public static DBHelper GetDBHelper() {
		if (_Instance == null) {
			_Instance = new DBHelper();
		}
		return _Instance;
	}

	private DBHelper() {
	}

	public ResultSet GetRecords(String sql, Object... parameterValues) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(url, userID, password);
		ps = conn.prepareStatement(sql);
		for (int i = 0; i < parameterValues.length; i++) {
			ps.setObject(i + 1, parameterValues[i]);
		}
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public void ExecuteDB(String sql, Object... parameterValues) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(url, userID, password);
		ps = conn.prepareStatement(sql);
		for (int i = 0; i < parameterValues.length; i++) {
			ps.setObject(i + 1, parameterValues[i]);
		}
		ps.executeUpdate();
	}

	public int addAndReturnId(String sql, Object... parameterValues) throws ClassNotFoundException, SQLException {
		int generatedId = -1; // Biến để lưu ID vừa được tạo
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, userID, password);

			// Chuẩn bị câu lệnh với RETURN_GENERATED_KEYS
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			for (int i = 0; i < parameterValues.length; i++) {
				ps.setObject(i + 1, parameterValues[i]);
			}

			// Thực thi câu lệnh thêm
			ps.executeUpdate();

			// Lấy ID vừa được tạo
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				generatedId = rs.getInt(1); // Lấy ID từ cột đầu tiên
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// Đảm bảo đóng PreparedStatement và Connection
			CloseConn();
		}

		return generatedId; // Trả về ID vừa được thêm
	}

	public void CloseConn() {
		try {
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
		} catch (Exception ex) {
			System.out.println("Error " + ex);
		}
	}
}
