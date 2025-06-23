// Scroll chaining: when left/right column reaches end, scroll center
function enableScrollChaining(sideSelector, centerSelector) {
  const side = document.querySelector(sideSelector);
  const center = document.querySelector(centerSelector);
  if (!side || !center) return;
  side.addEventListener(
    "wheel",
    function (e) {
      const atTop = side.scrollTop === 0;
      const atBottom =
        Math.ceil(side.scrollTop + side.clientHeight) >= side.scrollHeight;
      const scrollingDown = e.deltaY > 0;
      const scrollingUp = e.deltaY < 0;
      if ((scrollingDown && atBottom) || (scrollingUp && atTop)) {
        // Prevent side from handling the scroll, let center scroll
        e.preventDefault();
        center.scrollBy({
          top: e.deltaY,
          behavior: "auto",
        });
      }
    },
    { passive: false }
  );
}

// Disable scrolling on columns when footer is fully in view
function setColumnsScrollable(enabled) {
  const columns = document.querySelectorAll(
    ".left-column-content, .center-column-content, .right-column-content"
  );
  columns.forEach((col) => {
    if (enabled) {
      col.style.pointerEvents = "";
      col.style.overflowY = "";
      col.removeAttribute("aria-disabled");
      col.removeAttribute("tabindex");
    } else {
      col.style.pointerEvents = "none";
      col.style.overflowY = "hidden";
      col.setAttribute("aria-disabled", "true");
      col.setAttribute("tabindex", "-1");
    }
  });
}

function observeFooterVisibility() {
  const footer = document.querySelector(".main-footer");
  if (!footer) return;
  const observer = new window.IntersectionObserver(
    (entries) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting && entry.intersectionRatio >= 0.95) {
          setColumnsScrollable(false);
          console.log("Footer fully in view: columns disabled");
        } else {
          setColumnsScrollable(true);
          console.log("Footer not fully in view: columns enabled");
        }
      });
    },
    {
      root: null,
      threshold: 0.95, // Most of the footer in view
    }
  );
  observer.observe(footer);
}

document.addEventListener("DOMContentLoaded", function () {
  enableScrollChaining(".left-column-content", ".center-column-content");
  enableScrollChaining(".right-column-content", ".center-column-content");
  observeFooterVisibility();
});
