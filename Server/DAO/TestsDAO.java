package pbl4.Server.DAO;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import pbl4.Server.Entity.Test;
import pbl4.Server.Utils.DBHelper;

public class TestsDAO {
//	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static List<Test> listTest(String user_id) {
		List<Test> listData = new ArrayList<Test>();
		try {
			String sql = "SELECT * FROM tests WHERE user_id = ?";
			ResultSet rs = DBHelper.GetDBHelper().GetRecords(sql, user_id);
			while (rs.next()) {
				Test t = new Test(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getTimestamp(4));
				listData.add(t);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DBHelper.GetDBHelper().CloseConn();
		}
		return listData;
	}

	public static Integer addTest(String user_id, String name) {
		try {
			String sql = "insert into tests (user_id, name, created_at) values(?,?,?)";
			return DBHelper.GetDBHelper().addAndReturnId(sql, user_id, name, LocalDate.now());
		} catch(Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			DBHelper.GetDBHelper().CloseConn();
		}
	}

	public static void deleteTest(int test_id) {
		try {
			String sql = "delete from tests where id=?";
			DBHelper.GetDBHelper().ExecuteDB(sql, test_id);
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			DBHelper.GetDBHelper().CloseConn();
		}
	}

}
