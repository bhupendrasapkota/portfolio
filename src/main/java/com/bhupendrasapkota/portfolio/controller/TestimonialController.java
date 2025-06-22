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

@WebServlet("/testimonials")
public class TestimonialController extends HttpServlet {
    
    private static final Logger logger = Logger.getLogger(TestimonialController.class.getName());
    
    private TestimonialDAO testimonialDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        testimonialDAO = new TestimonialDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            List<Testimonial> testimonials = testimonialDAO.findPublished();
            request.setAttribute("testimonials", testimonials);
            
            List<Testimonial> featuredTestimonials = testimonialDAO.findFeatured();
            request.setAttribute("featuredTestimonials", featuredTestimonials);
            
            request.getRequestDispatcher("/WEB-INF/views/testimonials.jsp").forward(request, response);
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in TestimonialController: " + e.getMessage(), e);
            request.setAttribute("error", "Unable to load testimonials data");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
