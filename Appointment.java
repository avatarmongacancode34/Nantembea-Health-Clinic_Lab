
import java.time.LocalDate;

public  class Appointment{
    private Patient patient;
    private  Doctor doctor
    private LocalDate date;

    public Appointment(Patient patient, Doctor doctor){
        this.patient = patient;
        this.date = LocalDate.now();
    }
}