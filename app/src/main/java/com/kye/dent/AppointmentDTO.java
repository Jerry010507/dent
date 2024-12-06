package com.kye.dent;

public class AppointmentDTO {
    private int appointId;
    private String phone;
    private String Name;
    private String birthDate;
    private String appointmentDate;
    private String treatmentType;

    // 생성자
    public AppointmentDTO(String patientName, String birthDate, String appointmentDate, String treatmentType) {
        this.appointId = appointId;
        this.phone = phone;
        this.Name = patientName;
        this.birthDate = birthDate;
        this.appointmentDate = appointmentDate;
        this.treatmentType = treatmentType;
    }

    // Getter와 Setter
    public int getAppointId() {
        return appointId;
    }

    public void setAppointId(int appointId) {
        this.appointId = appointId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPatientName() {
        return Name;
    }

    public void setPatientName(String patientName) {
        this.Name = patientName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getTreatmentType() {
        return treatmentType;
    }

    public void setTreatmentType(String treatmentType) {
        this.treatmentType = treatmentType;
    }
}