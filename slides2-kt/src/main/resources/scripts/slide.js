export const keymap = mapping => {
    // console.debug('apply mapping', mapping);
    document.addEventListener('keydown', event => {
        if (event.target.type !== 'textarea') {
            const {code} = event;
            if (mapping[code]) {
                mapping[code](event);
                event.stopPropagation();
            // } else {
            //     console.debug('Skipped', {code});
            }
        }
    });
};

export const navigateTo = (selector) => {
    const elt = document.querySelector(selector);
    if (elt) {
        elt.click();
    }
};

export function defer(delay = 0) {
    return fun => setTimeout(fun, delay);
}
