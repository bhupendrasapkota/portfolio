package com.bhupendrasapkota.portfolio.controller;

import com.bhupendrasapkota.portfolio.dao.*;
import com.bhupendrasapkota.portfolio.models.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
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
            
            // Calculate average proficiency for Database skills
            List<Skill> databaseSkills = skillsByCategory.getOrDefault("Database", new java.util.ArrayList<>());
            if (!databaseSkills.isEmpty()) {
                double totalProficiency = 0;
                for (Skill skill : databaseSkills) {
                    totalProficiency += skill.getProficiencyLevel();
                }
                double averageProficiency = totalProficiency / databaseSkills.size();
                request.setAttribute("databaseProficiency", (int) Math.round(averageProficiency));
            } else {
                request.setAttribute("databaseProficiency", 0);
            }
            
            // Get work experience with achievements and calculate years of experience
            List<WorkExperience> allWorkExperiences = workExperienceDAO.findAll();
            LocalDate earliestStartDate = null;
            
            // Limit achievements per experience and total
            int totalAchievements = 0;
            final int MAX_ACHIEVEMENTS = 10;
            int totalAchievementCount = 0;
            
            for (WorkExperience workExp : allWorkExperiences) {
                if (earliestStartDate == null || workExp.getStartDate().isBefore(earliestStartDate)) {
                    earliestStartDate = workExp.getStartDate();
                }
                List<WorkAchievement> achievements = workAchievementDAO.findByWorkExperienceId(workExp.getId());
                totalAchievementCount += achievements.size();
                
                if (totalAchievements < MAX_ACHIEVEMENTS) {
                    int remainingSpace = MAX_ACHIEVEMENTS - totalAchievements;
                    if (achievements.size() > remainingSpace) {
                        workExp.setAchievements(achievements.subList(0, remainingSpace));
                        totalAchievements = MAX_ACHIEVEMENTS;
                    } else {
                        workExp.setAchievements(achievements);
                        totalAchievements += achievements.size();
                    }
                } else {
                    workExp.setAchievements(new java.util.ArrayList<>());
                }
            }
            
            if (earliestStartDate != null) {
                int yearsOfExperience = Period.between(earliestStartDate, LocalDate.now()).getYears();
                request.setAttribute("yearsOfExperience", yearsOfExperience);
            } else {
                request.setAttribute("yearsOfExperience", 0);
            }
            
            // Limit work experiences and set flag
            if (allWorkExperiences.size() > 5) {
                request.setAttribute("workExperiences", allWorkExperiences.subList(0, 5));
                request.setAttribute("showMoreWorkExperience", true);
            } else {
                request.setAttribute("workExperiences", allWorkExperiences);
            }

            if (totalAchievementCount > MAX_ACHIEVEMENTS) {
                request.setAttribute("showMoreAchievements", true);
            }
            
            // Get education and limit
            List<Education> allEducations = educationDAO.findAll();
            if (allEducations.size() > 4) {
                request.setAttribute("educations", allEducations.subList(0, 4));
                request.setAttribute("showMoreEducation", true);
            } else {
                request.setAttribute("educations", allEducations);
            }
            
            // Get certifications and limit
            List<Certification> allCertifications = certificationDAO.findActive();
            if (allCertifications.size() > 6) {
                request.setAttribute("certifications", allCertifications.subList(0, 6));
                request.setAttribute("showMoreCertifications", true);
            } else {
                request.setAttribute("certifications", allCertifications);
            }
            
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
