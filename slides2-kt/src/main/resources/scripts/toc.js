import {navigateTo} from './navigate';

const getToc = () => {
    const tocMenu = document.querySelector("body > .toc-menu");
    tocMenu.innerHTML = `
<input id="tocToggle" type="checkbox" class="visually-hidden">
<label for="tocToggle" class="toggle"></label>
<label for="tocGrid" class="grid"></label>
<ul></ul>`;
    document.body.append(tocMenu);
    return tocMenu;
};

const buildToc = () => {
    const tocMenu = getToc();
    let slides = Array.from(document.querySelectorAll("main > section"));

    tocMenu.querySelector('ul').innerHTML =
        slides.map(elt => {
            const classes =
                elt.classList.contains("cover") ? ` class="cover"` :
                    (elt.classList.contains("part") ? ` class="part"` : '');

            const txt = elt.querySelector("header").textContent;
            return `<li${classes}><a href="#${elt.id}">${txt}</a></li>`;
        })
            .join("\n");

    // Auto Close menu
    const tocToggle = document.getElementById('tocToggle');
    tocMenu.querySelectorAll("a")
        .forEach(a =>
            a.addEventListener('click', () => tocToggle.checked = false));

    // Navigate throw Slide click
    const tocGrid = document.getElementById('tocGrid');
    slides.forEach(slide =>
        slide.addEventListener('click', () => {
            if (tocGrid && tocGrid.checked) {
                tocGrid.checked = false;
                navigateTo(`a[href='${slide.id}']`);
            }
            return false;
        })
    );
};

buildToc();
