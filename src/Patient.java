/**
 * A patient booking an appointment. Kept simple - just enough
 * information to identify who is coming in and how to reach them.
 */
public class Patient {

    private String name;
    private String mobileNumber;

    public Patient(String name, String mobileNumber) {
        if (name == null || name.trim().length() == 0) {
            throw new IllegalArgumentException("Patient name cannot be empty.");
        }
        if (mobileNumber == null || mobileNumber.trim().length() == 0) {
            throw new IllegalArgumentException("Patient mobile number cannot be empty.");
        }
        this.name = name;
        this.mobileNumber = mobileNumber;
    }

    public String getName() {
        return name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    @Override
    public String toString() {
        return "Patient: " + name + " (" + mobileNumber + ")";
    }

    // Two patients are treated as the same patient if they share a mobile number.
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Patient)) {
            return false;
        }
        Patient other = (Patient) obj;
        return mobileNumber.equals(other.mobileNumber);
    }

    @Override
    public int hashCode() {
        return mobileNumber.hashCode();
    }
}
