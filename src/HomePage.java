import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * Apartment Maintenance Management System
 * Home Page (shown after a successful login)
 *
 * Project by: Peehu Sharma
 * Registration No: PIET25CS111
 *
 * NOTE: Make sure "logo.png" is placed in the SAME FOLDER as this
 * HomePage.java file (or update the LOGO_PATH constant below)
 * before you compile and run the program.
 */
public class HomePage extends JFrame {

    // ---- Path to the college/institute logo image ----
    private static final String LOGO_PATH = "logo.png";

    // ---- Path to the apartment illustration shown on the Home page ----
    private static final String BANNER_PATH = "banner.png";

    // Colors picked to match the Poornima Institute branding
    private static final Color NAVY_BLUE  = new Color(45, 42, 130);
    private static final Color RED_ACCENT = new Color(200, 30, 40);
    private static final Color LIGHT_BG   = new Color(245, 246, 250);
    private static final Color WHITE      = Color.WHITE;

    // Username passed in from the login page (shown as a welcome message)
    private final String loggedInUser;

    public HomePage(String loggedInUser) {
        this.loggedInUser = (loggedInUser == null || loggedInUser.isEmpty())
                ? "User" : loggedInUser;

        setTitle("Apartment Maintenance Management System - Home");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(820, 620);
        setMinimumSize(new Dimension(680, 540));
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
    // CENTER PANEL : Sidebar (buttons) on the left + banner image
    // ---------------------------------------------------------
    private JPanel buildCenterPanel() {
        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(LIGHT_BG);

        center.add(buildSidebarPanel(), BorderLayout.WEST);
        center.add(buildImagePanel(), BorderLayout.CENTER);

        return center;
    }

    // ---- Left sidebar: welcome message + the three action buttons ----
    private JPanel buildSidebarPanel() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(WHITE);
        sidebar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 2, new Color(230, 230, 230)),
                new EmptyBorder(30, 24, 30, 24)
        ));
        sidebar.setPreferredSize(new Dimension(220, 0));

        JLabel welcomeTitle = new JLabel("<html>Welcome,<br>" + loggedInUser + "!</html>");
        welcomeTitle.setFont(new Font("SansSerif", Font.BOLD, 19));
        welcomeTitle.setForeground(NAVY_BLUE);
        welcomeTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("Maintenance Dashboard");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subtitle.setForeground(Color.GRAY);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JSeparator sep = new JSeparator();
        sep.setForeground(RED_ACCENT);
        sep.setMaximumSize(new Dimension(60, 3));
        sep.setAlignmentX(Component.LEFT_ALIGNMENT);

        // ---- Add Location button ----
        JButton addLocationBtn = new RoundedButton("Add Location", NAVY_BLUE, WHITE);
        addLocationBtn.addActionListener(e -> handleAddLocation());

        // ---- View Apartment button ----
        JButton viewApartmentBtn = new RoundedButton("View Apartment", NAVY_BLUE, WHITE);
        viewApartmentBtn.addActionListener(e -> handleViewApartment());

        // ---- Change Password button ----
        JButton changePasswordBtn = new RoundedButton("Change Password", NAVY_BLUE, WHITE);
        changePasswordBtn.addActionListener(e -> handleChangePassword());

        for (JButton b : new JButton[]{addLocationBtn, viewApartmentBtn, changePasswordBtn}) {
            b.setAlignmentX(Component.LEFT_ALIGNMENT);
            b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
            b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        // ---- Logout button ----
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("SansSerif", Font.PLAIN, 12));
        logoutBtn.setForeground(RED_ACCENT);
        logoutBtn.setBorderPainted(false);
        logoutBtn.setContentAreaFilled(false);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutBtn.addActionListener(e -> handleLogout());

        sidebar.add(welcomeTitle);
        sidebar.add(Box.createRigidArea(new Dimension(0, 4)));
        sidebar.add(subtitle);
        sidebar.add(Box.createRigidArea(new Dimension(0, 14)));
        sidebar.add(sep);
        sidebar.add(Box.createRigidArea(new Dimension(0, 30)));
        sidebar.add(addLocationBtn);
        sidebar.add(Box.createRigidArea(new Dimension(0, 16)));
        sidebar.add(viewApartmentBtn);
        sidebar.add(Box.createRigidArea(new Dimension(0, 16)));
        sidebar.add(changePasswordBtn);
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(logoutBtn);

        return sidebar;
    }

    // ---- Right side: the apartment banner image, resizes with the window ----
    private JPanel buildImagePanel() {
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBackground(LIGHT_BG);
        imagePanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        imagePanel.add(new ResizableImagePanel(BANNER_PATH), BorderLayout.CENTER);
        return imagePanel;
    }

    // ---------------------------------------------------------
    // A panel that draws an image scaled to its own current size
    // (keeping aspect ratio and centering it), so the picture grows
    // and shrinks live as the window is resized or maximized.
    // ---------------------------------------------------------
    private static class ResizableImagePanel extends JPanel {
        private final Image originalImage;

        ResizableImagePanel(String path) {
            setOpaque(false);
            java.io.File file = new java.io.File(path);
            this.originalImage = file.exists() ? new ImageIcon(path).getImage() : null;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (originalImage == null) {
                g.setColor(Color.GRAY);
                g.drawString("[banner.png not found]", 10, 20);
                return;
            }

            int imgW = originalImage.getWidth(this);
            int imgH = originalImage.getHeight(this);
            if (imgW <= 0 || imgH <= 0) {
                return;
            }

            int panelW = getWidth();
            int panelH = getHeight();

            // Scale to fit inside the panel while keeping aspect ratio
            double scale = Math.min((double) panelW / imgW, (double) panelH / imgH);
            int drawW = (int) (imgW * scale);
            int drawH = (int) (imgH * scale);
            int x = (panelW - drawW) / 2;
            int y = (panelH - drawH) / 2;

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(originalImage, x, y, drawW, drawH, this);
            g2.dispose();
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
    private void handleAddLocation() {
        dispose();
        SwingUtilities.invokeLater(() -> new AddLocationPage(loggedInUser));
    }

    private void handleViewApartment() {
        dispose();
        SwingUtilities.invokeLater(() -> new ViewApartmentPage(loggedInUser));
    }

    private void handleChangePassword() {
        JPasswordField oldPassField = new JPasswordField();
        JPasswordField newPassField = new JPasswordField();
        JPasswordField confirmPassField = new JPasswordField();

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 8, 8));
        formPanel.add(new JLabel("Old Password:"));
        formPanel.add(oldPassField);
        formPanel.add(new JLabel("New Password:"));
        formPanel.add(newPassField);
        formPanel.add(new JLabel("Confirm Password:"));
        formPanel.add(confirmPassField);

        int result = JOptionPane.showConfirmDialog(this, formPanel,
                "Change Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String oldPass = new String(oldPassField.getPassword()).trim();
            String newPass = new String(newPassField.getPassword()).trim();
            String confirmPass = new String(confirmPassField.getPassword()).trim();

            if (oldPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
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

            // TODO: Replace with actual logic to verify oldPass against the
            // database and then update it to newPass.
            JOptionPane.showMessageDialog(this,
                    "Password changed successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void handleLogout() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to logout?",
                "Confirm Logout", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
            SwingUtilities.invokeLater(LoginPage::new);
        }
    }

    // ---------------------------------------------------------
    // Main (lets you test this page directly, without logging in)
    // ---------------------------------------------------------
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
        SwingUtilities.invokeLater(() -> new HomePage("Peehu"));
    }
}