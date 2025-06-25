<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib
prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="fmt"
uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Testimonials - Bhupendra Sapkota</title>
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
    <jsp:include page="/WEB-INF/views/common/navbar.jsp" />

    <div class="testimonials-container">
      <!-- Header -->
      <header class="testimonials-header">
        <h1 class="testimonials-title">Client Testimonials</h1>
        <p class="testimonials-subtitle">
          Real feedback from people I've worked with
        </p>
      </header>

      <!-- Featured Testimonials -->
      <c:if test="${not empty featuredTestimonials}">
        <section class="featured-testimonials">
          <h2 class="section-heading">Featured Reviews</h2>
          <div class="featured-grid">
            <c:forEach var="testimonial" items="${featuredTestimonials}">
              <div class="testimonial-card featured">
                <div class="testimonial-content">
                  <div class="quote-mark">"</div>
                  <p class="testimonial-text">${testimonial.testimonialText}</p>
                  <div class="testimonial-author">
                    <div class="author-info">
                      <div class="author-header">
                        <c:if test="${not empty testimonial.clientImageUrl}">
                          <img
                            class="author-avatar"
                            src="${testimonial.clientImageUrl}"
                            alt="${testimonial.clientName}"
                          />
                        </c:if>
                        <div class="author-text">
                          <h4 class="author-name">${testimonial.clientName}</h4>
                          <p class="author-details">
                            <c:if
                              test="${not empty testimonial.clientPosition}"
                            >
                              ${testimonial.clientPosition}
                            </c:if>
                            <c:if test="${not empty testimonial.clientCompany}">
                              <c:if
                                test="${not empty testimonial.clientPosition}"
                              >
                                at </c:if
                              >${testimonial.clientCompany}
                            </c:if>
                          </p>
                        </div>
                      </div>
                    </div>
                    <c:if test="${testimonial.rating > 0}">
                      <div class="rating">
                        <c:forEach var="i" begin="1" end="${testimonial.rating}"
                          >★</c:forEach
                        >
                      </div>
                    </c:if>
                  </div>
                </div>
              </div>
            </c:forEach>
          </div>
        </section>
      </c:if>

      <!-- All Testimonials -->
      <section class="all-testimonials">
        <h2 class="section-heading">All Testimonials</h2>
        <div class="testimonials-grid">
          <c:forEach var="testimonial" items="${testimonials}">
            <div class="testimonial-card">
              <div class="testimonial-content">
                <div class="quote-mark">"</div>
                <p class="testimonial-text">${testimonial.testimonialText}</p>
                <div class="testimonial-author">
                  <div class="author-info">
                    <div class="author-header">
                      <c:if test="${not empty testimonial.clientImageUrl}">
                        <img
                          class="author-avatar"
                          src="${testimonial.clientImageUrl}"
                          alt="${testimonial.clientName}"
                        />
                      </c:if>
                      <div class="author-text">
                        <h4 class="author-name">${testimonial.clientName}</h4>
                        <p class="author-details">
                          <c:if test="${not empty testimonial.clientPosition}">
                            ${testimonial.clientPosition}
                          </c:if>
                          <c:if test="${not empty testimonial.clientCompany}">
                            <c:if
                              test="${not empty testimonial.clientPosition}"
                            >
                              at </c:if
                            >${testimonial.clientCompany}
                          </c:if>
                        </p>
                      </div>
                    </div>
                  </div>
                  <c:if test="${testimonial.rating > 0}">
                    <div class="rating">
                      <c:forEach var="i" begin="1" end="${testimonial.rating}"
                        >★</c:forEach
                      >
                    </div>
                  </c:if>
                </div>
              </div>
            </div>
          </c:forEach>
        </div>
      </section>
    </div>

    <jsp:include page="/WEB-INF/views/common/footer.jsp" />
  </body>
</html>
