package com.employee.tracker.dto;

import java.time.LocalDateTime;

public class ActivityDto {
    private Long id; // Notification ID
    private String userName;
    private String type; // CHECK_IN, CHECK_OUT, REPORT
    private String message;
    private LocalDateTime timestamp;

    public ActivityDto(Long id, String userName, String type, String message, LocalDateTime timestamp) {
        this.id = id;
        this.userName = userName;
        this.type = type;
        this.message = message;
        this.timestamp = timestamp;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
