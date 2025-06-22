package com.bhupendrasapkota.portfolio.dao;

import com.bhupendrasapkota.portfolio.models.Service;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceDAO extends BaseDAO {

    public Service findById(int id) {
        String sql = "SELECT * FROM services WHERE id = ?";
        Service service = null;
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                service = mapResultSetToService(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding service by id: " + id, e);
        }
        
        return service;
    }

    public List<Service> findAll() {
        String sql = "SELECT * FROM services ORDER BY display_order, name";
        List<Service> services = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                services.add(mapResultSetToService(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all services", e);
        }
        
        return services;
    }

    public List<Service> findActive() {
        String sql = "SELECT * FROM services WHERE is_active = true ORDER BY display_order, name";
        List<Service> services = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                services.add(mapResultSetToService(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding active services", e);
        }
        
        return services;
    }

    public List<Service> findFeatured() {
        String sql = "SELECT * FROM services WHERE is_featured = true AND is_active = true ORDER BY display_order, name";
        List<Service> services = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                services.add(mapResultSetToService(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding featured services", e);
        }
        
        return services;
    }

    public Service save(Service service) {
        if (service.getId() == 0) {
            return insert(service);
        } else {
            return update(service);
        }
    }

    private Service insert(Service service) {
        String sql = "INSERT INTO services (name, description, short_description, price_range, " +
                    "duration, icon_name, is_featured, is_active, display_order) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            setServiceParameters(stmt, service);
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating service failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    service.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating service failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting service", e);
        }
        
        return service;
    }

    private Service update(Service service) {
        String sql = "UPDATE services SET name = ?, description = ?, short_description = ?, " +
                    "price_range = ?, duration = ?, icon_name = ?, is_featured = ?, is_active = ?, " +
                    "display_order = ? WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            setServiceParameters(stmt, service);
            stmt.setInt(10, service.getId());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating service failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating service", e);
        }
        
        return service;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM services WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting service with id: " + id, e);
        }
    }

    private void setServiceParameters(PreparedStatement stmt, Service service) throws SQLException {
        stmt.setString(1, service.getName());
        stmt.setString(2, service.getDescription());
        stmt.setString(3, service.getShortDescription());
        stmt.setString(4, service.getPriceRange());
        stmt.setString(5, service.getDuration());
        stmt.setString(6, service.getIconName());
        stmt.setBoolean(7, service.isFeatured());
        stmt.setBoolean(8, service.isActive());
        stmt.setInt(9, service.getDisplayOrder());
    }

    private Service mapResultSetToService(ResultSet rs) throws SQLException {
        Service service = new Service();
        service.setId(rs.getInt("id"));
        service.setName(rs.getString("name"));
        service.setDescription(rs.getString("description"));
        service.setShortDescription(rs.getString("short_description"));
        service.setPriceRange(rs.getString("price_range"));
        service.setDuration(rs.getString("duration"));
        service.setIconName(rs.getString("icon_name"));
        service.setFeatured(rs.getBoolean("is_featured"));
        service.setActive(rs.getBoolean("is_active"));
        service.setDisplayOrder(rs.getInt("display_order"));
        service.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return service;
    }
}
