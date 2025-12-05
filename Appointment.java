
import java.time.LocalDate;

public class Appointment {
    private Patient patient;
    private Doctor doctor;
    private LocalDate date;
    private String appointmentType;

    public Appointment(Doctor doctor, Patient patient, String appointmentType) {
        this.patient = patient;
        this.doctor = doctor;
        this.date = LocalDate.now();
        this.appointmentType = appointmentType;
    }

    public Appointment(Doctor doctor, Patient patient, String appointmentType, LocalDate date) {
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.appointmentType = appointmentType;
    }

    public Patient getPatient() {
        return patient;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        if (appointmentType == null || appointmentType.isEmpty()) {
            throw new IllegalArgumentException("Appointment type cannot be null or empty");
        }
        this.appointmentType = appointmentType;
    }

    public void setPatient(Patient patient) {
        if (patient == null) {
            throw new IllegalArgumentException("Patient cannot be null or empty");
        }
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        if (doctor == null) {
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
        return (patient.getPatientId() + "," + date.toString() + "," + doctor.getName() + doctor.getDoctorId() + ","+ "," + appointmentType);
    }
}