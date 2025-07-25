package com.bhupendrasapkota.portfolio.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Skill {
    private int id;
    private String name;
    private String category;
    private int proficiencyLevel;
    private BigDecimal yearsOfExperience;
    private String iconName;
    private String color;
    private boolean isFeatured;
    private int displayOrder;
    private LocalDateTime createdAt;

    // Default constructor
    public Skill() {}

    // Constructor with essential fields
    public Skill(String name, String category, int proficiencyLevel) {
        this.name = name;
        this.category = category;
        this.proficiencyLevel = proficiencyLevel;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getProficiencyLevel() { return proficiencyLevel; }
    public void setProficiencyLevel(int proficiencyLevel) { this.proficiencyLevel = proficiencyLevel; }

    public BigDecimal getYearsOfExperience() { return yearsOfExperience; }
    public void setYearsOfExperience(BigDecimal yearsOfExperience) { this.yearsOfExperience = yearsOfExperience; }

    public String getIconName() { return iconName; }
    public void setIconName(String iconName) { this.iconName = iconName; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public boolean isFeatured() { return isFeatured; }
    public void setFeatured(boolean featured) { isFeatured = featured; }

    public int getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(int displayOrder) { this.displayOrder = displayOrder; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Skill{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", proficiencyLevel=" + proficiencyLevel +
                '}';
    }
}