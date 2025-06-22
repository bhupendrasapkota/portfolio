package com.bhupendrasapkota.portfolio.dao;

import com.bhupendrasapkota.portfolio.models.SocialLink;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SocialLinkDAO extends BaseDAO {

    public SocialLink findById(int id) {
        String sql = "SELECT * FROM social_links WHERE id = ?";
        SocialLink socialLink = null;
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                socialLink = mapResultSetToSocialLink(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding social link by id: " + id, e);
        }
        
        return socialLink;
    }

    public List<SocialLink> findAll() {
        String sql = "SELECT * FROM social_links ORDER BY display_order, id";
        List<SocialLink> socialLinks = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                socialLinks.add(mapResultSetToSocialLink(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all social links", e);
        }
        
        return socialLinks;
    }

    public List<SocialLink> findActive() {
        String sql = "SELECT * FROM social_links WHERE is_active = true ORDER BY display_order, id";
        List<SocialLink> socialLinks = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                socialLinks.add(mapResultSetToSocialLink(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding active social links", e);
        }
        
        return socialLinks;
    }

    public SocialLink save(SocialLink socialLink) {
        if (socialLink.getId() == 0) {
            return insert(socialLink);
        } else {
            return update(socialLink);
        }
    }

    private SocialLink insert(SocialLink socialLink) {
        String sql = "INSERT INTO social_links (platform, url, icon_name, display_order, is_active) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            setSocialLinkParameters(stmt, socialLink);
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating social link failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    socialLink.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating social link failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting social link", e);
        }
        
        return socialLink;
    }

    private SocialLink update(SocialLink socialLink) {
        String sql = "UPDATE social_links SET platform = ?, url = ?, icon_name = ?, display_order = ?, is_active = ? WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            setSocialLinkParameters(stmt, socialLink);
            stmt.setInt(6, socialLink.getId());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating social link failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating social link", e);
        }
        
        return socialLink;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM social_links WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting social link with id: " + id, e);
        }
    }

    private void setSocialLinkParameters(PreparedStatement stmt, SocialLink socialLink) throws SQLException {
        stmt.setString(1, socialLink.getPlatform());
        stmt.setString(2, socialLink.getUrl());
        stmt.setString(3, socialLink.getIconName());
        stmt.setInt(4, socialLink.getDisplayOrder());
        stmt.setBoolean(5, socialLink.isActive());
    }

    private SocialLink mapResultSetToSocialLink(ResultSet rs) throws SQLException {
        SocialLink socialLink = new SocialLink();
        socialLink.setId(rs.getInt("id"));
        socialLink.setPlatform(rs.getString("platform"));
        socialLink.setUrl(rs.getString("url"));
        socialLink.setIconName(rs.getString("icon_name"));
        socialLink.setDisplayOrder(rs.getInt("display_order"));
        socialLink.setActive(rs.getBoolean("is_active"));
        socialLink.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return socialLink;
    }
}
