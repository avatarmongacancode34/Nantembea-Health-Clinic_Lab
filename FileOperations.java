import java.util.ArrayList;
import java.util.TreeMap;

public interface FileOperations {
    private int patientId;
    private ArrayList<MedicalRecord> medicalHistory;
    private  TreeMap<Date, Appointment> appointments;
}
