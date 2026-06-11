package com.employee.tracker.controller;

import com.employee.tracker.model.Attendance;
import com.employee.tracker.model.Report;
import com.employee.tracker.model.User;
import com.employee.tracker.repository.AttendanceRepository;
import com.employee.tracker.repository.ReportRepository;
import com.employee.tracker.repository.UserRepository;
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

    @GetMapping("/employees")
    public ResponseEntity<?> getAllEmployees() {
        List<User> employees = userRepository.findAll();
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
}
