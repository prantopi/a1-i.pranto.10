import java.util.ArrayList;

/**
 * A specialist doctor who focuses on one area of medicine, for example
 * cardiology or paediatrics. Like a GP, a specialist can prescribe
 * medication.
 */
public class Specialist extends HealthProfessional implements Prescriber {

    private static final String DEFAULT_SPECIALTY_AREA = "General Specialist";

    private String specialtyArea;

    public Specialist(String id, String name, ArrayList<String> workingDays, String specialtyArea) {
        super(id, name, workingDays);
        this.specialtyArea = specialtyArea;
    }

    // overloaded constructor - no specialty area given, falls back to a default
    public Specialist(String id, String name, ArrayList<String> workingDays) {
        this(id, name, workingDays, DEFAULT_SPECIALTY_AREA);
    }

    public String getSpecialtyArea() {
        return specialtyArea;
    }

    @Override
    public String getProfessionalType() {
        return "Specialist";
    }

    @Override
    public void prescribeMedication(Patient patient, String medication) {
        System.out.println("Dr " + getName() + " (" + specialtyArea + ") has prescribed "
                + medication + " for " + patient.getName());
    }

    @Override
    public String toString() {
        return super.toString() + ", Specialty area: " + specialtyArea;
    }
}
