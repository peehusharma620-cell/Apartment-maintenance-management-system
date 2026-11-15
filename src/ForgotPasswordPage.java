import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * Apartment Maintenance Management System
 * Forgot Password Page
 *
 * Project by: Peehu Sharma
 * Registration No: PIET25CS111
 *
 * NOTE: Make sure "logo.png" is placed in the SAME FOLDER as this
 * file (or update the LOGO_PATH constant below) before you compile
 * and run the program.
 */
public class ForgotPasswordPage extends JFrame {

    // ---- Path to the college/institute logo image ----
    private static final String LOGO_PATH = "logo.png";

    // Colors picked to match the Poornima Institute branding
    private static final Color NAVY_BLUE  = new Color(45, 42, 130);
    private static final Color RED_ACCENT = new Color(200, 30, 40);
    private static final Color LIGHT_BG   = new Color(245, 246, 250);
    private static final Color WHITE      = Color.WHITE;

    private JTextField usernameField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;

    public ForgotPasswordPage() {
        setTitle("Apartment Maintenance Management System - Forgot Password");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(480, 660);
        setMinimumSize(new Dimension(420, 600));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(buildHeaderPanel(), BorderLayout.NORTH);
        add(buildCenterPanel(), BorderLayout.CENTER);
        add(buildFooterPanel(), BorderLayout.SOUTH);

        setVisible(true);
    }

