(function () {
    // Mask Live-Code
    document.querySelectorAll('section.live-code pre')
        .forEach(elt => elt.addEventListener('click', () => {
            elt.classList.add('reveal');
        }));

    // currentTime
    const currentTime = () => {
        const now = new Date();
        const h = now.getHours();
        const min = now.getMinutes();
        const sec = now.getSeconds();
        return { h, min, sec };
    };

    // Set --hour, --minute and --second
    // const setHourMinuteSecond = () => (elt) => {
    //     const {h, min, sec} = currentTime();
    //     elt.style.setProperty("--hour", "" + h);
    //     elt.style.setProperty("--minute", "" + min);
    //     elt.style.setProperty("--second", "" + sec);
    // };

    // setInterval(setHourMinuteSecond(document.body), 1000 /* 1s */);

    const { h, min, sec } = currentTime();

    const delay = 60 * (min + 60 * h) + sec;
    document.body.style.setProperty("--delay", "" + delay);

})();