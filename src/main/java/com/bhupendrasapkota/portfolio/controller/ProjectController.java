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

@WebServlet("/projects/*")
public class ProjectController extends HttpServlet {

    private static final Logger logger = Logger.getLogger(ProjectController.class.getName());

    private ProjectDAO projectDAO;
    private ProjectImageDAO projectImageDAO;
    private ProjectTechnologyDAO projectTechnologyDAO;
    private TestimonialDAO testimonialDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        projectDAO = new ProjectDAO();
        projectImageDAO = new ProjectImageDAO();
        projectTechnologyDAO = new ProjectTechnologyDAO();
        testimonialDAO = new TestimonialDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                showAllProjects(request, response);
            } else {
                String slug = pathInfo.substring(1);
                showProjectBySlug(request, response, slug);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in ProjectController: " + e.getMessage(), e);
            request.setAttribute("error", "Unable to load project data");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    private void showAllProjects(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String type = request.getParameter("type");
        List<Project> projects;

        if (type != null && !type.isEmpty()) {
            projects = projectDAO.findByType(type);
            request.setAttribute("selectedType", type);
        } else {
            projects = projectDAO.findPublished();
        }

        List<String> projectTypes = projectDAO.findProjectTypes();

        request.setAttribute("projects", projects);
        request.setAttribute("projectTypes", projectTypes);
        request.getRequestDispatcher("/WEB-INF/views/projects/list.jsp").forward(request, response);
    }

    private void showProjectBySlug(HttpServletRequest request, HttpServletResponse response, String slug)
            throws ServletException, IOException {

        Project project = projectDAO.findBySlug(slug);

        if (project == null || !project.isPublished()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // Increment view count
        projectDAO.incrementViewCount(project.getId());

        // Get project technologies
        List<ProjectTechnology> projectTechnologies = projectTechnologyDAO.findByProjectId(project.getId());

        // Get project images
        List<ProjectImage> projectImages = projectImageDAO.findByProjectId(project.getId());

        // Get project testimonials
        List<Testimonial> projectTestimonials = testimonialDAO.findByProjectId(project.getId());

        request.setAttribute("project", project);
        request.setAttribute("projectTechnologies", projectTechnologies);
        request.setAttribute("projectImages", projectImages);
        request.setAttribute("projectTestimonials", projectTestimonials);
        request.getRequestDispatcher("/WEB-INF/views/projects/detail.jsp").forward(request, response);
    }
}
