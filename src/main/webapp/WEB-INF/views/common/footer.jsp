<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<footer class="main-footer">
  <div class="container footer-container">
    <div class="footer-left">
      <nav class="footer-nav">
        <ul class="footer-links">
          <li><a href="${pageContext.request.contextPath}/home">Top</a></li>
          <li><a href="${pageContext.request.contextPath}/about">About</a></li>
          <li>
            <a href="${pageContext.request.contextPath}/projects">Works</a>
          </li>
          <li>
            <a href="${pageContext.request.contextPath}/services">Services</a>
          </li>
          <li><a href="${pageContext.request.contextPath}/blog">Blog</a></li>
          <li>
            <a href="${pageContext.request.contextPath}/testimonials"
              >Testimonials</a
            >
          </li>
          <li>
            <a href="${pageContext.request.contextPath}/contact">Contact</a>
          </li>
        </ul>
      </nav>
      <div class="footer-social">
        <ul class="social-links">
          <li>
            <a
              href="https://www.instagram.com/bhupendrasapkota.me/"
              target="_blank"
              >Instagram</a
            >
          </li>
          <li>
            <a
              href="https://www.facebook.com/bhupendra.sapkota.687128/"
              target="_blank"
              >Facebook</a
            >
          </li>
          <li>
            <a href="https://www.linkedin.com/in/bhupendra0209/" target="_blank"
              >LinkedIn</a
            >
          </li>
        </ul>
      </div>
      <div class="footer-actions">
        <a
          href="${pageContext.request.contextPath}/contact"
          class="btn btn-outline"
          style="margin-top: 0.5rem"
        >
          Contact <span class="btn-icon">&rarr;</span>
        </a>
        <a
          href="${pageContext.request.contextPath}/projects"
          class="btn btn-outline"
          style="margin-top: 0.5rem"
        >
          Projects <span class="btn-icon">&rarr;</span>
        </a>
      </div>
    </div>
    <div class="footer-center">
      <p class="copyright">
        &copy; <%= new java.util.Date().getYear() + 1900 %> Bhupendra Sapkota.
      </p>
    </div>
    <div class="footer-right">
      <div class="ask-me-anything">
        <div class="ask-me-graphic">
          <!-- Placeholder for graphic -->
        </div>
        <span>Ask me anything!</span>
      </div>
    </div>
  </div>
</footer>
