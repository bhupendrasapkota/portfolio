package com.bhupendrasapkota.portfolio.dao;

import com.bhupendrasapkota.portfolio.models.Certification;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CertificationDAO extends BaseDAO {

    public Certification findById(int id) {
        String sql = "SELECT * FROM certifications WHERE id = ?";
        Certification certification = null;
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                certification = mapResultSetToCertification(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding certification by id: " + id, e);
        }
        
        return certification;
    }

    public List<Certification> findAll() {
        String sql = "SELECT * FROM certifications ORDER BY display_order, issue_date DESC";
        List<Certification> certifications = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                certifications.add(mapResultSetToCertification(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all certifications", e);
        }
        
        return certifications;
    }

    public List<Certification> findActive() {
        String sql = "SELECT * FROM certifications WHERE is_active = true ORDER BY display_order, issue_date DESC";
        List<Certification> certifications = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                certifications.add(mapResultSetToCertification(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding active certifications", e);
        }
        
        return certifications;
    }

    public Certification save(Certification certification) {
        if (certification.getId() == 0) {
            return insert(certification);
        } else {
            return update(certification);
        }
    }

    private Certification insert(Certification certification) {
        String sql = "INSERT INTO certifications (name, issuing_organization, issue_date, expiration_date, " +
                    "credential_id, credential_url, badge_image_url, is_active, display_order) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            setCertificationParameters(stmt, certification);
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating certification failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    certification.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating certification failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting certification", e);
        }
        
        return certification;
    }

    private Certification update(Certification certification) {
        String sql = "UPDATE certifications SET name = ?, issuing_organization = ?, issue_date = ?, " +
                    "expiration_date = ?, credential_id = ?, credential_url = ?, badge_image_url = ?, " +
                    "is_active = ?, display_order = ? WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            setCertificationParameters(stmt, certification);
            stmt.setInt(10, certification.getId());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating certification failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating certification", e);
        }
        
        return certification;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM certifications WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting certification with id: " + id, e);
        }
    }

    private void setCertificationParameters(PreparedStatement stmt, Certification certification) throws SQLException {
        stmt.setString(1, certification.getName());
        stmt.setString(2, certification.getIssuingOrganization());
        stmt.setDate(3, certification.getIssueDate() != null ? Date.valueOf(certification.getIssueDate()) : null);
        stmt.setDate(4, certification.getExpirationDate() != null ? Date.valueOf(certification.getExpirationDate()) : null);
        stmt.setString(5, certification.getCredentialId());
        stmt.setString(6, certification.getCredentialUrl());
        stmt.setString(7, certification.getBadgeImageUrl());
        stmt.setBoolean(8, certification.isActive());
        stmt.setInt(9, certification.getDisplayOrder());
    }

    private Certification mapResultSetToCertification(ResultSet rs) throws SQLException {
        Certification certification = new Certification();
        certification.setId(rs.getInt("id"));
        certification.setName(rs.getString("name"));
        certification.setIssuingOrganization(rs.getString("issuing_organization"));
        
        Date issueDate = rs.getDate("issue_date");
        if (issueDate != null) {
            certification.setIssueDate(issueDate.toLocalDate());
        }
        
        Date expirationDate = rs.getDate("expiration_date");
        if (expirationDate != null) {
            certification.setExpirationDate(expirationDate.toLocalDate());
        }
        
        certification.setCredentialId(rs.getString("credential_id"));
        certification.setCredentialUrl(rs.getString("credential_url"));
        certification.setBadgeImageUrl(rs.getString("badge_image_url"));
        certification.setActive(rs.getBoolean("is_active"));
        certification.setDisplayOrder(rs.getInt("display_order"));
        certification.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        
        return certification;
    }
}
