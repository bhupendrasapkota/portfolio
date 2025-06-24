<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="education-section">
    <h2 class="education-section-title">Education</h2>
    <div class="education-list">
        <c:forEach var="edu" items="${educations}">
            <div class="education-card">
                <div class="education-bg-year">
                    <fmt:formatDate value="${edu.endDateAsDate}" pattern="’yy" />
                </div>
                <div class="education-logo">
                    <c:if test="${not empty edu.institutionLogoUrl}">
                        <img src="${edu.institutionLogoUrl}" alt="${edu.institutionName} logo">
                    </c:if>
                </div>
                <div class="education-content">
                    <h3 class="degree">${edu.degree}</h3>
                    <h4 class="institution">
                        <c:choose>
                            <c:when test="${not empty edu.institutionUrl}">
                                <a href="${edu.institutionUrl}" target="_blank" rel="noopener noreferrer">
                                    ${edu.institutionName}
                                </a>
                            </c:when>
                            <c:otherwise>
                                ${edu.institutionName}
                            </c:otherwise>
                        </c:choose>
                    </h4>
                    <div class="field-of-study">${edu.fieldOfStudy}</div>
                    <div class="education-details">
                        <span class="date-range">
                            <fmt:formatDate value="${edu.startDateAsDate}" pattern="yyyy" /> - <fmt:formatDate value="${edu.endDateAsDate}" pattern="yyyy" />
                        </span>
                        <c:if test="${not empty edu.gpa}">
                            <span class="separator">|</span>
                            <span class="gpa">GPA: ${edu.gpa}</span>
                        </c:if>
                    </div>
                    <c:if test="${not empty edu.description}">
                        <p class="education-description">${edu.description}</p>
                    </c:if>
                </div>
            </div>
        </c:forEach>
    </div>

    <c:if test="${showMoreEducation}">
        <div class="section-footer-actions">
            <a href="${pageContext.request.contextPath}/education" class="btn btn-outline">
                View All Education <span class="btn-icon">&rarr;</span>
            </a>
        </div>
    </c:if>
</div>
