package DBData;

import db.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class dashboarddata {

 
     
    public double[] getAccountSummary(int userId) {
        double[] summary = new double[3]; // [balance, totalDebit, totalCredit]
        
        String sql = "SELECT " +
                     "SUM(CASE WHEN type = 'Credit' THEN amount ELSE -amount END) as balance, " +
                     "SUM(CASE WHEN type = 'Debit' THEN amount ELSE 0 END) as total_debit, " +
                     "SUM(CASE WHEN type = 'Credit' THEN amount ELSE 0 END) as total_credit " +
                     "FROM transactions WHERE user_id = ? AND status = 'Completed'";

        try (Connection conn = DBConnection.connect();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                summary[0] = rs.getDouble("balance");
                summary[1] = rs.getDouble("total_debit");
                summary[2] = rs.getDouble("total_credit");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return summary;
    }

    /**
     * Fetches the 10 most recent transactions for the JTable.
     */
    public ResultSet getRecentTransactions(int userId) {
        String sql = "SELECT id, entity, type, amount, created_at, status " +
                     "FROM transactions " +
                     "WHERE user_id = ? " +
                     "ORDER BY created_at DESC LIMIT 10";

        try {
            Connection conn = DBConnection.connect();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, userId);
            // Note: In a real app, convert this ResultSet into a List of Objects 
            // before closing the connection.
            return pst.executeQuery(); 
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}