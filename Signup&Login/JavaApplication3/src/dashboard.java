import DBData.dashboarddata;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.border.EmptyBorder; // Added this import
import javax.swing.table.*;  // Added this import

public class dashboard extends JFrame {

    // Modern Color Palette
    private final Color SIDEBAR_COLOR = new Color(15, 23, 42); 
    private final Color BACKGROUND_COLOR = new Color(248, 250, 252);
    private final Color ACCENT_BLUE = new Color(59, 130, 246); 
    private final Color TEXT_PRIMARY = new Color(30, 41, 59);
    private final Color CARD_WHITE = Color.WHITE;

    private JPanel sidePanel, mainContent, cardsContainer;
    private JTable transactionTable;
    private DefaultTableModel tableModel;

    // --- NEW FIELDS FOR DATA ---
    private dashboarddata dashboardDAO = new dashboarddata();
    private int currentUserId = 3; 
    private JLabel lblBalance, lblDebit, lblCredit;
    // ---------------------------

    public dashboard() {
        setTitle("NextGen Bank | Premium Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 800);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND_COLOR);
        setLayout(new BorderLayout());

        initSidebar();

        JPanel rightContainer = new JPanel(new BorderLayout());
        rightContainer.setBackground(BACKGROUND_COLOR);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BACKGROUND_COLOR);
        header.setBorder(new EmptyBorder(30, 40, 10, 40));
        
        JLabel title = new JLabel("Platform Overview");
        title.setFont(new Font("Inter", Font.BOLD, 28));
        title.setForeground(TEXT_PRIMARY);
        header.add(title, BorderLayout.WEST);
        
        rightContainer.add(header, BorderLayout.NORTH);

        mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setBackground(BACKGROUND_COLOR);
        mainContent.setBorder(new EmptyBorder(20, 40, 40, 40));

        initCards();
        mainContent.add(cardsContainer);
        mainContent.add(Box.createRigidArea(new Dimension(0, 40)));

        initTableSection();
        
        rightContainer.add(mainContent, BorderLayout.CENTER);
        add(rightContainer, BorderLayout.CENTER);

        // --- FETCH DATA ON STARTUP ---
        loadSupabaseData();
    }

