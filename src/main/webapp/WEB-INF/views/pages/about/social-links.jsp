<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%-- Social
Links Section --%>
<div class="social-links-section">
  <h2 class="social-links-title">Connect with Me</h2>
  <p class="social-links-subtitle">
    Follow me on my social media platforms to see more of my work and connect.
  </p>
  <div class="social-links-grid">
    <c:forEach var="link" items="${socialLinks}">
      <a href="${link.url}" class="social-link-card" target="_blank" rel="noopener noreferrer">
        <div class="social-icon">
          <c:if test="${not empty link.iconName}">
            <%-- Assuming you have icons at this path --%>
            <img src="${link.iconName}" alt="${link.platform}">
          </c:if>
        </div>
        <div class="social-platform-name">${link.platform}</div>
      </a>
    </c:forEach>
  </div>
</div>
