import {keymap, navigateTo} from './slide';

const registerToc = () => {
    // Auto Close menu
    const tocToggle = document.getElementById('tocToggle');
    document.querySelectorAll("body > .toc-menu a")
        .forEach(a => a.addEventListener('click', () => tocToggle.checked = false));
};

const registerGrid = () => {
    // Navigate throw Slide click
    const tocGrid = document.getElementById('tocGrid');

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
};

registerToc();
registerGrid();

keymap({
    Escape: () => {
        const tocGrid = document.getElementById('tocGrid');
        if (tocGrid) {
            tocGrid.checked = !tocGrid.checked;
        }
    }
});