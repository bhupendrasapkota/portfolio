<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="left-column-content">
  <!-- About Section -->
  <section class="about-section">
    <h3 class="section-title">About</h3>
    <c:if test="${not empty personalInfo}">
      <div class="about-header">
        <h4 class="about-name"><c:out value="${personalInfo.fullName}" /></h4>
        <c:if test="${not empty personalInfo.title}">
          <p class="about-title"><c:out value="${personalInfo.title}" /></p>
        </c:if>
      </div>
      <div class="about-bio">
        <c:out value="${personalInfo.bio}" />
      </div>
    </c:if>
    <c:if test="${empty personalInfo}">
      <div class="about-bio">
        Welcome! I'm Bhupendra Sapkota, a passionate developer and
        technologist.<br />
        I create future experiences with partners, blending digital and analog.
      </div>
    </c:if>
    <a href="${pageContext.request.contextPath}/about" class="btn btn-outline">
      About Me <span class="btn-icon">&rarr;</span>
    </a>
  </section>

  <!-- Skills Section -->
  <c:if test="${not empty featuredSkills}">
    <section class="skills-section">
      <h3 class="section-title">Skills</h3>
      <ul class="skills-list">
        <c:forEach var="skill" items="${featuredSkills}">
          <li class="skill-item"><c:out value="${skill.name}" /></li>
        </c:forEach>
      </ul>
      <a
        href="${pageContext.request.contextPath}/skills"
        class="btn btn-outline"
        style="margin-top: 0.5rem"
      >
        More Skills <span class="btn-icon">&rarr;</span>
      </a>
    </section>
  </c:if>

  <!-- Blog/News Section -->
  <section class="news-section">
    <h3 class="section-title">Blog</h3>
    <ul class="news-list">
      <c:forEach var="post" items="${latestBlogPosts}">
        <li>
          <a
            href="${pageContext.request.contextPath}/blog/<c:out value='${post.slug}'/>"
          >
            <c:out value="${post.title}" />
          </a>
        </li>
      </c:forEach>
    </ul>
    <a href="${pageContext.request.contextPath}/blog" class="btn btn-outline">
      More Blog <span class="btn-icon">&rarr;</span>
    </a>
  </section>

  <!-- Contact Button -->
  <section class="contact-section">
    <div class="contact-message">
      Interested in working together or have a question? <br />Feel free to
      reach out!
    </div>
    <a
      href="${pageContext.request.contextPath}/contact"
      class="btn btn-outline"
    >
      Contact <span class="btn-icon">&rarr;</span>
    </a>
  </section>
</div>
