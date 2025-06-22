package com.bhupendrasapkota.portfolio.dao;

import com.bhupendrasapkota.portfolio.models.ContactSubmission;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactSubmissionDAO extends BaseDAO {

    public ContactSubmission findById(int id) {
        String sql = "SELECT * FROM contact_submissions WHERE id = ?";
        ContactSubmission submission = null;
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                submission = mapResultSetToContactSubmission(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding contact submission by id: " + id, e);
        }
        
        return submission;
    }

    public List<ContactSubmission> findAll() {
        String sql = "SELECT * FROM contact_submissions ORDER BY created_at DESC";
        List<ContactSubmission> submissions = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                submissions.add(mapResultSetToContactSubmission(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all contact submissions", e);
        }
        
        return submissions;
    }

    public List<ContactSubmission> findUnread() {
        String sql = "SELECT * FROM contact_submissions WHERE is_read = false ORDER BY created_at DESC";
        List<ContactSubmission> submissions = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                submissions.add(mapResultSetToContactSubmission(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding unread contact submissions", e);
        }
        
        return submissions;
    }

    public List<ContactSubmission> findUnreplied() {
        String sql = "SELECT * FROM contact_submissions WHERE is_replied = false ORDER BY created_at DESC";
        List<ContactSubmission> submissions = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                submissions.add(mapResultSetToContactSubmission(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding unreplied contact submissions", e);
        }
        
        return submissions;
    }

    public ContactSubmission save(ContactSubmission submission) {
        if (submission.getId() == 0) {
            return insert(submission);
        } else {
            return update(submission);
        }
    }

    private ContactSubmission insert(ContactSubmission submission) {
        String sql = "INSERT INTO contact_submissions (name, email, subject, message, phone, " +
                    "company, project_budget, project_timeline, ip_address, user_agent) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            setContactSubmissionParameters(stmt, submission);
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating contact submission failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    submission.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating contact submission failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting contact submission", e);
        }
        
        return submission;
    }

    private ContactSubmission update(ContactSubmission submission) {
        String sql = "UPDATE contact_submissions SET name = ?, email = ?, subject = ?, message = ?, " +
                    "phone = ?, company = ?, project_budget = ?, project_timeline = ?, " +
                    "is_read = ?, is_replied = ? WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, submission.getName());
            stmt.setString(2, submission.getEmail());
            stmt.setString(3, submission.getSubject());
            stmt.setString(4, submission.getMessage());
            stmt.setString(5, submission.getPhone());
            stmt.setString(6, submission.getCompany());
            stmt.setString(7, submission.getProjectBudget());
            stmt.setString(8, submission.getProjectTimeline());
            stmt.setBoolean(9, submission.isRead());
            stmt.setBoolean(10, submission.isReplied());
            stmt.setInt(11, submission.getId());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating contact submission failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating contact submission", e);
        }
        
        return submission;
    }

    public boolean markAsRead(int id) {
        String sql = "UPDATE contact_submissions SET is_read = true WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error marking contact submission as read: " + id, e);
        }
    }

    public boolean markAsReplied(int id) {
        String sql = "UPDATE contact_submissions SET is_replied = true WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error marking contact submission as replied: " + id, e);
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM contact_submissions WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting contact submission with id: " + id, e);
        }
    }

    private void setContactSubmissionParameters(PreparedStatement stmt, ContactSubmission submission) throws SQLException {
        stmt.setString(1, submission.getName());
        stmt.setString(2, submission.getEmail());
        stmt.setString(3, submission.getSubject());
        stmt.setString(4, submission.getMessage());
        stmt.setString(5, submission.getPhone());
        stmt.setString(6, submission.getCompany());
        stmt.setString(7, submission.getProjectBudget());
        stmt.setString(8, submission.getProjectTimeline());
        stmt.setString(9, submission.getIpAddress());
        stmt.setString(10, submission.getUserAgent());
    }

    private ContactSubmission mapResultSetToContactSubmission(ResultSet rs) throws SQLException {
        ContactSubmission submission = new ContactSubmission();
        submission.setId(rs.getInt("id"));
        submission.setName(rs.getString("name"));
        submission.setEmail(rs.getString("email"));
        submission.setSubject(rs.getString("subject"));
        submission.setMessage(rs.getString("message"));
        submission.setPhone(rs.getString("phone"));
        submission.setCompany(rs.getString("company"));
        submission.setProjectBudget(rs.getString("project_budget"));
        submission.setProjectTimeline(rs.getString("project_timeline"));
        submission.setRead(rs.getBoolean("is_read"));
        submission.setReplied(rs.getBoolean("is_replied"));
        submission.setIpAddress(rs.getString("ip_address"));
        submission.setUserAgent(rs.getString("user_agent"));
        submission.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return submission;
    }
}
