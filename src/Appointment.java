/**
 * Links a patient to a health professional at a specific time slot on
 * the current day. Holds references to existing Patient/HealthProfessional
 * objects rather than extending either (composition, not inheritance).
 */
public class Appointment implements Comparable<Appointment> {

    private Patient patient;
    private HealthProfessional professional;
    private String timeSlot; // "HH:mm", 24 hour format

    // time must be "HH:mm", rest of the validation happens in AppointmentBook
    public Appointment(Patient patient, HealthProfessional professional, String timeSlot) {
        if (patient == null) {
            throw new IllegalArgumentException("An appointment must have a patient.");
        }
        if (professional == null) {
            throw new IllegalArgumentException("An appointment must have a health professional.");
        }
        if (!isValidTimeFormat(timeSlot)) {
            throw new IllegalArgumentException("Time slot must be in HH:mm 24 hour format, got: " + timeSlot);
        }
        this.patient = patient;
        this.professional = professional;
        this.timeSlot = timeSlot;
    }

    // just checks the shape/range of "HH:mm" - whether it's actually a
    // bookable slot is checked later in AppointmentBook
    private static boolean isValidTimeFormat(String time) {
        if (time == null || time.length() != 5 || time.charAt(2) != ':') {
            return false;
        }
        String hourPart = time.substring(0, 2);
        String minutePart = time.substring(3, 5);
        for (int i = 0; i < 2; i++) {
            if (!Character.isDigit(hourPart.charAt(i)) || !Character.isDigit(minutePart.charAt(i))) {
                return false;
            }
        }
        int hour = Integer.parseInt(hourPart);
        int minute = Integer.parseInt(minutePart);
        return hour >= 0 && hour <= 23 && minute >= 0 && minute <= 59;
    }

    public Patient getPatient() {
        return patient;
    }

    public HealthProfessional getProfessional() {
        return professional;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    // "HH:mm" is a fixed width, zero padded format, so comparing the
    // strings directly gives the same order as comparing the actual times.
    @Override
    public int compareTo(Appointment other) {
        return this.timeSlot.compareTo(other.timeSlot);
    }

    // Doesn't reformat the patient/professional details itself - it
    // delegates to their own toString() methods.
    @Override
    public String toString() {
        return "Appointment at " + timeSlot
                + "\n   " + patient
                + "\n   Professional -> " + professional;
    }
}
