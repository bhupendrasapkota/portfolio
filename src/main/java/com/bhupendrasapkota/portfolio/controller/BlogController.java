package com.bhupendrasapkota.portfolio.controller;

import com.bhupendrasapkota.portfolio.dao.*;
import com.bhupendrasapkota.portfolio.models.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

@WebServlet("/blog/*")
public class BlogController extends HttpServlet {
    
    private static final Logger logger = Logger.getLogger(BlogController.class.getName());
    
    private BlogPostDAO blogPostDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        blogPostDAO = new BlogPostDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                showAllBlogPosts(request, response);
            } else {
                String slug = pathInfo.substring(1);
                showBlogPostBySlug(request, response, slug);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in BlogController: " + e.getMessage(), e);
            request.setAttribute("error", "Unable to load blog data");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
    
    private void showAllBlogPosts(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String tag = request.getParameter("tag");
        List<BlogPost> blogPosts;
        
        if (tag != null && !tag.isEmpty()) {
            blogPosts = blogPostDAO.findByTag(tag);
            request.setAttribute("selectedTag", tag);
        } else {
            blogPosts = blogPostDAO.findPublished();
        }
        
        List<BlogPost> featuredPosts = blogPostDAO.findFeatured();
        if (featuredPosts.size() > 5) {
            featuredPosts = featuredPosts.subList(0, 5);
        }
        
        request.setAttribute("blogPosts", blogPosts);
        request.setAttribute("featuredPosts", featuredPosts);
        request.getRequestDispatcher("/WEB-INF/views/blog/list.jsp").forward(request, response);
    }
    
    private void showBlogPostBySlug(HttpServletRequest request, HttpServletResponse response, String slug) 
            throws ServletException, IOException {
        
        BlogPost blogPost = blogPostDAO.findBySlug(slug);
        
        if (blogPost == null || !blogPost.isPublished()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        blogPostDAO.incrementViewCount(blogPost.getId());
        
        List<BlogPost> relatedPosts = blogPostDAO.findFeatured();
        if (relatedPosts.size() > 3) {
            relatedPosts = relatedPosts.subList(0, 3);
        }
        
        request.setAttribute("blogPost", blogPost);
        request.setAttribute("relatedPosts", relatedPosts);
        request.getRequestDispatcher("/WEB-INF/views/blog/detail.jsp").forward(request, response);
    }
}
