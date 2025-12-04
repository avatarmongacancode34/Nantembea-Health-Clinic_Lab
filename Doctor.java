public class Doctor extends Person {
    private String speciality;
    private int doctorId;

    public Doctor(int doctorId, String email, String phone, String name) {
        super(name, email, phone);
        this.doctorId = doctorId;
    }

    public String getSpeciality() {
        if (speciality == null || speciality.isEmpty()) {
            throw new IllegalArgumentException("Speciality cannot be null or empty");
        }
        return speciality;
    }

    public void setSpeciality(String speciality) {
        if (speciality == null || speciality.isEmpty()) {
            throw new IllegalArgumentException("Speciality cannot be null or empty");
        }
        this.speciality = speciality;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        if (doctorId <= 0) {
            throw new IllegalArgumentException("Doctor ID must be a positive integer");
        }
        this.doctorId = doctorId;
    }

    @Override
    public void setEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        super.setEmail(email);
    }

    @Override
    public void setPhone(String phone) {
        if (phone == null || phone.isEmpty()) {
            throw new IllegalArgumentException("Phone cannot be null or empty");
        }
        super.setPhone(phone);
    }

    @Override
    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        super.setName(name);
    }
}