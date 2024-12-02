package com.kye.dent;

public class TreatmentDTO {
    private String Name;
    private String birthDate;
    private String treatmentDate;
    private String treatmentType;
    private String doctorName;
    private String doctorNotes;
    private double treatmentCost;

    // 생성자
    public TreatmentDTO(String patientName, String birthDate, String treatmentDate, String treatmentType,
                        String doctorName, String doctorNotes, double treatmentCost) {
        this.Name = patientName;
        this.birthDate = birthDate;
        this.treatmentDate = treatmentDate;
        this.treatmentType = treatmentType;
        this.doctorName = doctorName;
        this.doctorNotes = doctorNotes;
        this.treatmentCost = treatmentCost;
    }

    // Getter와 Setter
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

    public String getTreatmentDate() {
        return treatmentDate;
    }

    public void setTreatmentDate(String treatmentDate) {
        this.treatmentDate = treatmentDate;
    }

    public String getTreatmentType() {
        return treatmentType;
    }

    public void setTreatmentType(String treatmentType) {
        this.treatmentType = treatmentType;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorNotes() {
        return doctorNotes;
    }

    public void setDoctorNotes(String doctorNotes) {
        this.doctorNotes = doctorNotes;
    }

    public double getTreatmentCost() {
        return treatmentCost;
    }

    public void setTreatmentCost(double treatmentCost) {
        this.treatmentCost = treatmentCost;
    }
}

