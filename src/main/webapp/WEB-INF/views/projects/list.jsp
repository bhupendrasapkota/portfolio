<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib
prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="fmt"
uri="http://java.sun.com/jsp/jstl/fmt" %> <%@ taglib prefix="fn"
uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Portfolio - Projects</title>
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/css/base.css"
    />
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/css/projects.css"
    />
  </head>
  <body>
    <jsp:include page="/WEB-INF/views/common/navbar.jsp" />
    <div class="projects-content minimal">
      <!-- Title -->
      <div class="editorial-header-row">
        <div class="editorial-title">Projects</div>
      </div>
      <!-- Filter Bar -->
      <div class="projects-filter-container">
        <nav class="projects-filter-nav underline">
          <ul>
            <li>
              <a
                href="${pageContext.request.contextPath}/projects"
                class="<c:if test='${empty selectedType}'>active</c:if>'"
                >All</a
              >
            </li>
            <!-- Show all project types as buttons -->
            <c:forEach var="type" items="${projectTypes}">
              <li>
                <a
                  href="${pageContext.request.contextPath}/projects?type=${type}"
                  class="<c:if test='${type == selectedType}'>active</c:if>'"
                  >${type}</a
                >
              </li>
            </c:forEach>
          </ul>
        </nav>
      </div>
      <!-- Projects Grid -->
      <div class="editorial-projects-grid">
        <c:forEach var="project" items="${projects}">
          <div class="editorial-project-item">
            <c:if test="${not empty project.featuredImageUrl}">
              <img
                class="editorial-project-image"
                src="${project.featuredImageUrl}"
                alt="${project.title}"
              />
            </c:if>
            <div class="editorial-project-text">
              <div class="editorial-project-meta-row">
                <c:if test="${not empty project.clientName}">
                  <span class="editorial-project-client"
                    >${project.clientName}</span
                  >
                </c:if>
                <c:if test="${not empty project.status}">
                  <span
                    class="editorial-project-status editorial-status-${project.status}"
                    >${project.status}</span
                  >
                </c:if>
                <c:if test="${not empty project.startDateAsDate}">
                  <span class="editorial-project-date">
                    <fmt:formatDate
                      value="${project.startDateAsDate}"
                      pattern="yyyy"
                    />
                    <c:if test="${not empty project.endDateAsDate}"
                      >–<fmt:formatDate
                        value="${project.endDateAsDate}"
                        pattern="yyyy"
                    /></c:if>
                  </span>
                </c:if>
                <c:if test="${project.viewCount > 0}">
                  <span class="editorial-project-views"
                    >&#128065; ${project.viewCount}</span
                  >
                </c:if>
              </div>
              <c:if test="${not empty project.projectType}">
                <div class="editorial-project-type">${project.projectType}</div>
              </c:if>
              <div class="editorial-project-title">${project.title}</div>
              <div class="editorial-project-desc">
                ${project.shortDescription}
              </div>
              <div class="editorial-project-actions">
                <a
                  href="${pageContext.request.contextPath}/projects/${project.slug}"
                  class="btn btn-outline editorial-view-link"
                  >View Project <span class="btn-icon">&rarr;</span></a
                >
                <c:if test="${not empty project.projectUrl}">
                  <a
                    href="${project.projectUrl}"
                    class="btn btn-outline"
                    target="_blank"
                    >Visit Site <span class="btn-icon">&rarr;</span></a
                  >
                </c:if>
                <c:if test="${not empty project.githubUrl}">
                  <a
                    href="${project.githubUrl}"
                    class="btn btn-outline"
                    target="_blank"
                    >GitHub <span class="btn-icon">&rarr;</span></a
                  >
                </c:if>
                <c:if test="${not empty project.demoUrl}">
                  <a
                    href="${project.demoUrl}"
                    class="btn btn-outline"
                    target="_blank"
                    >Demo <span class="btn-icon">&rarr;</span></a
                  >
                </c:if>
              </div>
            </div>
          </div>
        </c:forEach>
      </div>
    </div>
    <jsp:include page="/WEB-INF/views/common/footer.jsp" />
  </body>
</html>
