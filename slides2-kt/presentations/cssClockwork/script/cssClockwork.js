(function () {
    // Mask Live-Code
    document.querySelectorAll('section.live-code pre')
        .forEach(elt => elt.addEventListener('click', () => {
            elt.classList.add('reveal');
        }));

    // currentTime
    const currentTime = () => {
        const now = new Date();
        const hour = now.getHours();
        const minute = now.getMinutes();
        const second = now.getSeconds();
        return {hour, minute, second};
    };


    const {hour, minute, second} = currentTime();
    const delay = 60 * (minute + 60 * hour) + second;
    Object.entries({hour, minute, second, delay})
        .forEach(([key, value]) =>
            document.body.style.setProperty(`--${key}`, `${value}`));

})();