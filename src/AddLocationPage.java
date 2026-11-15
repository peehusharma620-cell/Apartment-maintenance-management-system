
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

public class AddLocationPage extends JFrame {

    private final String loggedInUser;

    private JTextField fullNameField, dobField, idProofNumberField;
    private JTextField contactNumberField, emailField, currentAddressField;

    private JTextField locationNameField, previousAddressField;
    private JTextField durationOfStayField, reasonForLeavingField;
    private JTextField previousLandlordContactField;

    private JTextField ownerNameField, ownerContactField, ownerAddressField;

    private JCheckBox backgroundCheckConsentBox, creditCheckConsentBox;
    private JComboBox<String> genderBox, idProofTypeBox, screeningStatusBox;

    private JTextField occupationField, employerNameField, monthlyIncomeField;
    private JComboBox<String> employmentTypeBox;

    private static final Color NAVY_BLUE = new Color(45, 42, 130);
    private static final Color RED_ACCENT = new Color(200, 30, 40);
    private static final Color LIGHT_BG = new Color(245, 246, 250);

    public AddLocationPage(String loggedInUser) {

        this.loggedInUser =
                (loggedInUser == null || loggedInUser.isEmpty())
                        ? "User" : loggedInUser;

        setTitle("Apartment Management System - Add Location");
        setSize(720, 780);
        setMinimumSize(new Dimension(600, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(buildHeader(), BorderLayout.NORTH);
        add(buildForm(), BorderLayout.CENTER);
        add(buildFooter(), BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel buildHeader() {

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 10, 20, 10));

        JLabel title = new JLabel("APARTMENT MANAGEMENT SYSTEM");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(NAVY_BLUE);

        panel.add(title);
        return panel;
    }

    private JPanel buildForm() {

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(LIGHT_BG);
        mainPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        JLabel heading = new JLabel("Add Location");
        heading.setFont(new Font("SansSerif", Font.BOLD, 24));
        heading.setForeground(NAVY_BLUE);

        mainPanel.add(heading);
        mainPanel.add(Box.createVerticalStrut(15));

        // Personal Identity

        fullNameField = new JTextField();
        dobField = new JTextField();
        genderBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        idProofTypeBox = new JComboBox<>(new String[]{
                "Aadhaar Card", "PAN Card", "Passport",
                "Voter ID", "Driving License"
        });
        idProofNumberField = new JTextField();
        contactNumberField = new JTextField();
        emailField = new JTextField();
        currentAddressField = new JTextField();

        mainPanel.add(buildSection("Personal Identity",
                new String[]{"Full Name", "Date of Birth", "Gender",
                        "ID Proof Type", "ID Proof Number",
                        "Contact Number", "Email Address", "Current Address"},
                new JComponent[]{fullNameField, dobField, genderBox,
                        idProofTypeBox, idProofNumberField,
                        contactNumberField, emailField, currentAddressField}));

        // Rental History

        locationNameField = new JTextField();
        previousAddressField = new JTextField();
        durationOfStayField = new JTextField();
        reasonForLeavingField = new JTextField();
        previousLandlordContactField = new JTextField();

        mainPanel.add(buildSection("Rental History",
                new String[]{"Apartment / Location Name", "Previous Address",
                        "Duration of Previous Stay", "Reason for Leaving",
                        "Previous Landlord Contact"},
                new JComponent[]{locationNameField, previousAddressField,
                        durationOfStayField, reasonForLeavingField,
                        previousLandlordContactField}));

        // Owner Details

        ownerNameField = new JTextField();
        ownerContactField = new JTextField();
        ownerAddressField = new JTextField();

        mainPanel.add(buildSection("Owner's Details",
                new String[]{"Owner's Name", "Owner's Contact Number",
                        "Owner's Address"},
                new JComponent[]{ownerNameField, ownerContactField,
                        ownerAddressField}));

        // Consent and Screening

        backgroundCheckConsentBox =
                new JCheckBox("I consent to a background check");

        creditCheckConsentBox =
                new JCheckBox("I consent to a credit check");

        screeningStatusBox = new JComboBox<>(new String[]{
                "Pending", "In Progress", "Approved", "Rejected"
        });

        mainPanel.add(buildSection("Consent & Screening",
                new String[]{"Background Check", "Credit Check",
                        "Screening Status"},
                new JComponent[]{backgroundCheckConsentBox,
                        creditCheckConsentBox, screeningStatusBox}));

        // Financial and Employment Details

        occupationField = new JTextField();
        employerNameField = new JTextField();
        monthlyIncomeField = new JTextField();

        employmentTypeBox = new JComboBox<>(new String[]{
                "Salaried", "Self-Employed", "Business Owner",
                "Student", "Unemployed", "Retired"
        });

        mainPanel.add(buildSection("Financial & Employment Data",
                new String[]{"Occupation", "Employer Name",
                        "Monthly Income", "Employment Type"},
                new JComponent[]{occupationField, employerNameField,
                        monthlyIncomeField, employmentTypeBox}));

        // Buttons

        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(Color.WHITE);
        submitButton.setForeground(Color.BLACK);
        submitButton.addActionListener(e -> handleSubmit());

        JButton backButton = new JButton("Back to Home");
        backButton.addActionListener(e -> handleBack());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(submitButton);
        buttonPanel.add(backButton);

        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(buttonPanel);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(scrollPane, BorderLayout.CENTER);

        return wrapper;
    }

    private JPanel buildSection(String title, String[] labels,
                                JComponent[] fields) {

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBackground(Color.WHITE);

        TitledBorder border = BorderFactory.createTitledBorder(title);
        border.setTitleColor(NAVY_BLUE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                border, new EmptyBorder(10, 10, 10, 10)));

        for (int i = 0; i < labels.length; i++) {

            panel.add(new JLabel(labels[i]));

            if (fields[i] instanceof JTextField) {
                fields[i].setPreferredSize(new Dimension(250, 30));
            }

            panel.add(fields[i]);
        }

        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE,
                panel.getPreferredSize().height + 20));

