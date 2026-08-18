import java.util.ArrayList;

/**
 * Abstract base class for every kind of health professional in the system.
 * Captures the information and rules that are common to all of them: a
 * numeric id, a name, and the days of the week they work. Concrete
 * subclasses (GeneralPractitioner, Specialist, Nurse, Dietitian, ...) add
 * whatever extra information and behaviour is specific to that type.
 *
 * This class is abstract on purpose - a "plain" health professional with
 * no specialty does not make sense in this system, so nobody should be
 * able to write "new HealthProfessional(...)" directly. Every subclass is
 * also forced to say what kind of professional it is, by implementing
 * getProfessionalType().
 */
public abstract class HealthProfessional {

    // the only days the clinic recognises - used to validate workingDays
    private static final String[] VALID_DAYS =
            {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    // sensible default for professionals who don't specify their days
    private static final String[] DEFAULT_WORKING_DAYS =
            {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

    private String id;
    private String name;
    private ArrayList<String> workingDays;

    /**
     * Creates a health professional with an explicit set of working days.
     *
     * @param id an id made up of digits only
     * @param name the professional's name
     * @param workingDays the days of the week this professional works, must not be empty
     * @throws IllegalArgumentException if any of the details are missing or invalid
     */
    public HealthProfessional(String id, String name, ArrayList<String> workingDays) {
        if (id == null || id.length() == 0 || !isNumeric(id)) {
            throw new IllegalArgumentException("Health professional id must contain digits only.");
        }
        if (name == null || name.trim().length() == 0) {
            throw new IllegalArgumentException("Health professional name cannot be empty.");
        }
        if (workingDays == null || workingDays.isEmpty()) {
            throw new IllegalArgumentException("A health professional must work at least one day a week.");
        }
        for (String day : workingDays) {
            if (!isValidDay(day)) {
                throw new IllegalArgumentException("Not a valid day of the week: " + day);
            }
        }

        this.id = id;
        this.name = name;
        // defensive copy so external code can't reach in and change our list later
        this.workingDays = new ArrayList<String>(workingDays);
    }

    /**
     * Creates a health professional working the default Monday-Friday
     * schedule, for callers who don't need anything different.
     *
     * @param id an id made up of digits only
     * @param name the professional's name
     */
    public HealthProfessional(String id, String name) {
        this(id, name, defaultWorkingDays());
    }

    private static ArrayList<String> defaultWorkingDays() {
        ArrayList<String> days = new ArrayList<String>();
        for (String day : DEFAULT_WORKING_DAYS) {
            days.add(day);
        }
        return days;
    }

    private static boolean isNumeric(String value) {
        for (int i = 0; i < value.length(); i++) {
            if (!Character.isDigit(value.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isValidDay(String day) {
        for (String valid : VALID_DAYS) {
            if (valid.equalsIgnoreCase(day)) {
                return true;
            }
        }
        return false;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    /**
     * @return a copy of the working days, so callers can look but not change them
     */
    public ArrayList<String> getWorkingDays() {
        return new ArrayList<String>(workingDays);
    }

    /**
     * @return a short label identifying what kind of health professional this is,
     *         e.g. "General Practitioner"
     */
    public abstract String getProfessionalType();

    // Shared fields only - subclasses override toString(), call
    // super.toString() and append their own extra detail on top.
    @Override
    public String toString() {
        return "Id: " + id + ", Name: " + name + ", Type: " + getProfessionalType()
                + ", Working days: " + formatWorkingDays();
    }

    private String formatWorkingDays() {
        String result = "";
        for (int i = 0; i < workingDays.size(); i++) {
            result += workingDays.get(i);
            if (i < workingDays.size() - 1) {
                result += ", ";
            }
        }
        return result;
    }

    // Two health professionals are the same professional if they share an
    // id - this is what lets the appointment book detect a professional
    // being double booked, no matter their concrete subtype.
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof HealthProfessional)) {
            return false;
        }
        HealthProfessional other = (HealthProfessional) obj;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
