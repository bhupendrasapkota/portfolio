<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="certifications-section">
    <h2 class="certifications-section-title">Licenses & Certifications</h2>
    <p class="certifications-subtitle">
        A collection of my professional licenses and certifications.
    </p>

    <div class="certifications-grid">
        <c:forEach var="cert" items="${certifications}" varStatus="loop">
            <div class="certification-card card-item-${loop.index + 1}">
                <div class="certification-bg-year">
                    <fmt:formatDate value="${cert.issueDateAsDate}" pattern="’yy" />
                </div>
                <div class="certification-header">
                    <c:if test="${not empty cert.badgeImageUrl}">
                        <img src="${cert.badgeImageUrl}" alt="${cert.name} badge" class="certification-badge">
                    </c:if>
                    <h3 class="certification-name">${cert.name}</h3>
                </div>
                <div class="certification-body">
                    <p class="issuing-org">${cert.issuingOrganization}</p>
                    <p class="issue-date">
                        Issued <fmt:formatDate value="${cert.issueDateAsDate}" type="DATE" dateStyle="long" />
                    </p>
                </div>
                <div class="certification-footer">
                    <c:if test="${not empty cert.credentialUrl}">
                        <a href="${cert.credentialUrl}" target="_blank" rel="noopener noreferrer" class="btn btn-outline">
                            Credential <span class="btn-icon">&rarr;</span>
                        </a>
                    </c:if>
                </div>
            </div>
        </c:forEach>
    </div>

    <c:if test="${showMoreCertifications}">
        <div class="section-footer-actions">
            <a href="${pageContext.request.contextPath}/certifications" class="btn btn-outline">
                View All Certifications <span class="btn-icon">&rarr;</span>
            </a>
        </div>
    </c:if>
</div>
