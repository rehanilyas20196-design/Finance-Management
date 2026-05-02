import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.sql.ResultSet;
import DBData.dashboarddata; // Pulls from your existing DAO

public class TransactionDetail extends JPanel {
    // Styling constants from the reference image
    private final Color BG_LIGHT = new Color(249, 250, 251);
    private final Color BORDER_GRAY = new Color(229, 231, 235);
    private final Color TEXT_SLATE = new Color(75, 85, 99);
    private final Color BUTTON_BLUE = new Color(13, 110, 253);

    private DefaultTableModel model;
    private JTable table;
    private int currentUserId = 3; // Syed Muhammad Anser

    public TransactionDetail() {
        setBackground(BG_LIGHT);
        setLayout(new BorderLayout(0, 20));
        setBorder(new EmptyBorder(30, 40, 40, 40));

        initToolbar();
        initTableArea();
        loadDatabaseContent();
    }

    private void initToolbar() {
        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setOpaque(false);

        // Search bar styled like the image
        JTextField search = new JTextField("  Search transactions...");
        search.setPreferredSize(new Dimension(300, 42));
        search.setBorder(BorderFactory.createLineBorder(BORDER_GRAY));
        
        // Buttons with the modern "Flat" look
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        btnPanel.setOpaque(false);
        btnPanel.add(createActionButton("NEW PAYMENT", BUTTON_BLUE, Color.WHITE));
        btnPanel.add(createActionButton("EXPORT", Color.WHITE, TEXT_SLATE));
        btnPanel.add(createActionButton("FILTER", Color.WHITE, TEXT_SLATE));

        toolbar.add(search, BorderLayout.WEST);
        toolbar.add(btnPanel, BorderLayout.EAST);
        add(toolbar, BorderLayout.NORTH);
    }

    private void initTableArea() {
        // Updated column headers to match the design
        String[] cols = {"REFERENCE", "ENTITY", "TYPE", "AMOUNT", "DATE", "STATUS"};
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(model);
        table.setRowHeight(68); // High row height for the "airy" feel
        table.setShowVerticalLines(false);
        table.setGridColor(BORDER_GRAY);
        table.setSelectionBackground(new Color(243, 244, 246));

        // Header customization
        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(0, 50));
        header.setBackground(BG_LIGHT);
        header.setFont(new Font("Inter", Font.BOLD, 12));
        header.setForeground(TEXT_SLATE);

        // Custom Renderers for Amounts and Status Badges
        table.getColumnModel().getColumn(5).setCellRenderer(new BadgeRenderer());
        table.getColumnModel().getColumn(3).setCellRenderer(new AmountRenderer());

        JScrollPane pane = new JScrollPane(table);
        pane.setBorder(BorderFactory.createLineBorder(BORDER_GRAY));
        pane.getViewport().setBackground(Color.WHITE);
        add(pane, BorderLayout.CENTER);
    }

    private void loadDatabaseContent() {
        dashboarddata dao = new dashboarddata(); //
        try (ResultSet rs = dao.getRecentTransactions(currentUserId)) {
            model.setRowCount(0);
            while (rs != null && rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("id").substring(0, 10).toUpperCase(), // Reference
                    rs.getString("entity"), // Entity
                    rs.getString("type"),   // Type (Credit/Debit)
                    rs.getDouble("amount"), // Amount
                    rs.getTimestamp("created_at"), // Date
                    rs.getString("status")  // Status
                });
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private JButton createActionButton(String text, Color bg, Color fg) {
        JButton b = new JButton(text);
        b.setPreferredSize(new Dimension(140, 42));
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFocusPainted(false);
        b.setFont(new Font("Inter", Font.BOLD, 11));
        b.setBorder(BorderFactory.createLineBorder(BORDER_GRAY));
        return b;
    }

    // Amount Renderer to format text properly
    class AmountRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable t, Object v, boolean isS, boolean hasF, int r, int c) {
            JLabel l = (JLabel) super.getTableCellRendererComponent(t, v, isS, hasF, r, c);
            l.setText("$" + String.format("%,.2f", (Double)v));
            l.setFont(new Font("Inter", Font.BOLD, 14));
            return l;
        }
    }

    // Professional Badge Renderer (Matches image)
    class BadgeRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable t, Object v, boolean isS, boolean hasF, int r, int c) {
            String status = (String) v;
            JLabel badge = new JLabel(status.toUpperCase());
            badge.setHorizontalAlignment(SwingConstants.CENTER);
            badge.setOpaque(true);
            badge.setFont(new Font("Inter", Font.BOLD, 10));

            if ("Completed".equalsIgnoreCase(status)) {
                badge.setBackground(new Color(220, 252, 231)); // Light green
                badge.setForeground(new Color(21, 128, 61));  // Dark green text
            } else {
                badge.setBackground(new Color(254, 243, 199)); // Light orange
                badge.setForeground(new Color(180, 83, 9));   // Dark orange text
            }
            
            // Add padding to make it look like a pill/badge
            badge.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
            
            JPanel container = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 18));
            container.setBackground(isS ? t.getSelectionBackground() : Color.WHITE);
            container.add(badge);
            return container;
        }
    }
}