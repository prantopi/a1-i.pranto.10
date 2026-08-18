import java.util.ArrayList;

/**
 * A general practitioner - usually the first point of contact for a
 * patient. Every GP can prescribe medication.
 */
public class GeneralPractitioner extends HealthProfessional implements Prescriber {

    private int yearsOfExperience;

    public GeneralPractitioner(String id, String name, ArrayList<String> workingDays, int yearsOfExperience) {
        super(id, name, workingDays);
        this.yearsOfExperience = yearsOfExperience;
    }

    // overloaded constructor - no working days supplied, defaults to Monday-Friday
    public GeneralPractitioner(String id, String name, int yearsOfExperience) {
        super(id, name);
        this.yearsOfExperience = yearsOfExperience;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    @Override
    public String getProfessionalType() {
        return "General Practitioner";
    }

    @Override
    public void prescribeMedication(Patient patient, String medication) {
        System.out.println("Dr " + getName() + " (GP) has prescribed " + medication + " for " + patient.getName());
    }

    @Override
    public String toString() {
        return super.toString() + ", Years of experience: " + yearsOfExperience;
    }
}