    // --- NEW METHOD: LOAD DATA FROM SUPABASE ---
    private void loadSupabaseData() {
        // 1. Update Cards
        double[] stats = dashboardDAO.getAccountSummary(currentUserId);
        lblBalance.setText("$" + String.format("%,.2f", stats[0]));
        lblDebit.setText("$" + String.format("%,.2f", stats[1]));
        lblCredit.setText("$" + String.format("%,.2f", stats[2]));

        // 2. Update Table
        try {
            ResultSet rs = dashboardDAO.getRecentTransactions(currentUserId);
            tableModel.setRowCount(0); // Clear placeholders
            while (rs != null && rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getString("id").substring(0, 8) + "...", // Shorten UUID
                    rs.getString("entity"),
                    rs.getString("type"),
                    "$" + String.format("%,.2f", rs.getDouble("amount")),
                    rs.getTimestamp("created_at"),
                    rs.getString("status")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initSidebar() {
        sidePanel = new JPanel(new BorderLayout());
        sidePanel.setPreferredSize(new Dimension(280, 800));
        sidePanel.setBackground(SIDEBAR_COLOR);

        JPanel topSection = new JPanel();
        topSection.setLayout(new BoxLayout(topSection, BoxLayout.Y_AXIS));
        topSection.setOpaque(false);

        JLabel logo = new JLabel("NEXTGEN BANK");
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Inter", Font.BOLD, 20));
        logo.setBorder(new EmptyBorder(40, 30, 40, 0));
        topSection.add(logo);

      

        String[] navs = {"Dashboard", "Transaction details", "Loan Details", "Analytics"};
        for (String nav : navs) {
            JButton navBtn = createNavButton(nav, false);
            
            // Add the connection logic here
            navBtn.addActionListener(e -> {
                // 1. Identify the container holding your center content (usually named 'mainContent' or similar)
                // 2. Remove previous components to clear the screen
                mainContent.removeAll(); 
                
                // 3. Switch between your different panels
                switch (nav) {
                    case "Dashboard":
                        // Replace this with your original overview panel logic
                        // mainContent.add(new OverviewPanel(), BorderLayout.CENTER);
                        break;
                        
                    case "Transaction details":
                        // Connects the TransactionDetail class you just created
                        mainContent.add(new TransactionDetail(), BorderLayout.CENTER);
                        break;
                        
                    case "Loan Details":
                        // Slot for your future Loan panel
                        mainContent.add(new LoanDetails(), BorderLayout.CENTER);
                        break;
                        
                    case "Analytics":
                        // Slot for your future Analytics panel
                        // mainContent.add(new AnalyticsPanel(), BorderLayout.CENTER);
                        break;
                }
                
                // 4. Refresh the UI so the new panel shows up immediately
                mainContent.revalidate();
                mainContent.repaint();
            });

            topSection.add(navBtn);
        }
        
        // ... (rest of your existing sidebar and logout code)
        sidePanel.add(topSection, BorderLayout.NORTH);

        JButton logoutBtn = createNavButton("Logout", true);
        logoutBtn.addActionListener(e -> System.exit(0));
        sidePanel.add(logoutBtn, BorderLayout.SOUTH);

        add(sidePanel, BorderLayout.WEST);
    }

    private JButton createNavButton(String text, boolean isLogout) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(280, 50));
        btn.setFont(new Font("Inter", Font.PLAIN, 15));
        btn.setForeground(isLogout ? new Color(248, 113, 113) : new Color(148, 163, 184));
        btn.setBackground(SIDEBAR_COLOR);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setMargin(new Insets(0, 30, 0, 0));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setForeground(Color.WHITE);
                btn.setContentAreaFilled(true);
                btn.setBackground(new Color(30, 41, 59));
            }
            public void mouseExited(MouseEvent e) {
                btn.setForeground(isLogout ? new Color(248, 113, 113) : new Color(148, 163, 184));
                btn.setContentAreaFilled(false);
            }
        });
        return btn;
    }

    private void initCards() {
        cardsContainer = new JPanel(new GridLayout(1, 3, 25, 0));
        cardsContainer.setOpaque(false);
        cardsContainer.setMaximumSize(new Dimension(1600, 160));

        // Assign labels to the class-level variables so loadSupabaseData can edit them
        lblBalance = new JLabel("$0.00");
        lblDebit = new JLabel("$0.00");
        lblCredit = new JLabel("$0.00");

        cardsContainer.add(createModernCard("Total Balance", lblBalance, ACCENT_BLUE));
        cardsContainer.add(createModernCard("Total Debit", lblDebit, new Color(16, 185, 129))); 
        cardsContainer.add(createModernCard("Total Credit", lblCredit, new Color(245, 158, 11))); 
    }

    // Updated helper to accept the JLabel as a reference
    private JPanel createModernCard(String title, JLabel valueLabel, Color accent) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
            new EmptyBorder(25, 25, 25, 25)
        ));

        JLabel t = new JLabel(title.toUpperCase());
        t.setFont(new Font("Inter", Font.BOLD, 12));
        t.setForeground(new Color(100, 116, 139));

        valueLabel.setFont(new Font("Inter", Font.BOLD, 32));
        valueLabel.setForeground(TEXT_PRIMARY);

        JPanel line = new JPanel();
        line.setPreferredSize(new Dimension(0, 4));
        line.setBackground(accent);

        card.add(t, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        card.add(line, BorderLayout.SOUTH);
        
        return card;
    }

    private void initTableSection() {
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(CARD_WHITE);
        tableContainer.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1));

        JLabel label = new JLabel("Recent Activity");
        label.setFont(new Font("Inter", Font.BOLD, 18));
        label.setBorder(new EmptyBorder(20, 25, 20, 25));
        tableContainer.add(label, BorderLayout.NORTH);

        String[] cols = {"Reference", "Entity", "Type", "Amount", "Date", "Status"};
        tableModel = new DefaultTableModel(cols, 0);
        transactionTable = new JTable(tableModel);
        
        transactionTable.setRowHeight(50);
        transactionTable.setGridColor(new Color(241, 245, 249));
        transactionTable.setFont(new Font("Inter", Font.PLAIN, 14));
        transactionTable.setSelectionBackground(new Color(241, 245, 249));
        transactionTable.getTableHeader().setBackground(Color.WHITE);
        transactionTable.getTableHeader().setFont(new Font("Inter", Font.BOLD, 13));
        transactionTable.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(226, 232, 240)));

        JScrollPane scroll = new JScrollPane(transactionTable);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);
        
        tableContainer.add(scroll, BorderLayout.CENTER);
        mainContent.add(tableContainer);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}
        SwingUtilities.invokeLater(() -> new dashboard().setVisible(true));
    }
}