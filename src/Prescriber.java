// Capability interface - not every health professional can prescribe
// (a dietitian can't), so this isn't on the abstract class, it cuts
// across the hierarchy instead.
public interface Prescriber {

    void prescribeMedication(Patient patient, String medication);
}
