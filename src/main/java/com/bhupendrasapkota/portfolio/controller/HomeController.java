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

@WebServlet({"/home"})
public class HomeController extends HttpServlet {
    
    private static final Logger logger = Logger.getLogger(HomeController.class.getName());
    
    private PersonalInfoDAO personalInfoDAO;
    private ProjectDAO projectDAO;
    private SkillDAO skillDAO;
    private TestimonialDAO testimonialDAO;
    private ServiceDAO serviceDAO;
    private SocialLinkDAO socialLinkDAO;
    private BlogPostDAO blogPostDAO;
    private WorkExperienceDAO workExperienceDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        personalInfoDAO = new PersonalInfoDAO();
        projectDAO = new ProjectDAO();
        skillDAO = new SkillDAO();
        testimonialDAO = new TestimonialDAO();
        serviceDAO = new ServiceDAO();
        socialLinkDAO = new SocialLinkDAO();
        blogPostDAO = new BlogPostDAO();
        workExperienceDAO = new WorkExperienceDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            // Get personal information
            PersonalInfo personalInfo = personalInfoDAO.findFirst();
            request.setAttribute("personalInfo", personalInfo);
            
            // Get featured projects (limit to 6)
            List<Project> featuredProjects = projectDAO.findFeatured();
            if (featuredProjects.size() > 6) {
                featuredProjects = featuredProjects.subList(0, 6);
            }
            request.setAttribute("featuredProjects", featuredProjects);
            
            // Get featured skills (limit to 8)
            List<Skill> featuredSkills = skillDAO.findFeatured();
            if (featuredSkills.size() > 8) {
                featuredSkills = featuredSkills.subList(0, 8);
            }
            request.setAttribute("featuredSkills", featuredSkills);
            
            // Get featured testimonials (limit to 3)
            List<Testimonial> featuredTestimonials = testimonialDAO.findFeatured();
            if (featuredTestimonials.size() > 3) {
                featuredTestimonials = featuredTestimonials.subList(0, 3);
            }
            request.setAttribute("featuredTestimonials", featuredTestimonials);
            
            // Get featured services (limit to 4)
            List<Service> featuredServices = serviceDAO.findFeatured();
            if (featuredServices.size() > 4) {
                featuredServices = featuredServices.subList(0, 4);
            }
            request.setAttribute("featuredServices", featuredServices);
            
            // Get social links
            List<SocialLink> socialLinks = socialLinkDAO.findActive();
            request.setAttribute("socialLinks", socialLinks);
            
            // Get latest blog posts (limit 4)
            List<BlogPost> latestBlogPosts = blogPostDAO.findPublished();
            if (latestBlogPosts.size() > 4) {
                latestBlogPosts = latestBlogPosts.subList(0, 4);
            }
            request.setAttribute("latestBlogPosts", latestBlogPosts);
            
            // Get work experiences (limit 4)
            List<WorkExperience> workExperiences = workExperienceDAO.findAll();
            if (workExperiences.size() > 4) {
                workExperiences = workExperiences.subList(0, 4);
            }
            request.setAttribute("workExperiences", workExperiences);
            
            // Forward to home page
            request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in HomeController: " + e.getMessage(), e);
            request.setAttribute("error", "Unable to load home page data");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
