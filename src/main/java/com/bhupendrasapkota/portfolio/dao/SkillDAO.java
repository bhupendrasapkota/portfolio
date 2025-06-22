package com.bhupendrasapkota.portfolio.dao;

import com.bhupendrasapkota.portfolio.models.Skill;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SkillDAO extends BaseDAO {

    public Skill findById(int id) {
        String sql = "SELECT * FROM skills WHERE id = ?";
        Skill skill = null;
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                skill = mapResultSetToSkill(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding skill by id: " + id, e);
        }
        
        return skill;
    }

    public List<Skill> findAll() {
        String sql = "SELECT * FROM skills ORDER BY display_order, name";
        List<Skill> skills = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                skills.add(mapResultSetToSkill(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all skills", e);
        }
        
        return skills;
    }

    public List<Skill> findByCategory(String category) {
        String sql = "SELECT * FROM skills WHERE category = ? ORDER BY display_order, name";
        List<Skill> skills = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, category);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                skills.add(mapResultSetToSkill(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding skills by category: " + category, e);
        }
        
        return skills;
    }

    public List<Skill> findFeatured() {
        String sql = "SELECT * FROM skills WHERE is_featured = true ORDER BY display_order, name";
        List<Skill> skills = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                skills.add(mapResultSetToSkill(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding featured skills", e);
        }
        
        return skills;
    }

    public List<String> findAllCategories() {
        String sql = "SELECT DISTINCT category FROM skills ORDER BY category";
        List<String> categories = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                categories.add(rs.getString("category"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding skill categories", e);
        }
        
        return categories;
    }

    public List<Skill> findByProjectId(int projectId) {
        String sql = "SELECT s.* FROM skills s " +
                    "INNER JOIN project_technologies pt ON s.id = pt.skill_id " +
                    "WHERE pt.project_id = ? ORDER BY s.name";
        List<Skill> skills = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, projectId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                skills.add(mapResultSetToSkill(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding skills by project id: " + projectId, e);
        }
        
        return skills;
    }

    public Skill save(Skill skill) {
        if (skill.getId() == 0) {
            return insert(skill);
        } else {
            return update(skill);
        }
    }

    private Skill insert(Skill skill) {
        String sql = "INSERT INTO skills (name, category, proficiency_level, years_of_experience, " +
                    "icon_name, color, is_featured, display_order) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            setSkillParameters(stmt, skill);
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating skill failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    skill.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating skill failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting skill", e);
        }
        
        return skill;
    }

    private Skill update(Skill skill) {
        String sql = "UPDATE skills SET name = ?, category = ?, proficiency_level = ?, " +
                    "years_of_experience = ?, icon_name = ?, color = ?, is_featured = ?, " +
                    "display_order = ? WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            setSkillParameters(stmt, skill);
            stmt.setInt(9, skill.getId());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating skill failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating skill", e);
        }
        
        return skill;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM skills WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting skill with id: " + id, e);
        }
    }

    private void setSkillParameters(PreparedStatement stmt, Skill skill) throws SQLException {
        stmt.setString(1, skill.getName());
        stmt.setString(2, skill.getCategory());
        stmt.setInt(3, skill.getProficiencyLevel());
        stmt.setBigDecimal(4, skill.getYearsOfExperience());
        stmt.setString(5, skill.getIconName());
        stmt.setString(6, skill.getColor());
        stmt.setBoolean(7, skill.isFeatured());
        stmt.setInt(8, skill.getDisplayOrder());
    }

    private Skill mapResultSetToSkill(ResultSet rs) throws SQLException {
        Skill skill = new Skill();
        skill.setId(rs.getInt("id"));
        skill.setName(rs.getString("name"));
        skill.setCategory(rs.getString("category"));
        skill.setProficiencyLevel(rs.getInt("proficiency_level"));
        skill.setYearsOfExperience(rs.getBigDecimal("years_of_experience"));
        skill.setIconName(rs.getString("icon_name"));
        skill.setColor(rs.getString("color"));
        skill.setFeatured(rs.getBoolean("is_featured"));
        skill.setDisplayOrder(rs.getInt("display_order"));
        skill.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return skill;
    }
}
