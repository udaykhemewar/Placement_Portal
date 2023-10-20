package net.javaguides.springboot.controller;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Attendance;
import net.javaguides.springboot.repository.AttendanceRepository;
import net.javaguides.springboot.repository.TotalAttendanceDTO;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/attendance")
public class AttendanceController {

    private final AttendanceRepository attendanceRepository;
    @Autowired
    private Producer<Attendance> attendanceProducer;
    @Autowired
    public AttendanceController(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    @GetMapping("/")
    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

//    private final Logger logger = LoggerFactory.getLogger(AttendanceController.class);

    @GetMapping("/{id}")
    @Cacheable(value = "attendanceCache", key = "#attendanceId", unless = "#result == null")
//    public ResponseEntity<Attendance> getAttendanceById(@PathVariable(value = "id") Long attendanceId)
//            throws ResourceNotFoundException {
//        Attendance attendance = attendanceRepository.findById(attendanceId)
//                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found for this id :: " + attendanceId));
//
//        if (attendance != null) {
//            logger.info("Attendance with ID {} fetched from cache or database", attendanceId);
//        } else {
//            logger.info("Attendance with ID {} not found", attendanceId);
//        }
//        return ResponseEntity.ok().body(attendance);
//    }
    //  public ResponseEntity<Map<String, Object>> getAttendanceById(@PathVariable(value = "id") Long attendanceId)
//        throws ResourceNotFoundException {
//    Attendance attendance = attendanceRepository.findById(attendanceId)
//            .orElseThrow(() -> new ResourceNotFoundException("Attendance not found for this id :: " + attendanceId));
//        Map<String, Object> response = new HashMap<>();
//        response.put("data", attendance);
//        if (attendance != null) {
//            response.put("source", "database");
//        } else {
//            response.put("source", "cache");
//        }
//         return ResponseEntity.ok().body(response);
    public Attendance getAttendanceById(@PathVariable(value = "id") Long attendanceId)
            throws ResourceNotFoundException {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance not fund for this id: " + attendanceId));
        return attendance;
    }



//    @PostMapping("/")
//    public Attendance createAttendance(@RequestBody Attendance attendance)  {
//        Attendance savedAttendance = attendanceRepository.save(attendance);
//        try {
//            attendanceProducer.send(savedAttendance);
//        } catch (PulsarClientException e) {
//            e.printStackTrace();
//        }
//
//        return savedAttendance;
//    }

    @PostMapping("/")
    @Async
    public CompletableFuture<Attendance> createAttendanceAsync(@RequestBody Attendance attendance) {
        CompletableFuture<Attendance> savedAttendanceFuture = saveToDatabaseAsync(attendance);
        publishToPulsarAsync(attendance);
        return savedAttendanceFuture;
    }
    @Async
    public CompletableFuture<Attendance> saveToDatabaseAsync(Attendance attendance) {
        Attendance savedAttendance = attendanceRepository.save(attendance);
        return CompletableFuture.completedFuture(savedAttendance);
    }

    @Async
    public void publishToPulsarAsync(Attendance attendance) {
        try {
            attendanceProducer.send(attendance);
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Attendance> updateAttendance(@PathVariable(value = "id") Long attendanceId,
                                                       @RequestBody Attendance attendanceDetails) throws ResourceNotFoundException {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found for this id: " + attendanceId));

        attendance.setEmployee(attendanceDetails.getEmployee());
        attendance.setDate(attendanceDetails.getDate());
        attendance.setAttendance(attendanceDetails.isAttendance());
        final Attendance updatedAttendance = attendanceRepository.save(attendance);
        return ResponseEntity.ok(updatedAttendance);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "attendanceCache", key = "#attendanceId")
    public void deleteAttendance(@PathVariable(value = "id") Long attendanceId)
            throws ResourceNotFoundException {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found for this id :: " + attendanceId));
        attendanceRepository.delete(attendance);
    }

    @GetMapping("/total_attendance")
    public List<TotalAttendanceDTO> getTotalAttendance() {
        List<Object[]> results = attendanceRepository.getTotalAttendanceByMonth();

        List<TotalAttendanceDTO> totalAttendanceList = new ArrayList<>();

        for (Object[] result : results) {
            String Name = (String) result[0];
            String Month = (String) result[1];
            Long TotalAttendancee = ((Number) result[2]).longValue();

            TotalAttendanceDTO dto = new TotalAttendanceDTO(Name, Month, TotalAttendancee);
            totalAttendanceList.add(dto);
        }

        return totalAttendanceList;
    }
}
