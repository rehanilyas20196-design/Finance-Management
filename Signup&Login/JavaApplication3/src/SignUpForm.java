import javax.swing.*;
import java.awt.*;

/**
 * SignUpForm - Professional Banking Signup UI
 */
public class SignUpForm extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(SignUpForm.class.getName());

    public SignUpForm() {
        initComponents();
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        panelLeft = new javax.swing.JPanel();
        lblBrandTitle = new javax.swing.JLabel();
        lblBrandSub = new javax.swing.JLabel();
        lblTagline = new javax.swing.JLabel();

        panelRight = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();

        lblFullName = new javax.swing.JLabel();
        txtFullName = new javax.swing.JTextField();

        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();

        lblUsername = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();

        lblPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();

        chkShowPassword = new javax.swing.JCheckBox();

        btnCreateAccount = new javax.swing.JButton();
        btnBackToLogin = new javax.swing.JButton();

        lblStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Secure Banking Sign Up");
        setMinimumSize(new java.awt.Dimension(900, 560));
        setPreferredSize(new java.awt.Dimension(900, 560));
        setResizable(false);

        panelLeft.setBackground(new java.awt.Color(16, 35, 80));
        panelLeft.setPreferredSize(new java.awt.Dimension(360, 560));
        panelLeft.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblBrandTitle.setFont(new java.awt.Font("Segoe UI", 1, 28));
        lblBrandTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblBrandTitle.setText("NextGen Bank");
        panelLeft.add(lblBrandTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, -1, -1));

        lblBrandSub.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblBrandSub.setForeground(new java.awt.Color(180, 210, 255));
        lblBrandSub.setText("Create Your Secure Account");
        panelLeft.add(lblBrandSub, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 200, 280, -1));

        lblTagline.setFont(new java.awt.Font("Segoe UI", 2, 12));
        lblTagline.setForeground(new java.awt.Color(120, 170, 230));
        lblTagline.setText("<html>Join secure digital banking<br>built for your future.</html>");
        panelLeft.add(lblTagline, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 250, 280, 50));

        getContentPane().add(panelLeft, java.awt.BorderLayout.WEST);

        panelRight.setBackground(new java.awt.Color(235, 242, 255));
        panelRight.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/login_image.png")));
        panelRight.add(lblLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, 80, 80));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 20));
        lblTitle.setForeground(new java.awt.Color(16, 35, 80));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("Create New Account");
        panelRight.add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 105, 360, -1));

        lblFullName.setFont(new java.awt.Font("Segoe UI", 1, 12));
        lblFullName.setForeground(new java.awt.Color(50, 70, 120));
        lblFullName.setText("Full Name");
        panelRight.add(lblFullName, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 145, 200, -1));

        txtFullName.setFont(new java.awt.Font("Segoe UI", 0, 13));
        panelRight.add(txtFullName, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 165, 360, 32));

        lblEmail.setFont(new java.awt.Font("Segoe UI", 1, 12));
        lblEmail.setForeground(new java.awt.Color(50, 70, 120));
        lblEmail.setText("Email Address");
        panelRight.add(lblEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 205, 200, -1));

        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 13));
        panelRight.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 225, 360, 32));

        lblUsername.setFont(new java.awt.Font("Segoe UI", 1, 12));
        lblUsername.setForeground(new java.awt.Color(50, 70, 120));
        lblUsername.setText("Username");
        panelRight.add(lblUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 265, 200, -1));

        txtUsername.setFont(new java.awt.Font("Segoe UI", 0, 13));
        panelRight.add(txtUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 285, 360, 32));

        lblPassword.setFont(new java.awt.Font("Segoe UI", 1, 12));
        lblPassword.setForeground(new java.awt.Color(50, 70, 120));
        lblPassword.setText("Password");
        panelRight.add(lblPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 325, 200, -1));

        txtPassword.setFont(new java.awt.Font("Segoe UI", 0, 13));
        panelRight.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 345, 360, 32));

        chkShowPassword.setBackground(new java.awt.Color(235, 242, 255));
        chkShowPassword.setFont(new java.awt.Font("Segoe UI", 0, 11));
        chkShowPassword.setForeground(new java.awt.Color(70, 90, 140));
        chkShowPassword.setText("Show Password");
        chkShowPassword.addActionListener(evt -> {
            if (chkShowPassword.isSelected()) {
                txtPassword.setEchoChar((char) 0);
            } else {
                txtPassword.setEchoChar('\u2022');
            }
        });
        panelRight.add(chkShowPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 382, 140, -1));

        btnCreateAccount.setBackground(new java.awt.Color(0, 160, 120));
        btnCreateAccount.setFont(new java.awt.Font("Segoe UI", 1, 13));
        btnCreateAccount.setForeground(new java.awt.Color(255, 255, 255));
        btnCreateAccount.setText("Create Account");
        btnCreateAccount.setFocusPainted(false);
        btnCreateAccount.addActionListener(this::btnCreateAccountActionPerformed);
        panelRight.add(btnCreateAccount, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 420, 170, 36));

        btnBackToLogin.setBackground(new java.awt.Color(180, 195, 230));
        btnBackToLogin.setFont(new java.awt.Font("Segoe UI", 1, 13));
        btnBackToLogin.setForeground(new java.awt.Color(10, 35, 80));
        btnBackToLogin.setText("Back to Login");
        btnBackToLogin.setFocusPainted(false);
        btnBackToLogin.addActionListener(this::btnBackToLoginActionPerformed);
        panelRight.add(btnBackToLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 420, 170, 36));

        lblStatus.setFont(new java.awt.Font("Segoe UI", 2, 11));
        lblStatus.setForeground(new java.awt.Color(200, 50, 50));
        lblStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblStatus.setText(" ");
        panelRight.add(lblStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 475, 360, -1));

        getContentPane().add(panelRight, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }

    private void btnCreateAccountActionPerformed(java.awt.event.ActionEvent evt) {
        String fullName = txtFullName.getText().trim();
        String email = txtEmail.getText().trim();
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (fullName.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            lblStatus.setText("Please fill in all fields.");
            return;
        }

        lblStatus.setForeground(new java.awt.Color(30, 130, 30));
        lblStatus.setText("Account created successfully!");
    }

    private void btnBackToLoginActionPerformed(java.awt.event.ActionEvent evt) {
        LoginForm form = new LoginForm();
        form.setVisible(true);
        this.dispose();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new SignUpForm().setVisible(true));
    }

    private javax.swing.JButton btnBackToLogin;
    private javax.swing.JButton btnCreateAccount;
    private javax.swing.JCheckBox chkShowPassword;
    private javax.swing.JLabel lblBrandSub;
    private javax.swing.JLabel lblBrandTitle;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblFullName;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblTagline;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JPanel panelLeft;
    private javax.swing.JPanel panelRight;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFullName;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
}