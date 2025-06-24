package com.bhupendrasapkota.portfolio.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Certification {
    private int id;
    private String name;
    private String issuingOrganization;
    private LocalDate issueDate;
    private LocalDate expirationDate;
    private String credentialId;
    private String credentialUrl;
    private String badgeImageUrl;
    private boolean isActive;
    private int displayOrder;
    private LocalDateTime createdAt;

    // Default constructor
    public Certification() {}

    // Constructor with essential fields
    public Certification(String name, String issuingOrganization) {
        this.name = name;
        this.issuingOrganization = issuingOrganization;
        this.isActive = true;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getIssuingOrganization() { return issuingOrganization; }
    public void setIssuingOrganization(String issuingOrganization) { this.issuingOrganization = issuingOrganization; }

    public LocalDate getIssueDate() { return issueDate; }
    public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }

    public LocalDate getExpirationDate() { return expirationDate; }
    public void setExpirationDate(LocalDate expirationDate) { this.expirationDate = expirationDate; }

    public String getCredentialId() { return credentialId; }
    public void setCredentialId(String credentialId) { this.credentialId = credentialId; }

    public String getCredentialUrl() { return credentialUrl; }
    public void setCredentialUrl(String credentialUrl) { this.credentialUrl = credentialUrl; }

    public String getBadgeImageUrl() { return badgeImageUrl; }
    public void setBadgeImageUrl(String badgeImageUrl) { this.badgeImageUrl = badgeImageUrl; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public int getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(int displayOrder) { this.displayOrder = displayOrder; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    // --- Converters for JSTL ---
    public Date getIssueDateAsDate() {
        if (this.issueDate == null) {
            return null;
        }
        return Date.from(this.issueDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    // -------------------------

    @Override
    public String toString() {
        return "Certification{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", issuingOrganization='" + issuingOrganization + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
