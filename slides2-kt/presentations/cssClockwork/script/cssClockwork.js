(function () {
    document.querySelectorAll('section.live-code pre')
        .forEach(elt => elt.addEventListener('click', () => {
            elt.classList.add('reveal');
        }));
})();