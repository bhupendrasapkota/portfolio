<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Portfolio - Services</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/services.css">
</head>
<body>
    <jsp:include page="common/navbar.jsp" />

    <div class="services-content">
        <header class="services-header">
            <div class="services-header-container">
                <h1 class="services-header-title">Services</h1>
                <p class="services-header-subtitle">Solutions I provide to bring your ideas to life.</p>
            </div>
        </header>

        <main class="services-main">
            <!-- Featured Services Section -->
            <c:if test="${not empty featuredServices}">
                <section class="featured-services-section">
                    <h2 class="section-title">Featured Offerings</h2>
                    <div class="featured-services-grid">
                        <c:forEach var="service" items="${featuredServices}">
                            <div class="service-card featured">
                                <span class="service-bg-icon">
                                     <img src="${service.iconName}" alt="">
                                </span>
                                <div class="service-card-content">
                                    <h3 class="service-name">${service.name}</h3>
                                    <p class="service-description">${service.description}</p>
                                    <div class="service-details">
                                        <c:if test="${not empty service.duration}">
                                            <span class="service-duration">
                                                Duration: ${service.duration}
                                            </span>
                                        </c:if>
                                        <c:if test="${not empty service.priceRange}">
                                            <span class="service-price">
                                                Price: ${service.priceRange}
                                            </span>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </section>
            </c:if>

            <!-- All Services Section -->
            <section class="all-services-section">
                <h2 class="section-title">All Services</h2>
                <div class="services-grid">
                     <c:forEach var="service" items="${services}">
                        <div class="service-card">
                            <span class="service-bg-icon">
                                 <img src="${service.iconName}" alt="">
                            </span>
                            <div class="service-card-content">
                                <h3 class="service-name">${service.name}</h3>
                                <p class="service-description">${service.description}</p>
                                <div class="service-details">
                                    <c:if test="${not empty service.duration}">
                                        <span class="service-duration">
                                            Duration: ${service.duration}
                                        </span>
                                    </c:if>
                                    <c:if test="${not empty service.priceRange}">
                                        <span class="service-price">
                                            Price: ${service.priceRange}
                                        </span>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </section>
        </main>
    </div>

    <jsp:include page="common/footer.jsp" />
</body>
</html> 