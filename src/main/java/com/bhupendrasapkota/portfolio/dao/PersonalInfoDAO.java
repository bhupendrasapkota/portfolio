package com.bhupendrasapkota.portfolio.dao;

import com.bhupendrasapkota.portfolio.models.PersonalInfo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonalInfoDAO extends BaseDAO {

    public PersonalInfo findById(int id) {
        String sql = "SELECT * FROM personal_info WHERE id = ?";
        PersonalInfo personalInfo = null;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                personalInfo = mapResultSetToPersonalInfo(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding personal info by id: " + id, e);
        }

        return personalInfo;
    }

    public PersonalInfo findFirst() {
        String sql = "SELECT * FROM personal_info ORDER BY id LIMIT 1";
        PersonalInfo personalInfo = null;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                personalInfo = mapResultSetToPersonalInfo(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding first personal info", e);
        }

        return personalInfo;
    }

    public List<PersonalInfo> findAll() {
        String sql = "SELECT * FROM personal_info ORDER BY id";
        List<PersonalInfo> personalInfoList = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                personalInfoList.add(mapResultSetToPersonalInfo(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all personal info", e);
        }

        return personalInfoList;
    }

    public PersonalInfo save(PersonalInfo personalInfo) {
        if (personalInfo.getId() == 0) {
            return insert(personalInfo);
        } else {
            return update(personalInfo);
        }
    }

    private PersonalInfo insert(PersonalInfo personalInfo) {
        String sql = "INSERT INTO personal_info (full_name, title, bio, location, email, phone, " +
                "website_url, resume_url, profile_image_url, years_of_experience, " +
                "available_for_work, hourly_rate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            setPersonalInfoParameters(stmt, personalInfo);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating personal info failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    personalInfo.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating personal info failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting personal info", e);
        }

        return personalInfo;
    }

    private PersonalInfo update(PersonalInfo personalInfo) {
        String sql = "UPDATE personal_info SET full_name = ?, title = ?, bio = ?, location = ?, " +
                "email = ?, phone = ?, website_url = ?, resume_url = ?, profile_image_url = ?, " +
                "years_of_experience = ?, available_for_work = ?, hourly_rate = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            setPersonalInfoParameters(stmt, personalInfo);
            stmt.setInt(13, personalInfo.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating personal info failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating personal info", e);
        }

        return personalInfo;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM personal_info WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting personal info with id: " + id, e);
        }
    }

    private void setPersonalInfoParameters(PreparedStatement stmt, PersonalInfo personalInfo) throws SQLException {
        stmt.setString(1, personalInfo.getFullName());
        stmt.setString(2, personalInfo.getTitle());
        stmt.setString(3, personalInfo.getBio());
        stmt.setString(4, personalInfo.getLocation());
        stmt.setString(5, personalInfo.getEmail());
        stmt.setString(6, personalInfo.getPhone());
        stmt.setString(7, personalInfo.getWebsiteUrl());
        stmt.setString(8, personalInfo.getResumeUrl());
        stmt.setString(9, personalInfo.getProfileImageUrl());
        stmt.setInt(10, personalInfo.getYearsOfExperience());
        stmt.setBoolean(11, personalInfo.isAvailableForWork());
        stmt.setBigDecimal(12, personalInfo.getHourlyRate());
    }

    private PersonalInfo mapResultSetToPersonalInfo(ResultSet rs) throws SQLException {
        PersonalInfo personalInfo = new PersonalInfo();
        personalInfo.setId(rs.getInt("id"));
        personalInfo.setFullName(rs.getString("full_name"));
        personalInfo.setTitle(rs.getString("title"));
        personalInfo.setBio(rs.getString("bio"));
        personalInfo.setLocation(rs.getString("location"));
        personalInfo.setEmail(rs.getString("email"));
        personalInfo.setPhone(rs.getString("phone"));
        personalInfo.setWebsiteUrl(rs.getString("website_url"));
        personalInfo.setResumeUrl(rs.getString("resume_url"));
        personalInfo.setProfileImageUrl(rs.getString("profile_image_url"));
        personalInfo.setYearsOfExperience(rs.getInt("years_of_experience"));
        personalInfo.setAvailableForWork(rs.getBoolean("available_for_work"));
        personalInfo.setHourlyRate(rs.getBigDecimal("hourly_rate"));
        personalInfo.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        personalInfo.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return personalInfo;
    }
}