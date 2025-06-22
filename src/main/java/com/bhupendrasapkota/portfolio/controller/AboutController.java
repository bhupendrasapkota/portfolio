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
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.logging.Logger;
import java.util.logging.Level;

@WebServlet("/about")
public class AboutController extends HttpServlet {
    
    private static final Logger logger = Logger.getLogger(AboutController.class.getName());
    
    private PersonalInfoDAO personalInfoDAO;
    private SkillDAO skillDAO;
    private WorkExperienceDAO workExperienceDAO;
    private WorkAchievementDAO workAchievementDAO;
    private EducationDAO educationDAO;
    private CertificationDAO certificationDAO;
    private SocialLinkDAO socialLinkDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        personalInfoDAO = new PersonalInfoDAO();
        skillDAO = new SkillDAO();
        workExperienceDAO = new WorkExperienceDAO();
        workAchievementDAO = new WorkAchievementDAO();
        educationDAO = new EducationDAO();
        certificationDAO = new CertificationDAO();
        socialLinkDAO = new SocialLinkDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            // Get personal information
            PersonalInfo personalInfo = personalInfoDAO.findFirst();
            request.setAttribute("personalInfo", personalInfo);
            
            // Get all skills grouped by category
            List<Skill> allSkills = skillDAO.findAll();
            Map<String, List<Skill>> skillsByCategory = new LinkedHashMap<>();
            
            for (Skill skill : allSkills) {
                String category = skill.getCategory();
                if (!skillsByCategory.containsKey(category)) {
                    skillsByCategory.put(category, new java.util.ArrayList<>());
                }
                skillsByCategory.get(category).add(skill);
            }
            
            request.setAttribute("skillsByCategory", skillsByCategory);
            
            // Get work experience with achievements
            List<WorkExperience> workExperiences = workExperienceDAO.findAll();
            for (WorkExperience workExp : workExperiences) {
                List<WorkAchievement> achievements = workAchievementDAO.findByWorkExperienceId(workExp.getId());
                workExp.setAchievements(achievements);
            }
            request.setAttribute("workExperiences", workExperiences);
            
            // Get education
            List<Education> educations = educationDAO.findAll();
            request.setAttribute("educations", educations);
            
            // Get certifications
            List<Certification> certifications = certificationDAO.findActive();
            request.setAttribute("certifications", certifications);
            
            // Get social links
            List<SocialLink> socialLinks = socialLinkDAO.findActive();
            request.setAttribute("socialLinks", socialLinks);
            
            request.getRequestDispatcher("/WEB-INF/views/about.jsp").forward(request, response);
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in AboutController: " + e.getMessage(), e);
            request.setAttribute("error", "Unable to load about page data");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
