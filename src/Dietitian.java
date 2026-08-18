import java.util.ArrayList;

/**
 * A dietitian. Dietitians don't prescribe medication or take blood
 * samples, but they have their own unique behaviour - providing a diet
 * plan - so this class does not implement either capability interface.
 */
public class Dietitian extends HealthProfessional {

    private String dietFocus;

    public Dietitian(String id, String name, ArrayList<String> workingDays, String dietFocus) {
        super(id, name, workingDays);
        this.dietFocus = dietFocus;
    }

    // overloaded constructor - no working days supplied, defaults to Monday-Friday
    public Dietitian(String id, String name, String dietFocus) {
        super(id, name);
        this.dietFocus = dietFocus;
    }

    public String getDietFocus() {
        return dietFocus;
    }

    @Override
    public String getProfessionalType() {
        return "Dietitian";
    }

    public void providePlan(Patient patient, String planDetails) {
        System.out.println(getName() + " has given " + patient.getName() + " a diet plan: " + planDetails);
    }

    @Override
    public String toString() {
        return super.toString() + ", Diet focus: " + dietFocus;
    }
}