    // ---------------------------------------------------------
    // HEADER PANEL : Logo only, centered (same as Login page)
    // ---------------------------------------------------------
    private JPanel buildHeaderPanel() {
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 14));
        header.setBackground(WHITE);
        header.setBorder(new EmptyBorder(10, 15, 10, 15));

        JLabel logoLabel = new JLabel();
        ImageIcon icon = loadLogo(LOGO_PATH, 260, 80);
        if (icon != null) {
            logoLabel.setIcon(icon);
        } else {
            logoLabel.setText("[LOGO]");
        }
        header.add(logoLabel);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(header, BorderLayout.CENTER);
        wrapper.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, RED_ACCENT));

        return wrapper;
    }

    // ---------------------------------------------------------
    // CENTER PANEL : Reset password form
    // ---------------------------------------------------------
    private JPanel buildCenterPanel() {
        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(LIGHT_BG);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                new EmptyBorder(30, 35, 30, 35)
        ));
        card.setMaximumSize(new Dimension(340, 480));

        // Title
        JLabel formTitle = new JLabel("Forgot Password");
        formTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        formTitle.setForeground(NAVY_BLUE);
        formTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JSeparator sep = new JSeparator();
        sep.setForeground(RED_ACCENT);
        sep.setMaximumSize(new Dimension(80, 3));
        sep.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel infoLabel = new JLabel("<html><div style='text-align:center;'>Enter your username and set a new password.</div></html>");
        infoLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        infoLabel.setForeground(Color.GRAY);
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Form fields, aligned in a GridBagLayout column
        JPanel formGrid = new JPanel(new GridBagLayout());
        formGrid.setBackground(WHITE);
        formGrid.setAlignmentX(Component.CENTER_ALIGNMENT);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));

        usernameField = new JTextField();
        styleField(usernameField);
        usernameField.setColumns(18);

        JLabel newPassLabel = new JLabel("New Password");
        newPassLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));

        newPasswordField = new JPasswordField();
        styleField(newPasswordField);
        newPasswordField.setColumns(18);

        JLabel confirmPassLabel = new JLabel("Confirm Password");
        confirmPassLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));

        confirmPasswordField = new JPasswordField();
        styleField(confirmPasswordField);
        confirmPasswordField.setColumns(18);

        gbc.insets = new Insets(6, 0, 6, 0);  gbc.gridy = 0; formGrid.add(userLabel, gbc);
        gbc.insets = new Insets(6, 0, 6, 0);  gbc.gridy = 1; formGrid.add(usernameField, gbc);
        gbc.insets = new Insets(16, 0, 6, 0); gbc.gridy = 2; formGrid.add(newPassLabel, gbc);
        gbc.insets = new Insets(6, 0, 6, 0);  gbc.gridy = 3; formGrid.add(newPasswordField, gbc);
        gbc.insets = new Insets(16, 0, 6, 0); gbc.gridy = 4; formGrid.add(confirmPassLabel, gbc);
        gbc.insets = new Insets(6, 0, 6, 0);  gbc.gridy = 5; formGrid.add(confirmPasswordField, gbc);

        // Reset Password button (blue, matches Login button style)
        JButton resetBtn = new RoundedButton("RESET PASSWORD", NAVY_BLUE, WHITE);
        resetBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        resetBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        resetBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        resetBtn.addActionListener(e -> handleResetPassword());

        // Back button (returns to Login page)
        JButton backBtn = new JButton("Back to Login");
        backBtn.setFont(new Font("SansSerif", Font.PLAIN, 12));
        backBtn.setForeground(RED_ACCENT);
        backBtn.setBorderPainted(false);
        backBtn.setContentAreaFilled(false);
        backBtn.setFocusPainted(false);
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.addActionListener(e -> handleBack());

        // Assemble card
        card.add(formTitle);
        card.add(Box.createRigidArea(new Dimension(0, 8)));
        card.add(sep);
        card.add(Box.createRigidArea(new Dimension(0, 12)));
        card.add(infoLabel);
        card.add(Box.createRigidArea(new Dimension(0, 20)));
        card.add(formGrid);
        card.add(Box.createRigidArea(new Dimension(0, 22)));
        card.add(resetBtn);
        card.add(Box.createRigidArea(new Dimension(0, 12)));
        card.add(backBtn);

        center.add(card);
        return center;
    }

    private void styleField(JTextField field) {
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(5, 8, 5, 8)
        ));
    }

    // ---------------------------------------------------------
    // Custom button that always paints its own background color,
    // regardless of the OS Look and Feel.
    // ---------------------------------------------------------
    private static class RoundedButton extends JButton {
        private final Color bgColor;
        private final Color hoverColor;

        RoundedButton(String text, Color bgColor, Color textColor) {
            super(text);
            this.bgColor = bgColor;
            this.hoverColor = bgColor.darker();
            setForeground(textColor);
            setFont(new Font("SansSerif", Font.BOLD, 14));
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setOpaque(false);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setBackground(hoverColor);
                    repaint();
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground(bgColor);
                    repaint();
                }
            });
            setBackground(bgColor);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // ---------------------------------------------------------
    // FOOTER PANEL : Same as Login page
    // ---------------------------------------------------------
    private JPanel buildFooterPanel() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(NAVY_BLUE);
        footer.setBorder(new EmptyBorder(10, 15, 10, 15));

        JLabel footerLabel = new JLabel(
                "<html><div style='text-align:center; width:100%;'>"
                        + "Peehu Sharma &nbsp;|&nbsp; PIET25CS111"
                        + "</div></html>",
                SwingConstants.CENTER
        );
        footerLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        footerLabel.setForeground(WHITE);
        footerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        footer.add(footerLabel, BorderLayout.CENTER);
        return footer;
    }

    // ---------------------------------------------------------
    // Helper: load and scale the logo image safely
    // ---------------------------------------------------------
    private ImageIcon loadLogo(String path, int width, int height) {
        java.io.File file = new java.io.File(path);
        if (!file.exists()) {
            return null;
        }
        ImageIcon original = new ImageIcon(path);
        Image scaled = original.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    // ---------------------------------------------------------
    // Button actions
    // ---------------------------------------------------------
    private void handleResetPassword() {
        String username = usernameField.getText().trim();
        String newPass = new String(newPasswordField.getPassword()).trim();
        String confirmPass = new String(confirmPasswordField.getPassword()).trim();

        if (username.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in all fields.",
                    "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!newPass.equals(confirmPass)) {
            JOptionPane.showMessageDialog(this,
                    "New password and confirm password do not match.",
                    "Password Mismatch", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // TODO: Replace with actual logic to verify the username exists
        // and update its password in your database.
        JOptionPane.showMessageDialog(this,
                "Password reset successfully for \"" + username + "\".\nPlease login with your new password.",
                "Success", JOptionPane.INFORMATION_MESSAGE);

        dispose();
        SwingUtilities.invokeLater(LoginPage::new);
    }

    private void handleBack() {
        dispose();
        SwingUtilities.invokeLater(LoginPage::new);
    }

    // ---------------------------------------------------------
    // Main (lets you test this page directly)
    // ---------------------------------------------------------
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
        SwingUtilities.invokeLater(ForgotPasswordPage::new);
    }
}