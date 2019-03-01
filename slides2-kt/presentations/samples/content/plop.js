tocMenu.querySelectorAll("a")
    .forEach(a =>
        a.addEventListener('click', () =>
            $("#toc-toggle").checked = false
        )
    );