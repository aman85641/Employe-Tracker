package com.employee.tracker.repository;

import com.employee.tracker.model.Report;
import com.employee.tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByUserOrderByDateDesc(User user);
    Optional<Report> findByUserAndDate(User user, LocalDate date);
}
