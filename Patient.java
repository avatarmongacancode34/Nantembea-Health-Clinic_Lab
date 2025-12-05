import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Patient extends Person {
    private int patientId;
    private ArrayList<MedicalRecord> medicalHistory;
    private TreeMap<LocalDate, Appointment> appointments;

    public Patient(int patientId, String name, String email, String phone) {
        super(name, email, phone);
        this.patientId = patientId;
        this.medicalHistory = new ArrayList<>();
        this.appointments = new TreeMap<>();

    }

    public ArrayList getMedicalRecord(){
        return medicalHistory;
    }
    public TreeMap getAppointments(){
        return appointments;
    }

    public int getPatientId() {
        return patientId;
    }

    public void addMedicalRecord(MedicalRecord record) {
        medicalHistory.add(record); // Array:st addition
    }

    public void addAppointment(LocalDate date, Appointment appointment){
        appointments.put(date, appointment);
    }

    public List<MedicalRecord> getMedicalHistory() {
        return new ArrayList<>(medicalHistory);
    }
    
    @Override
    public String toString(){
        return(patientId+ "," + super.getName() + "," + super.getEmail() + "," + super.getPhone());
    }
}