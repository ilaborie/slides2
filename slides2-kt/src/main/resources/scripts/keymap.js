export const keymap = mapping => {
    // console.debug('apply mapping', mapping);
    document.addEventListener('keydown', event => {
        if (event.target.type !== 'textarea') {
            const {code} = event;
            if (mapping[code]) {
                mapping[code](event);
                event.stopPropagation();
            }
        }
    });
};