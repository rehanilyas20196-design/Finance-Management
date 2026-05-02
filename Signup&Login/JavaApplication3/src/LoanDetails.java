import DBData.UserLoanDAO;
import java.awt.*;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.border.EmptyBorder; // Importing from your specific DBData package

/**
 * Full implementation of the Loan Details panel.
 * Matches the UI aesthetics of the provided reference image.
 */
public class LoanDetails extends JPanel {
    // Theme Palette for a modern Fintech look
    private final Color BACKGROUND = new Color(248, 250, 252);
    private final Color TEXT_PRIMARY = new Color(30, 41, 59);
    private final Color TEXT_SECONDARY = new Color(100, 116, 139);
    private final Color BADGE_GREEN_BG = new Color(220, 252, 231);
    private final Color BADGE_GREEN_TXT = new Color(21, 128, 61);
    private final Color BORDER_COLOR = new Color(226, 232, 240);

    // GUI Components for live data
    private JLabel lblAmount, lblInterest, lblDuration, lblEMI;
    private JLabel lblStartDate, lblEndDate, lblTotalPayable, lblRemaining;
    private JLabel statusBadge;
    
    // Set to your specific User ID
    private final int currentUserId = 3; 

    public LoanDetails() {
        setBackground(BACKGROUND);
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(40, 40, 40, 40));

        // 1. Main White Card Container
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));

        // 2. Header Section (Title + Status Badge)
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(new EmptyBorder(25, 30, 20, 30));

        JLabel title = new JLabel("Loan Details");
        title.setFont(new Font("Inter", Font.BOLD, 22));
        title.setForeground(TEXT_PRIMARY);

        statusBadge = new JLabel(" LOAN ACTIVE ");
        statusBadge.setOpaque(true);
        statusBadge.setBackground(BADGE_GREEN_BG);
        statusBadge.setForeground(BADGE_GREEN_TXT);
        statusBadge.setFont(new Font("Inter", Font.BOLD, 11));
        statusBadge.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));
        
        header.add(title, BorderLayout.WEST);
        header.add(statusBadge, BorderLayout.EAST);
        card.add(header, BorderLayout.NORTH);

        // 3. Information Grid (2 Columns x 4 Rows)
        JPanel grid = new JPanel(new GridLayout(4, 2, 60, 35));
        grid.setBackground(Color.WHITE);
        grid.setBorder(new EmptyBorder(10, 30, 40, 30));

        // Adding row data groups
        grid.add(createDataGroup("Amount", lblAmount = new JLabel("₹0.00")));
        grid.add(createDataGroup("Interest Rate", lblInterest = new JLabel("0.00%")));
        grid.add(createDataGroup("Duration", lblDuration = new JLabel("0 years")));
        grid.add(createDataGroup("Monthly EMI", lblEMI = new JLabel("₹0.00")));
        grid.add(createDataGroup("Loan Start Date", lblStartDate = new JLabel("-")));
        grid.add(createDataGroup("Loan End Date", lblEndDate = new JLabel("-")));
        grid.add(createDataGroup("Total Amount (P+I)", lblTotalPayable = new JLabel("₹0.00")));
        grid.add(createDataGroup("Remaining Amount", lblRemaining = new JLabel("₹0.00")));

        card.add(grid, BorderLayout.CENTER);

        // 4. Footer Section
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(Color.WHITE);
        footer.setBorder(new EmptyBorder(0, 30, 30, 30));

        JLabel footerText = new JLabel("<html>If you have any queries regarding loan or need any help, you can either connect<br>" +
                                       "with your asset manager or contact our customer service by clicking here.</html>");
        footerText.setFont(new Font("Inter", Font.PLAIN, 12));
        footerText.setForeground(TEXT_SECONDARY);
        footer.add(footerText, BorderLayout.WEST);
        
        card.add(footer, BorderLayout.SOUTH);

        add(card, BorderLayout.NORTH); 
        
        // Fetch data immediately upon creation
        fetchLoanDataFromDatabase();
    }

    /**
     * Helper to create the stylized Label/Value pairs.
     */
    private JPanel createDataGroup(String labelStr, JLabel valueLabel) {
        JPanel group = new JPanel(new GridLayout(2, 1, 0, 8));
        group.setOpaque(false);
        
        JLabel title = new JLabel(labelStr);
        title.setFont(new Font("Inter", Font.PLAIN, 13));
        title.setForeground(TEXT_SECONDARY);
        
        valueLabel.setFont(new Font("Inter", Font.BOLD, 18));
        valueLabel.setForeground(TEXT_PRIMARY);
        
        group.add(title);
        group.add(valueLabel);
        return group;
    }

    /**
     * Logic to pull live data from the UserLoanDAO.
     */
    private void fetchLoanDataFromDatabase() {
        UserLoanDAO dao = new UserLoanDAO();
        try (ResultSet rs = dao.getActiveLoanDetails(currentUserId)) {
            if (rs != null && rs.next()) {
                // Formatting currency values
                lblAmount.setText("₹" + String.format("%,.0f", rs.getDouble("loan_amount")));
                lblInterest.setText(String.format("%.2f", rs.getDouble("interest_rate")) + "%");
                lblDuration.setText(rs.getInt("duration_years") + " years");
                lblEMI.setText("₹" + String.format("%,.0f", rs.getDouble("monthly_emi")));
                
                // Timeline Dates
                lblStartDate.setText(rs.getDate("start_date").toString());
                lblEndDate.setText(rs.getDate("end_date").toString());
                
                // Balance Summaries
                lblTotalPayable.setText("₹" + String.format("%,.0f", rs.getDouble("total_payable")));
                lblRemaining.setText("₹" + String.format("%,.0f", rs.getDouble("remaining_amount")));
                
                // Dynamic Status Badge
                statusBadge.setText(" LOAN " + rs.getString("status").toUpperCase() + " ");
            }
        } catch (Exception e) {
            System.err.println("GUI Error: Unable to populate LoanDetails fields.");
            e.printStackTrace();
        }
    }
}