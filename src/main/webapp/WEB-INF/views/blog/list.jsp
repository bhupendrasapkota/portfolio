<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib
prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="fmt"
uri="http://java.sun.com/jsp/jstl/fmt" %> <%@ taglib prefix="fn"
uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Portfolio - Blogs</title>
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/css/base.css"
    />
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/css/blog.css"
    />
  </head>
  <body>
    <jsp:include page="/WEB-INF/views/common/navbar.jsp" />
    <div class="blog-container">
      <!-- Header (matching testimonials style) -->
      <header class="blog-header">
        <h1 class="blog-title">Blog</h1>
        <p class="blog-subtitle">
          Insights, tutorials, and stories from my journey.
        </p>
      </header>
      <!-- Featured Post -->
      <c:if test="${not empty featuredPosts}">
        <c:set var="post" value="${featuredPosts[0]}" />
        <h2 class="blog-section-heading">Featured Post</h2>
        <a
          href="${pageContext.request.contextPath}/blog/${post.slug}"
          class="blog-list-item-link"
        >
          <div class="blog-list-item">
            <c:if test="${not empty post.featuredImageUrl}">
              <img
                class="blog-list-image"
                src="${post.featuredImageUrl}"
                alt="${post.title}"
              />
            </c:if>
            <div class="blog-list-info">
              <div class="blog-list-meta-row">
                <span class="blog-list-date">${post.formattedPublishedAt}</span>
                <c:if test="${not empty post.readTimeMinutes}">
                  <span class="blog-list-meta-item"
                    >· ${post.readTimeMinutes} min read</span
                  >
                </c:if>
              </div>
              <h3 class="blog-list-title">${post.title}</h3>
              <p class="blog-list-excerpt">${post.excerpt}</p>
              <c:if test="${not empty post.tags}">
                <div class="blog-list-tags">
                  <c:forEach var="tag" items="${fn:split(post.tags, ',')}">
                    <span class="blog-tag">#${fn:trim(tag)}</span>
                  </c:forEach>
                </div>
              </c:if>
            </div>
          </div>
        </a>
      </c:if>

      <!-- All Posts -->
      <c:if test="${not empty blogPosts}">
        <h2 class="blog-section-heading">All Posts</h2>
        <c:forEach var="post" items="${blogPosts}">
          <a
            href="${pageContext.request.contextPath}/blog/${post.slug}"
            class="blog-list-item-link"
          >
            <div class="blog-list-item">
              <c:if test="${not empty post.featuredImageUrl}">
                <img
                  class="blog-list-image"
                  src="${post.featuredImageUrl}"
                  alt="${post.title}"
                />
              </c:if>
              <div class="blog-list-info">
                <div class="blog-list-meta-row">
                  <span class="blog-list-date"
                    >${post.formattedPublishedAt}</span
                  >
                  <c:if test="${not empty post.readTimeMinutes}">
                    <span class="blog-list-meta-item"
                      >· ${post.readTimeMinutes} min read</span
                    >
                  </c:if>
                </div>
                <h3 class="blog-list-title">${post.title}</h3>
                <p class="blog-list-excerpt">${post.excerpt}</p>
                <c:if test="${not empty post.tags}">
                  <div class="blog-list-tags">
                    <c:forEach var="tag" items="${fn:split(post.tags, ',')}">
                      <span class="blog-tag">#${fn:trim(tag)}</span>
                    </c:forEach>
                  </div>
                </c:if>
              </div>
            </div>
          </a>
        </c:forEach>
      </c:if>
    </div>
    <jsp:include page="/WEB-INF/views/common/footer.jsp" />
  </body>
</html>
