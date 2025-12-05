
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

                        appointmentsByDate.get(date).add(appointment);
                        patient.addAppointment(date,appointment);
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
        System.out.println("====== Welcome to Natembea =======");
        boolean continueRun = true;
        while (continueRun) { 
            System.out.println("What would you like to do: ");
            System.out.println("1. Create Appointment \n 2. Register Doctor 3. Generate Report");
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
                    System.out.println("Type of Appointment:  \n 1.Checkup \n2.Vaccination \n3.Follow-Up ");
                    int appoint = scanner.nextInt();
                    scanner.nextLine();
                    String inputAppointmentType ="";
                    switch(appoint){
                        case 1:
                            inputAppointmentType = "Checkup";
                            break;
                        case 2:
                            inputAppointmentType = "Vaccination";
                            break;
                        case 3:
                            inputAppointmentType = "Follow-Up";
                            break;
                        default:
                            System.out.println("Enter options 1-3");
                    }
                    if (!inputAppointmentType.equals("")){
                        if (system.findDoctor(doctorId) != null){
                            //addAppointmentByDate(String doctor, String appointmentType,String patient)
                            system.addAppointmentByDate(system.findDoctor(doctorId),inputAppointmentType, system.findPatient( nextPatentId - 1),LocalDate.now().toString());
                            System.out.println("Enter the patients' weight: ");
                            double weight = scanner.nextDouble();
                            scanner.nextLine();
                            System.out.println("Enter the patients' height: ");
                            double height = scanner.nextDouble();
                            scanner.nextLine();
                            System.out.println("Enter the patients' symptoms: ");
                            String symptoms = scanner.nextLine();
                            LocalDate date = LocalDate.now();
                            //public MedicalRecord(int patientId, Patient patient, Doctor doctor, double weight, double height, String symptoms, LocalDateTime date) {

                           MedicalRecord record = new MedicalRecord(system.findPatient(nextPatentId - 1), system.findDoctor(doctorId), weight, height, symptoms, null);
                           //MedicalRecord(Patient patient, Doctor doctor, double weight, double height, String symptoms, LocalDateTime date, String prescription) {
       
                           system.findPatient(nextPatentId - 1).addMedicalRecord(record);
                            
                        }
                    }
        
                    break;
                case 2:
                    System.out.println("Enter the Doctors full name: ");
                    String doctorName = scanner.nextLine();
                    System.out.println("Enter the Doctors email address: ");
                    String doctorEmail = scanner.nextLine();
                    System.out.println("Enter the Doctors phone number: ");
                    String doctorPhone = scanner.nextLine();
                    system.addDoctor(doctorName, doctorEmail, doctorPhone);
                    System.out.println("Doctor added succesfully");
                    break;
                case 3:
                    System.out.println("Enter the Patient ID: ");
                    int patientid = Integer.parseInt(scanner.nextLine());
                    Patient patient = system.findPatient(patientid);

                    if (patient == null) {
                        System.out.println("No patient with that ID.");
                        break;
                    }

                    System.out.println("What kind of report would you like to generate: \n 1. Medical History \n 2. Appointments");
                    int opt = scanner.nextInt();
                    scanner.nextLine();

                    switch (opt) {
                        case 1:
                            System.out.println("Medical history for patient " + patientid + ":");
                            System.out.println(patient.getMedicalHistory());
                            break;
                        case 2:
                            System.out.println("Appointments for patient " + patientid + ":");
                            System.out.println(patient.getAppointments());
                            break;
                        default:
                            System.out.println("Select 1 or 2 only");
                    }
                    break;

                default:
                    System.out.println("Please select option 1 - 3 ");
            }
            System.out.println("Do you want to continue: \n1. Yes\n2. No");
            int run = scanner.nextInt();
            switch (run) {
                case 1:
                    continueRun = true;
                    break;
                case 2:
                    continueRun = false;
                    System.out.println("System Shutdown");
                    break;
                default:
                    throw new AssertionError();
                }
        }   
    }
           

}