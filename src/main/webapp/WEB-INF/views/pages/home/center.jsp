<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="center-column-content">
  <h2 class="center-headline slogan">
    The best way to predict the future is to build it
  </h2>
  <c:forEach var="project" items="${featuredProjects}">
    <div class="project-hero">
      <div class="project-image">
        <img src="${project.featuredImageUrl}" alt="${project.title}" />
      </div>
      <h3 class="project-title">${project.title}</h3>
      <p class="project-desc">${project.shortDescription}</p>
    </div>
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
