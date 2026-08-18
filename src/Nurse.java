import java.util.ArrayList;

/**
 * A nurse. Nurses do not prescribe medication in this system, but they
 * can take blood samples.
 */
public class Nurse extends HealthProfessional implements BloodSampler {

    private String ward;

    public Nurse(String id, String name, ArrayList<String> workingDays, String ward) {
        super(id, name, workingDays);
        this.ward = ward;
    }

    // overloaded constructor - no working days supplied, defaults to Monday-Friday
    public Nurse(String id, String name, String ward) {
        super(id, name);
        this.ward = ward;
    }

    public String getWard() {
        return ward;
    }

    @Override
    public String getProfessionalType() {
        return "Nurse";
    }

    @Override
    public void takeBloodSample(Patient patient) {
        System.out.println("Nurse " + getName() + " has taken a blood sample from " + patient.getName());
    }

    @Override
    public String toString() {
        return super.toString() + ", Ward: " + ward;
    }
}
