package com.bhupendrasapkota.portfolio.models;

import java.time.LocalDateTime;

public class ProjectTechnology {
    private int id;
    private int projectId;
    private int skillId;
    private String role;
    private LocalDateTime createdAt;
    
    // Related entities
    private Project project;
    private Skill skill;

    // Default constructor
    public ProjectTechnology() {}

    // Constructor with essential fields
    public ProjectTechnology(int projectId, int skillId) {
        this.projectId = projectId;
        this.skillId = skillId;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getProjectId() { return projectId; }
    public void setProjectId(int projectId) { this.projectId = projectId; }

    public int getSkillId() { return skillId; }
    public void setSkillId(int skillId) { this.skillId = skillId; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }

    public Skill getSkill() { return skill; }
    public void setSkill(Skill skill) { this.skill = skill; }

    @Override
    public String toString() {
        return "ProjectTechnology{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", skillId=" + skillId +
                ", role='" + role + '\'' +
                '}';
    }
}
