package com.bhupendrasapkota.portfolio.models;

import java.time.LocalDateTime;

public class ProjectImage {
    private int id;
    private int projectId;
    private String imageUrl;
    private String altText;
    private String caption;
    private int displayOrder;
    private boolean isFeatured;
    private LocalDateTime createdAt;

    // Default constructor
    public ProjectImage() {}

    // Constructor with essential fields
    public ProjectImage(int projectId, String imageUrl) {
        this.projectId = projectId;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getProjectId() { return projectId; }
    public void setProjectId(int projectId) { this.projectId = projectId; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getAltText() { return altText; }
    public void setAltText(String altText) { this.altText = altText; }

    public String getCaption() { return caption; }
    public void setCaption(String caption) { this.caption = caption; }

    public int getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(int displayOrder) { this.displayOrder = displayOrder; }

    public boolean isFeatured() { return isFeatured; }
    public void setFeatured(boolean featured) { isFeatured = featured; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "ProjectImage{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", imageUrl='" + imageUrl + '\'' +
                ", isFeatured=" + isFeatured +
                '}';
    }
}
