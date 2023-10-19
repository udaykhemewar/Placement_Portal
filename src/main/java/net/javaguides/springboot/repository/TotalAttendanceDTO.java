package net.javaguides.springboot.repository;

public class TotalAttendanceDTO {
    private String Name;
    private String Month;
    private Long TotalAttendancee;

    public String getEmpName() {
        return Name;
    }

    public void setEmpName(String Name) {
        this.Name = Name;
    }

    public String getMonthName() {
        return Month;
    }

    public void setMonthName(String Month) {
        this.Month = Month;
    }

    public Long getTotalAttendance() {
        return TotalAttendancee;
    }

    public void setTotalAttendance(Long TotalAttendancee) {
        this.TotalAttendancee = TotalAttendancee;
    }

    public TotalAttendanceDTO(String Name, String Month, Long TotalAttendancee) {
        this.Name = Name;
        this.Month = Month;
        this.TotalAttendancee = TotalAttendancee;
    }

}