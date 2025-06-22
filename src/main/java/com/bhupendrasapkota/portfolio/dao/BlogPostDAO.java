package com.bhupendrasapkota.portfolio.dao;

import com.bhupendrasapkota.portfolio.models.BlogPost;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BlogPostDAO extends BaseDAO {

    public BlogPost findById(int id) {
        String sql = "SELECT * FROM blog_posts WHERE id = ?";
        BlogPost blogPost = null;
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                blogPost = mapResultSetToBlogPost(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding blog post by id: " + id, e);
        }
        
        return blogPost;
    }

    public BlogPost findBySlug(String slug) {
        String sql = "SELECT * FROM blog_posts WHERE slug = ?";
        BlogPost blogPost = null;
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, slug);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                blogPost = mapResultSetToBlogPost(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding blog post by slug: " + slug, e);
        }
        
        return blogPost;
    }

    public List<BlogPost> findAll() {
        String sql = "SELECT * FROM blog_posts ORDER BY created_at DESC";
        List<BlogPost> blogPosts = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                blogPosts.add(mapResultSetToBlogPost(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all blog posts", e);
        }
        
        return blogPosts;
    }

    public List<BlogPost> findPublished() {
        String sql = "SELECT * FROM blog_posts WHERE is_published = true ORDER BY published_at DESC";
        List<BlogPost> blogPosts = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                blogPosts.add(mapResultSetToBlogPost(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding published blog posts", e);
        }
        
        return blogPosts;
    }

    public List<BlogPost> findFeatured() {
        String sql = "SELECT * FROM blog_posts WHERE is_featured = true AND is_published = true " +
                    "ORDER BY published_at DESC";
        List<BlogPost> blogPosts = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                blogPosts.add(mapResultSetToBlogPost(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding featured blog posts", e);
        }
        
        return blogPosts;
    }

    public List<BlogPost> findByTag(String tag) {
        String sql = "SELECT * FROM blog_posts WHERE tags LIKE ? AND is_published = true " +
                    "ORDER BY published_at DESC";
        List<BlogPost> blogPosts = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + tag + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                blogPosts.add(mapResultSetToBlogPost(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding blog posts by tag: " + tag, e);
        }
        
        return blogPosts;
    }

    public BlogPost save(BlogPost blogPost) {
        if (blogPost.getId() == 0) {
            return insert(blogPost);
        } else {
            return update(blogPost);
        }
    }

    private BlogPost insert(BlogPost blogPost) {
        String sql = "INSERT INTO blog_posts (title, slug, excerpt, content, featured_image_url, " +
                    "is_published, is_featured, read_time_minutes, seo_title, seo_description, " +
                    "tags, published_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            setBlogPostParameters(stmt, blogPost);
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating blog post failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    blogPost.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating blog post failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting blog post", e);
        }
        
        return blogPost;
    }

    private BlogPost update(BlogPost blogPost) {
        String sql = "UPDATE blog_posts SET title = ?, slug = ?, excerpt = ?, content = ?, " +
                    "featured_image_url = ?, is_published = ?, is_featured = ?, read_time_minutes = ?, " +
                    "seo_title = ?, seo_description = ?, tags = ?, published_at = ? WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            setBlogPostParameters(stmt, blogPost);
            stmt.setInt(13, blogPost.getId());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating blog post failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating blog post", e);
        }
        
        return blogPost;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM blog_posts WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting blog post with id: " + id, e);
        }
    }

    public void incrementViewCount(int blogPostId) {
        String sql = "UPDATE blog_posts SET view_count = view_count + 1 WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, blogPostId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error incrementing view count for blog post: " + blogPostId, e);
        }
    }

    private void setBlogPostParameters(PreparedStatement stmt, BlogPost blogPost) throws SQLException {
        stmt.setString(1, blogPost.getTitle());
        stmt.setString(2, blogPost.getSlug());
        stmt.setString(3, blogPost.getExcerpt());
        stmt.setString(4, blogPost.getContent());
        stmt.setString(5, blogPost.getFeaturedImageUrl());
        stmt.setBoolean(6, blogPost.isPublished());
        stmt.setBoolean(7, blogPost.isFeatured());
        if (blogPost.getReadTimeMinutes() != null) {
            stmt.setInt(8, blogPost.getReadTimeMinutes());
        } else {
            stmt.setNull(8, Types.INTEGER);
        }
        stmt.setString(9, blogPost.getSeoTitle());
        stmt.setString(10, blogPost.getSeoDescription());
        stmt.setString(11, blogPost.getTags());
        if (blogPost.getPublishedAt() != null) {
            stmt.setTimestamp(12, Timestamp.valueOf(blogPost.getPublishedAt()));
        } else {
            stmt.setNull(12, Types.TIMESTAMP);
        }
    }

    private BlogPost mapResultSetToBlogPost(ResultSet rs) throws SQLException {
        BlogPost blogPost = new BlogPost();
        blogPost.setId(rs.getInt("id"));
        blogPost.setTitle(rs.getString("title"));
        blogPost.setSlug(rs.getString("slug"));
        blogPost.setExcerpt(rs.getString("excerpt"));
        blogPost.setContent(rs.getString("content"));
        blogPost.setFeaturedImageUrl(rs.getString("featured_image_url"));
        blogPost.setPublished(rs.getBoolean("is_published"));
        blogPost.setFeatured(rs.getBoolean("is_featured"));
        blogPost.setViewCount(rs.getInt("view_count"));
        
        int readTime = rs.getInt("read_time_minutes");
        if (!rs.wasNull()) {
            blogPost.setReadTimeMinutes(readTime);
        }
        
        blogPost.setSeoTitle(rs.getString("seo_title"));
        blogPost.setSeoDescription(rs.getString("seo_description"));
        blogPost.setTags(rs.getString("tags"));
        
        Timestamp publishedAt = rs.getTimestamp("published_at");
        if (publishedAt != null) {
            blogPost.setPublishedAt(publishedAt.toLocalDateTime());
        }
        
        blogPost.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        blogPost.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        
        return blogPost;
    }
}
