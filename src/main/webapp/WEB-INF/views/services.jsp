<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib
prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="fn"
uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Services - Bhupendra Sapkota</title>
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/css/base.css"
    />
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/css/testimonials.css"
    />
  </head>
  <body>
    <jsp:include page="common/navbar.jsp" />

    <div class="testimonials-container">
      <!-- Header -->
      <header class="testimonials-header">
        <h1 class="testimonials-title">Services</h1>
        <p class="testimonials-subtitle">
          Solutions I provide to bring your ideas to life
        </p>
      </header>

      <!-- Featured Services -->
      <c:if test="${not empty featuredServices}">
        <section class="featured-testimonials">
          <h2 class="section-heading">Featured Offerings</h2>
          <div class="featured-grid">
            <c:forEach var="service" items="${featuredServices}">
              <div class="testimonial-card featured">
                <span class="service-bg-icon">
                  <img src="${service.iconName}" alt="${service.name}">
                </span>
                <div class="testimonial-content">
                  <div class="quote-mark">⚡</div>
                  <p class="testimonial-text">${service.description}</p>
                  <div class="testimonial-author">
                    <div class="author-info">
                      <div class="author-header">
                        <div class="author-text">
                          <h4 class="author-name">${service.name}</h4>
                          <div class="author-details">
                            <c:if test="${not empty service.duration}">
                              <p>Duration: ${service.duration}</p>
                            </c:if>
                            <c:if test="${not empty service.priceRange}">
                              <p>Price: ${service.priceRange}</p>
                            </c:if>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </c:forEach>
          </div>
        </section>
      </c:if>

      <!-- All Services -->
      <section class="all-testimonials">
        <h2 class="section-heading">All Services</h2>
        <div class="testimonials-grid">
          <c:forEach var="service" items="${services}">
            <div class="testimonial-card">
              <span class="service-bg-icon">
                <img src="${service.iconName}" alt="${service.name}">
              </span>
              <div class="testimonial-content">
                <div class="quote-mark">🛠️</div>
                <p class="testimonial-text">${service.description}</p>
                <div class="testimonial-author">
                  <div class="author-info">
                    <div class="author-header">
                      <div class="author-text">
                        <h4 class="author-name">${service.name}</h4>
                        <div class="author-details">
                          <c:if test="${not empty service.duration}">
                            <p>Duration: ${service.duration}</p>
                          </c:if>
                          <c:if test="${not empty service.priceRange}">
                            <p>Price: ${service.priceRange}</p>
                          </c:if>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </c:forEach>
        </div>
      </section>
    </div>

    <jsp:include page="common/footer.jsp" />
  </body>
</html>
