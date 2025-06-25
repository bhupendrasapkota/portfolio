<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib
prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> <%@ taglib prefix="fn"
uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="right-column-content">
  <!-- Projects Section -->
  <section class="projects-section">
    <h3 class="section-title">Projects</h3>
    <ul class="pill-list projects-list">
      <c:forEach var="project" items="${featuredProjects}">
        <a
          href="${pageContext.request.contextPath}/projects/<c:out value='${project.slug}'/>"
          style="text-decoration: none"
        >
          <li style="cursor: pointer">
            <c:out value="${project.title}" />
          </li>
        </a>
      </c:forEach>
    </ul>
    <a
      href="${pageContext.request.contextPath}/projects"
      class="btn btn-outline"
    >
      More Projects <span class="btn-icon">&rarr;</span>
    </a>
  </section>

  <!-- Services Section -->
  <section class="service-section">
    <h3 class="section-title">Services</h3>
    <ul class="pill-list services-list">
      <c:forEach var="service" items="${featuredServices}">
        <li>
          <span class="service-name"><c:out value="${service.name}" /></span>
          <span class="service-desc"
            ><c:out value="${service.shortDescription}"
          /></span>
        </li>
      </c:forEach>
    </ul>
    <a
      href="${pageContext.request.contextPath}/services"
      class="btn btn-outline"
    >
      More Info <span class="btn-icon">&rarr;</span>
    </a>
  </section>

  <!-- Work Experience Section -->
  <c:if test="${not empty workExperiences}">
    <section class="experience-section">
      <h3 class="section-title">Work Experience</h3>
      <ul class="pill-list experience-list">
        <c:forEach var="exp" items="${workExperiences}">
          <li>
            <span class="exp-title"><c:out value="${exp.position}" /></span>
            <span class="exp-company"
              ><c:out value="${exp.companyName}"
            /></span>
            <span class="exp-duration">
              (${exp.startYear} - ${exp.endYear})
            </span>
          </li>
        </c:forEach>
      </ul>
      <c:if test="${not empty workExperiences and fn:length(workExperiences) > 0}">
        <a
          href="${pageContext.request.contextPath}/about#experience"
          class="btn btn-outline"
        >
          Experience <span class="btn-icon">&rarr;</span>
        </a>
      </c:if>
    </section>
  </c:if>

  <!-- Social Links Section -->
  <section class="right-social-section">
    <h3 class="section-title">Connect</h3>
    <div class="social-links">
      <c:forEach var="link" items="${socialLinks}">
        <a
          href="${link.url}"
          target="_blank"
          rel="noopener"
          title="${link.platform}"
        >
            <img src="${link.iconName}" alt="${link.platform}">
        </a>
      </c:forEach>
    </div>
  </section>
</div>
