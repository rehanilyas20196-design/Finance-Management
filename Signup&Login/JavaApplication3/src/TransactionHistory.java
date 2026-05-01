import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class TransactionHistory extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger =
        java.util.logging.Logger.getLogger(TransactionHistory.class.getName());

    // ── Colors ───────────────────────────────────────────────────────────────
    private static final Color NAVY        = new Color(14,  9,   60);
    private static final Color NAVY_DARK   = new Color(10,  12,  28);
    private static final Color NAVY_CARD   = new Color(25,  30,  60);
    private static final Color TEXT_WHITE  = new Color(255, 255, 255);
    private static final Color TEXT_MUTED  = new Color(120, 130, 170);
    private static final Color GREEN       = new Color(52,  211, 153);
    private static final Color RED         = new Color(248, 113, 113);
    private static final Color BLUE        = new Color(59,  130, 246);
    private static final Color YELLOW      = new Color(251, 191, 36);
    private static final Color ROW_ALT     = new Color(30,  36,  70);
    private static final Color ROW_HOVER   = new Color(45,  52,  95);

    // ── Sample data ──────────────────────────────────────────────────────────
    private static final Object[][] SAMPLE_DATA = {
        {"TXN-00412", "2026-05-01  09:14", "Ahmed Khan",      "PKR 25,000", "Credit", "Completed"},
        {"TXN-00411", "2026-04-30  18:42", "Zara Malik",      "PKR  8,500", "Debit",  "Completed"},
        {"TXN-00410", "2026-04-30  11:05", "Bank Transfer",   "PKR 50,000", "Credit", "Pending"},
        {"TXN-00409", "2026-04-29  20:33", "Ali Hassan",      "PKR  3,200", "Debit",  "Completed"},
        {"TXN-00408", "2026-04-28  14:17", "Fatima Siddiqui", "PKR 12,750", "Credit", "Failed"},
        {"TXN-00407", "2026-04-27  09:00", "Online Purchase", "PKR  1,499", "Debit",  "Completed"},
        {"TXN-00406", "2026-04-26  16:55", "Sara Bilal",      "PKR 30,000", "Credit", "Completed"},
        {"TXN-00405", "2026-04-25  13:22", "Utility Bill",    "PKR  4,200", "Debit",  "Completed"},
    };

    private String currentFilter = "All";
    private ButtonGroup filterGroup;

    // ════════════════════════════════════════════════════════════════════════
    public TransactionHistory() {
        initComponents();
        applyStyles();      // Our custom styling on top
        setupButtonGroup(); // Wire up the filter buttons
        loadTableData();    // Fill table with sample data
        updateSummary();    // Calculate and show totals
    }

    // ════════════════════════════════════════════════════════════════════════
    //  STEP 1 — Apply visual styles to the NetBeans components
    // ════════════════════════════════════════════════════════════════════════
    private void applyStyles() {
        // --- Table styling ---
        jTable1.setBackground(NAVY_DARK);
        jTable1.setForeground(TEXT_WHITE);
        jTable1.setSelectionBackground(ROW_HOVER);
        jTable1.setSelectionForeground(TEXT_WHITE);
        jTable1.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        jTable1.setRowHeight(46);
        jTable1.setShowGrid(false);
        jTable1.setIntercellSpacing(new Dimension(0, 0));
        jTable1.setFillsViewportHeight(true);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        jTable1.setAutoscrolls(true);
        // Table header
        JTableHeader th = jTable1.getTableHeader();
        th.setBackground(NAVY_CARD);
        th.setForeground(TEXT_MUTED);
        th.setFont(new Font("Segoe UI", Font.BOLD, 12));
        th.setPreferredSize(new Dimension(0, 42));
        th.setReorderingAllowed(false);

        // Custom cell renderer (colors per column)
        jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(
                    JTable t, Object val, boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                setOpaque(true);
                setBorder(BorderFactory.createEmptyBorder(0, 14, 0, 14));
                setBackground(sel ? ROW_HOVER : (row % 2 == 0 ? NAVY_DARK : ROW_ALT));
                String text = val == null ? "" : val.toString();
                setFont(new Font("Segoe UI", Font.PLAIN, 13));

                if (col == 4) { // Type
                    setForeground("Credit".equals(text) ? GREEN : "Debit".equals(text) ? RED : TEXT_WHITE);
                } else if (col == 5) { // Status
                    switch (text) {
                        case "Completed": setForeground(GREEN);  setText("● Completed"); break;
                        case "Pending":   setForeground(YELLOW); setText("● Pending");   break;
                        case "Failed":    setForeground(RED);    setText("● Failed");     break;
                        default:          setForeground(TEXT_WHITE);
                    }
                } else if (col == 3) { // Amount — bold
                    setForeground(TEXT_WHITE);
                    setFont(new Font("Segoe UI", Font.BOLD, 13));
                } else if (col == 0) { // TXN ID — muted
                    setForeground(TEXT_MUTED);
                } else {
                    setForeground(TEXT_WHITE);
                }
                return this;
            }
        });

        // ScrollPane background
        jScrollPane1.setBackground(NAVY_DARK);
        jScrollPane1.getViewport().setBackground(NAVY_DARK);
        jScrollPane1.setBorder(BorderFactory.createEmptyBorder());

        // Fix "Ststus" typo in column header
        jTable1.getColumnModel().getColumn(5).setHeaderValue("Status");
        jTable1.getTableHeader().repaint();

        // Search field style
        jTextField1.setBackground(new Color(20, 40, 120));
        jTextField1.setForeground(TEXT_WHITE);
        jTextField1.setCaretColor(TEXT_WHITE);
        jTextField1.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        jTextField1.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 100, 200), 1),
            BorderFactory.createEmptyBorder(4, 10, 4, 10)
        ));

        // Search field live filter
        jTextField1.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e)  { filterTable(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e)  { filterTable(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filterTable(); }
        });

        // Export button style
        jButton1.setBackground(new Color(30, 60, 180));
        jButton1.setForeground(TEXT_WHITE);
        jButton1.setFont(new Font("Segoe UI", Font.BOLD, 13));
        jButton1.setFocusPainted(false);
        jButton1.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 120, 220), 1),
            BorderFactory.createEmptyBorder(4, 14, 4, 14)
        ));
        jButton1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jButton1.addActionListener(e ->
            JOptionPane.showMessageDialog(this, "Export coming soon!", "Export",
                JOptionPane.INFORMATION_MESSAGE));

        // Fix label colors in summary cards
        jLabel5.setText("");          // remove the stray "jLabel3" text
        jLabel8.setFont(new Font("Segoe UI", Font.BOLD, 20));
        jLabel8.setForeground(GREEN); // Credits value
        jLabel9.setFont(new Font("Segoe UI", Font.BOLD, 20));
        jLabel9.setForeground(RED);   // Debits value
        jLabel7.setFont(new Font("Segoe UI", Font.BOLD, 20));
        jLabel7.setForeground(BLUE);  // Balance value
    }

    // ════════════════════════════════════════════════════════════════════════
    //  STEP 2 — Wire up ButtonGroup so only 1 button is selected at a time
    // ════════════════════════════════════════════════════════════════════════
    private void setupButtonGroup() {
        filterGroup = new ButtonGroup();
        filterGroup.add(jToggleButton2);   // All
        filterGroup.add(jToggleButton7);   // Credit
        filterGroup.add(jToggleButton8);   // Debit
        filterGroup.add(jToggleButton9);   // Completed
        filterGroup.add(jToggleButton10);  // Pending
        filterGroup.add(jToggleButton11);  // Failed
        jToggleButton2.setSelected(true);  // "All" selected by default
    }

    // ════════════════════════════════════════════════════════════════════════
    //  STEP 3 — Load sample data into the table
    // ════════════════════════════════════════════════════════════════════════
    private void loadTableData() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // clear existing empty rows
        for (Object[] row : SAMPLE_DATA) {
            model.addRow(row);
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    //  STEP 4 — Calculate and show totals in summary cards
    // ════════════════════════════════════════════════════════════════════════
    private void updateSummary() {
        long credit = 0, debit = 0;
        for (Object[] row : SAMPLE_DATA) {
            String s = row[3].toString().replaceAll("[^0-9]", "");
            if (s.isEmpty()) continue;
            long amt = Long.parseLong(s);
            if ("Credit".equals(row[4])) credit += amt;
            else debit += amt;
        }
        long balance = credit - debit;
        jLabel8.setText(String.format("PKR %,d", credit));   // Total Credits
        jLabel9.setText(String.format("PKR %,d", debit));    // Total Debits
        jLabel7.setText(String.format("PKR %,d", balance));  // Net Balance
        jLabel7.setForeground(balance >= 0 ? BLUE : RED);
    }

    // ════════════════════════════════════════════════════════════════════════
    //  Filter logic
    // ════════════════════════════════════════════════════════════════════════
    private void filterTable() {
        String query = jTextField1.getText().toLowerCase().trim();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        for (Object[] row : SAMPLE_DATA) {
            boolean matchFilter = currentFilter.equals("All")
                || row[4].toString().equals(currentFilter)
                || row[5].toString().equals(currentFilter);
            boolean matchSearch = query.isEmpty()
                || java.util.Arrays.stream(row)
                    .anyMatch(c -> c.toString().toLowerCase().contains(query));
            if (matchFilter && matchSearch) model.addRow(row);
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    //  NetBeans Generated Code — DO NOT MODIFY
    // ════════════════════════════════════════════════════════════════════════
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">

    // ── Main ─────────────────────────────────────────────────────────────────
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new TransactionHistory().setVisible(true);
        });
    }  
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jToggleButton2 = new javax.swing.JToggleButton();
        jToggleButton7 = new javax.swing.JToggleButton();
        jToggleButton8 = new javax.swing.JToggleButton();
        jToggleButton9 = new javax.swing.JToggleButton();
        jToggleButton10 = new javax.swing.JToggleButton();
        jToggleButton11 = new javax.swing.JToggleButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Transaction History - Finance Dashboard");
        setBackground(new java.awt.Color(14, 9, 60));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel2.setBackground(new java.awt.Color(14, 9, 60));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel2.setPreferredSize(new java.awt.Dimension(1100, 90));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Transaction History");

        jLabel2.setBackground(new java.awt.Color(120, 130, 170));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(120, 130, 170));
        jLabel2.setText("Bank & Finance System. All Accounts");

        jPanel1.setBackground(new java.awt.Color(14, 9, 60));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 255), 1, true));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jTextField1.setBackground(new java.awt.Color(0, 51, 102));
        jTextField1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(204, 204, 204));
        jTextField1.setText("Search transactions");
        jTextField1.setPreferredSize(new java.awt.Dimension(220, 36));

        jButton1.setBackground(new java.awt.Color(0, 0, 102));
        jButton1.setFont(new java.awt.Font("Segoe UI Historic", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(204, 204, 204));
        jButton1.setText("Export");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(572, 572, 572)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(10, 12, 28));

        jLabel3.setBackground(new java.awt.Color(120, 130, 170));
        jLabel3.setForeground(new java.awt.Color(120, 130, 170));
        jLabel3.setText("Total Credits");

        jLabel5.setText("jLabel3");

        jLabel8.setForeground(new java.awt.Color(52, 211, 153));
        jLabel8.setText("Credits");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(25, 30, 60));
        jPanel4.setPreferredSize(new java.awt.Dimension(340, 90));

        jLabel4.setForeground(new java.awt.Color(120, 130, 170));
        jLabel4.setText("Total Debits");

        jLabel9.setForeground(new java.awt.Color(248, 113, 113));
        jLabel9.setText("Debits");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(99, Short.MAX_VALUE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(25, 30, 60));
        jPanel5.setPreferredSize(new java.awt.Dimension(340, 90));

        jLabel6.setForeground(new java.awt.Color(120, 130, 170));
        jLabel6.setText("Net Balance");

        jLabel7.setForeground(new java.awt.Color(59, 130, 246));
        jLabel7.setText("Net Balance");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(134, Short.MAX_VALUE)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(14, 9, 60));
        jPanel6.setPreferredSize(new java.awt.Dimension(450, 184));

        jToggleButton2.setBackground(new java.awt.Color(0, 0, 102));
        jToggleButton2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jToggleButton2.setForeground(new java.awt.Color(255, 255, 255));
        jToggleButton2.setText("All");
        jToggleButton2.addActionListener(this::jToggleButton2ActionPerformed);

        jToggleButton7.setBackground(new java.awt.Color(0, 0, 102));
        jToggleButton7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jToggleButton7.setForeground(new java.awt.Color(255, 255, 255));
        jToggleButton7.setText("Credit");
        jToggleButton7.addActionListener(this::jToggleButton7ActionPerformed);

        jToggleButton8.setBackground(new java.awt.Color(0, 0, 102));
        jToggleButton8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jToggleButton8.setForeground(new java.awt.Color(255, 255, 255));
        jToggleButton8.setText("Debit");
        jToggleButton8.addActionListener(this::jToggleButton8ActionPerformed);

        jToggleButton9.setBackground(new java.awt.Color(0, 0, 102));
        jToggleButton9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jToggleButton9.setForeground(new java.awt.Color(255, 255, 255));
        jToggleButton9.setText("Completed");
        jToggleButton9.addActionListener(this::jToggleButton9ActionPerformed);

        jToggleButton10.setBackground(new java.awt.Color(0, 0, 102));
        jToggleButton10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jToggleButton10.setForeground(new java.awt.Color(255, 255, 255));
        jToggleButton10.setText("Pending");
        jToggleButton10.addActionListener(this::jToggleButton10ActionPerformed);

        jToggleButton11.setBackground(new java.awt.Color(0, 0, 102));
        jToggleButton11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jToggleButton11.setForeground(new java.awt.Color(255, 255, 255));
        jToggleButton11.setText("Failed");
        jToggleButton11.addActionListener(this::jToggleButton11ActionPerformed);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jToggleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addComponent(jToggleButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addComponent(jToggleButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(jToggleButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jToggleButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(jToggleButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jToggleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31))
        );

        jTable1.setBackground(new java.awt.Color(14, 9, 60));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Transaction ID", "Date and Time", "Sender / Receiver ", "Amount", "Type", "Ststus"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setRowHeight(46);
        jTable1.setShowGrid(false);
        jScrollPane1.setViewportView(jTable1);

        jPanel7.setBackground(new java.awt.Color(10, 12, 28));
        jPanel7.setPreferredSize(new java.awt.Dimension(100, 44));

        jLabel10.setForeground(new java.awt.Color(120, 130, 170));
        jLabel10.setText("Showing 8 transactions");

        jLabel11.setForeground(new java.awt.Color(60, 65, 100));
        jLabel11.setText("Finance Dashboard © 2026");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 952, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addGap(20, 20, 20))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 1256, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1256, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 1256, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jToggleButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPerformed
        // TODO add your handling code here:
        currentFilter = "All"; filterTable();
    }//GEN-LAST:event_jToggleButton2ActionPerformed

    private void jToggleButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton7ActionPerformed
        // TODO add your handling code here:
        currentFilter = "Credit"; filterTable();
        
    }//GEN-LAST:event_jToggleButton7ActionPerformed

    private void jToggleButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton8ActionPerformed
        // TODO add your handling code here:
        currentFilter = "Debit"; filterTable();
    }//GEN-LAST:event_jToggleButton8ActionPerformed

    private void jToggleButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton9ActionPerformed
        // TODO add your handling code here:
        currentFilter = "Completed"; filterTable();
    }//GEN-LAST:event_jToggleButton9ActionPerformed

    private void jToggleButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton10ActionPerformed
        // TODO add your handling code here:
        currentFilter = "Pending"; filterTable();
    }//GEN-LAST:event_jToggleButton10ActionPerformed

    private void jToggleButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton11ActionPerformed
        // TODO add your handling code here:
        currentFilter = "Failed"; filterTable();
    }//GEN-LAST:event_jToggleButton11ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JToggleButton jToggleButton10;
    private javax.swing.JToggleButton jToggleButton11;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JToggleButton jToggleButton7;
    private javax.swing.JToggleButton jToggleButton8;
    private javax.swing.JToggleButton jToggleButton9;
    // End of variables declaration//GEN-END:variables
}
