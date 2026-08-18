import java.util.ArrayList;
import java.util.Collections;

/**
 * Manages the clinic's collection of appointments for the day - enforces
 * the fixed daily schedule and stops a professional being double booked.
 */
public class AppointmentBook {

    // the clinic's daily schedule - change these three constants and the
    // whole bookable slot list rebuilds itself accordingly
    private static final String SCHEDULE_START = "08:00";
    private static final String SCHEDULE_END = "16:00";
    private static final int SLOT_MINUTES = 30;

    private static final ArrayList<String> VALID_SLOTS = buildValidSlots();

    private ArrayList<Appointment> appointments;

    public AppointmentBook() {
        this.appointments = new ArrayList<Appointment>();
    }

    private static ArrayList<String> buildValidSlots() {
        ArrayList<String> slots = new ArrayList<String>();
        int startMinutes = toMinutes(SCHEDULE_START);
        int endMinutes = toMinutes(SCHEDULE_END);
        for (int minutes = startMinutes; minutes <= endMinutes; minutes += SLOT_MINUTES) {
            slots.add(toTimeString(minutes));
        }
        return slots;
    }

    private static int toMinutes(String time) {
        int hour = Integer.parseInt(time.substring(0, 2));
        int minute = Integer.parseInt(time.substring(3, 5));
        return hour * 60 + minute;
    }

    private static String toTimeString(int totalMinutes) {
        int hour = totalMinutes / 60;
        int minute = totalMinutes % 60;
        String hourPart = (hour < 10 ? "0" : "") + hour;
        String minutePart = (minute < 10 ? "0" : "") + minute;
        return hourPart + ":" + minutePart;
    }

    // rejects the appointment if the slot isn't one the clinic runs, or the
    // professional is already booked then - returns false either way instead
    // of throwing, since a failed booking is a normal thing that can happen
    public boolean addAppointment(Appointment appointment) {
        if (!VALID_SLOTS.contains(appointment.getTimeSlot())) {
            System.out.println("Warning: " + appointment.getTimeSlot() + " is not a bookable clinic time slot.");
            return false;
        }
        if (isDoubleBooked(appointment.getProfessional(), appointment.getTimeSlot())) {
            System.out.println("Warning: " + appointment.getProfessional().getName()
                    + " already has an appointment at " + appointment.getTimeSlot() + ".");
            return false;
        }
        appointments.add(appointment);
        System.out.println("Booked: " + appointment.getTimeSlot() + " with " + appointment.getProfessional().getName()
                + " for " + appointment.getPatient().getName());
        return true;
    }

    // Two appointments clash if they are for the same professional at the
    // same time slot. Relies on HealthProfessional.equals() so it works no
    // matter what concrete subtype the professional is.
    private boolean isDoubleBooked(HealthProfessional professional, String timeSlot) {
        for (Appointment appointment : appointments) {
            if (appointment.getProfessional().equals(professional) && appointment.getTimeSlot().equals(timeSlot)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Appointment> getAllAppointments() {
        return new ArrayList<Appointment>(appointments);
    }

    public ArrayList<Appointment> filterByProfessionalId(String professionalId) {
        ArrayList<Appointment> result = new ArrayList<Appointment>();
        for (Appointment appointment : appointments) {
            if (appointment.getProfessional().getId().equals(professionalId)) {
                result.add(appointment);
            }
        }
        return result;
    }

    public ArrayList<Appointment> filterByPatientPhone(String mobileNumber) {
        ArrayList<Appointment> result = new ArrayList<Appointment>();
        for (Appointment appointment : appointments) {
            if (appointment.getPatient().getMobileNumber().equals(mobileNumber)) {
                result.add(appointment);
            }
        }
        return result;
    }

    // works on the full list or a filtered subset - whatever gets passed in
    public ArrayList<Appointment> sortByTime(ArrayList<Appointment> list) {
        Collections.sort(list);
        return list;
    }

    public boolean cancelByProfessionalAndTime(String professionalId, String timeSlot) {
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            if (appointment.getProfessional().getId().equals(professionalId) && appointment.getTimeSlot().equals(timeSlot)) {
                appointments.remove(i);
                System.out.println("Cancelled appointment at " + timeSlot + " with professional id " + professionalId);
                return true;
            }
        }
        System.out.println("Warning: no appointment found for professional id " + professionalId + " at " + timeSlot + ".");
        return false;
    }

    // same idea, but matched by phone number instead of professional + time
    public boolean cancelByPatientPhone(String mobileNumber) {
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            if (appointment.getPatient().getMobileNumber().equals(mobileNumber)) {
                appointments.remove(i);
                System.out.println("Cancelled appointment for phone number " + mobileNumber);
                return true;
            }
        }
        System.out.println("Warning: no appointment found for phone number " + mobileNumber + ".");
        return false;
    }

    // static since it doesn't need this book's own data - can print any list handed to it
    public static void printAppointments(ArrayList<Appointment> list) {
        if (list.isEmpty()) {
            System.out.println("(no appointments)");
            return;
        }
        for (Appointment appointment : list) {
            System.out.println(appointment);
            System.out.println("-----------------------------------");
        }
    }
}
