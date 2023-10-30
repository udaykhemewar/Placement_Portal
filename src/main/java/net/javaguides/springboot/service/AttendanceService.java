package net.javaguides.springboot.service;

import net.javaguides.springboot.model.Attendance;
import net.javaguides.springboot.dao.AttendanceRepository;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    @Autowired
    public AttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    public Attendance createAttendance(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    public Attendance getAttendanceById(Long attendanceId) throws ResourceNotFoundException {
        return attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found for this id: " + attendanceId));
    }

    public Attendance updateAttendance(Long attendanceId, Attendance attendanceDetails) throws ResourceNotFoundException {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found for this id: " + attendanceId));

        attendance.setEmployee(attendanceDetails.getEmployee());
        attendance.setDate(attendanceDetails.getDate());
        attendance.setAttendance(attendanceDetails.isAttendance());

        return attendanceRepository.save(attendance);
    }

    public void deleteAttendance(Long attendanceId) throws ResourceNotFoundException {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found for this id :: " + attendanceId));
        attendanceRepository.delete(attendance);
    }
}
