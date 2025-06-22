package com.bhupendrasapkota.portfolio.dao;

import com.bhupendrasapkota.portfolio.models.PageView;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PageViewDAO extends BaseDAO {

    public PageView save(PageView pageView) {
        String sql = "INSERT INTO page_views (page_path, referrer, user_agent, ip_address, country, city, device_type) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, pageView.getPagePath());
            stmt.setString(2, pageView.getReferrer());
            stmt.setString(3, pageView.getUserAgent());
            stmt.setString(4, pageView.getIpAddress());
            stmt.setString(5, pageView.getCountry());
            stmt.setString(6, pageView.getCity());
            stmt.setString(7, pageView.getDeviceType());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating page view failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    pageView.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating page view failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting page view", e);
        }

        return pageView;
    }

    public List<PageView> findAll() {
        String sql = "SELECT * FROM page_views ORDER BY created_at DESC";
        List<PageView> pageViews = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                pageViews.add(mapResultSetToPageView(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all page views", e);
        }

        return pageViews;
    }

    public int getViewCountByPath(String pagePath) {
        String sql = "SELECT COUNT(*) FROM page_views WHERE page_path = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pagePath);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting view count for path: " + pagePath, e);
        }

        return 0;
    }

    public List<PageView> findByDateRange(Date startDate, Date endDate) {
        String sql = "SELECT * FROM page_views WHERE DATE(created_at) BETWEEN ? AND ? ORDER BY created_at DESC";
        List<PageView> pageViews = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, startDate);
            stmt.setDate(2, endDate);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                pageViews.add(mapResultSetToPageView(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding page views by date range", e);
        }

        return pageViews;
    }

    private PageView mapResultSetToPageView(ResultSet rs) throws SQLException {
        PageView pageView = new PageView();
        pageView.setId(rs.getInt("id"));
        pageView.setPagePath(rs.getString("page_path"));
        pageView.setReferrer(rs.getString("referrer"));
        pageView.setUserAgent(rs.getString("user_agent"));
        pageView.setIpAddress(rs.getString("ip_address"));
        pageView.setCountry(rs.getString("country"));
        pageView.setCity(rs.getString("city"));
        pageView.setDeviceType(rs.getString("device_type"));
        pageView.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return pageView;
    }
}
