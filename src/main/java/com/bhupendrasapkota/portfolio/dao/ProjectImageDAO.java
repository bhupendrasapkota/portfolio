package com.bhupendrasapkota.portfolio.dao;

import com.bhupendrasapkota.portfolio.models.ProjectImage;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectImageDAO extends BaseDAO {

    public ProjectImage findById(int id) {
        String sql = "SELECT * FROM project_images WHERE id = ?";
        ProjectImage projectImage = null;
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                projectImage = mapResultSetToProjectImage(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding project image by id: " + id, e);
        }
        
        return projectImage;
    }

    public List<ProjectImage> findByProjectId(int projectId) {
        String sql = "SELECT * FROM project_images WHERE project_id = ? ORDER BY display_order, id";
        List<ProjectImage> projectImages = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, projectId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                projectImages.add(mapResultSetToProjectImage(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding project images by project id: " + projectId, e);
        }
        
        return projectImages;
    }

    public ProjectImage save(ProjectImage projectImage) {
        if (projectImage.getId() == 0) {
            return insert(projectImage);
        } else {
            return update(projectImage);
        }
    }

    private ProjectImage insert(ProjectImage projectImage) {
        String sql = "INSERT INTO project_images (project_id, image_url, alt_text, caption, display_order, is_featured) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            setProjectImageParameters(stmt, projectImage);
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating project image failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    projectImage.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating project image failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting project image", e);
        }
        
        return projectImage;
    }

    private ProjectImage update(ProjectImage projectImage) {
        String sql = "UPDATE project_images SET project_id = ?, image_url = ?, alt_text = ?, caption = ?, display_order = ?, is_featured = ? WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            setProjectImageParameters(stmt, projectImage);
            stmt.setInt(7, projectImage.getId());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating project image failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating project image", e);
        }
        
        return projectImage;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM project_images WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting project image with id: " + id, e);
        }
    }

    private void setProjectImageParameters(PreparedStatement stmt, ProjectImage projectImage) throws SQLException {
        stmt.setInt(1, projectImage.getProjectId());
        stmt.setString(2, projectImage.getImageUrl());
        stmt.setString(3, projectImage.getAltText());
        stmt.setString(4, projectImage.getCaption());
        stmt.setInt(5, projectImage.getDisplayOrder());
        stmt.setBoolean(6, projectImage.isFeatured());
    }

    private ProjectImage mapResultSetToProjectImage(ResultSet rs) throws SQLException {
        ProjectImage projectImage = new ProjectImage();
        projectImage.setId(rs.getInt("id"));
        projectImage.setProjectId(rs.getInt("project_id"));
        projectImage.setImageUrl(rs.getString("image_url"));
        projectImage.setAltText(rs.getString("alt_text"));
        projectImage.setCaption(rs.getString("caption"));
        projectImage.setDisplayOrder(rs.getInt("display_order"));
        projectImage.setFeatured(rs.getBoolean("is_featured"));
        projectImage.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return projectImage;
    }
}
