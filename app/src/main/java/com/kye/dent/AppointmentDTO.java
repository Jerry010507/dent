package com.kye.dent;

public class AppointmentDTO {
    private int appointId;
    private String phone;
    private String name;
    private String birthDate;
    private String appointmentDate;
    private String treatmentType;

    // 생성자
    public AppointmentDTO(String patientName,String phone, String birthDate, String appointmentDate, String treatmentType) {
        this.appointId = appointId;
        this.phone = phone;
        this.name = patientName;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
