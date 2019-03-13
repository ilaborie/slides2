import {keymap, navigateTo} from './slide';

const tocGrid = document.getElementById('tocGrid');

document.querySelector('label[for=tocGrid]').style.display = 'inline-block';

// Navigate throw Slide click
const navigateToSlide = id => () => {
    if (tocGrid && tocGrid.checked) {
        // console.debug('navigateToSlide', id);
        tocGrid.checked = false;
        navigateTo(`a[href='#${id}']`);
        document.getElementById(id).scrollIntoView();
    }
    return false;
};

document.querySelectorAll("main > section")
    .forEach(slide => slide.addEventListener('click', navigateToSlide(slide.id)));


keymap({
    Escape: () => {
        const tocGrid = document.getElementById('tocGrid');
        if (tocGrid) {
            tocGrid.checked = !tocGrid.checked;
        }
    }
});