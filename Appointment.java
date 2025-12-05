
import java.time.LocalDate;

public  class Appointment{
    private String patient;
    private  String doctor;
    private LocalDate date;
    private int appointmentId;
    private String appointmentType;

    public Appointment(int appointmentId,String doctor, String patient, String appointmentType){
        this.patient = patient;
        this.doctor = doctor;
        this.date = LocalDate.now();
        this.appointmentId = appointmentId;
        this.appointmentType = appointmentType;

    }
    public int getAppointmentId() {
        return appointmentId;
    }
    public String getPatient() {
        return patient;
    }
    public String getAppointmentType(){
        return appointmentType;
    }
    public void setAppoitmentType(String appointmentType){
        this.appointmentType = appointmentType;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString(){
        return(appointmentId + "," + date + "," + doctor + "," + appointmentType + patient);
    }
}