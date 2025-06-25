<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
  .project-hero-link::after {
    content: none !important;
  }
  .project-image {
    overflow: hidden;
  }
  .project-image img {
    transition: transform 0.3s ease-in-out;
  }
  .project-hero-link:hover .project-image img {
    transform: scale(1.05);
  }
</style>
<div class="center-column-content">
  <h2 class="center-headline slogan">
    The best way to predict the future is to build it
  </h2>
  <c:forEach var="project" items="${featuredProjects}">
    <a
      href="${pageContext.request.contextPath}/projects/${project.slug}"
      class="project-hero-link"
      style="text-decoration: none; color: inherit"
    >
    <div class="project-hero">
      <div class="project-image">
        <img src="${project.featuredImageUrl}" alt="${project.title}" />
      </div>
      <h3 class="project-title">${project.title}</h3>
      <p class="project-desc">${project.shortDescription}</p>
    </div>
    </a>
  </c:forEach>
  <div style="text-align: center; margin-top: 2rem">
    <a
      href="${pageContext.request.contextPath}/projects"
      class="btn btn-outline"
      style="margin-top: 0.5rem"
    >
      More <span class="btn-icon">&rarr;</span>
    </a>
  </div>
</div>
