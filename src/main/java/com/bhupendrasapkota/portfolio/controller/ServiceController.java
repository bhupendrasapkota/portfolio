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

@WebServlet("/services")
public class ServiceController extends HttpServlet {
    
    private static final Logger logger = Logger.getLogger(ServiceController.class.getName());
    
    private ServiceDAO serviceDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        serviceDAO = new ServiceDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            // Get all active services
            List<Service> services = serviceDAO.findActive();
            request.setAttribute("services", services);
            
            // Get featured services for highlighting
            List<Service> featuredServices = serviceDAO.findFeatured();
            request.setAttribute("featuredServices", featuredServices);
            
            request.getRequestDispatcher("/WEB-INF/views/services.jsp").forward(request, response);
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in ServiceController: " + e.getMessage(), e);
            request.setAttribute("error", "Unable to load services data");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
