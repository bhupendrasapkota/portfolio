package com.bhupendrasapkota.portfolio.models;

import java.time.LocalDateTime;

public class ContactSubmission {
    private int id;
    private String name;
    private String email;
    private String subject;
    private String message;
    private String phone;
    private String company;
    private String projectBudget;
    private String projectTimeline;
    private boolean isRead;
    private boolean isReplied;
    private String ipAddress;
    private String userAgent;
    private LocalDateTime createdAt;

    // Default constructor
    public ContactSubmission() {}

    // Constructor with essential fields
    public ContactSubmission(String name, String email, String message) {
        this.name = name;
        this.email = email;
        this.message = message;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public String getProjectBudget() { return projectBudget; }
    public void setProjectBudget(String projectBudget) { this.projectBudget = projectBudget; }

    public String getProjectTimeline() { return projectTimeline; }
    public void setProjectTimeline(String projectTimeline) { this.projectTimeline = projectTimeline; }

    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { isRead = read; }

    public boolean isReplied() { return isReplied; }
    public void setReplied(boolean replied) { isReplied = replied; }

    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "ContactSubmission{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", subject='" + subject + '\'' +
                ", isRead=" + isRead +
                '}';
    }
}
