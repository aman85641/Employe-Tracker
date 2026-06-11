package com.employee.tracker.controller;

import com.employee.tracker.dto.MessageResponse;
import com.employee.tracker.model.Attendance;
import com.employee.tracker.model.Report;
import com.employee.tracker.model.User;
import com.employee.tracker.repository.AttendanceRepository;
import com.employee.tracker.repository.ReportRepository;
import com.employee.tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    UserRepository userRepository;

    private User getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(userDetails.getUsername()).orElse(null);
    }

    @PostMapping("/checkin")
    public ResponseEntity<?> checkIn() {
        User user = getCurrentUser();
        LocalDate today = LocalDate.now();

        Optional<Attendance> existing = attendanceRepository.findByUserAndDate(user, today);
        if (existing.isPresent()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Already checked in today!"));
        }

        Attendance attendance = new Attendance();
        attendance.setUser(user);
        attendance.setDate(today);
        attendance.setCheckInTime(LocalDateTime.now());
        attendanceRepository.save(attendance);

        return ResponseEntity.ok(new MessageResponse("Checked in successfully at " + attendance.getCheckInTime()));
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkOut() {
        User user = getCurrentUser();
        LocalDate today = LocalDate.now();

        Optional<Attendance> existing = attendanceRepository.findByUserAndDate(user, today);
        if (existing.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("No check-in found for today!"));
        }

        Attendance attendance = existing.get();
        if (attendance.getCheckOutTime() != null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Already checked out today!"));
        }

        LocalDateTime checkOutTime = LocalDateTime.now();
        attendance.setCheckOutTime(checkOutTime);
        
        Duration duration = Duration.between(attendance.getCheckInTime(), checkOutTime);
        double hours = duration.toMinutes() / 60.0;
        attendance.setTotalHours(Math.round(hours * 100.0) / 100.0);
        
        attendanceRepository.save(attendance);

        return ResponseEntity.ok(new MessageResponse("Checked out successfully! Total hours: " + attendance.getTotalHours()));
    }

    @PostMapping("/report")
    public ResponseEntity<?> addReport(@RequestBody String taskDescription) {
        User user = getCurrentUser();
        LocalDate today = LocalDate.now();

        Optional<Report> existing = reportRepository.findByUserAndDate(user, today);
        Report report;
        if (existing.isPresent()) {
            report = existing.get();
            report.setTaskDescription(report.getTaskDescription() + "\n" + taskDescription);
        } else {
            report = new Report();
            report.setUser(user);
            report.setDate(today);
            report.setTaskDescription(taskDescription);
        }
        reportRepository.save(report);

        return ResponseEntity.ok(new MessageResponse("Report saved successfully!"));
    }

    @GetMapping("/attendance")
    public ResponseEntity<?> getAttendanceHistory() {
        List<Attendance> attendances = attendanceRepository.findByUserOrderByDateDesc(getCurrentUser());
        return ResponseEntity.ok(attendances);
    }
}
