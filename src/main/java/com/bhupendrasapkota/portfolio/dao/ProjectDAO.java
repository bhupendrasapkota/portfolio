package com.bhupendrasapkota.portfolio.dao;

import com.bhupendrasapkota.portfolio.models.Project;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO extends BaseDAO {

    public Project findById(int id) {
        String sql = "SELECT * FROM projects WHERE id = ?";
        Project project = null;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                project = mapResultSetToProject(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding project by id: " + id, e);
        }

        return project;
    }

    public Project findBySlug(String slug) {
        String sql = "SELECT * FROM projects WHERE slug = ?";
        Project project = null;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, slug);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                project = mapResultSetToProject(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding project by slug: " + slug, e);
        }

        return project;
    }

    public List<Project> findAll() {
        String sql = "SELECT * FROM projects ORDER BY display_order, created_at DESC";
        List<Project> projects = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                projects.add(mapResultSetToProject(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all projects", e);
        }

        return projects;
    }

    public List<Project> findPublished() {
        String sql = "SELECT * FROM projects WHERE is_published = true ORDER BY display_order, created_at DESC";
        List<Project> projects = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                projects.add(mapResultSetToProject(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding published projects", e);
        }

        return projects;
    }

    public List<Project> findFeatured() {
        String sql = "SELECT * FROM projects WHERE is_featured = true AND is_published = true " +
                "ORDER BY display_order, created_at DESC";
        List<Project> projects = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                projects.add(mapResultSetToProject(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding featured projects", e);
        }

        return projects;
    }

    public List<Project> findByType(String projectType) {
        String sql = "SELECT * FROM projects WHERE project_type = ? AND is_published = true " +
                "ORDER BY display_order, created_at DESC";
        List<Project> projects = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, projectType);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                projects.add(mapResultSetToProject(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding projects by type: " + projectType, e);
        }

        return projects;
    }

    public List<String> findProjectTypes() {
        String sql = "SELECT DISTINCT project_type FROM projects WHERE is_published = true AND project_type IS NOT NULL ORDER BY project_type";
        List<String> projectTypes = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                projectTypes.add(rs.getString("project_type"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding project types", e);
        }

        return projectTypes;
    }

    public Project save(Project project) {
        if (project.getId() == 0) {
            return insert(project);
        } else {
            return update(project);
        }
    }

    private Project insert(Project project) {
        String sql = "INSERT INTO projects (title, slug, short_description, full_description, " +
                "project_type, status, start_date, end_date, client_name, project_url, " +
                "github_url, demo_url, featured_image_url, is_featured, is_published, " +
                "display_order) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            setProjectParameters(stmt, project);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating project failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    project.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating project failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting project", e);
        }

        return project;
    }

    private Project update(Project project) {
        String sql = "UPDATE projects SET title = ?, slug = ?, short_description = ?, " +
                "full_description = ?, project_type = ?, status = ?, start_date = ?, " +
                "end_date = ?, client_name = ?, project_url = ?, github_url = ?, " +
                "demo_url = ?, featured_image_url = ?, is_featured = ?, is_published = ?, " +
                "display_order = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            setProjectParameters(stmt, project);
            stmt.setInt(17, project.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating project failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating project", e);
        }

        return project;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM projects WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting project with id: " + id, e);
        }
    }

    public void incrementViewCount(int projectId) {
        String sql = "UPDATE projects SET view_count = view_count + 1 WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, projectId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error incrementing view count for project: " + projectId, e);
        }
    }

    private void setProjectParameters(PreparedStatement stmt, Project project) throws SQLException {
        stmt.setString(1, project.getTitle());
        stmt.setString(2, project.getSlug());
        stmt.setString(3, project.getShortDescription());
        stmt.setString(4, project.getFullDescription());
        stmt.setString(5, project.getProjectType());
        stmt.setString(6, project.getStatus());
        stmt.setDate(7, project.getStartDate() != null ? Date.valueOf(project.getStartDate()) : null);
        stmt.setDate(8, project.getEndDate() != null ? Date.valueOf(project.getEndDate()) : null);
        stmt.setString(9, project.getClientName());
        stmt.setString(10, project.getProjectUrl());
        stmt.setString(11, project.getGithubUrl());
        stmt.setString(12, project.getDemoUrl());
        stmt.setString(13, project.getFeaturedImageUrl());
        stmt.setBoolean(14, project.isFeatured());
        stmt.setBoolean(15, project.isPublished());
        stmt.setInt(16, project.getDisplayOrder());
    }

    private Project mapResultSetToProject(ResultSet rs) throws SQLException {
        Project project = new Project();
        project.setId(rs.getInt("id"));
        project.setTitle(rs.getString("title"));
        project.setSlug(rs.getString("slug"));
        project.setShortDescription(rs.getString("short_description"));
        project.setFullDescription(rs.getString("full_description"));
        project.setProjectType(rs.getString("project_type"));
        project.setStatus(rs.getString("status"));

        Date startDate = rs.getDate("start_date");
        if (startDate != null) {
            project.setStartDate(startDate.toLocalDate());
        }

        Date endDate = rs.getDate("end_date");
        if (endDate != null) {
            project.setEndDate(endDate.toLocalDate());
        }

        project.setClientName(rs.getString("client_name"));
        project.setProjectUrl(rs.getString("project_url"));
        project.setGithubUrl(rs.getString("github_url"));
        project.setDemoUrl(rs.getString("demo_url"));
        project.setFeaturedImageUrl(rs.getString("featured_image_url"));
        project.setFeatured(rs.getBoolean("is_featured"));
        project.setPublished(rs.getBoolean("is_published"));
        project.setViewCount(rs.getInt("view_count"));
        project.setDisplayOrder(rs.getInt("display_order"));
        project.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        project.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());

        return project;
    }
}