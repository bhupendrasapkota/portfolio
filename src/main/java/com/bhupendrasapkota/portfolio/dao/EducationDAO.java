package com.bhupendrasapkota.portfolio.dao;

import com.bhupendrasapkota.portfolio.models.Education;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EducationDAO extends BaseDAO {

    public Education findById(int id) {
        String sql = "SELECT * FROM education WHERE id = ?";
        Education education = null;
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                education = mapResultSetToEducation(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding education by id: " + id, e);
        }
        
        return education;
    }

    public List<Education> findAll() {
        String sql = "SELECT * FROM education ORDER BY display_order, end_date DESC";
        List<Education> educations = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                educations.add(mapResultSetToEducation(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all education records", e);
        }
        
        return educations;
    }

    public Education save(Education education) {
        if (education.getId() == 0) {
            return insert(education);
        } else {
            return update(education);
        }
    }

    private Education insert(Education education) {
        String sql = "INSERT INTO education (institution_name, degree, field_of_study, start_date, " +
                    "end_date, gpa, description, institution_url, institution_logo_url, display_order) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            setEducationParameters(stmt, education);
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating education failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    education.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating education failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting education", e);
        }
        
        return education;
    }

    private Education update(Education education) {
        String sql = "UPDATE education SET institution_name = ?, degree = ?, field_of_study = ?, " +
                    "start_date = ?, end_date = ?, gpa = ?, description = ?, institution_url = ?, " +
                    "institution_logo_url = ?, display_order = ? WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            setEducationParameters(stmt, education);
            stmt.setInt(11, education.getId());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating education failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating education", e);
        }
        
        return education;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM education WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting education with id: " + id, e);
        }
    }

    private void setEducationParameters(PreparedStatement stmt, Education education) throws SQLException {
        stmt.setString(1, education.getInstitutionName());
        stmt.setString(2, education.getDegree());
        stmt.setString(3, education.getFieldOfStudy());
        stmt.setDate(4, education.getStartDate() != null ? Date.valueOf(education.getStartDate()) : null);
        stmt.setDate(5, education.getEndDate() != null ? Date.valueOf(education.getEndDate()) : null);
        stmt.setBigDecimal(6, education.getGpa());
        stmt.setString(7, education.getDescription());
        stmt.setString(8, education.getInstitutionUrl());
        stmt.setString(9, education.getInstitutionLogoUrl());
        stmt.setInt(10, education.getDisplayOrder());
    }

    private Education mapResultSetToEducation(ResultSet rs) throws SQLException {
        Education education = new Education();
        education.setId(rs.getInt("id"));
        education.setInstitutionName(rs.getString("institution_name"));
        education.setDegree(rs.getString("degree"));
        education.setFieldOfStudy(rs.getString("field_of_study"));
        
        Date startDate = rs.getDate("start_date");
        if (startDate != null) {
            education.setStartDate(startDate.toLocalDate());
        }
        
        Date endDate = rs.getDate("end_date");
        if (endDate != null) {
            education.setEndDate(endDate.toLocalDate());
        }
        
        education.setGpa(rs.getBigDecimal("gpa"));
        education.setDescription(rs.getString("description"));
        education.setInstitutionUrl(rs.getString("institution_url"));
        education.setInstitutionLogoUrl(rs.getString("institution_logo_url"));
        education.setDisplayOrder(rs.getInt("display_order"));
        education.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        
        return education;
    }
}
