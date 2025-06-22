package com.bhupendrasapkota.portfolio.controller;

import com.bhupendrasapkota.portfolio.dao.*;
import com.bhupendrasapkota.portfolio.models.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/contact")
public class ContactController extends HttpServlet {
    
    private ContactSubmissionDAO contactDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        contactDAO = new ContactSubmissionDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.getRequestDispatcher("/WEB-INF/views/contact.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            // Get form parameters
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String subject = request.getParameter("subject");
            String message = request.getParameter("message");
            String phone = request.getParameter("phone");
            String company = request.getParameter("company");
            String projectBudget = request.getParameter("project_budget");
            String projectTimeline = request.getParameter("project_timeline");
            
            // Validate required fields
            if (name == null || name.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                message == null || message.trim().isEmpty()) {
                
                request.setAttribute("error", "Please fill in all required fields.");
                request.getRequestDispatcher("/WEB-INF/views/contact.jsp").forward(request, response);
                return;
            }
            
            // Create contact submission
            ContactSubmission submission = new ContactSubmission();
            submission.setName(name.trim());
            submission.setEmail(email.trim());
            submission.setSubject(subject != null ? subject.trim() : "");
            submission.setMessage(message.trim());
            submission.setPhone(phone != null ? phone.trim() : "");
            submission.setCompany(company != null ? company.trim() : "");
            submission.setProjectBudget(projectBudget);
            submission.setProjectTimeline(projectTimeline);
            submission.setIpAddress(getClientIpAddress(request));
            submission.setUserAgent(request.getHeader("User-Agent"));
            
            // Save to database
            contactDAO.save(submission);
            
            // Set success message and redirect
            request.setAttribute("success", "Thank you for your message! I'll get back to you soon.");
            request.getRequestDispatcher("/WEB-INF/views/contact.jsp").forward(request, response);
            
        } catch (Exception e) {
            request.setAttribute("error", "Sorry, there was an error sending your message. Please try again.");
            request.getRequestDispatcher("/WEB-INF/views/contact.jsp").forward(request, response);
        }
    }
    
    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        
        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty()) {
            return xRealIp;
        }
        
        return request.getRemoteAddr();
    }
}
