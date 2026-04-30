import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * LoginForm - Professional Banking Login UI
 * @author Generated for NetBeans
 */
public class LoginForm extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(LoginForm.class.getName());

    public LoginForm() {
        initComponents();
        setLocationRelativeTo(null);
        // Optionally load a scaled logo image:
        // setScaledImage(lblLogo, "/login_image.png", 120, 120);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelLeft = new javax.swing.JPanel();
        lblBrandTitle = new javax.swing.JLabel();
        lblBrandSub = new javax.swing.JLabel();
        lblTagline = new javax.swing.JLabel();
        panelRight = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        lblUsername = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        chkShowPassword = new javax.swing.JCheckBox();
        lblForgotPassword = new javax.swing.JLabel();
        btnLogin = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        lblSeparator = new javax.swing.JLabel();
        lblNoAccount = new javax.swing.JLabel();
        btnSignUp = new javax.swing.JButton();
        lblStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Secure Banking Login");
        setResizable(false);
        setMinimumSize(new java.awt.Dimension(900, 560));

        panelLeft.setBackground(new java.awt.Color(16, 35, 80));
        panelLeft.setPreferredSize(new java.awt.Dimension(360, 560));
        panelLeft.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblBrandTitle.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        lblBrandTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblBrandTitle.setText("NextGen Bank");
        panelLeft.add(lblBrandTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, -1, -1));

        lblBrandSub.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblBrandSub.setForeground(new java.awt.Color(180, 210, 255));
        lblBrandSub.setText("Financial Management System");
        panelLeft.add(lblBrandSub, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 200, 280, -1));

        lblTagline.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        lblTagline.setForeground(new java.awt.Color(120, 170, 230));
        lblTagline.setText("<html>Secure · Reliable · Smart Banking<br>for the Modern World.</html>");
        panelLeft.add(lblTagline, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 250, 280, 50));

        getContentPane().add(panelLeft, java.awt.BorderLayout.WEST);

        panelRight.setBackground(new java.awt.Color(235, 242, 255));
        panelRight.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/login_image.png"))); // NOI18N
        lblLogo.setPreferredSize(new java.awt.Dimension(80, 80));
        panelRight.add(lblLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 30, 80, 80));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(16, 35, 80));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("Secure Banking Login");
        panelRight.add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 125, 360, -1));

        lblUsername.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblUsername.setForeground(new java.awt.Color(50, 70, 120));
        lblUsername.setText("National ID / Username");
        panelRight.add(lblUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 175, 200, -1));

        txtUsername.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtUsername.setForeground(new java.awt.Color(20, 20, 60));
        txtUsername.setToolTipText("Enter your National ID or Username");
        panelRight.add(txtUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 198, 360, 34));

        lblPassword.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblPassword.setForeground(new java.awt.Color(50, 70, 120));
        lblPassword.setText("Password");
        panelRight.add(lblPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 248, 100, -1));

        txtPassword.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtPassword.setForeground(new java.awt.Color(20, 20, 60));
        txtPassword.setToolTipText("Enter your password");
        txtPassword.addActionListener(this::txtPasswordActionPerformed);
        panelRight.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 270, 360, 34));

        chkShowPassword.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        chkShowPassword.setForeground(new java.awt.Color(70, 90, 140));
        chkShowPassword.setBackground(new java.awt.Color(235, 242, 255));
        chkShowPassword.setText("Show Password");
        chkShowPassword.addActionListener(this::chkShowPasswordActionPerformed);
        panelRight.add(chkShowPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 312, 140, -1));

        lblForgotPassword.setFont(new java.awt.Font("Segoe UI", 3, 11)); // NOI18N
        lblForgotPassword.setForeground(new java.awt.Color(30, 80, 200));
        lblForgotPassword.setText("<html><u>Forgot Password?</u></html>");
        lblForgotPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblForgotPasswordMouseClicked(evt);
            }
        });
        panelRight.add(lblForgotPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 312, 140, -1));

        btnLogin.setBackground(new java.awt.Color(10, 50, 160));
        btnLogin.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnLogin.setText("Login");
        btnLogin.setFocusPainted(false);
        btnLogin.addActionListener(this::btnLoginActionPerformed);
        panelRight.add(btnLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 350, 170, 36));

        btnReset.setBackground(new java.awt.Color(180, 195, 230));
        btnReset.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnReset.setForeground(new java.awt.Color(10, 35, 80));
        btnReset.setText("Reset");
        btnReset.setFocusPainted(false);
        btnReset.addActionListener(this::btnResetActionPerformed);
        panelRight.add(btnReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 350, 170, 36));

        lblSeparator.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lblSeparator.setForeground(new java.awt.Color(150, 160, 180));
        lblSeparator.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSeparator.setText("──────────────── or ────────────────");
        panelRight.add(lblSeparator, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 405, 360, -1));

        lblNoAccount.setFont(new java.awt.Font("Segoe UI", 2, 11)); // NOI18N
        lblNoAccount.setForeground(new java.awt.Color(80, 90, 110));
        lblNoAccount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNoAccount.setText("Don't have an account?");
        panelRight.add(lblNoAccount, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 430, 200, -1));

        btnSignUp.setBackground(new java.awt.Color(0, 160, 120));
        btnSignUp.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSignUp.setForeground(new java.awt.Color(255, 255, 255));
        btnSignUp.setText("Sign Up");
        btnSignUp.setFocusPainted(false);
        btnSignUp.addActionListener(this::btnSignUpActionPerformed);
        panelRight.add(btnSignUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 425, 150, 32));

        lblStatus.setFont(new java.awt.Font("Segoe UI", 2, 11)); // NOI18N
        lblStatus.setForeground(new java.awt.Color(200, 50, 50));
        lblStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblStatus.setText(" ");
        panelRight.add(lblStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 475, 360, -1));

        getContentPane().add(panelRight, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // ── Event Handlers ─────────────────────────────────────────

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        btnLoginActionPerformed(evt);
    }//GEN-LAST:event_txtPasswordActionPerformed

    private void chkShowPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkShowPasswordActionPerformed
        if (chkShowPassword.isSelected()) {
            txtPassword.setEchoChar((char) 0);
        } else {
            txtPassword.setEchoChar('\u2022');
        }
    }//GEN-LAST:event_chkShowPasswordActionPerformed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            lblStatus.setText("Please fill in all fields.");
            lblStatus.setForeground(new java.awt.Color(200, 50, 50));
            return;
        }

        // TODO: Replace with real authentication logic
        lblStatus.setText("Logging in...");
        lblStatus.setForeground(new java.awt.Color(30, 130, 30));
        // Example: new DashboardForm().setVisible(true); this.dispose();
    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        txtUsername.setText("");
        txtPassword.setText("");
        chkShowPassword.setSelected(false);
        txtPassword.setEchoChar('\u2022');
        lblStatus.setText(" ");
        txtUsername.requestFocus();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnSignUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignUpActionPerformed
        SignUpForm form = new SignUpForm();
        form.setVisible(true);
        this.dispose();
        // new SignUpForm().setVisible(true);
        
    }//GEN-LAST:event_btnSignUpActionPerformed

    private void lblForgotPasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblForgotPasswordMouseClicked
        // TODO: Navigate to forgot password form
        JOptionPane.showMessageDialog(this, "Forgot Password form not yet linked.", "Forgot Password", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_lblForgotPasswordMouseClicked

    // ── Utility ────────────────────────────────────────────────

    private void setScaledImage(javax.swing.JLabel targetLabel, String imagePath, int width, int height) {
        try {
            javax.swing.ImageIcon originalIcon = new javax.swing.ImageIcon(getClass().getResource(imagePath));
            if (originalIcon.getImageLoadStatus() != java.awt.MediaTracker.COMPLETE) {
                System.err.println("Could not load image: " + imagePath);
                return;
            }
            java.awt.Image scaled = originalIcon.getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
            targetLabel.setIcon(new javax.swing.ImageIcon(scaled));
            targetLabel.setText("");
        } catch (NullPointerException e) {
            System.err.println("Image resource not found: " + imagePath);
        }
    }

    // ── Main ───────────────────────────────────────────────────

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> new LoginForm().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSignUp;
    private javax.swing.JCheckBox chkShowPassword;
    private javax.swing.JLabel lblBrandSub;
    private javax.swing.JLabel lblBrandTitle;
    private javax.swing.JLabel lblForgotPassword;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblNoAccount;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblSeparator;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblTagline;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JPanel panelLeft;
    private javax.swing.JPanel panelRight;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}