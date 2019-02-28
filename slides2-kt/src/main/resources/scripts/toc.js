console.log('TOC');

const toc = Array.from(document.querySelectorAll("main > section"))
    .map(elt => `<li><a href="#${elt.id}">${elt.querySelector("header").innerHTML}</a></li>`)
    .join("\n");

const tocMenu = document.createElement("nav");
tocMenu.classList.add("toc-menu");
tocMenu.innerHTML = `
<input id="toc-toggle" type="checkbox" class="visually-hidden">
<label for="toc-toggle"></label>
<ul>${toc}</ul>`;

document.body.append(tocMenu);

tocMenu.querySelectorAll("a")
    .forEach(a =>
        a.addEventListener('click', () =>
            document.getElementById("toc-toggle").checked = false));