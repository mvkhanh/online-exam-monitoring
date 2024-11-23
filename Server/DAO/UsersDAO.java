package pbl4.Server.DAO;

import java.sql.ResultSet;

import pbl4.Server.Utils.DBHelper;

public class UsersDAO {

	public static int login(String username, String password) {
		try {
			String sql = "Select * from users where username = ? and password = ?";
			ResultSet rs = DBHelper.GetDBHelper().GetRecords(sql, username, password);
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DBHelper.GetDBHelper().CloseConn();
		}
		return -1;
	}

	public static int register(String username, String password) {
		try {
			String sql = "Select * from users where username = ?";
			ResultSet rs = DBHelper.GetDBHelper().GetRecords(sql, username);
			if (!rs.next()) {
				// insert user
				sql = "insert into users(username, password) values(?,?)";
				DBHelper.GetDBHelper().ExecuteDB(sql, username, password);
				sql = "select * from users where username = ?";
				rs = DBHelper.GetDBHelper().GetRecords(sql, username);
				rs.next();
				return rs.getInt(1);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DBHelper.GetDBHelper().CloseConn();
		}
		return -1;
	}

	public static void updatePassword(String user_id, String password) {
		try {
			String sql = "update users set password = ? where id = ?";
			DBHelper.GetDBHelper().ExecuteDB(sql, password, user_id);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DBHelper.GetDBHelper().CloseConn();
		}

	}
}
