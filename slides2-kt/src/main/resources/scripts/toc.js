const tocToggle = document.getElementById('tocToggle');

// Auto Close menu
document.querySelectorAll("body > .toc-menu ul a")
    .forEach(a => a.addEventListener('click', () => tocToggle.checked = false));
