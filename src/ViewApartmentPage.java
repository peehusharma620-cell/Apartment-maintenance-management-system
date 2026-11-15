
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ViewApartmentPage extends JFrame {

    private static final String LOGO_PATH = "logo.png";

    private static final Color NAVY_BLUE = new Color(45, 42, 130);
    private static final Color RED_ACCENT = new Color(200, 30, 40);
    private static final Color GREEN_EDIT = new Color(30, 130, 76);
    private static final Color LIGHT_BG = new Color(245, 246, 250);
    private static final Color WHITE = Color.WHITE;

    private final String loggedInUser;
    private JPanel listContainer;

    public ViewApartmentPage(String loggedInUser) {
        this.loggedInUser = (loggedInUser == null || loggedInUser.isEmpty())
                ? "User" : loggedInUser;

        setTitle("Apartment Maintenance Management System - View Apartment");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(760, 700);
        setMinimumSize(new Dimension(600, 560));
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
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(LIGHT_BG);
        wrapper.setBorder(new EmptyBorder(20, 30, 10, 30));

        JPanel titleRow = new JPanel(new BorderLayout());
        titleRow.setBackground(LIGHT_BG);

        JPanel titleTextPanel = new JPanel();
        titleTextPanel.setLayout(new BoxLayout(titleTextPanel, BoxLayout.Y_AXIS));
        titleTextPanel.setBackground(LIGHT_BG);

        JLabel pageTitle = new JLabel("View Apartment");
        pageTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        pageTitle.setForeground(NAVY_BLUE);

        JLabel pageSubtitle = new JLabel("All apartment / tenant records currently on file");
        pageSubtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        pageSubtitle.setForeground(Color.GRAY);

        titleTextPanel.add(pageTitle);
        titleTextPanel.add(Box.createRigidArea(new Dimension(0, 2)));
        titleTextPanel.add(pageSubtitle);

        JButton backBtn = new JButton("Back to Home");
        backBtn.setFont(new Font("SansSerif", Font.PLAIN, 13));
        backBtn.setForeground(RED_ACCENT);
        backBtn.setBorderPainted(false);
        backBtn.setContentAreaFilled(false);
        backBtn.setFocusPainted(false);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.addActionListener(e -> handleBack());

        titleRow.add(titleTextPanel, BorderLayout.WEST);
        titleRow.add(backBtn, BorderLayout.EAST);

        wrapper.add(titleRow, BorderLayout.NORTH);

        listContainer = new JPanel();
        listContainer.setLayout(new BoxLayout(listContainer, BoxLayout.Y_AXIS));
        listContainer.setBackground(LIGHT_BG);
        listContainer.setBorder(new EmptyBorder(16, 0, 0, 0));

        refreshList();

        JScrollPane scrollPane = new JScrollPane(listContainer);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBackground(LIGHT_BG);

        wrapper.add(scrollPane, BorderLayout.CENTER);

        return wrapper;
    }

    private void refreshList() {
        listContainer.removeAll();

        List<Apartment> apartments = ApartmentRepository.getAll();

        if (apartments.isEmpty()) {
            JLabel emptyLabel = new JLabel("No apartments have been added yet.");
            emptyLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
            emptyLabel.setForeground(Color.GRAY);
            listContainer.add(emptyLabel);
        } else {
            for (Apartment apt : apartments) {
                listContainer.add(buildApartmentCard(apt));
                listContainer.add(Box.createRigidArea(new Dimension(0, 12)));
            }
        }

        listContainer.revalidate();
        listContainer.repaint();
    }

    private JPanel buildApartmentCard(Apartment apt) {
        JPanel card = new JPanel(new BorderLayout(10, 0));
        card.setBackground(WHITE);
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 225, 225)),
                new EmptyBorder(12, 16, 12, 16)
        ));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(WHITE);

        String locationName = (apt.locationName == null || apt.locationName.isEmpty())
                ? "(Unnamed Location)" : apt.locationName;

        JLabel nameLabel = new JLabel(locationName);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        nameLabel.setForeground(NAVY_BLUE);

        String tenant = (apt.fullName == null || apt.fullName.isEmpty())
                ? "-" : apt.fullName;

        String owner = (apt.ownerName == null || apt.ownerName.isEmpty())
                ? "-" : apt.ownerName;

        JLabel detailLabel = new JLabel("Tenant: " + tenant + "   |   Owner: " + owner);
        detailLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        detailLabel.setForeground(Color.GRAY);

        infoPanel.add(nameLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 4)));
        infoPanel.add(detailLabel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        buttonPanel.setBackground(WHITE);

        JButton viewBtn = new RoundedButton("View", NAVY_BLUE, WHITE);
        viewBtn.setPreferredSize(new Dimension(80, 34));
        viewBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        viewBtn.addActionListener(e -> showViewDialog(apt));

        JButton editBtn = new RoundedButton("Edit", GREEN_EDIT, WHITE);
        editBtn.setPreferredSize(new Dimension(80, 34));
        editBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        editBtn.addActionListener(e -> showEditDialog(apt));

        JButton deleteBtn = new RoundedButton("Delete", RED_ACCENT, WHITE);
        deleteBtn.setPreferredSize(new Dimension(80, 34));
        deleteBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteBtn.addActionListener(e -> handleDelete(apt));

        buttonPanel.add(viewBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);

        card.add(infoPanel, BorderLayout.CENTER);
        card.add(buttonPanel, BorderLayout.EAST);

        return card;
    }

    private void showViewDialog(Apartment apt) {
        JDialog dialog = new JDialog(this, "Apartment Details", true);
        dialog.setSize(480, 620);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(WHITE);
        content.setBorder(new EmptyBorder(20, 24, 20, 24));

        content.add(buildReadOnlySection("Personal Identity", new String[][] {
                {"Full Name", apt.fullName},
                {"Date of Birth", apt.dob},
                {"Gender", apt.gender},
                {"ID Proof Type", apt.idProofType},
                {"ID Proof Number", apt.idProofNumber},
                {"Contact Number", apt.contactNumber},
                {"Email Address", apt.email},
                {"Current Address", apt.currentAddress}
        }));

        content.add(Box.createRigidArea(new Dimension(0, 14)));

        content.add(buildReadOnlySection("Rental History", new String[][] {
                {"Apartment / Location Name", apt.locationName},
                {"Previous Address", apt.previousAddress},
                {"Duration of Previous Stay", apt.durationOfStay},
                {"Reason for Leaving", apt.reasonForLeaving},
                {"Previous Landlord Contact", apt.previousLandlordContact}
        }));

        content.add(Box.createRigidArea(new Dimension(0, 14)));

        content.add(buildReadOnlySection("Owner's Details", new String[][] {
                {"Owner's Name", apt.ownerName},
                {"Owner's Contact Number", apt.ownerContact},
                {"Owner's Address", apt.ownerAddress}
        }));

        content.add(Box.createRigidArea(new Dimension(0, 14)));

        content.add(buildReadOnlySection("Consent & Screening", new String[][] {
                {"Background Check Consent", apt.backgroundCheckConsent ? "Yes" : "No"},
                {"Credit Check Consent", apt.creditCheckConsent ? "Yes" : "No"},
                {"Screening Status", apt.screeningStatus}
        }));

        content.add(Box.createRigidArea(new Dimension(0, 14)));

        content.add(buildReadOnlySection("Financial & Employment Data", new String[][] {
                {"Occupation", apt.occupation},
                {"Employer Name", apt.employerName},
                {"Monthly Income", apt.monthlyIncome},
                {"Employment Type", apt.employmentType}
        }));

        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JButton closeBtn = new RoundedButton("Close", NAVY_BLUE, WHITE);
        closeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeBtn.addActionListener(e -> dialog.dispose());

        JPanel footer = new JPanel();
        footer.setBackground(WHITE);
        footer.setBorder(new EmptyBorder(10, 20, 16, 20));
        footer.add(closeBtn);

        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.add(footer, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private JPanel buildReadOnlySection(String title, String[][] rows) {
        JPanel section = new JPanel(new GridBagLayout());
        section.setBackground(WHITE);
        section.setAlignmentX(Component.LEFT_ALIGNMENT);

        TitledBorder titledBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(225, 225, 225)), title);

        titledBorder.setTitleFont(new Font("SansSerif", Font.BOLD, 13));
        titledBorder.setTitleColor(NAVY_BLUE);

        section.setBorder(BorderFactory.createCompoundBorder(
                titledBorder, new EmptyBorder(8, 12, 10, 12)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        for (int i = 0; i < rows.length; i++) {
            JLabel label = new JLabel(rows[i][0] + ":");
            label.setFont(new Font("SansSerif", Font.BOLD, 12));

            String val = rows[i][1];
            JLabel valueLabel = new JLabel(
                    (val == null || val.isEmpty()) ? "-" : val);

            valueLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));

            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.weightx = 0;
            section.add(label, gbc);

            gbc.gridx = 1;
            gbc.weightx = 1.0;
            section.add(valueLabel, gbc);
        }

        return section;
    }

    private void showEditDialog(Apartment apt) {
        JDialog dialog = new JDialog(this, "Edit Apartment", true);
        dialog.setSize(480, 680);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(WHITE);
        content.setBorder(new EmptyBorder(20, 24, 20, 24));

        JTextField fullNameField = editableField(apt.fullName);
        JTextField dobField = editableField(apt.dob);

        JComboBox<String> genderBox = editableCombo(
                new String[]{"Male", "Female", "Other"}, apt.gender);

        JComboBox<String> idProofTypeBox = editableCombo(
                new String[]{"Aadhaar Card", "PAN Card", "Passport",
                        "Voter ID", "Driving License"}, apt.idProofType);

        JTextField idProofNumberField = editableField(apt.idProofNumber);
        JTextField contactNumberField = editableField(apt.contactNumber);
        JTextField emailField = editableField(apt.email);
        JTextField currentAddressField = editableField(apt.currentAddress);

        JTextField locationNameField = editableField(apt.locationName);
        JTextField previousAddressField = editableField(apt.previousAddress);
        JTextField durationOfStayField = editableField(apt.durationOfStay);
        JTextField reasonForLeavingField = editableField(apt.reasonForLeaving);
        JTextField previousLandlordContactField = editableField(apt.previousLandlordContact);

        JTextField ownerNameField = editableField(apt.ownerName);
        JTextField ownerContactField = editableField(apt.ownerContact);
        JTextField ownerAddressField = editableField(apt.ownerAddress);

        JCheckBox backgroundCheckBox = new JCheckBox(
                "I consent to a background check", apt.backgroundCheckConsent);

        JCheckBox creditCheckBox = new JCheckBox(
                "I consent to a credit check", apt.creditCheckConsent);

        backgroundCheckBox.setBackground(WHITE);
        creditCheckBox.setBackground(WHITE);

        JComboBox<String> screeningStatusBox = editableCombo(
                new String[]{"Pending", "In Progress", "Approved", "Rejected"},
                apt.screeningStatus);

        JTextField occupationField = editableField(apt.occupation);
        JTextField employerNameField = editableField(apt.employerName);
        JTextField monthlyIncomeField = editableField(apt.monthlyIncome);

        JComboBox<String> employmentTypeBox = editableCombo(
                new String[]{"Salaried", "Self-Employed", "Business Owner",
                        "Student", "Unemployed", "Retired"},
                apt.employmentType);

        content.add(buildEditableSection("Personal Identity", new Object[][] {
                {"Full Name", fullNameField}, {"Date of Birth", dobField},
                {"Gender", genderBox}, {"ID Proof Type", idProofTypeBox},
                {"ID Proof Number", idProofNumberField},
                {"Contact Number", contactNumberField},
                {"Email Address", emailField},
                {"Current Address", currentAddressField}
        }));

        content.add(Box.createRigidArea(new Dimension(0, 14)));

        content.add(buildEditableSection("Rental History", new Object[][] {
                {"Apartment / Location Name", locationNameField},
                {"Previous Address", previousAddressField},
                {"Duration of Previous Stay", durationOfStayField},
                {"Reason for Leaving", reasonForLeavingField},
                {"Previous Landlord Contact", previousLandlordContactField}
        }));

        content.add(Box.createRigidArea(new Dimension(0, 14)));

        content.add(buildEditableSection("Owner's Details", new Object[][] {
                {"Owner's Name", ownerNameField},
                {"Owner's Contact Number", ownerContactField},
                {"Owner's Address", ownerAddressField}
        }));

        content.add(Box.createRigidArea(new Dimension(0, 14)));

        content.add(buildEditableSection("Consent & Screening", new Object[][] {
                {"Background Check", backgroundCheckBox},
                {"Credit Check", creditCheckBox},
                {"Screening Status", screeningStatusBox}
        }));

        content.add(Box.createRigidArea(new Dimension(0, 14)));

        content.add(buildEditableSection("Financial & Employment Data", new Object[][] {
                {"Occupation", occupationField},
                {"Employer Name", employerNameField},
                {"Monthly Income", monthlyIncomeField},
                {"Employment Type", employmentTypeBox}
        }));

        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JButton saveBtn = new RoundedButton("Save Changes", NAVY_BLUE, WHITE);
        saveBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        saveBtn.addActionListener(e -> {
            apt.fullName = fullNameField.getText().trim();
            apt.dob = dobField.getText().trim();
            apt.gender = (String) genderBox.getSelectedItem();
            apt.idProofType = (String) idProofTypeBox.getSelectedItem();
            apt.idProofNumber = idProofNumberField.getText().trim();
            apt.contactNumber = contactNumberField.getText().trim();
            apt.email = emailField.getText().trim();
            apt.currentAddress = currentAddressField.getText().trim();

            apt.locationName = locationNameField.getText().trim();
            apt.previousAddress = previousAddressField.getText().trim();
            apt.durationOfStay = durationOfStayField.getText().trim();
            apt.reasonForLeaving = reasonForLeavingField.getText().trim();
            apt.previousLandlordContact = previousLandlordContactField.getText().trim();

            apt.ownerName = ownerNameField.getText().trim();
            apt.ownerContact = ownerContactField.getText().trim();
            apt.ownerAddress = ownerAddressField.getText().trim();

            apt.backgroundCheckConsent = backgroundCheckBox.isSelected();
            apt.creditCheckConsent = creditCheckBox.isSelected();
            apt.screeningStatus = (String) screeningStatusBox.getSelectedItem();

            apt.occupation = occupationField.getText().trim();
            apt.employerName = employerNameField.getText().trim();
            apt.monthlyIncome = monthlyIncomeField.getText().trim();
            apt.employmentType = (String) employmentTypeBox.getSelectedItem();

            try {
                ApartmentRepository.update(apt);

                dialog.dispose();
                refreshList();

                JOptionPane.showMessageDialog(this,
                        "Apartment details updated successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Update failed: " + ex.getMessage(),
                        "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setFont(new Font("SansSerif", Font.PLAIN, 13));
        cancelBtn.setForeground(RED_ACCENT);
        cancelBtn.setBorderPainted(false);
        cancelBtn.setContentAreaFilled(false);
        cancelBtn.setFocusPainted(false);
        cancelBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelBtn.addActionListener(e -> dialog.dispose());

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        footer.setBackground(WHITE);
        footer.setBorder(new EmptyBorder(10, 20, 16, 20));
        footer.add(saveBtn);
        footer.add(cancelBtn);

        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.add(footer, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private JTextField editableField(String value) {
        JTextField field = new JTextField(value == null ? "" : value);
        field.setFont(new Font("SansSerif", Font.PLAIN, 13));
        field.setPreferredSize(new Dimension(230, 30));

        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(4, 8, 4, 8)));

        return field;
    }

    private JComboBox<String> editableCombo(String[] options, String selected) {
        JComboBox<String> box = new JComboBox<>(options);
        box.setFont(new Font("SansSerif", Font.PLAIN, 13));

        if (selected != null) {
            box.setSelectedItem(selected);
        }

        return box;
    }

    private JPanel buildEditableSection(String title, Object[][] rows) {
        JPanel section = new JPanel(new GridBagLayout());
        section.setBackground(WHITE);
        section.setAlignmentX(Component.LEFT_ALIGNMENT);

        TitledBorder titledBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(225, 225, 225)), title);

        titledBorder.setTitleFont(new Font("SansSerif", Font.BOLD, 13));
        titledBorder.setTitleColor(NAVY_BLUE);

        section.setBorder(BorderFactory.createCompoundBorder(
                titledBorder, new EmptyBorder(8, 12, 10, 12)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        for (int i = 0; i < rows.length; i++) {
            JLabel label = new JLabel((String) rows[i][0]);
            label.setFont(new Font("SansSerif", Font.PLAIN, 12));

            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.weightx = 0;
            section.add(label, gbc);

            gbc.gridx = 1;
            gbc.weightx = 1.0;
            section.add((JComponent) rows[i][1], gbc);
        }

        return section;
    }

    // Corrected database DELETE operation
    private void handleDelete(Apartment apt) {
        int choice = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this apartment?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (choice == JOptionPane.YES_OPTION) {
            try {
                ApartmentRepository.remove(apt);
                refreshList();

                JOptionPane.showMessageDialog(this,
                        "Apartment deleted successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Delete failed: " + e.getMessage(),
                        "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static class RoundedButton extends JButton {
        private final Color bgColor;
        private final Color hoverColor;

        RoundedButton(String text, Color bgColor, Color textColor) {
            super(text);
            this.bgColor = bgColor;
            this.hoverColor = bgColor.darker();

            setForeground(textColor);
            setFont(new Font("SansSerif", Font.BOLD, 12));
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setOpaque(false);

            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    setBackground(hoverColor);
                    repaint();
                }

                public void mouseExited(MouseEvent e) {
                    setBackground(bgColor);
                    repaint();
                }
            });

            setBackground(bgColor);
        }

        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
            g2.dispose();

            super.paintComponent(g);
        }
    }

    private JPanel buildFooterPanel() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(NAVY_BLUE);
        footer.setBorder(new EmptyBorder(10, 15, 10, 15));

        JLabel footerLabel = new JLabel(
                "<html><div style='text-align:center; width:100%;'>"
                        + "Peehu Sharma &nbsp;|&nbsp; PIET25CS111"
                        + "</div></html>",
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

    private void handleBack() {
        dispose();
        SwingUtilities.invokeLater(() -> new HomePage(loggedInUser));
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(() ->
                new ViewApartmentPage("Peehu"));
    }
}
