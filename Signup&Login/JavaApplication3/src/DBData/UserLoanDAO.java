package DBData;

import db.DBConnection; // Correctly referencing your connection utility
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object for Loan-related database operations.
 * Located in the DBData package.
 */
public class UserLoanDAO {

    public ResultSet getActiveLoanDetails(int userId) {
        // Query matches the fields required for the LoanDetails GUI
        String sql = "SELECT loan_amount, interest_rate, duration_years, monthly_emi, " +
                     "start_date, end_date, total_payable, remaining_amount, status " +
                     "FROM loans " +
                     "WHERE user_id = ? AND status = 'Active' " +
                     "LIMIT 1";

        try {
            // Using your project's specific connection method
            Connection conn = DBConnection.connect();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, userId);
            
            // Returns the live data stream to the calling JPanel
            return pst.executeQuery();
            
        } catch (SQLException e) {
            System.err.println("Database Error: Could not retrieve loan record for ID " + userId);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Optional method to fetch all historical loans for a user.
     */
    public ResultSet getAllLoans(int userId) {
        String sql = "SELECT * FROM loans WHERE user_id = ? ORDER BY created_at DESC";
        
        try {
            Connection conn = DBConnection.connect();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, userId);
            return pst.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}