package pbl4.Server.DAO;

import java.time.LocalDate;
import java.util.List;

import pbl4.Server.Entity.Test;

public class TestDAO {
    private static final GenericDAO<Test> genericDAO = new GenericDAO<>(rs -> 
        new Test(rs.getInt("id"), rs.getInt("user_id"), rs.getString("name"), rs.getTimestamp("created_at"))
    );

    public static List<Test> listTests(String userId) {
        String sql = "SELECT * FROM tests WHERE user_id = ?";
        return genericDAO.list(sql, userId);
    }

    public static Integer addTest(String userId, String name) {
        String sql = "INSERT INTO tests (user_id, name, created_at) VALUES (?, ?, ?)";
        return genericDAO.add(sql, userId, name, LocalDate.now());
    }

    public static void deleteTest(int testId) {
        String sql = "DELETE FROM tests WHERE id = ?";
        genericDAO.updateOrDelete(sql, testId);
    }
}
