<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%--
Achievements Section --%>
<div class="achievements-section">
  <h2 class="achievements-section-title">Key Achievements</h2>
  <p class="achievements-subtitle">
    A selection of achievements that highlight my impact and contributions
    across various roles.
  </p>

  <div class="achievements-grid">
    <c:set var="counter" value="0" />
    <c:forEach var="exp" items="${workExperiences}">
      <c:if test="${not empty exp.achievements}">
        <c:forEach var="ach" items="${exp.achievements}">
          <c:set var="counter" value="${counter + 1}" />
          <div class="achievement-card card-item-${counter}">
            <div class="achievement-icon">
              <%-- Using a generic star icon, can be replaced with a dynamic one
              --%>
              <i class="fas fa-star"></i>
            </div>
            <div class="achievement-content">
              <p class="achievement-description">${ach.description}</p>
              <span class="achievement-context">
                At ${exp.companyName} as ${exp.position}
              </span>
            </div>
          </div>
        </c:forEach>
      </c:if>
    </c:forEach>
  </div>

  <c:if test="${showMoreAchievements}">
    <div class="section-footer-actions">
      <a
        href="${pageContext.request.contextPath}/achievements"
        class="btn btn-outline"
      >
        View All Achievements <span class="btn-icon">&rarr;</span>
      </a>
    </div>
  </c:if>
</div>
