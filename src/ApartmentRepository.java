
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApartmentRepository {

    // ADD apartment
    public static void add(Apartment a) {
        String sql = "INSERT INTO apartments VALUES (NULL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql,
                     Statement.RETURN_GENERATED_KEYS)) {

            setValues(ps, a);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                a.id = rs.getInt(1);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error adding apartment", e);
        }
    }

    // GET all apartments
    public static List<Apartment> getAll() {
        List<Apartment> list = new ArrayList<>();
        String sql = "SELECT * FROM apartments";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Apartment a = new Apartment();

                a.id = rs.getInt("id");
                a.fullName = rs.getString("full_name");
                a.dob = rs.getString("dob");
                a.gender = rs.getString("gender");
                a.idProofType = rs.getString("id_proof_type");
                a.idProofNumber = rs.getString("id_proof_number");
                a.contactNumber = rs.getString("contact_number");
                a.email = rs.getString("email");
                a.currentAddress = rs.getString("current_address");
                a.locationName = rs.getString("location_name");
                a.previousAddress = rs.getString("previous_address");
                a.durationOfStay = rs.getString("duration_of_stay");
                a.reasonForLeaving = rs.getString("reason_for_leaving");
                a.previousLandlordContact = rs.getString("previous_landlord_contact");
                a.ownerName = rs.getString("owner_name");
                a.ownerContact = rs.getString("owner_contact");
                a.ownerAddress = rs.getString("owner_address");
                a.backgroundCheckConsent = rs.getBoolean("background_check_consent");
                a.creditCheckConsent = rs.getBoolean("credit_check_consent");
                a.screeningStatus = rs.getString("screening_status");
                a.occupation = rs.getString("occupation");
                a.employerName = rs.getString("employer_name");
                a.monthlyIncome = rs.getString("monthly_income");
                a.employmentType = rs.getString("employment_type");

                list.add(a);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error fetching apartments", e);
        }

        return list;
    }

    // UPDATE apartment
    public static void update(Apartment a) {
        String sql = "UPDATE apartments SET full_name=?, dob=?, gender=?, " +
                "id_proof_type=?, id_proof_number=?, contact_number=?, email=?, " +
                "current_address=?, location_name=?, previous_address=?, " +
                "duration_of_stay=?, reason_for_leaving=?, " +
                "previous_landlord_contact=?, owner_name=?, owner_contact=?, " +
                "owner_address=?, background_check_consent=?, " +
                "credit_check_consent=?, screening_status=?, occupation=?, " +
                "employer_name=?, monthly_income=?, employment_type=? WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            setValues(ps, a);
            ps.setInt(24, a.id);
            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Error updating apartment", e);
        }
    }

    // DELETE apartment
    public static void remove(Apartment a) {
        String sql = "DELETE FROM apartments WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, a.id);
            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Error deleting apartment", e);
        }
    }

    // Set all 23 apartment values
    private static void setValues(PreparedStatement ps, Apartment a)
            throws SQLException {

        ps.setString(1, a.fullName);
        ps.setString(2, a.dob);
        ps.setString(3, a.gender);
        ps.setString(4, a.idProofType);
        ps.setString(5, a.idProofNumber);
        ps.setString(6, a.contactNumber);
        ps.setString(7, a.email);
        ps.setString(8, a.currentAddress);
        ps.setString(9, a.locationName);
        ps.setString(10, a.previousAddress);
        ps.setString(11, a.durationOfStay);
        ps.setString(12, a.reasonForLeaving);
        ps.setString(13, a.previousLandlordContact);
        ps.setString(14, a.ownerName);
        ps.setString(15, a.ownerContact);
        ps.setString(16, a.ownerAddress);
        ps.setBoolean(17, a.backgroundCheckConsent);
        ps.setBoolean(18, a.creditCheckConsent);
        ps.setString(19, a.screeningStatus);
        ps.setString(20, a.occupation);
        ps.setString(21, a.employerName);
        ps.setString(22, a.monthlyIncome);
        ps.setString(23, a.employmentType);
    }
}
