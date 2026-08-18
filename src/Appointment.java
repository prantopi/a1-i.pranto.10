/**
 * A single appointment - links a patient to a health professional at a
 * specific time slot on the current day. An Appointment does not extend
 * Patient or HealthProfessional, it simply holds references to existing
 * objects that are passed in (composition, not inheritance).
 *
 * Appointments know how to order themselves by time, so a collection of
 * them can be sorted from earliest to latest.
 */
public class Appointment implements Comparable<Appointment> {

    private Patient patient;
    private HealthProfessional professional;
    private String timeSlot; // "HH:mm", 24 hour format

    /**
     * Creates a new appointment.
     *
     * @param patient the patient booking the appointment
     * @param professional the health professional being seen - can be any subtype
     * @param timeSlot the time of the appointment, in "HH:mm" 24 hour format
     * @throws IllegalArgumentException if any detail is missing or the time is not well formed
     */
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

    // Checks the string looks like a real "HH:mm" time - just the shape
    // and range, not whether it is one of the clinic's bookable slots.
    // Whether it's an actual bookable slot is a business rule enforced by
    // the appointment book, not something a single appointment can know.
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
