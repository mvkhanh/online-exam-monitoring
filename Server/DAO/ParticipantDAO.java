package pbl4.Server.DAO;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import pbl4.Server.Entity.Participant;
import pbl4.Server.Utils.DBHelper;

public class ParticipantDAO {
	
	public static List<Participant> listParticipant(String test_id) {
		List<Participant> listData = new ArrayList<>();
		try {
			String sql = "SELECT * FROM participants WHERE test_id = ?";
			ResultSet rs = DBHelper.GetDBHelper().GetRecords(sql, test_id);
			while (rs.next()) {
				Participant t = new Participant(rs.getInt(1), rs.getInt(2), rs.getString(3));
				listData.add(t);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DBHelper.GetDBHelper().CloseConn();
		}
		return listData;
	}

	public static Integer addParticipant(int test_id, String name) {
		try {
			String sql = "insert into participants (test_id, name) values(?,?)";
			return DBHelper.GetDBHelper().addAndReturnId(sql, test_id, name);
		} catch(Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			DBHelper.GetDBHelper().CloseConn();
		}
	}
}
