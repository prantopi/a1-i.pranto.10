/**
 * Capability of a health professional who is allowed to prescribe
 * medication to a patient. Not every professional can do this (a
 * dietitian cannot), so it is expressed as an interface rather than
 * being placed on the abstract HealthProfessional class - it cuts across
 * the class hierarchy instead of following it.
 */
public interface Prescriber {

    /**
     * Prescribes a medication to a patient.
     *
     * @param patient the patient being prescribed to
     * @param medication the name of the medication
     */
    void prescribeMedication(Patient patient, String medication);
}
