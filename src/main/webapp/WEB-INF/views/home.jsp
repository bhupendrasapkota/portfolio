<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Portfolio - Home</title>
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/css/base.css"
    />
  </head>
  <body>
    <jsp:include page="common/navbar.jsp" />

    <main class="main-content">
      <!-- Hero Section -->
      <jsp:include page="pages/home/left.jsp" />
      <jsp:include page="pages/home/center.jsp" />
      <jsp:include page="pages/home/right.jsp" />
    </main>

    <jsp:include page="common/footer.jsp" />

    <script src="${pageContext.request.contextPath}/js/scroll-chaining.js"></script>
  </body>
</html>
