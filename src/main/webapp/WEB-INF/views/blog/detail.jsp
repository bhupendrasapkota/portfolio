<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib
prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="fmt"
uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <c:set
      var="pageTitle"
      value="${not empty blogPost.seoTitle ? blogPost.seoTitle : blogPost.title}"
    />
    <title>${pageTitle}</title>
    <c:if test="${not empty blogPost.seoDescription}">
      <meta name="description" content="${blogPost.seoDescription}" />
    </c:if>
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/css/base.css"
    />
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/css/blog-detail.css"
    />
  </head>
  <body>
    <jsp:include page="/WEB-INF/views/common/navbar.jsp" />

    <main class="blog-detail-container">
      <header class="blog-header">
        <h1 class="blog-title">${blogPost.title}</h1>
        <p class="blog-subtitle">${blogPost.excerpt}</p>
      </header>

      <c:if test="${not empty blogPost.featuredImageUrl}">
        <img
          class="blog-hero-image"
          src="${blogPost.featuredImageUrl}"
          alt="${blogPost.title}"
        />
      </c:if>

      <div class="blog-info-card">
        <div class="blog-content">${blogPost.content}</div>

        <div class="meta-section" style="margin-top: var(--space-10)">
          <h3 class="meta-title">Post Details</h3>
          <ul>
            <li>
              <strong>Published:</strong> ${blogPost.getFormattedPublishedAt()}
            </li>
            <li>
              <strong>Read Time:</strong> ${blogPost.readTimeMinutes} min read
            </li>
            <c:if test="${blogPost.viewCount > 0}">
              <li><strong>Views:</strong> ${blogPost.viewCount}</li>
            </c:if>
          </ul>

          <c:if test="${not empty blogPost.tags}">
            <h3 class="meta-title" style="margin-top: var(--space-8)">Tags</h3>
            <div class="tags-list">
              <c:forEach var="tag" items="${blogPost.tags.split(',')}">
                <span class="skill-item">${tag.trim()}</span>
              </c:forEach>
            </div>
          </c:if>
        </div>
      </div>
    </main>

    <jsp:include page="/WEB-INF/views/common/footer.jsp" />
  </body>
</html>
