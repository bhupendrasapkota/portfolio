<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Portfolio - About</title>
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/css/base.css"
    />
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/css/about.css"
    />
  </head>
  <body>
    <jsp:include page="common/navbar.jsp" />
    <main class="about-content">
      <jsp:include page="pages/about/header.jsp" />
      <jsp:include page="pages/about/personal-info.jsp" />
      <jsp:include page="pages/about/skills.jsp" />
      <jsp:include page="pages/about/experience.jsp" />
      <jsp:include page="pages/about/achievements.jsp" />
      <jsp:include page="pages/about/education.jsp" />
      <jsp:include page="pages/about/certifications.jsp" />
      <jsp:include page="pages/about/social-links.jsp" />
    </main>
    <jsp:include page="common/footer.jsp" />
  </body>
</html>
