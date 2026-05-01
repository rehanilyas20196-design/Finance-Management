package DBData;

import db.DBConnection;
import MainClasses.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDAO {

    public boolean registerUser(User user) {

        String sql = "INSERT INTO users " +
                     "(full_name, email, password_hash, banana_string, cnic, phone) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.connect();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, user.getFullName());
            pst.setString(2, user.getEmail());
            pst.setString(3, user.getPasswordHash());

            // IMPORTANT: still using bananaHash from Java, but DB column is banana_string
            pst.setString(4, user.getBananaHash());

            pst.setString(5, user.getCnic());
            pst.setString(6, user.getPhone());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}