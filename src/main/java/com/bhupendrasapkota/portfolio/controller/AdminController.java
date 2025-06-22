package com.bhupendrasapkota.portfolio.controller;

import com.bhupendrasapkota.portfolio.dao.*;
import com.bhupendrasapkota.portfolio.models.*;
import com.bhupendrasapkota.portfolio.util.AppConfig;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

@WebServlet("/admin/*")
public class AdminController extends HttpServlet {
    
    private static final Logger logger = Logger.getLogger(AdminController.class.getName());
    
    private ContactSubmissionDAO contactDAO;
    private ProjectDAO projectDAO;
    private BlogPostDAO blogPostDAO;
    private TestimonialDAO testimonialDAO;
    private PersonalInfoDAO personalInfoDAO;
    private SkillDAO skillDAO;
    private ServiceDAO serviceDAO;
    private WorkExperienceDAO workExperienceDAO;
    private EducationDAO educationDAO;
    private CertificationDAO certificationDAO;
    private PageViewDAO pageViewDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        contactDAO = new ContactSubmissionDAO();
        projectDAO = new ProjectDAO();
        blogPostDAO = new BlogPostDAO();
        testimonialDAO = new TestimonialDAO();
        personalInfoDAO = new PersonalInfoDAO();
        skillDAO = new SkillDAO();
        serviceDAO = new ServiceDAO();
        workExperienceDAO = new WorkExperienceDAO();
        educationDAO = new EducationDAO();
        certificationDAO = new CertificationDAO();
        pageViewDAO = new PageViewDAO();
    }
    
    private String getSecretCode() {
        String secretCode = AppConfig.getProperty("admin.secret.code", "BhupendraParu0209P@");
        if ("BhupendraParu0209P@".equals(secretCode)) {
            logger.warning("Using default secret code. Set ADMIN_SECRET_CODE environment variable for production.");
        }
        return secretCode;
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo != null && pathInfo.equals("/secret-access")) {
            showSecretAccess(request, response);
            return;
        }
        
        if (!isAuthenticated(request)) {
            response.sendRedirect(request.getContextPath() + "/admin/secret-access");
            return;
        }
        
        try {
            if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals("/dashboard")) {
                showDashboard(request, response);
            } else if (pathInfo.equals("/contacts")) {
                showContacts(request, response);
            } else if (pathInfo.equals("/projects")) {
                showProjects(request, response);
            } else if (pathInfo.equals("/blog")) {
                showBlogPosts(request, response);
            } else if (pathInfo.equals("/testimonials")) {
                showTestimonials(request, response);
            } else if (pathInfo.equals("/profile")) {
                showProfile(request, response);
            } else if (pathInfo.equals("/skills")) {
                showSkills(request, response);
            } else if (pathInfo.equals("/services")) {
                showServices(request, response);
            } else if (pathInfo.equals("/work-experience")) {
                showWorkExperience(request, response);
            } else if (pathInfo.equals("/education")) {
                showEducation(request, response);
            } else if (pathInfo.equals("/certifications")) {
                showCertifications(request, response);
            } else if (pathInfo.equals("/analytics")) {
                showAnalytics(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in AdminController GET: " + e.getMessage(), e);
            request.setAttribute("error", "Unable to load admin data");
            request.getRequestDispatcher("/WEB-INF/views/admin/error.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        
        try {
            if (pathInfo != null && pathInfo.equals("/authenticate")) {
                handleAuthentication(request, response);
            } else if (pathInfo != null && pathInfo.equals("/logout")) {
                handleLogout(request, response);
            } else if (pathInfo != null && pathInfo.startsWith("/contacts/")) {
                handleContactAction(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in AdminController POST: " + e.getMessage(), e);
            request.setAttribute("error", "Unable to process admin action");
            request.getRequestDispatcher("/WEB-INF/views/admin/error.jsp").forward(request, response);
        }
    }
    
    private void showSecretAccess(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/admin/secret-access.jsp").forward(request, response);
    }
    
    private void handleAuthentication(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String enteredCode = request.getParameter("secretCode");
        
        if (getSecretCode().equals(enteredCode)) {
            HttpSession session = request.getSession();
            session.setAttribute("adminAuthenticated", true);
            session.setAttribute("adminLoginTime", System.currentTimeMillis());
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
        } else {
            request.setAttribute("error", "Invalid secret code. Access denied.");
            request.getRequestDispatcher("/WEB-INF/views/admin/secret-access.jsp").forward(request, response);
        }
    }
    
    private boolean isAuthenticated(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }
        
        Boolean authenticated = (Boolean) session.getAttribute("adminAuthenticated");
        if (authenticated == null || !authenticated) {
            return false;
        }
        
        Long loginTime = (Long) session.getAttribute("adminLoginTime");
        if (loginTime == null || (System.currentTimeMillis() - loginTime) > 24 * 60 * 60 * 1000) {
            session.invalidate();
            return false;
        }
        
        return true;
    }
    
    private void showDashboard(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<ContactSubmission> recentContacts = contactDAO.findUnread();
        List<Project> allProjects = projectDAO.findAll();
        List<BlogPost> allBlogPosts = blogPostDAO.findAll();
        List<Testimonial> allTestimonials = testimonialDAO.findAll();
        
        request.setAttribute("unreadContactsCount", recentContacts.size());
        request.setAttribute("totalProjectsCount", allProjects.size());
        request.setAttribute("totalBlogPostsCount", allBlogPosts.size());
        request.setAttribute("totalTestimonialsCount", allTestimonials.size());
        request.setAttribute("recentContacts", recentContacts.size() > 5 ? recentContacts.subList(0, 5) : recentContacts);
        
        request.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp").forward(request, response);
    }
    
    private void showContacts(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<ContactSubmission> contacts = contactDAO.findAll();
        request.setAttribute("contacts", contacts);
        request.getRequestDispatcher("/WEB-INF/views/admin/contacts.jsp").forward(request, response);
    }
    
    private void showProjects(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<Project> projects = projectDAO.findAll();
        request.setAttribute("projects", projects);
        request.getRequestDispatcher("/WEB-INF/views/admin/projects.jsp").forward(request, response);
    }
    
    private void showBlogPosts(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<BlogPost> blogPosts = blogPostDAO.findAll();
        request.setAttribute("blogPosts", blogPosts);
        request.getRequestDispatcher("/WEB-INF/views/admin/blog.jsp").forward(request, response);
    }
    
    private void showTestimonials(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<Testimonial> testimonials = testimonialDAO.findAll();
        request.setAttribute("testimonials", testimonials);
        request.getRequestDispatcher("/WEB-INF/views/admin/testimonials.jsp").forward(request, response);
    }
    
    private void showProfile(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        PersonalInfo personalInfo = personalInfoDAO.findFirst();
        request.setAttribute("personalInfo", personalInfo);
        request.getRequestDispatcher("/WEB-INF/views/admin/profile.jsp").forward(request, response);
    }
    
    private void showSkills(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<Skill> skills = skillDAO.findAll();
        request.setAttribute("skills", skills);
        request.getRequestDispatcher("/WEB-INF/views/admin/skills.jsp").forward(request, response);
    }
    
    private void showServices(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<Service> services = serviceDAO.findAll();
        request.setAttribute("services", services);
        request.getRequestDispatcher("/WEB-INF/views/admin/services.jsp").forward(request, response);
    }
    
    private void showWorkExperience(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<WorkExperience> workExperiences = workExperienceDAO.findAll();
        request.setAttribute("workExperiences", workExperiences);
        request.getRequestDispatcher("/WEB-INF/views/admin/work-experience.jsp").forward(request, response);
    }
    
    private void showEducation(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<Education> educations = educationDAO.findAll();
        request.setAttribute("educations", educations);
        request.getRequestDispatcher("/WEB-INF/views/admin/education.jsp").forward(request, response);
    }
    
    private void showCertifications(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<Certification> certifications = certificationDAO.findAll();
        request.setAttribute("certifications", certifications);
        request.getRequestDispatcher("/WEB-INF/views/admin/certifications.jsp").forward(request, response);
    }
    
    private void showAnalytics(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<PageView> pageViews = pageViewDAO.findAll();
        request.setAttribute("pageViews", pageViews);
        request.getRequestDispatcher("/WEB-INF/views/admin/analytics.jsp").forward(request, response);
    }
    
    private void handleLogout(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect(request.getContextPath() + "/");
    }
    
    private void handleContactAction(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo.contains("/mark-read/")) {
            int contactId = Integer.parseInt(pathInfo.substring(pathInfo.lastIndexOf("/") + 1));
            contactDAO.markAsRead(contactId);
        } else if (pathInfo.contains("/mark-replied/")) {
            int contactId = Integer.parseInt(pathInfo.substring(pathInfo.lastIndexOf("/") + 1));
            contactDAO.markAsReplied(contactId);
        }
        
        response.sendRedirect(request.getContextPath() + "/admin/contacts");
    }
}
