<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="experience-section">
    <h2 class="experience-section-title">Work Experience</h2>

    <c:forEach var="exp" items="${workExperiences}" varStatus="loop">
        <div class="experience-item">
            <div class="experience-image-wrapper">
                <c:if test="${not empty exp.companyLogoUrl}">
                    <img src="${exp.companyLogoUrl}" alt="${exp.companyName} logo">
                </c:if>
            </div>
            <div class="experience-content-wrapper">
                <h3 class="position">${exp.position}</h3>
                <h4 class="company">
                    ${exp.companyName}
                    <span class="employment-type">(${exp.employmentType})</span>
                </h4>
                <c:if test="${not empty exp.companyUrl}">
                    <a href="${exp.companyUrl}" target="_blank" rel="noopener noreferrer" class="btn btn-outline" style="margin-top: var(--space-4);">
                        Visit Website <span class="btn-icon">&rarr;</span>
                    </a>
                </c:if>
                <div class="date-location">
                    <fmt:formatDate value="${exp.startDateAsDate}" pattern="MMM yyyy" /> -
                    <c:choose>
                        <c:when test="${exp.isCurrent()}">
                            Present
                        </c:when>
                        <c:otherwise>
                            <fmt:formatDate value="${exp.endDateAsDate}" pattern="MMM yyyy" />
                        </c:otherwise>
                    </c:choose>
                    <span class="separator">|</span>
                    ${exp.location}
                </div>

                <c:if test="${not empty exp.description}">
                    <p class="experience-description">${exp.description}</p>
                </c:if>

                <c:if test="${not empty exp.achievements}">
                    <ul class="achievements-list">
                        <c:forEach var="ach" items="${exp.achievements}">
                            <li>${ach.description}</li>
                        </c:forEach>
                    </ul>
                </c:if>
            </div>
        </div>
    </c:forEach>

    <c:if test="${showMoreWorkExperience}">
        <div class="section-footer-actions">
            <a href="${pageContext.request.contextPath}/experience" class="btn btn-outline">
                View All Experience <span class="btn-icon">&rarr;</span>
            </a>
        </div>
    </c:if>
</div>
