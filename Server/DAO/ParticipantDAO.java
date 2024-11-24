package pbl4.Server.DAO;

import java.util.List;

import pbl4.Server.Entity.Participant;

public class ParticipantDAO {
    private static final GenericDAO<Participant> genericDAO = new GenericDAO<>(rs -> 
        new Participant(rs.getInt("id"), rs.getInt("test_id"), rs.getString("name"))
    );

    public static List<Participant> listParticipants(String testId) {
        String sql = "SELECT * FROM participants WHERE test_id = ?";
        return genericDAO.list(sql, testId);
    }

    public static Integer addParticipant(int testId, String name) {
        String sql = "INSERT INTO participants (test_id, name) VALUES (?, ?)";
        return genericDAO.add(sql, testId, name);
    }
}