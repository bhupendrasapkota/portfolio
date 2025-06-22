package com.bhupendrasapkota.portfolio.dao;

import com.bhupendrasapkota.portfolio.models.WorkAchievement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkAchievementDAO extends BaseDAO {

    public WorkAchievement findById(int id) {
        String sql = "SELECT * FROM work_achievements WHERE id = ?";
        WorkAchievement workAchievement = null;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                workAchievement = mapResultSetToWorkAchievement(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding work achievement by id: " + id, e);
        }

        return workAchievement;
    }

    public List<WorkAchievement> findByWorkExperienceId(int workExperienceId) {
        String sql = "SELECT * FROM work_achievements WHERE work_experience_id = ? ORDER BY display_order, id";
        List<WorkAchievement> workAchievements = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, workExperienceId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                workAchievements.add(mapResultSetToWorkAchievement(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding work achievements by work experience id: " + workExperienceId, e);
        }

        return workAchievements;
    }

    public WorkAchievement save(WorkAchievement workAchievement) {
        if (workAchievement.getId() == 0) {
            return insert(workAchievement);
        } else {
            return update(workAchievement);
        }
    }

    private WorkAchievement insert(WorkAchievement workAchievement) {
        String sql = "INSERT INTO work_achievements (work_experience_id, description, display_order) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, workAchievement.getWorkExperienceId());
            stmt.setString(2, workAchievement.getDescription());
            stmt.setInt(3, workAchievement.getDisplayOrder());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating work achievement failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    workAchievement.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating work achievement failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting work achievement", e);
        }

        return workAchievement;
    }

    private WorkAchievement update(WorkAchievement workAchievement) {
        String sql = "UPDATE work_achievements SET work_experience_id = ?, description = ?, display_order = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, workAchievement.getWorkExperienceId());
            stmt.setString(2, workAchievement.getDescription());
            stmt.setInt(3, workAchievement.getDisplayOrder());
            stmt.setInt(4, workAchievement.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating work achievement failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating work achievement", e);
        }

        return workAchievement;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM work_achievements WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting work achievement with id: " + id, e);
        }
    }

    public boolean deleteByWorkExperienceId(int workExperienceId) {
        String sql = "DELETE FROM work_achievements WHERE work_experience_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, workExperienceId);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting work achievements for work experience: " + workExperienceId, e);
        }
    }

    private WorkAchievement mapResultSetToWorkAchievement(ResultSet rs) throws SQLException {
        WorkAchievement workAchievement = new WorkAchievement();
        workAchievement.setId(rs.getInt("id"));
        workAchievement.setWorkExperienceId(rs.getInt("work_experience_id"));
        workAchievement.setDescription(rs.getString("description"));
        workAchievement.setDisplayOrder(rs.getInt("display_order"));
        return workAchievement;
    }
}
