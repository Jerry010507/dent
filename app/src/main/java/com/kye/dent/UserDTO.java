package com.kye.dent;

public class UserDTO {
    private int pid;  // 환자번호 (일관된 소문자 사용)
    private String name;
    private String birthDate;  // 비밀번호 (PW)
    private String phoneNumber;  // 사용자 ID

    // 생성자
    public UserDTO(int pid, String name, String birthDate, String phoneNumber) {
        this.pid = pid;
        this.name = name;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
    }

    // 기본 생성자
    public UserDTO() {
    }

    // Getter와 Setter
    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    // 객체 정보를 보기 쉽게 포맷
    @Override
    public String toString() {
        return String.format(
                "UserDTO [PID=%d, Name=%s, PhoneNumber=%s, BirthDate=%s]",
                pid, name, phoneNumber, birthDate
        );
    }
}
