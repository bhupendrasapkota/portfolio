package com.bhupendrasapkota.portfolio.models;

import java.time.LocalDateTime;

public class SocialLink {
    private int id;
    private String platform;
    private String url;
    private String iconName;
    private int displayOrder;
    private boolean isActive;
    private LocalDateTime createdAt;

    // Default constructor
    public SocialLink() {}

    // Constructor with essential fields
    public SocialLink(String platform, String url) {
        this.platform = platform;
        this.url = url;
        this.isActive = true;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPlatform() { return platform; }
    public void setPlatform(String platform) { this.platform = platform; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getIconName() { return iconName; }
    public void setIconName(String iconName) { this.iconName = iconName; }

    public int getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(int displayOrder) { this.displayOrder = displayOrder; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "SocialLink{" +
                "id=" + id +
                ", platform='" + platform + '\'' +
                ", url='" + url + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
