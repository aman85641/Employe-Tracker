package com.employee.tracker.controller;

import com.employee.tracker.model.Attendance;
import com.employee.tracker.model.Report;
import com.employee.tracker.model.User;
import com.employee.tracker.repository.AttendanceRepository;
import com.employee.tracker.repository.ReportRepository;
import com.employee.tracker.repository.UserRepository;
import com.employee.tracker.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @GetMapping("/employees")
    public ResponseEntity<?> getAllEmployees() {
        List<User> employees = userRepository.findByRole(com.employee.tracker.model.Role.ROLE_EMPLOYEE);
        // Remove passwords from response for security
        employees.forEach(e -> e.setPassword(null));
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/attendance")
    public ResponseEntity<?> getAllAttendances() {
        List<Attendance> attendances = attendanceRepository.findAll();
        return ResponseEntity.ok(attendances);
    }

    @GetMapping("/reports")
    public ResponseEntity<?> getAllReports() {
        List<Report> reports = reportRepository.findAll();
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/notifications")
    public ResponseEntity<?> getRecentNotifications() {
        List<com.employee.tracker.model.Notification> notifications = notificationRepository.findTop20ByOrderByTimestampDesc();
        List<com.employee.tracker.dto.ActivityDto> dtos = notifications.stream().map(n -> 
            new com.employee.tracker.dto.ActivityDto(n.getId(), n.getUser().getName(), n.getType(), n.getMessage(), n.getTimestamp())
        ).toList();
        return ResponseEntity.ok(dtos);
    }
}
