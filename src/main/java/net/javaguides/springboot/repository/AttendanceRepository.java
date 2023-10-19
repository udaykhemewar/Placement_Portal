package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    @Query(value = "SELECT e.first_name AS empName, " +
            "TO_CHAR(a.date, 'Month') AS monthName, " +
            "SUM(CASE WHEN a.attendance = true THEN 1 ELSE 0 END) AS totalAttendance " +
            "FROM employees e " +
            "LEFT JOIN attendance a ON e.id = a.employee_id " +
            "GROUP BY e.first_name, TO_CHAR(a.date, 'Month') " +
            "ORDER BY e.first_name, TO_CHAR(a.date, 'Month')", nativeQuery = true)
    List<Object[]> getTotalAttendanceByMonth();
}
