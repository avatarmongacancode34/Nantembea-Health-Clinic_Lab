
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

public class ClinicManagementSystem implements FileOperations, ReportGenerator {
    private HashMap<Integer, Patient> patients;
    private ArrayList<Doctor> doctors;
    private TreeMap<LocalDate, ArrayList<Appointment>> appointmentsByDate;
    private static int nextPatentId = 1;
    private static int nextDoctorId = 1;
    private static int nextAppointmentId = 1;

    public ClinicManagementSystem() {
        this.patients = new HashMap<>();
        this.doctors = new ArrayList<>();
        this.appointmentsByDate = new TreeMap<>();
    }

    // TODO: Implement interface methods
    public void saveToFile(String filename) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            for (Patient patient : patients.values()) {
                writer.write(patient.toString());
                writer.write("\n");
            }
        }
    }

    @Override
    public void loadFromFile(String filename) throws IOException {
        // Use Scanner to read patient data
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] attributes = line.split(",");
                if (filename.endsWith("patients.txt")) {
                    int patientId = Integer.parseInt(attributes[0]);
                    String name = attributes[1];
                    String email = attributes[2];
                    String phone = attributes[3];
                    Patient patient = new Patient(patientId, name, email, phone);
                    patients.put(patientId, patient);
                    nextPatentId = (patientId > nextPatentId) ? patientId + 1 : nextPatentId;

                } else if (filename.endsWith("doctors.txt")) {
                    int doctorId = Integer.parseInt(attributes[0]);
                    String name = attributes[1];
                    String email = attributes[2];
                    String phone = attributes[3];
                    Doctor doctor = new Doctor(doctorId, name, email, phone);
                    doctors.add(doctor);

                    nextDoctorId = (doctorId > nextPatentId) ? doctorId + 1 : nextDoctorId;

                } else if (filename.endsWith("appointments.txt")) {
                    int appointmentId = Integer.parseInt(attributes[0]);
                    LocalDate date = LocalDate.parse(attributes[1]);
                    String doctorName = attributes[2];
                    String appointmentType = attributes[3];
                    String patient = attributes[4];

                    Appointment appointment = new Appointment(appointmentId, doctorName, patient, appointmentType,
                            date);
                    ArrayList<Appointment> appointmentList = new ArrayList<>();
                    appointmentList.add(appointment);
                    appointmentsByDate.put(appointment.getDate(), appointmentList);
                    nextAppointmentId = (appointmentId > nextAppointmentId) ? appointmentId + 1 : nextAppointmentId;
                }
            }
        }
    }

    public void addPatient(String name, String email, String phone) {
        Patient patient = new Patient(nextPatentId++, name, email, phone);
        patients.put(patient.getPatientId(), patient);
    }

    public Patient findPatient(int patientId) {
        Patient patient = patients.get(patientId);
        if (patient == null) {
            System.out.println("Patient not found");
        }
        return patient;
    }

    public void addDoctor(String name, String email, String phone) {
        Doctor doctor = new Doctor(nextDoctorId++, name, email, phone);
        doctors.add(doctor);
    }

    public Doctor findDoctor(int doctorId) {
        for (Doctor doctor : doctors) {
            if (doctor.getDoctorId() == doctorId) {
                return doctor;
            }
        }
        return null;
    }

    public void addAppointment(String doctor, String appointmentType, String patient, String date) {
        LocalDate localDate = LocalDate.parse(date);
        Appointment appointment = new Appointment(nextAppointmentId++, doctor, patient, appointmentType, localDate);

        if (appointmentsByDate.containsKey(localDate)) {
            ArrayList<Appointment> existingList = appointmentsByDate.get(localDate);
            existingList.add(appointment);
        } else {
            ArrayList<Appointment> newList = new ArrayList<>();
            newList.add(appointment);
            appointmentsByDate.put(localDate, newList);
        }
    }

    public ArrayList<Appointment> getAppointmentByDate(LocalDate date) {
        return appointmentsByDate.get(date);
    }

    @Override
    public String generatePatientReport(int patientId) {
        Patient patient = findPatient(patientId);
        if (patient == null) {
            return "N/A";
        }
        return "Patient Name: " + patient.getName() +
                "\nEmail: \n" + patient.getEmail() +
                "\nPhone: \n" + patient.getPhone() +
                "\nMedical Record: " + patient.getMedicalHistory() +
                "\nAppointments: \n" + patient.getAppointments();
    }

    @Override
    public String generateDailyAppointments(String date) {
        return "Appointments for " + date;
    }

    public static void main(String[] args) {
        ClinicManagementSystem system = new ClinicManagementSystem();
        Scanner scanner = new Scanner(System.in);
        System.out.println("====== Welcome to Natembea =======");

        System.out.println("What would you like to do: ");
        System.out.println("1. Create Appointment \n 2. Register Doctor");
        int menuOption = scanner.nextInt();
        scanner.nextLine();

        switch (menuOption) {
            case 1:
                System.out.println("Enter the patients full name: ");
                String patientName = scanner.nextLine();
                System.out.println("Enter the patients email address: ");
                String patientEmail = scanner.nextLine();
                System.out.println("Enter the patients phone number: ");
                String patientPhone = scanner.nextLine();
                system.addPatient(patientName, patientEmail, patientPhone);
                System.out.println("ID for Doctor to attend to patient");
                int doctorId = scanner.nextInt();
                scanner.nextLine();
                if (system.findDoctor(doctorId) != null) {
                    System.out.println("Enter appointment type:");
                    String appointmentType = scanner.nextLine();
                    System.out.println("Enter date (YYYY-MM-DD):");
                    String date = scanner.nextLine();
                    system.addAppointment(system.findDoctor(doctorId).getName(), appointmentType, patientName, date);
                } else {
                    System.out.println("Doctor not found.");
                }
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                System.out.println("Please select option 1 - 3 ");
        }
    }
}