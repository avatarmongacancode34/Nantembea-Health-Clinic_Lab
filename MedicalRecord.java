
import java.time.LocalDateTime;

public class MedicalRecord {

    private Doctor doctor;
    private double weight;
    private double height;
    private String symptoms;
    private String prescription;
    private LocalDateTime date;

    public MedicalRecord(int patientId, Patient patient, Doctor doctor, double weight, double height, String symptoms,
            String prescription, LocalDateTime date) {

        this.doctor = doctor;
        this.weight = weight;
        this.height = height;
        this.symptoms = symptoms;
        this.prescription = prescription;
        this.date = LocalDateTime.now();
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

    public String getPrescription() {
        return prescription;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Doctor=" + doctor.getName() + "\nweight=" + weight + height + "\nsymptoms=" + symptoms
                + "\nprescription=" + prescription + "\ndate=" + date;
    }

}