package com.bhupendrasapkota.portfolio.dao;

import com.bhupendrasapkota.portfolio.models.Testimonial;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestimonialDAO extends BaseDAO {

    public Testimonial findById(int id) {
        String sql = "SELECT * FROM testimonials WHERE id = ?";
        Testimonial testimonial = null;
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                testimonial = mapResultSetToTestimonial(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding testimonial by id: " + id, e);
        }
        
        return testimonial;
    }

    public List<Testimonial> findAll() {
        String sql = "SELECT * FROM testimonials ORDER BY display_order, created_at DESC";
        List<Testimonial> testimonials = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                testimonials.add(mapResultSetToTestimonial(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all testimonials", e);
        }
        
        return testimonials;
    }

    public List<Testimonial> findPublished() {
        String sql = "SELECT * FROM testimonials WHERE is_published = true ORDER BY display_order, created_at DESC";
        List<Testimonial> testimonials = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                testimonials.add(mapResultSetToTestimonial(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding published testimonials", e);
        }
        
        return testimonials;
    }

    public List<Testimonial> findFeatured() {
        String sql = "SELECT * FROM testimonials WHERE is_featured = true AND is_published = true " +
                    "ORDER BY display_order, created_at DESC";
        List<Testimonial> testimonials = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                testimonials.add(mapResultSetToTestimonial(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding featured testimonials", e);
        }
        
        return testimonials;
    }

    public List<Testimonial> findByProjectId(int projectId) {
        String sql = "SELECT * FROM testimonials WHERE project_id = ? AND is_published = true " +
                    "ORDER BY display_order, created_at DESC";
        List<Testimonial> testimonials = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, projectId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                testimonials.add(mapResultSetToTestimonial(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding testimonials by project id: " + projectId, e);
        }
        
        return testimonials;
    }

    public Testimonial save(Testimonial testimonial) {
        if (testimonial.getId() == 0) {
            return insert(testimonial);
        } else {
            return update(testimonial);
        }
    }

    private Testimonial insert(Testimonial testimonial) {
        String sql = "INSERT INTO testimonials (client_name, client_position, client_company, " +
                    "client_image_url, testimonial_text, rating, project_id, is_featured, " +
                    "is_published, display_order) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            setTestimonialParameters(stmt, testimonial);
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating testimonial failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    testimonial.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating testimonial failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting testimonial", e);
        }
        
        return testimonial;
    }

    private Testimonial update(Testimonial testimonial) {
        String sql = "UPDATE testimonials SET client_name = ?, client_position = ?, client_company = ?, " +
                    "client_image_url = ?, testimonial_text = ?, rating = ?, project_id = ?, " +
                    "is_featured = ?, is_published = ?, display_order = ? WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            setTestimonialParameters(stmt, testimonial);
            stmt.setInt(11, testimonial.getId());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating testimonial failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating testimonial", e);
        }
        
        return testimonial;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM testimonials WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting testimonial with id: " + id, e);
        }
    }

    private void setTestimonialParameters(PreparedStatement stmt, Testimonial testimonial) throws SQLException {
        stmt.setString(1, testimonial.getClientName());
        stmt.setString(2, testimonial.getClientPosition());
        stmt.setString(3, testimonial.getClientCompany());
        stmt.setString(4, testimonial.getClientImageUrl());
        stmt.setString(5, testimonial.getTestimonialText());
        stmt.setInt(6, testimonial.getRating());
        if (testimonial.getProjectId() != null) {
            stmt.setInt(7, testimonial.getProjectId());
        } else {
            stmt.setNull(7, Types.INTEGER);
        }
        stmt.setBoolean(8, testimonial.isFeatured());
        stmt.setBoolean(9, testimonial.isPublished());
        stmt.setInt(10, testimonial.getDisplayOrder());
    }

    private Testimonial mapResultSetToTestimonial(ResultSet rs) throws SQLException {
        Testimonial testimonial = new Testimonial();
        testimonial.setId(rs.getInt("id"));
        testimonial.setClientName(rs.getString("client_name"));
        testimonial.setClientPosition(rs.getString("client_position"));
        testimonial.setClientCompany(rs.getString("client_company"));
        testimonial.setClientImageUrl(rs.getString("client_image_url"));
        testimonial.setTestimonialText(rs.getString("testimonial_text"));
        testimonial.setRating(rs.getInt("rating"));
        
        int projectId = rs.getInt("project_id");
        if (!rs.wasNull()) {
            testimonial.setProjectId(projectId);
        }
        
        testimonial.setFeatured(rs.getBoolean("is_featured"));
        testimonial.setPublished(rs.getBoolean("is_published"));
        testimonial.setDisplayOrder(rs.getInt("display_order"));
        testimonial.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        
        return testimonial;
    }
}
