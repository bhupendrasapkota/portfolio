<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib
prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="fn"
uri="http://java.sun.com/jsp/jstl/functions" %> <%@ taglib prefix="fmt"
uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>${project.title} - Project Details</title>
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/css/base.css"
    />
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/css/project-detail.css"
    />
  </head>
  <body>
    <jsp:include page="/WEB-INF/views/common/navbar.jsp" />

    <div class="project-detail-container">
      <!-- Header -->
      <header class="project-header">
        <h1 class="project-title">${project.title}</h1>
        <p class="project-subtitle">${project.projectType}</p>
      </header>

      <!-- Main Content -->
      <div class="project-content-wrapper">
        <!-- Main Image -->
        <c:if test="${not empty project.featuredImageUrl}">
          <img
            src="${project.featuredImageUrl}"
            alt="${project.title}"
            class="project-hero-image"
          />
        </c:if>

        <!-- Description -->
        <div class="project-info-card">
          <div class="project-description">
            <h2 class="section-title">About the Project</h2>
            <p>${project.fullDescription}</p>
          </div>
        </div>

        <!-- Info Card -->
        <div class="project-info-card">
          <div class="project-meta-grid">
            <div class="meta-section">
              <h3 class="meta-title">Project Info</h3>
              <ul>
                <c:if test="${not empty project.clientName}"
                  ><li><strong>Client:</strong> ${project.clientName}</li></c:if
                >
                <c:if test="${not empty project.startDate}"
                  ><li>
                    <strong>Year:</strong>
                    <fmt:formatDate
                      value="${project.startDateAsDate}"
                      pattern="yyyy"
                    /></li
                ></c:if>
                <c:if test="${not empty project.status}"
                  ><li><strong>Status:</strong> ${project.status}</li></c:if
                >
              </ul>
              <div class="project-links">
                <c:if test="${not empty project.projectUrl}">
                  <a
                    href="${project.projectUrl}"
                    class="btn btn-outline"
                    target="_blank"
                    rel="noopener noreferrer"
                  >
                    Visit Site <span class="btn-icon">&rarr;</span>
                  </a>
                </c:if>
                <c:if test="${not empty project.githubUrl}">
                  <a
                    href="${project.githubUrl}"
                    class="btn btn-outline"
                    target="_blank"
                    rel="noopener noreferrer"
                  >
                    View Code <span class="btn-icon">&rarr;</span>
                  </a>
                </c:if>
                <c:if test="${not empty project.demoUrl}">
                  <a
                    href="${project.demoUrl}"
                    class="btn btn-outline"
                    target="_blank"
                    rel="noopener noreferrer"
                  >
                    View Demo <span class="btn-icon">&rarr;</span>
                  </a>
                </c:if>
              </div>
            </div>
            <c:if test="${not empty projectTechnologies}">
              <div class="meta-section">
                <h3 class="meta-title">Technology Stack</h3>
                <div class="tech-stack">
                  <c:forEach var="tech" items="${projectTechnologies}">
                    <span class="skill-item"
                      ><c:out value="${tech.skill.name}"
                    /></span>
                  </c:forEach>
                </div>
              </div>
            </c:if>
          </div>
        </div>
      </div>

      <!-- Other Sections -->
      <c:if test="${not empty projectImages and fn:length(projectImages) > 1}">
        <section class="project-section">
          <h2 class="project-section-heading">Gallery</h2>
          <div class="image-gallery">
            <c:forEach var="image" items="${projectImages}">
              <a href="${image.imageUrl}" target="_blank">
                <img
                  src="${image.imageUrl}"
                  alt="Project image for ${project.title}"
                />
              </a>
            </c:forEach>
          </div>
        </section>
      </c:if>

      <c:if test="${not empty projectTestimonials}">
        <section class="project-section">
          <h2 class="project-section-heading">Testimonials</h2>
          <div class="project-info-card">
            <div class="testimonial-list">
              <c:forEach var="testimonial" items="${projectTestimonials}">
                <div class="testimonial-card">
                  <p class="testimonial-text">
                    "${testimonial.testimonialText}"
                  </p>
                  <div class="testimonial-author">
                    <span class="author-name">${testimonial.clientName}</span>
                  </div>
                </div>
              </c:forEach>
            </div>
          </div>
        </section>
      </c:if>
    </div>

    <jsp:include page="/WEB-INF/views/common/footer.jsp" />
  </body>
</html>
