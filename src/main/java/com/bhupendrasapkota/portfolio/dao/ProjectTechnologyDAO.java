package com.bhupendrasapkota.portfolio.dao;

import com.bhupendrasapkota.portfolio.models.ProjectTechnology;
import com.bhupendrasapkota.portfolio.models.Skill;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectTechnologyDAO extends BaseDAO {

    public ProjectTechnology findById(int id) {
        String sql = "SELECT pt.*, s.name as skill_name, s.category as skill_category " +
                "FROM project_technologies pt " +
                "LEFT JOIN skills s ON pt.skill_id = s.id " +
                "WHERE pt.id = ?";
        ProjectTechnology projectTechnology = null;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                projectTechnology = mapResultSetToProjectTechnology(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding project technology by id: " + id, e);
        }

        return projectTechnology;
    }

    public List<ProjectTechnology> findByProjectId(int projectId) {
        String sql = "SELECT pt.*, s.name as skill_name, s.category as skill_category " +
                "FROM project_technologies pt " +
                "LEFT JOIN skills s ON pt.skill_id = s.id " +
                "WHERE pt.project_id = ?";
        List<ProjectTechnology> projectTechnologies = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, projectId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                projectTechnologies.add(mapResultSetToProjectTechnology(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding project technologies by project id: " + projectId, e);
        }

        return projectTechnologies;
    }

    public List<ProjectTechnology> findBySkillId(int skillId) {
        String sql = "SELECT pt.*, s.name as skill_name, s.category as skill_category " +
                "FROM project_technologies pt " +
                "LEFT JOIN skills s ON pt.skill_id = s.id " +
                "WHERE pt.skill_id = ?";
        List<ProjectTechnology> projectTechnologies = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, skillId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                projectTechnologies.add(mapResultSetToProjectTechnology(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding project technologies by skill id: " + skillId, e);
        }

        return projectTechnologies;
    }

    public ProjectTechnology save(ProjectTechnology projectTechnology) {
        if (projectTechnology.getId() == 0) {
            return insert(projectTechnology);
        } else {
            return update(projectTechnology);
        }
    }

    private ProjectTechnology insert(ProjectTechnology projectTechnology) {
        String sql = "INSERT INTO project_technologies (project_id, skill_id, role) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, projectTechnology.getProjectId());
            stmt.setInt(2, projectTechnology.getSkillId());
            stmt.setString(3, projectTechnology.getRole());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating project technology failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    projectTechnology.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating project technology failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting project technology", e);
        }

        return projectTechnology;
    }

    private ProjectTechnology update(ProjectTechnology projectTechnology) {
        String sql = "UPDATE project_technologies SET project_id = ?, skill_id = ?, role = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, projectTechnology.getProjectId());
            stmt.setInt(2, projectTechnology.getSkillId());
            stmt.setString(3, projectTechnology.getRole());
            stmt.setInt(4, projectTechnology.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating project technology failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating project technology", e);
        }

        return projectTechnology;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM project_technologies WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting project technology with id: " + id, e);
        }
    }

    public boolean deleteByProjectId(int projectId) {
        String sql = "DELETE FROM project_technologies WHERE project_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, projectId);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting project technologies for project: " + projectId, e);
        }
    }

    private ProjectTechnology mapResultSetToProjectTechnology(ResultSet rs) throws SQLException {
        ProjectTechnology projectTechnology = new ProjectTechnology();
        projectTechnology.setId(rs.getInt("id"));
        projectTechnology.setProjectId(rs.getInt("project_id"));
        projectTechnology.setSkillId(rs.getInt("skill_id"));
        projectTechnology.setRole(rs.getString("role"));
        projectTechnology.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

        // Set skill information if available
        String skillName = rs.getString("skill_name");
        if (skillName != null) {
            Skill skill = new Skill();
            skill.setId(rs.getInt("skill_id"));
            skill.setName(skillName);
            skill.setCategory(rs.getString("skill_category"));
            projectTechnology.setSkill(skill);
        }

        return projectTechnology;
    }
}
