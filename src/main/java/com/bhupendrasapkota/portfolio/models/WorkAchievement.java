package com.bhupendrasapkota.portfolio.models;

public class WorkAchievement {
    private int id;
    private int workExperienceId;
    private String description;
    private int displayOrder;

    // Default constructor
    public WorkAchievement() {}

    // Constructor with essential fields
    public WorkAchievement(int workExperienceId, String description) {
        this.workExperienceId = workExperienceId;
        this.description = description;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getWorkExperienceId() { return workExperienceId; }
    public void setWorkExperienceId(int workExperienceId) { this.workExperienceId = workExperienceId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(int displayOrder) { this.displayOrder = displayOrder; }

    @Override
    public String toString() {
        return "WorkAchievement{" +
                "id=" + id +
                ", workExperienceId=" + workExperienceId +
                ", description='" + description + '\'' +
                '}';
    }
}
