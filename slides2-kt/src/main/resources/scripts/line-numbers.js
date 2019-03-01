Array.from(document.querySelectorAll("section pre"))
    .forEach(elt => {
        elt.classList.add('line-numbers');
    });