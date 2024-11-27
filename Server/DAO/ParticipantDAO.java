package pbl4.Server.DAO;

import java.io.File;
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
        int participant_id = genericDAO.add(sql, testId, name);
        createNewParticipantFolder(participant_id);
        return participant_id;
    }
    
    public static void createNewParticipantFolder(int participant_id) {
        String folderPath = "D:/real_pbl4/participant" + participant_id;

        File folder = new File(folderPath);
        
        if(!folder.exists()) {
        	folder.mkdirs();
        }
    }
}