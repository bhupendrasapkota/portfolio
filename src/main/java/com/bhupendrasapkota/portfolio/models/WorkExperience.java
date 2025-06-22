package com.bhupendrasapkota.portfolio.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class WorkExperience {
    private int id;
    private String companyName;
    private String position;
    private String employmentType;
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isCurrent;
    private String description;
    private String companyUrl;
    private String companyLogoUrl;
    private int displayOrder;
    private LocalDateTime createdAt;
    
    // Related entities
    private List<WorkAchievement> achievements;

    // Default constructor
    public WorkExperience() {}

    // Constructor with essential fields
    public WorkExperience(String companyName, String position, LocalDate startDate) {
        this.companyName = companyName;
        this.position = position;
        this.startDate = startDate;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getEmploymentType() { return employmentType; }
    public void setEmploymentType(String employmentType) { this.employmentType = employmentType; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public boolean isCurrent() { return isCurrent; }
    public void setCurrent(boolean current) { isCurrent = current; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCompanyUrl() { return companyUrl; }
    public void setCompanyUrl(String companyUrl) { this.companyUrl = companyUrl; }

    public String getCompanyLogoUrl() { return companyLogoUrl; }
    public void setCompanyLogoUrl(String companyLogoUrl) { this.companyLogoUrl = companyLogoUrl; }

    public int getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(int displayOrder) { this.displayOrder = displayOrder; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public List<WorkAchievement> getAchievements() { return achievements; }
    public void setAchievements(List<WorkAchievement> achievements) { this.achievements = achievements; }

    public String getStartYear() {
        return startDate != null ? startDate.format(DateTimeFormatter.ofPattern("yyyy")) : "";
    }
    public String getEndYear() {
        if (endDate != null) {
            return endDate.format(DateTimeFormatter.ofPattern("yyyy"));
        } else if (isCurrent) {
            return "Present";
        }
        return "";
    }

    @Override
    public String toString() {
        return "WorkExperience{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", position='" + position + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", isCurrent=" + isCurrent +
                '}';
    }
}
