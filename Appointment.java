
import java.time.LocalDate;

public class Appointment {
    private String patient;
    private String doctor;
    private LocalDate date;
    private int appointmentId;
    private String appointmentType;

    public Appointment(int appointmentId, String doctor, String patient, String appointmentType) {
        this.patient = patient;
        this.doctor = doctor;
        this.date = LocalDate.now();
        this.appointmentId = appointmentId;
        this.appointmentType = appointmentType;
    }

    public Appointment(int appointmentId, String doctor, String patient, String appointmentType, LocalDate date) {
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.appointmentId = appointmentId;
        this.appointmentType = appointmentType;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public String getPatient() {
        return patient;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppoitmentType(String appointmentType) {
        if (appointmentType == null || appointmentType.isEmpty()) {
            throw new IllegalArgumentException("Appointment type cannot be null or empty");
        }
        this.appointmentType = appointmentType;
    }

    public void setPatient(String patient) {
        if (patient == null || patient.isEmpty()) {
            throw new IllegalArgumentException("Patient cannot be null or empty");
        }
        this.patient = patient;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        if (doctor == null || doctor.isEmpty()) {
            throw new IllegalArgumentException("Doctor cannot be null or empty");
        }
        this.doctor = doctor;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        this.date = date;
    }

    @Override
    public String toString() {
        return (appointmentId + "," + date + "," + doctor + "," + appointmentType + patient);
    }
}