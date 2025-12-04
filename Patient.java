import java.util.ArrayList;

public class Patient extends Person{
    private int patientId;
    private ArrayList<MedicalRecord> medicalHistory;
    private TreeMap<Date, Appointment> appointments;

    public Patient(int patientId, String name, String email, String phone){
        super(name,email, phone);
        this.patientId = patientId;
        this.medicalHistory = new ArrayList<>();
        this.appointments = new TreeMap<>();
    }

    public void addMedicalRecord(MedicalRecord record){
        medicalHistory.add(record); // Array:st addition
    }
    public List<MedicalRecord> getMedicalHistory(){
        return new ArrayList<>(medicalHistory);
    }
}