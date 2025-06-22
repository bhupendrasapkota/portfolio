package com.bhupendrasapkota.portfolio.dao;

import com.bhupendrasapkota.portfolio.models.WorkExperience;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkExperienceDAO extends BaseDAO {

    public WorkExperience findById(int id) {
        String sql = "SELECT * FROM work_experience WHERE id = ?";
        WorkExperience workExperience = null;
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                workExperience = mapResultSetToWorkExperience(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding work experience by id: " + id, e);
        }
        
        return workExperience;
    }

    public List<WorkExperience> findAll() {
        String sql = "SELECT * FROM work_experience ORDER BY display_order, start_date DESC";
        List<WorkExperience> workExperiences = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                workExperiences.add(mapResultSetToWorkExperience(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all work experiences", e);
        }
        
        return workExperiences;
    }

    public WorkExperience findCurrent() {
        String sql = "SELECT * FROM work_experience WHERE is_current = true ORDER BY start_date DESC LIMIT 1";
        WorkExperience workExperience = null;
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                workExperience = mapResultSetToWorkExperience(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding current work experience", e);
        }
        
        return workExperience;
    }

    public WorkExperience save(WorkExperience workExperience) {
        if (workExperience.getId() == 0) {
            return insert(workExperience);
        } else {
            return update(workExperience);
        }
    }

    private WorkExperience insert(WorkExperience workExperience) {
        String sql = "INSERT INTO work_experience (company_name, position, employment_type, location, " +
                    "start_date, end_date, is_current, description, company_url, company_logo_url, " +
                    "display_order) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            setWorkExperienceParameters(stmt, workExperience);
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating work experience failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    workExperience.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating work experience failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting work experience", e);
        }
        
        return workExperience;
    }

    private WorkExperience update(WorkExperience workExperience) {
        String sql = "UPDATE work_experience SET company_name = ?, position = ?, employment_type = ?, " +
                    "location = ?, start_date = ?, end_date = ?, is_current = ?, description = ?, " +
                    "company_url = ?, company_logo_url = ?, display_order = ? WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            setWorkExperienceParameters(stmt, workExperience);
            stmt.setInt(12, workExperience.getId());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating work experience failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating work experience", e);
        }
        
        return workExperience;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM work_experience WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting work experience with id: " + id, e);
        }
    }

    private void setWorkExperienceParameters(PreparedStatement stmt, WorkExperience workExperience) throws SQLException {
        stmt.setString(1, workExperience.getCompanyName());
        stmt.setString(2, workExperience.getPosition());
        stmt.setString(3, workExperience.getEmploymentType());
        stmt.setString(4, workExperience.getLocation());
        stmt.setDate(5, workExperience.getStartDate() != null ? Date.valueOf(workExperience.getStartDate()) : null);
        stmt.setDate(6, workExperience.getEndDate() != null ? Date.valueOf(workExperience.getEndDate()) : null);
        stmt.setBoolean(7, workExperience.isCurrent());
        stmt.setString(8, workExperience.getDescription());
        stmt.setString(9, workExperience.getCompanyUrl());
        stmt.setString(10, workExperience.getCompanyLogoUrl());
        stmt.setInt(11, workExperience.getDisplayOrder());
    }

    private WorkExperience mapResultSetToWorkExperience(ResultSet rs) throws SQLException {
        WorkExperience workExperience = new WorkExperience();
        workExperience.setId(rs.getInt("id"));
        workExperience.setCompanyName(rs.getString("company_name"));
        workExperience.setPosition(rs.getString("position"));
        workExperience.setEmploymentType(rs.getString("employment_type"));
        workExperience.setLocation(rs.getString("location"));
        
        Date startDate = rs.getDate("start_date");
        if (startDate != null) {
            workExperience.setStartDate(startDate.toLocalDate());
        }
        
        Date endDate = rs.getDate("end_date");
        if (endDate != null) {
            workExperience.setEndDate(endDate.toLocalDate());
        }
        
        workExperience.setCurrent(rs.getBoolean("is_current"));
        workExperience.setDescription(rs.getString("description"));
        workExperience.setCompanyUrl(rs.getString("company_url"));
        workExperience.setCompanyLogoUrl(rs.getString("company_logo_url"));
        workExperience.setDisplayOrder(rs.getInt("display_order"));
        workExperience.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        
        return workExperience;
    }
}
