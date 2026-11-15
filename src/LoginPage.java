
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginPage extends JFrame {

    private static final String LOGO_PATH = "logo.png";

    private static final Color NAVY_BLUE = new Color(45, 42, 130);
    private static final Color RED_ACCENT = new Color(200, 30, 40);
    private static final Color LIGHT_BG = new Color(245, 246, 250);
    private static final Color WHITE = Color.WHITE;

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginPage() {
        setTitle("Apartment Maintenance Management System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(480, 620);
        setMinimumSize(new Dimension(420, 580));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(buildHeaderPanel(), BorderLayout.NORTH);
        add(buildCenterPanel(), BorderLayout.CENTER);
        add(buildFooterPanel(), BorderLayout.SOUTH);

        setVisible(true);
    }

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
        card.setMaximumSize(new Dimension(340, 400));

        JLabel loginTitle = new JLabel("Login");
        loginTitle.setFont(new Font("SansSerif", Font.BOLD, 26));
        loginTitle.setForeground(NAVY_BLUE);
        loginTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JSeparator sep = new JSeparator();
        sep.setForeground(RED_ACCENT);
        sep.setMaximumSize(new Dimension(80, 3));
        sep.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel formGrid = new JPanel(new GridBagLayout());
        formGrid.setBackground(WHITE);
        formGrid.setAlignmentX(Component.CENTER_ALIGNMENT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 0, 6, 0);
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));

        usernameField = new JTextField();
        styleField(usernameField);
        usernameField.setColumns(18);

        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));

        passwordField = new JPasswordField();
        styleField(passwordField);
        passwordField.setColumns(18);

        gbc.gridy = 0;
        formGrid.add(userLabel, gbc);

        gbc.gridy = 1;
        formGrid.add(usernameField, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(16, 0, 6, 0);
        formGrid.add(passLabel, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(6, 0, 6, 0);
        formGrid.add(passwordField, gbc);

        JButton loginBtn = new RoundedButton("LOGIN", NAVY_BLUE, WHITE);
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginBtn.addActionListener(e -> handleLogin());

        JButton forgetBtn = new JButton("Forgot Password?");
        forgetBtn.setFont(new Font("SansSerif", Font.PLAIN, 12));
        forgetBtn.setForeground(RED_ACCENT);
        forgetBtn.setBorderPainted(false);
        forgetBtn.setContentAreaFilled(false);
        forgetBtn.setFocusPainted(false);
        forgetBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        forgetBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        forgetBtn.addActionListener(e -> handleForgetPassword());

        card.add(loginTitle);
        card.add(Box.createRigidArea(new Dimension(0, 8)));
        card.add(sep);
        card.add(Box.createRigidArea(new Dimension(0, 25)));
        card.add(formGrid);
        card.add(Box.createRigidArea(new Dimension(0, 20)));
        card.add(loginBtn);
        card.add(Box.createRigidArea(new Dimension(0, 12)));
        card.add(forgetBtn);

        center.add(card);
        return center;
    }

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
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
            g2.dispose();

            super.paintComponent(g);
        }
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

    private JPanel buildFooterPanel() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(NAVY_BLUE);
        footer.setBorder(new EmptyBorder(10, 15, 10, 15));

        JLabel footerLabel = new JLabel(
                "<html><div style='text-align:center; width:100%;'>" +
                        "Peehu Sharma &nbsp;|&nbsp; PIET25CS111" +
                        "</div></html>",
                SwingConstants.CENTER);

        footerLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        footerLabel.setForeground(WHITE);
        footerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        footer.add(footerLabel, BorderLayout.CENTER);
        return footer;
    }

    private ImageIcon loadLogo(String path, int width, int height) {
        java.io.File file = new java.io.File(path);

        if (!file.exists()) {
            return null;
        }

        ImageIcon original = new ImageIcon(path);
        Image scaled = original.getImage().getScaledInstance(
                width, height, Image.SCALE_SMOOTH);

        return new ImageIcon(scaled);
    }

    // MySQL Login Authentication
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter both username and password.",
                    "Login Failed", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, username);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login Successful!");

                dispose();
                SwingUtilities.invokeLater(() -> new HomePage(username));

            } else {
                JOptionPane.showMessageDialog(this,
                        "Invalid username or password.",
                        "Login Failed", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Database Error: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleForgetPassword() {
        dispose();
        SwingUtilities.invokeLater(ForgotPasswordPage::new);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(LoginPage::new);
    }
}
