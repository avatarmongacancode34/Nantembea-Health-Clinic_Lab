
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

public class ClinicManagementSystem{
    private  HashMap<Integer, Patient> patients;
    private ArrayList<Doctor> doctors;
    private TreeMap<String, ArrayList<Appointment>> appointmentsByDate;
    private static int nextPatentId = 1;

    public ClinicManagementSystem(){
        this.patients = new HashMap<>();
        this.doctors = new ArrayList<>();
        this.appointmentsByDate = new TreeMap<>();
    }

    /// TODO: Implement interface methods
    public void saveToFile(String filename) throws IOException{
        // Use FileWrite to save patient data 
    }

    @Override 
    public void loadFromFile(String filename) throws IOException{
        //Use Scanner to read patient data
        try (Scanner scanner = new Scanner(new File(filename))){
            while (scanner.hashNextLine()){
                String line = scanner.nextLine();
                // Parse and create Patient Objects
            }

        }
    }
    public void addPatient(String name, String email, String phone){
        Patient patient = new Patient(nextPatentId++, name, email, phone);
        patients.put(patient.getPatientId(), patient);
    }

    public Patient findPatient(int patientId){
        return patients.get(patientId); //HashMap Lookup
    }
}