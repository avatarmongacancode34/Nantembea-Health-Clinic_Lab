
import java.time.LocalDateTime;

public class MedicalRecord {

    private Doctor doctor;
    private double weight;
    private double height;
    private String symptoms;
    private LocalDateTime date;
    private Patient patient;

    public MedicalRecord(Patient patient, Doctor doctor, double weight, double height, String symptoms, LocalDateTime date) {
        this.patient = patient;
        this.doctor = doctor;
        this.weight = weight;
        this.height = height;
        this.symptoms = symptoms;
        this.date = date;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public double getWeight() {
        return weight;
    }

    public double getHeight() {
        return height;
    }

    public String getSymptoms() {
        return symptoms;
    }
    public Patient getPatient() {
        return patient;
    }


    public LocalDateTime getDate() {
        return date;
    }
    public void setPatient(Patient patient) {
        if (patient == null) {
            throw new IllegalArgumentException("Patient cannot be null");
        }
        this.patient = patient;
    }

    public void setDoctor(Doctor doctor) {
        if (doctor == null) {
            throw new IllegalArgumentException("Doctor cannot be null");
        }
        this.doctor = doctor;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setSymptoms(String symptoms) {
        if (symptoms == null || symptoms.isEmpty()) {
            throw new IllegalArgumentException("Symptoms cannot be null or empty");
        }
        this.symptoms = symptoms;
    }
    

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    

    @Override
    public String toString() {
        return patient.getPatientId() + "," + doctor.getDoctorId() + "," + weight + "," + height + "," + symptoms +  "," + date;
    }

}