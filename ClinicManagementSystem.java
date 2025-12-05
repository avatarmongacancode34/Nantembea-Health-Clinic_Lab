
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    Scanner scanner = new Scanner(System.in);
   

    public ClinicManagementSystem() {
        this.patients = new HashMap<>();
        this.doctors = new ArrayList<>();
        this.appointmentsByDate = new TreeMap<>();
    }

    // TODO: Implement interface methods
    public void saveToFile(String filename) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {

            if (filename.endsWith("patients.txt")) {
                for (Patient p : patients.values()) {
                    writer.write(p.toString() + "\n");
                }
            }

            else if (filename.endsWith("doctors.txt")) {
                for (Doctor d : doctors) {
                    writer.write(d.toString() + "\n");
                }
            }

            else if (filename.endsWith("appointments.txt")) {
                for (LocalDate date : appointmentsByDate.keySet()) {
                    for (Appointment a : appointmentsByDate.get(date)) {
                        writer.write(a.toString() + "\n");
                    }
                }
            }else if (filename.endsWith("records.txt")) {
                for (Patient patient : patients.values()) {
                    for (MedicalRecord record : patient.getMedicalHistory()) {
                        writer.write(record.toString() + "\n");
                    }
                }
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
                    nextDoctorId = (doctorId > nextDoctorId) ? doctorId + 1 : nextDoctorId;

                }else if (filename.endsWith("appointments.txt")) {
                    int patientId = Integer.parseInt(attributes[0]);
                    LocalDate date = LocalDate.parse(attributes[1]);
                    String doctorName = attributes[2];
                    int doctorId = Integer.parseInt(attributes[3]);
                    String appointmentType = attributes[4];
                    
                    Patient patient = findPatient(patientId);
                    Doctor doctor = findDoctor(doctorId);

                    if (doctor != null && patient != null) {
                        Appointment appointment = new Appointment(doctor, patient, appointmentType, date);

                        if (!appointmentsByDate.containsKey(date)) {
                            appointmentsByDate.put(date, new ArrayList<>());
                        }
                        System.out.println("Appoint setup successfully");
                        appointmentsByDate.get(date).add(appointment);
                        patient.addAppointment(date,appointment);
                        
                    }
                }

                else if (filename.endsWith("records.txt")) {
                    int patientId = Integer.parseInt(attributes[0]);
                    int doctorId = Integer.parseInt(attributes[1]);
                    double weight = Double.parseDouble(attributes[2]);
                    double height = Double.parseDouble(attributes[3]);
                    String symptoms = attributes[4];
                    LocalDateTime date = LocalDateTime.parse(attributes[5]);

                    Patient p = findPatient(patientId);
                    Doctor d = findDoctor(doctorId);

                    if (p != null && d != null) {
                        MedicalRecord r = new MedicalRecord(p, d, weight, height, symptoms, date);
                        p.addMedicalRecord(r);
                    }
                }

        }}
    }

    public void addPatient(String name, String email, String phone) {
        Patient patient = new Patient(nextPatentId++, name, email, phone);
        patients.put(patient.getPatientId(), patient);
    }

    public Patient findPatient(int patientId) {
        Patient patient = patients.get(patientId);
        if (patient == null) {
            System.out.println("Patient not found");
            return null;
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
        System.out.println("Doctor not found, Try another ID.");
        return null;
    }

    public void addAppointmentByDate(Doctor doctor, String appointmentType, Patient patient, String date) {
        LocalDate dateLocal = LocalDate.parse(date);
        // public Appointment(Doctor doctor, Patient patient, String appointmentType, date)
        Appointment appointment = new Appointment(doctor,  patient, appointmentType, dateLocal);

        if (!appointmentsByDate.containsKey(dateLocal)) {
            appointmentsByDate.put(dateLocal, new ArrayList<>());
            
        } 
        appointmentsByDate.get(dateLocal).add(appointment);
        patient.addAppointment(dateLocal, appointment);
    }

    public ArrayList<Appointment> getAppointmentByDate(LocalDate date) {
        return appointmentsByDate.get(date);
    }

    public boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.contains(".") && email.length() >= 5;
    }


    public String readValidEmail() {
        String email;
        while (true) {
            System.out.println("Enter email: ");
            email = scanner.nextLine();
            if (isValidEmail(email)) break;
            System.out.println("Invalid email! Try again.");
        }
        return email;
    }
    public boolean isValidPhone(String phone) {
        if (phone == null || phone.isEmpty()) {
            return false;
        }

        // some numbers can start na +
        if (phone.startsWith("+")) {
            phone = phone.substring(1); // tobva taskipper +
        }

        // isdigit() paya 
        for (int i = 0; i < phone.length(); i++) {
            char c = phone.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        // phone numbers restricted to length of 7 - 15
        return phone.length() >= 7 && phone.length() <= 15;
    }

    public String readValidPhone() {
        String phone;
        while (true) {
            System.out.println("Enter phone number: ");
            phone = scanner.nextLine();
            if (isValidPhone(phone)) break;
            System.out.println("Invalid phone number! Try again.");
        }
        return phone;
    }
    public String readValidName() {
        String name;

        while (true) {
            System.out.println("Enter full name (first + surname): ");
            name = scanner.nextLine();

            if (isValidName(name)) {
                return name.toUpperCase();   // capitalize once, return
            }

            System.out.println("Invalid name! Try again.");
        }
    }
    public boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) return false;

        String[] parts = name.trim().split("\\s+");

        if (parts.length < 2) return false; // Must have first + surname

        for (String part : parts) {
            if (part.length() < 2) return false;      // At least 2 letters each
            for (int i = 0; i < part.length(); i++) { // Letters only
                if (!Character.isLetter(part.charAt(i))) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public String generatePatientReport(int patientId) {
        Patient patient = findPatient(patientId);
        if (patient == null) {
            return "N/A";
        }
        return "Patient Name: " + patient.getName() +"\nEmail: \n" + patient.getEmail() +"\nPhone: \n" + patient.getPhone() + "\nMedical Record: " + patient.getMedicalHistory() + "\nAppointments: \n" + patient.getAppointments();
    }

    @Override
    public String generateDailyAppointments(String date) {
        LocalDate dateLocal = LocalDate.parse(date);
        if (getAppointmentByDate(dateLocal) != null){
            for(Appointment appointment: getAppointmentByDate(dateLocal)){
                System.out.println(appointment.toString());
            }
            return "Report Generated Successfully";
            
        }
        return "No Records for selected day";
    }

    public static void main(String[] args) {
        ClinicManagementSystem system = new ClinicManagementSystem();
        Scanner scanner = new Scanner(System.in);

        // load saved files from folder
        try {
            system.loadFromFile("file/patients.txt");
            system.loadFromFile("file/doctors.txt");
            system.loadFromFile("file/appointments.txt");
            system.loadFromFile("file/records.txt");

        } catch (Exception e) {
            System.out.println("Starting with empty records...");
        }

        boolean continueRun = true;
        System.out.println("====== Welcome to Natembea =======");

        while (continueRun) {
            System.out.println("What would you like to do: ");
            System.out.println("1. Create Appointment");
            System.out.println("2. Register Doctor");
            System.out.println("3. Generate Report");
            System.out.println("4. View All Patients");
            System.out.println("5. View All Doctors");
            System.out.println("6. View Appointments by Date");
            System.out.println("7. Exit System");

            int menuOption;
            try {
                menuOption = scanner.nextInt();;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); 
                continue; 
            }
            scanner.nextLine();

            switch (menuOption) {

                case 1: // CREATE APPOINTMENT
                    String patientName = system.readValidName();
                    String patientEmail = system.readValidEmail();
                    String patientPhone = system.readValidPhone();

                    system.addPatient(patientName, patientEmail, patientPhone);

                    System.out.println("Enter Doctor ID to attend to patient: ");
                    int doctorId = scanner.nextInt();
                    scanner.nextLine();

                    Doctor doctor = system.findDoctor(doctorId);
                    if (doctor == null) break;

                    System.out.println("Type of Appointment:");
                    System.out.println("1. Checkup");
                    System.out.println("2. Vaccination");
                    System.out.println("3. Follow-Up");
                    int app = scanner.nextInt();
                    scanner.nextLine();

                    String type = "";
                    switch (app) {
                        case 1:
                            type = "Checkup";
                            break;
                        case 2:
                            type = "Vaccination";
                            break;
                        case 3:
                            type = "Follow-Up";
                            break;
                        default:
                            System.out.println("Invalid option.");
                            break;
                    }

                    system.addAppointmentByDate(doctor,type,system.findPatient(nextPatentId - 1),LocalDate.now().toString());

                    System.out.println("Enter patient's weight: ");
                    double weight = scanner.nextDouble();
                    scanner.nextLine();

                    System.out.println("Enter patient's height: ");
                    double height = scanner.nextDouble();
                    scanner.nextLine();

                    System.out.println("Enter symptoms: ");
                    String symptoms = scanner.nextLine();

                    MedicalRecord record = new MedicalRecord(system.findPatient(nextPatentId - 1),doctor,weight,height,symptoms,null);

                    system.findPatient(nextPatentId - 1).addMedicalRecord(record);
                    try {
                        system.saveToFile("file/patients.txt");
                        system.saveToFile("file/appointments.txt");
                        system.saveToFile("file/records.txt");
                    } catch (IOException e) {
                        System.out.println("Error saving files.");
                    }

                    break;

                case 2: // REGISTER DOCTOR
                    String doctorName = system.readValidName();
                    String doctorEmail = system.readValidEmail();
                    String doctorPhone = system.readValidPhone();

                    system.addDoctor(doctorName, doctorEmail, doctorPhone);

                    try {
                        system.saveToFile("file/doctors.txt");
                    } catch (IOException e) {
                        System.out.println("Error saving doctor.");
                    }

                    System.out.println("Doctor added successfully.");
                    break;

                case 3: // REPORTS
                    System.out.println("Enter Patient ID: ");
                    int pid = scanner.nextInt();
                    scanner.nextLine();

                    Patient p = system.findPatient(pid);
                    if (p == null) {
                        System.out.println("No patient with that ID.");
                        break;
                    }

                    System.out.println("1. Medical History");
                    System.out.println("2. Appointments");
                    int r = scanner.nextInt();
                    scanner.nextLine();

                    if (r == 1) {
                        System.out.println(p.getMedicalHistory());
                    } else if (r == 2) {
                        System.out.println(p.getAppointments());
                    } else {
                        System.out.println("Invalid option.");
                    }
                    break;

                case 4: // VIEW ALL PATIENTS
                    if (system.patients.isEmpty()) System.out.println("No patients found.");
                    else system.patients.values().forEach(System.out::println);
                    break;

                case 5: // VIEW ALL DOCTORS
                    if (system.doctors.isEmpty()) System.out.println("No doctors found.");
                    else system.doctors.forEach(System.out::println);
                    break;

                case 6: // VIEW APPOINTMENTS BY DATE
                    System.out.println("Enter date (YYYY-MM-DD):");
                    String dateInput = scanner.nextLine();
                    LocalDate d = LocalDate.parse(dateInput);

                    if (system.getAppointmentByDate(d) == null)
                        System.out.println("No appointments on that date.");
                    else
                        system.getAppointmentByDate(d).forEach(System.out::println);
                    break;

                case 7: // EXIT
                    continueRun = false;
                    System.out.println("System Shutdown.");
                    break;

                default:
                    System.out.println("Please select a valid option (1-7)");
            }
        }
    }


}