        return panel;
    }

    private JPanel buildFooter() {

        JPanel footer = new JPanel();
        footer.setBackground(NAVY_BLUE);

        JLabel label = new JLabel("Apartment Management System");
        label.setForeground(Color.WHITE);

        footer.add(label);
        return footer;
    }

    private void handleSubmit() {

        if (fullNameField.getText().trim().isEmpty()
                || contactNumberField.getText().trim().isEmpty()
                || locationNameField.getText().trim().isEmpty()
                || ownerNameField.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Please fill in Full Name, Contact Number, "
                            + "Location Name and Owner Name.");

            return;
        }

        if (!backgroundCheckConsentBox.isSelected()) {

            JOptionPane.showMessageDialog(this,
                    "Background check consent is required.");

            return;
        }

        Apartment a = new Apartment();

        a.fullName = fullNameField.getText().trim();
        a.dob = dobField.getText().trim();
        a.gender = (String) genderBox.getSelectedItem();
        a.idProofType = (String) idProofTypeBox.getSelectedItem();
        a.idProofNumber = idProofNumberField.getText().trim();
        a.contactNumber = contactNumberField.getText().trim();
        a.email = emailField.getText().trim();
        a.currentAddress = currentAddressField.getText().trim();

        a.locationName = locationNameField.getText().trim();
        a.previousAddress = previousAddressField.getText().trim();
        a.durationOfStay = durationOfStayField.getText().trim();
        a.reasonForLeaving = reasonForLeavingField.getText().trim();
        a.previousLandlordContact =
                previousLandlordContactField.getText().trim();

        a.ownerName = ownerNameField.getText().trim();
        a.ownerContact = ownerContactField.getText().trim();
        a.ownerAddress = ownerAddressField.getText().trim();

        a.backgroundCheckConsent =
                backgroundCheckConsentBox.isSelected();

        a.creditCheckConsent =
                creditCheckConsentBox.isSelected();

        a.screeningStatus =
                (String) screeningStatusBox.getSelectedItem();

        a.occupation = occupationField.getText().trim();
        a.employerName = employerNameField.getText().trim();
        a.monthlyIncome = monthlyIncomeField.getText().trim();

        a.employmentType =
                (String) employmentTypeBox.getSelectedItem();

        try {

            ApartmentRepository.add(a);

            JOptionPane.showMessageDialog(this,
                    "Location added successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);

            dispose();
            SwingUtilities.invokeLater(
                    () -> new HomePage(loggedInUser));

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this,
                    "Database Error: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);

            e.printStackTrace();
        }
    }

    private void handleBack() {

        dispose();

        SwingUtilities.invokeLater(
                () -> new HomePage(loggedInUser));
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                () -> new AddLocationPage("Peehu"));
    }
}
