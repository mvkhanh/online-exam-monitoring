package pbl4.Server.DAO;

import pbl4.Server.Entity.User;

public class UsersDAO {
    private static final GenericDAO<User> genericDAO = new GenericDAO<>(rs -> 
        new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"))
    );

    public static User login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        return genericDAO.find(sql, username, password);
    }

    public static Integer register(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ?";
        User existingUser = genericDAO.find(sql, username);

        if (existingUser == null) {
            sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            return genericDAO.add(sql, username, password);
        }
        return -1; // User already exists
    }

    public static void updatePassword(String userId, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE id = ?";
        genericDAO.updateOrDelete(sql, newPassword, userId);
    }
}
