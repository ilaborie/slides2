document.body.classList.add('line-numbers');

function highlight() {
    if (window['Prism']) {
        Prism.highlightAll();
    } else {

    setTimeout(highlight, 500);
    }
}
highlight();