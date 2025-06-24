<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="personal-info-wrapper">
  <div class="personal-info-left">
    <h1 class="personal-info-title">${personalInfo.title}</h1>
    <p class="personal-info-bio">${personalInfo.bio}</p>
    <div class="personal-info-address">
      <strong>Address</strong>
      <div>${personalInfo.location}</div>
    </div>
    <div class="personal-info-buttons">
      <a
        href="https://maps.google.com/?q=${personalInfo.location}"
        class="btn btn-outline"
        target="_blank"
        style="margin-top: 0.5rem"
      >
        Google Map <span class="btn-icon">&rarr;</span>
      </a>
      <a
        href="${personalInfo.websiteUrl}"
        class="btn btn-outline"
        target="_blank"
        style="margin-top: 0.5rem"
      >
        Official Sites <span class="btn-icon">&rarr;</span>
      </a>
    </div>
  </div>
  <div class="personal-info-right">
    <img
      src="${personalInfo.profileImageUrl}"
      alt="Profile Image"
      class="personal-info-image"
    />
  </div>
</div>
