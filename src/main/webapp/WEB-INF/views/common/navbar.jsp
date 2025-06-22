<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<header class="main-header">
  <div class="container">
    <nav class="main-nav">
      <div class="nav-left">
        <a href="#" class="nav-link">Menu</a>
      </div>
      <div class="nav-center">
        <a
          href="?lang=en"
          class="nav-link ${param.lang == 'en' || empty param.lang ? 'active' : ''}"
          >EN</a
        >
        <span class="nav-separator">/</span>
        <a
          href="?lang=ne"
          class="nav-link ${param.lang == 'ne' ? 'active' : ''}"
          >NE</a
        >
      </div>
      <div class="nav-right">
        <a href="${pageContext.request.contextPath}/contact" class="nav-link"
          >Contact</a
        >
      </div>
    </nav>
  </div>
</header>
