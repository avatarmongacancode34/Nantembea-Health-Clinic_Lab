
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

public class ClinicManagementSystem implements FileOperations, ReportGenerator {
    private HashMap<Integer, Patient> patients;
    private ArrayList<Doctor> doctors;
    private TreeMap<String, ArrayList<Appointment>> appointmentsByDate;
    private static int nextPatentId = 1;

    public ClinicManagementSystem() {
        this.patients = new HashMap<>();
        this.doctors = new ArrayList<>();
        this.appointmentsByDate = new TreeMap<>();
    }

    /// TODO: Implement interface methods
    public void saveToFile(String filename) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            for (Patient patient : patients.values()) {
                writer.write(patient.toString());
                writer.write("\n");
            }
        }
        // Use FileWrite to save patient data
    }

    @Override
    public void loadFromFile(String filename) throws IOException {
        // Use Scanner to read patient data
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // Parse and create Patient Objects
            }

        }
    }

    public void addPatient(String name, String email, String phone) {
        Patient patient = new Patient(nextPatentId++, name, email, phone);
        patients.put(patient.getPatientId(), patient);
    }

    public Patient findPatient(int patientId) {
        return patients.get(patientId); // HashMap Lookup
    }
    
    @Override
    public String generatePatientReport(int patientId) {
        return "Report for patient " + patientId;
    }

    @Override
    public String generateDailyAppointments(String date) {
        return "Appointments for " + date;
    }
}