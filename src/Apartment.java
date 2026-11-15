
/**
 * Apartment data model
 */
public class Apartment {

    // Database ID
    public int id;

    // Personal Identity
    public String fullName;
    public String dob;
    public String gender;
    public String idProofType;
    public String idProofNumber;
    public String contactNumber;
    public String email;
    public String currentAddress;

    // Rental History
    public String locationName;
    public String previousAddress;
    public String durationOfStay;
    public String reasonForLeaving;
    public String previousLandlordContact;

    // Owner Details
    public String ownerName;
    public String ownerContact;
    public String ownerAddress;

    // Consent & Screening
    public boolean backgroundCheckConsent;
    public boolean creditCheckConsent;
    public String screeningStatus;

    // Financial & Employment
    public String occupation;
    public String employerName;
    public String monthlyIncome;
    public String employmentType;

    public Apartment() {
    }
}
