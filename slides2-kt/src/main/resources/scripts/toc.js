const getToc = () => {
    const result = document.querySelector("body > .toc-menu");
    if (result) {
        return result;
    }

    const tocMenu = document.createElement("nav");
    tocMenu.classList.add("toc-menu");
    tocMenu.innerHTML = `
<input id="toc-toggle" type="checkbox" class="visually-hidden">
<label for="toc-toggle"></label>
<ul></ul>`;
    document.body.append(tocMenu);
    return tocMenu;
};


const buildToc = () => {
    const tocMenu = getToc();
    let slides = Array.from(document.querySelectorAll("main > section"));
    // if (slides.length) {
        tocMenu.querySelector('ul').innerHTML =
            slides.map(elt => `<li><a href="#${elt.id}">${elt.querySelector("header").innerHTML}</a></li>`)
                .join("\n");

        tocMenu.querySelectorAll("a")
            .forEach(a =>
                a.addEventListener('click', () =>
                    document.getElementById("toc-toggle").checked = false));
    // } else {
    //     setTimeout(buildToc, 300);
    // }
};

buildToc();
