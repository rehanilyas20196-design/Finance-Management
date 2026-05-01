import javax.swing.*;
import java.awt.*;
import java.util.UUID;
import MainClasses.User;
import DBData.UserDAO;
import org.mindrot.jbcrypt.BCrypt;
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

        lblCnic = new javax.swing.JLabel();
        txtCnic = new javax.swing.JTextField();

        lblPhone = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();

        chkShowPassword = new javax.swing.JCheckBox();

        btnCreateAccount = new javax.swing.JButton();
        btnBackToLogin = new javax.swing.JButton();

        lblStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Secure Banking Sign Up");
        setMinimumSize(new java.awt.Dimension(900, 620));
        setPreferredSize(new java.awt.Dimension(900, 620));
        setResizable(false);

        // LEFT PANEL
        panelLeft.setBackground(new java.awt.Color(16, 35, 80));
        panelLeft.setPreferredSize(new java.awt.Dimension(360, 620));
        panelLeft.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblBrandTitle.setFont(new java.awt.Font("Segoe UI", 1, 28));
        lblBrandTitle.setForeground(Color.WHITE);
        lblBrandTitle.setText("NextGen Bank");

        lblBrandSub.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblBrandSub.setForeground(new java.awt.Color(180, 210, 255));
        lblBrandSub.setText("Create Your Secure Account");

        lblTagline.setFont(new java.awt.Font("Segoe UI", 2, 12));
        lblTagline.setForeground(new java.awt.Color(120, 170, 230));
        lblTagline.setText("<html>Join secure digital banking<br>built for your future.</html>");

        panelLeft.add(lblBrandTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, -1, -1));
        panelLeft.add(lblBrandSub, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 200, 280, -1));
        panelLeft.add(lblTagline, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 250, 280, 50));

        getContentPane().add(panelLeft, java.awt.BorderLayout.WEST);

        // RIGHT PANEL
        panelRight.setBackground(new java.awt.Color(235, 242, 255));
        panelRight.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/login_image.png")));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 20));
        lblTitle.setForeground(new java.awt.Color(16, 35, 80));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("Create New Account");

        panelRight.add(lblLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, 80, 80));
        panelRight.add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 95, 360, -1));

        // Full Name
        lblFullName.setText("Full Name");
        panelRight.add(lblFullName, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 130, 200, -1));
        panelRight.add(txtFullName, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 150, 360, 32));

        // Email
        lblEmail.setText("Email Address");
        panelRight.add(lblEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 190, 200, -1));
        panelRight.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 210, 360, 32));

        // Username
        lblUsername.setText("Username");
        panelRight.add(lblUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 250, 200, -1));
        panelRight.add(txtUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 270, 360, 32));

        // Password
        lblPassword.setText("Password");
        panelRight.add(lblPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 310, 200, -1));
        panelRight.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 330, 360, 32));

        // Show Password
        chkShowPassword.setText("Show Password");
        chkShowPassword.addActionListener(evt -> {
            if (chkShowPassword.isSelected()) {
                txtPassword.setEchoChar((char) 0);
            } else {
                txtPassword.setEchoChar('\u2022');
            }
        });
        panelRight.add(chkShowPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 370, 140, -1));

        // CNIC
        lblCnic.setText("CNIC");
        panelRight.add(lblCnic, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 400, 200, -1));
        panelRight.add(txtCnic, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 420, 360, 32));

        // Phone
        lblPhone.setText("Phone");
        panelRight.add(lblPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 460, 200, -1));
        panelRight.add(txtPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 480, 360, 32));

        // Buttons
        btnCreateAccount.setText("Create Account");
        btnCreateAccount.addActionListener(this::btnCreateAccountActionPerformed);

        btnBackToLogin.setText("Back to Login");
        btnBackToLogin.addActionListener(this::btnBackToLoginActionPerformed);

        panelRight.add(btnCreateAccount, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 530, 170, 36));
        panelRight.add(btnBackToLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 530, 170, 36));

        // Status
        lblStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panelRight.add(lblStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 580, 360, -1));

        getContentPane().add(panelRight, java.awt.BorderLayout.CENTER);

        pack();
    }

    // ✅ WORKING BUTTON LOGIC
    private void btnCreateAccountActionPerformed(java.awt.event.ActionEvent evt) {

        try {
            String fullName = txtFullName.getText().trim();
            String email = txtEmail.getText().trim();
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword()).trim();
            String cnic = txtCnic.getText().trim();
            String phone = txtPhone.getText().trim();

            if (fullName.isEmpty() || email.isEmpty() || username.isEmpty()
                    || password.isEmpty() || cnic.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all required fields.");
                return;
            }

            
            
            String bananaHash = UUID.randomUUID().toString();   // random secret per user

            String passwordHash = BCrypt.hashpw(
                bananaHash + password,
                BCrypt.gensalt(12)
            );

            User user = new User(
                    fullName,
                    email,
                    username,
                    passwordHash,
                    bananaHash,
                    cnic,
                    phone
            );

            UserDAO userDAO = new UserDAO();
            boolean success = userDAO.registerUser(user);

            if (success) {
                JOptionPane.showMessageDialog(this,
                        "Signup successful.\nYour security key: " + bananaHash);
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Signup failed.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void btnBackToLoginActionPerformed(java.awt.event.ActionEvent evt) {
        new LoginForm().setVisible(true);
        this.dispose();
    }

    private void clearFields() {
        txtFullName.setText("");
        txtEmail.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        txtCnic.setText("");
        txtPhone.setText("");
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new SignUpForm().setVisible(true));
    }

    // VARIABLES
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

    private javax.swing.JLabel lblCnic;
    private javax.swing.JLabel lblPhone;

    private javax.swing.JPanel panelLeft;
    private javax.swing.JPanel panelRight;

    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFullName;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    private javax.swing.JTextField txtCnic;
    private javax.swing.JTextField txtPhone;
}