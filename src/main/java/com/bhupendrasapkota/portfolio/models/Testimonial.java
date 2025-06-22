package com.bhupendrasapkota.portfolio.models;

import java.time.LocalDateTime;

public class Testimonial {
    private int id;
    private String clientName;
    private String clientPosition;
    private String clientCompany;
    private String clientImageUrl;
    private String testimonialText;
    private int rating;
    private Integer projectId;
    private boolean isFeatured;
    private boolean isPublished;
    private int displayOrder;
    private LocalDateTime createdAt;
    
    // Related entity
    private Project project;

    // Default constructor
    public Testimonial() {}

    // Constructor with essential fields
    public Testimonial(String clientName, String testimonialText, int rating) {
        this.clientName = clientName;
        this.testimonialText = testimonialText;
        this.rating = rating;
        this.isPublished = true;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }

    public String getClientPosition() { return clientPosition; }
    public void setClientPosition(String clientPosition) { this.clientPosition = clientPosition; }

    public String getClientCompany() { return clientCompany; }
    public void setClientCompany(String clientCompany) { this.clientCompany = clientCompany; }

    public String getClientImageUrl() { return clientImageUrl; }
    public void setClientImageUrl(String clientImageUrl) { this.clientImageUrl = clientImageUrl; }

    public String getTestimonialText() { return testimonialText; }
    public void setTestimonialText(String testimonialText) { this.testimonialText = testimonialText; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public Integer getProjectId() { return projectId; }
    public void setProjectId(Integer projectId) { this.projectId = projectId; }

    public boolean isFeatured() { return isFeatured; }
    public void setFeatured(boolean featured) { isFeatured = featured; }

    public boolean isPublished() { return isPublished; }
    public void setPublished(boolean published) { isPublished = published; }

    public int getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(int displayOrder) { this.displayOrder = displayOrder; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }

    @Override
    public String toString() {
        return "Testimonial{" +
                "id=" + id +
                ", clientName='" + clientName + '\'' +
                ", rating=" + rating +
                ", isPublished=" + isPublished +
                '}';
    }
}
