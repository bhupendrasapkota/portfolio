package com.bhupendrasapkota.portfolio.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Education {
    private int id;
    private String institutionName;
    private String degree;
    private String fieldOfStudy;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal gpa;
    private String description;
    private String institutionUrl;
    private String institutionLogoUrl;
    private int displayOrder;
    private LocalDateTime createdAt;

    // Default constructor
    public Education() {}

    // Constructor with essential fields
    public Education(String institutionName, String degree) {
        this.institutionName = institutionName;
        this.degree = degree;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getInstitutionName() { return institutionName; }
    public void setInstitutionName(String institutionName) { this.institutionName = institutionName; }

    public String getDegree() { return degree; }
    public void setDegree(String degree) { this.degree = degree; }

    public String getFieldOfStudy() { return fieldOfStudy; }
    public void setFieldOfStudy(String fieldOfStudy) { this.fieldOfStudy = fieldOfStudy; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public BigDecimal getGpa() { return gpa; }
    public void setGpa(BigDecimal gpa) { this.gpa = gpa; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getInstitutionUrl() { return institutionUrl; }
    public void setInstitutionUrl(String institutionUrl) { this.institutionUrl = institutionUrl; }

    public String getInstitutionLogoUrl() { return institutionLogoUrl; }
    public void setInstitutionLogoUrl(String institutionLogoUrl) { this.institutionLogoUrl = institutionLogoUrl; }

    public int getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(int displayOrder) { this.displayOrder = displayOrder; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Education{" +
                "id=" + id +
                ", institutionName='" + institutionName + '\'' +
                ", degree='" + degree + '\'' +
                ", fieldOfStudy='" + fieldOfStudy + '\'' +
                '}';
    }
}
