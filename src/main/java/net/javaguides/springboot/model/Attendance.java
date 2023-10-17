package net.javaguides.springboot.model;

import jakarta.persistence.*;

//import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "attendance")
public class Attendance implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "date")
    private Date date;

    @Column(name = "attendance")
    private boolean attendance;

    public Attendance() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isAttendance() {
        return attendance;
    }

    public void setAttendance(boolean attendance) {
        this.attendance = attendance;
    }

    public Attendance(Employee employee, Date date, boolean attendance) {
        this.employee = employee;
        this.date = date;
        this.attendance = attendance;
    }

}
