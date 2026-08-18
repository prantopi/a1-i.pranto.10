import java.util.ArrayList;

/**
 * Entry point - builds a small set of health professionals and patients,
 * then runs the appointment system end to end, normal cases and error
 * cases both.
 */
public class AssignmentOne {

    public static void main(String[] args) {

        // ---------- Part 1 & 2: health professionals ----------
        ArrayList<String> mondayWednesdayFriday = new ArrayList<String>();
        mondayWednesdayFriday.add("Monday");
        mondayWednesdayFriday.add("Wednesday");
        mondayWednesdayFriday.add("Friday");

        GeneralPractitioner gp1 = new GeneralPractitioner("1001", "Sarah Chen", mondayWednesdayFriday, 9);
        GeneralPractitioner gp2 = new GeneralPractitioner("1002", "Michael Ho", 5); // default Mon-Fri
        GeneralPractitioner gp3 = new GeneralPractitioner("1003", "Amelia Fox", 14);

        Specialist specialist1 = new Specialist("2001", "Priya Nair", mondayWednesdayFriday, "Cardiology");
        Specialist specialist2 = new Specialist("2002", "Tom Baird", defaultWeekdays()); // overloaded ctor, default specialty

        Nurse nurse1 = new Nurse("3001", "Jade Whitmore", "Emergency");
        Nurse nurse2 = new Nurse("3002", "Leon Marsh", "Outpatients");

        Dietitian dietitian1 = new Dietitian("4001", "Chloe Bennett", "Weight management");

        // ---------- Part 3: work with the professionals as a group ----------
        ArrayList<HealthProfessional> staff = new ArrayList<HealthProfessional>();
        staff.add(gp1);
        staff.add(gp2);
        staff.add(gp3);
        staff.add(specialist1);
        staff.add(specialist2);
        staff.add(nurse1);
        staff.add(nurse2);
        staff.add(dietitian1);

        System.out.println("=== Clinic staff ===");
        // one loop handles every type - each object prints with its own
        // overridden toString(), and adding a new professional type later
        // would not require any change to this loop
        for (HealthProfessional professional : staff) {
            System.out.println(professional);
        }
        System.out.println();

        // error case - a professional cannot be created with a non-numeric id
        try {
            GeneralPractitioner invalidGp = new GeneralPractitioner("GP01", "Bad Id Example", 2);
        } catch (IllegalArgumentException e) {
            System.out.println("Error case - creating a professional with a bad id: " + e.getMessage());
        }
        System.out.println();

        // ---------- patients ----------
        Patient patient1 = new Patient("Alan Wu", "0400111222");
        Patient patient2 = new Patient("Meera Shah", "0400333444");
        Patient patient3 = new Patient("Ryan Cole", "0400555666");

        // capability check - only professionals who actually implement
        // Prescriber / BloodSampler get asked to do those things, found
        // through the same staff list using instanceof
        System.out.println("=== Capability check ===");
        for (HealthProfessional professional : staff) {
            if (professional instanceof Prescriber) {
                Prescriber prescriber = (Prescriber) professional;
                prescriber.prescribeMedication(patient1, "Amoxicillin");
            }
            if (professional instanceof BloodSampler) {
                BloodSampler sampler = (BloodSampler) professional;
                sampler.takeBloodSample(patient2);
            }
        }
        dietitian1.providePlan(patient3, "Low sodium diet");
        System.out.println();

        // ---------- Part 4, 5 & 6: appointments ----------
        AppointmentBook book = new AppointmentBook();

        // normal case - booking appointments across different professional types
        book.addAppointment(new Appointment(patient1, gp1, "08:00"));
        book.addAppointment(new Appointment(patient2, specialist1, "08:30"));
        book.addAppointment(new Appointment(patient3, nurse1, "09:00"));
        book.addAppointment(new Appointment(patient1, dietitian1, "10:00"));
        System.out.println();

        // error case - the same professional cannot be double booked into the same slot
        book.addAppointment(new Appointment(patient3, gp1, "08:00"));

        // error case - a time that is not one of the clinic's 30 minute slots
        book.addAppointment(new Appointment(patient2, nurse2, "08:15"));
        System.out.println();

        // error case - a badly formed time is rejected before it can even become an Appointment
        try {
            Appointment badTime = new Appointment(patient1, gp2, "25:99");
        } catch (IllegalArgumentException e) {
            System.out.println("Error case - creating an appointment with a bad time: " + e.getMessage());
        }
        System.out.println();

        System.out.println("=== All appointments ===");
        AppointmentBook.printAppointments(book.getAllAppointments());

        System.out.println("=== Appointments with professional id 1001 ===");
        AppointmentBook.printAppointments(book.filterByProfessionalId("1001"));

        System.out.println("=== Appointments for phone 0400111222 ===");
        AppointmentBook.printAppointments(book.filterByPatientPhone("0400111222"));

        System.out.println("=== All appointments sorted by time ===");
        AppointmentBook.printAppointments(book.sortByTime(book.getAllAppointments()));

        // normal case - cancelling appointments, by both supported methods
        book.cancelByProfessionalAndTime("3001", "09:00");
        book.cancelByPatientPhone("0400111222"); // cancels Alan Wu's 08:00 GP appointment

        // error case - trying to cancel an appointment that no longer / never existed
        book.cancelByProfessionalAndTime("3001", "09:00");
        book.cancelByPatientPhone("0000000000");
        System.out.println();

        System.out.println("=== All appointments after cancellations ===");
        AppointmentBook.printAppointments(book.getAllAppointments());
    }

    private static ArrayList<String> defaultWeekdays() {
        ArrayList<String> days = new ArrayList<String>();
        days.add("Monday");
        days.add("Tuesday");
        days.add("Wednesday");
        days.add("Thursday");
        days.add("Friday");
        return days;
    }
}
