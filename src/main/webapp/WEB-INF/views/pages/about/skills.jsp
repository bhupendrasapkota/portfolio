<%-- /src/main/webapp/WEB-INF/views/pages/about/skills.jsp --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="skills-section-wrapper">
  <div class="skills-grid">
    <div class="skills-summary">
      <div class="experience-highlight">
        <div class="experience-number">${yearsOfExperience}</div>
        <div class="experience-label">Years of<br>Experience</div>
      </div>
      <c:if test="${databaseProficiency > 0}">
        <div class="experience-highlight">
          <div class="experience-number">${databaseProficiency}%</div>
          <div class="experience-label">Database<br>Expertise</div>
        </div>
      </c:if>
      <p class="skills-description">
        With over ${yearsOfExperience} years of hands-on experience, I have a
        proven track record of designing, developing, and deploying high-quality
        software solutions. My expertise spans a wide range of technologies and
        industries.
      </p>
    </div>
    <div class="skills-main-content">
      <h2 class="skills-section-title">My Skills</h2>
      <div class="skills-categories">
        <c:forEach var="entry" items="${skillsByCategory}">
          <div class="skills-category">
            <h3 class="skills-category-title">${entry.key}</h3>
            <ul class="skills-list">
              <c:forEach var="skill" items="${entry.value}">
                <li class="skill-item">${skill.name}</li>
              </c:forEach>
            </ul>
          </div>
        </c:forEach>
      </div>
    </div>
  </div>
</div>
