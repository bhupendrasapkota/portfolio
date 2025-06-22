package com.bhupendrasapkota.portfolio.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Project {
    private int id;
    private String title;
    private String slug;
    private String shortDescription;
    private String fullDescription;
    private String projectType;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;
    private String clientName;
    private String projectUrl;
    private String githubUrl;
    private String demoUrl;
    private String featuredImageUrl;
    private boolean isFeatured;
    private boolean isPublished;
    private int viewCount;
    private int displayOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Related entities
    private List<ProjectImage> images;
    private List<Skill> technologies;

    // Default constructor
    public Project() {}

    // Constructor with essential fields
    public Project(String title, String slug, String shortDescription, String projectType) {
        this.title = title;
        this.slug = slug;
        this.shortDescription = shortDescription;
        this.projectType = projectType;
        this.status = "completed";
        this.isPublished = true;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public String getShortDescription() { return shortDescription; }
    public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }

    public String getFullDescription() { return fullDescription; }
    public void setFullDescription(String fullDescription) { this.fullDescription = fullDescription; }

    public String getProjectType() { return projectType; }
    public void setProjectType(String projectType) { this.projectType = projectType; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }

    public String getProjectUrl() { return projectUrl; }
    public void setProjectUrl(String projectUrl) { this.projectUrl = projectUrl; }

    public String getGithubUrl() { return githubUrl; }
    public void setGithubUrl(String githubUrl) { this.githubUrl = githubUrl; }

    public String getDemoUrl() { return demoUrl; }
    public void setDemoUrl(String demoUrl) { this.demoUrl = demoUrl; }

    public String getFeaturedImageUrl() { return featuredImageUrl; }
    public void setFeaturedImageUrl(String featuredImageUrl) { this.featuredImageUrl = featuredImageUrl; }

    public boolean isFeatured() { return isFeatured; }
    public void setFeatured(boolean featured) { isFeatured = featured; }

    public boolean isPublished() { return isPublished; }
    public void setPublished(boolean published) { isPublished = published; }

    public int getViewCount() { return viewCount; }
    public void setViewCount(int viewCount) { this.viewCount = viewCount; }

    public int getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(int displayOrder) { this.displayOrder = displayOrder; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public List<ProjectImage> getImages() { return images; }
    public void setImages(List<ProjectImage> images) { this.images = images; }

    public List<Skill> getTechnologies() { return technologies; }
    public void setTechnologies(List<Skill> technologies) { this.technologies = technologies; }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", slug='" + slug + '\'' +
                ", projectType='" + projectType + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}