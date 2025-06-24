<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib
prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> <%-- Skills Section
--%>
<header class="about-header">
  <div class="about-header-container">
    <h1 class="about-header-title">
      <a
        href="${pageContext.request.contextPath}/home"
        class="about-header-name"
      >
        ${fn:split(personalInfo.fullName, ' ')[0]}
      </a>
      <span class="about-header-separator">/</span>
      <span class="about-header-section">About</span>
    </h1>
  </div>
</header>
