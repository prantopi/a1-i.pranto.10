import java.util.ArrayList;

/**
 * Abstract base class for every health professional in the system.
 * Holds what's common to all of them - an id, a name and the days they
 * work. Concrete subclasses (GeneralPractitioner, Specialist, Nurse,
 * Dietitian) add whatever is specific to that type.
 *
 * Abstract because a "plain" health professional doesn't make sense here
 * - every object has to be a real, specific type.
 */
public abstract class HealthProfessional {

    // the only days the clinic recognises
    private static final String[] VALID_DAYS =
            {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    // default schedule if a subclass doesn't specify one
    private static final String[] DEFAULT_WORKING_DAYS =
            {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

    private String id;
    private String name;
    private ArrayList<String> workingDays;

    /** Full constructor - explicit working days. */
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
        this.workingDays = new ArrayList<String>(workingDays); // copy so callers can't mutate it later
    }

    // overloaded constructor, defaults to Mon-Fri
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

    // returns a copy, not the real list, so callers can't change it from outside
    public ArrayList<String> getWorkingDays() {
        return new ArrayList<String>(workingDays);
    }

    // every subclass has to say what type of professional it is
    public abstract String getProfessionalType();

    // subclasses call super.toString() then add their own detail
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

    // same id = same professional, regardless of subtype - used to catch double bookings
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
