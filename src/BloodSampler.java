/**
 * Capability of a health professional who is able to take a blood sample
 * from a patient.
 */
public interface BloodSampler {

    /**
     * Takes a blood sample from a patient.
     *
     * @param patient the patient the sample is taken from
     */
    void takeBloodSample(Patient patient);
}
